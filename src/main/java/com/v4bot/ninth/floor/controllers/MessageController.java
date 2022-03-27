package com.v4bot.ninth.floor.controllers;

import com.v4bot.ninth.floor.services.ButtonsService;
import com.v4bot.ninth.floor.services.CharactersService;
import com.v4bot.ninth.floor.services.CommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.v4bot.ninth.floor.services.ChatPlayersService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageController extends TelegramLongPollingBot {

    private final ChatPlayersService chatPlayersService;
    private final CharactersService charactersService;
    private final ButtonsService buttonsService;
    private final CommandService commandService;

    @Override
    public String getBotUsername() {
        return "eera4t0libot";
    }

    @Override
    public String getBotToken() {
        return "2140274092:AAHypMpLHv6PdrU0eSIm_CCLS7oY51NZzqU";
    }

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            final Message input = update.getMessage();

            SendMessage message = new SendMessage(); // Create a SendMessage and SendPhoto objects with mandatory fields
            message.setChatId(input.getChatId().toString());
            SendPhoto image = new SendPhoto();
            image.setChatId(input.getChatId().toString());

            if(input.getText().startsWith("/")) {
                try {
                    commandService.processCommand(input, message, image);
                } catch (Exception e) {
                    log.info("Ошибка при обработке команды /start");
                }

            } else {
                //todo отображать кнопки
            }

//            chatPlayersService.upsertChatMembersByMessageInfo(input);
//
//            charactersService.getNewPlayableCharacter(input, message);
//            buttonsService.setButtons(message);

            try {
                if(message.getText()!=null) {
                    execute(message);
                } else if(image.getPhoto()!=null) {
                    execute(image);
                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public int getRandomNumber(int max) {
        return (int) (Math.random() * max);
    }

    @Override
    public void onClosing() {
        super.onClosing();
    }
}
