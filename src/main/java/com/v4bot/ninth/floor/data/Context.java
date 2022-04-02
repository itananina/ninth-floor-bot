package com.v4bot.ninth.floor.data;

import com.v4bot.ninth.floor.entities.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

@Data
@AllArgsConstructor
public class Context {
    private Player player;
    private String messageText;
    private Long chatId;
    private SendMessage response;
    private SendPhoto imgResponse;
}
