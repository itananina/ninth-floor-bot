package com.v4bot.ninth.floor.services;

import com.v4bot.ninth.floor.entities.Chat;
import com.v4bot.ninth.floor.entities.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import com.v4bot.ninth.floor.repositories.ChatsRepository;
import com.v4bot.ninth.floor.repositories.PlayersRepository;

import javax.transaction.Transactional;

import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatPlayersService {
    private final PlayersRepository playersRepository;
    private final ChatsRepository chatsRepository;

    public void upsertChatMembersByMessageInfo(Message input) {
        Player player = findOrCreatePlayerByUser(input.getFrom()); //ищем игрока

        if(!isNull(player.getChatSet()) && player.getChatSet().stream().noneMatch(chat -> chat.getId().equals(input.getChatId()))) {
            Chat chat = findOrCreateChatById(input.getChat()); //ищем чат
            addPlayerToChat(player, chat); //связываем
        }
    }

    public Player findOrCreatePlayerByUser(User user) {
        return playersRepository.findPlayerByUsername(user.getUserName())
                .orElseGet(()->playersRepository.save(new Player(user.getUserName(), user.getFirstName(), user.getLastName())));
    }

    public Chat findOrCreateChatById(org.telegram.telegrambots.meta.api.objects.Chat chat) {
        return chatsRepository.findChatById(chat.getId())
                .orElseGet(()->chatsRepository.save(new Chat(chat.getId(),chat.getUserName())));
    }

    public void addPlayerToChat(Player player, Chat chat) {
        Player playerExt = playersRepository.findPlayerByIdExt(player.getId()).stream()
                .findAny()
                .orElseThrow(()-> new RuntimeException("Не найден игрок с id="+player.getId()));
        playerExt.getChatSet().add(chat); //дубля не будет
    }

    public List<Player> findPlayersByChatId(Long chatId) {
        return playersRepository.findPlayersByChatId(chatId);
    }

    public Player findPlayerByUsername(String username) {
        return playersRepository.findPlayerByUsername(username)
                .orElseThrow(()-> new RuntimeException("Не найден игрок с username="+username));
    }

    public void savePlayer(Player player) {
        playersRepository.save(player);
    }
}
