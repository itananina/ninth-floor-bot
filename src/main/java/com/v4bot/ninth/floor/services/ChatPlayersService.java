package com.v4bot.ninth.floor.services;

import com.v4bot.ninth.floor.entities.Chat;
import com.v4bot.ninth.floor.entities.PlayableCharacter;
import com.v4bot.ninth.floor.entities.Player;
import com.v4bot.ninth.floor.enums.ReplyType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    public Player upsertChatPlayerByMessageInfo(User user, Long chatId) {
        Player player = findOrCreatePlayerByUser(user); //ищем игрока

        if(!isNull(player.getChatSet()) && player.getChatSet().stream().noneMatch(chat -> chat.getId().equals(chatId))) {
            Chat chat = findOrCreateChatById(chatId, user.getUserName()); //ищем чат
            addPlayerToChat(player, chat); //связываем
        }
        return player;
    }

    public Player findOrCreatePlayerByUser(User user) {
        return playersRepository.findPlayerByUsername(user.getUserName())
                .orElseGet(()->playersRepository.save(new Player(user.getUserName(), user.getFirstName(), user.getLastName())));
    }

    public Chat findOrCreateChatById(Long chatId, String primaryUserName) {
        return chatsRepository.findChatById(chatId)
                .orElseGet(()->chatsRepository.save(new Chat(chatId,primaryUserName)));
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

    public void setPlayerReplyingFlag(Player player, Boolean isReplying, ReplyType replyType) {
        player.setIsReplying(isReplying);
        player.setReplyType(replyType);
        savePlayer(player);
    }
}
