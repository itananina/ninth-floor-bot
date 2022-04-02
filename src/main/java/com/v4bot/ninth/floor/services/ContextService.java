package com.v4bot.ninth.floor.services;

import com.v4bot.ninth.floor.data.Context;
import com.v4bot.ninth.floor.entities.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@RequiredArgsConstructor
public class ContextService {
    private final ChatPlayersService chatPlayersService;

    public Context setContext(Message input) {
        SendMessage message = new SendMessage(); // Create a SendMessage and SendPhoto objects with mandatory fields
        message.setChatId(input.getChatId().toString());
        SendPhoto image = new SendPhoto();
        image.setChatId(input.getChatId().toString());

        Player player = chatPlayersService.upsertChatPlayerByMessageInfo(input.getFrom(), input.getChatId());
        return new Context(player, input.getText(), input.getChatId(), message, image);
    }
}
