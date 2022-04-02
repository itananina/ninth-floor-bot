package com.v4bot.ninth.floor.controllers;

import com.v4bot.ninth.floor.data.Context;
import com.v4bot.ninth.floor.services.CommandService;
import com.v4bot.ninth.floor.services.ContextService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Slf4j
@Component
@RequiredArgsConstructor
public class MessageController extends TelegramLongPollingBot {

    private final ContextService contextService;
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

            Context context = contextService.setContext(input); //заполняем контекст

            if(context.getMessageText().startsWith("/")) {
                try {
                    commandService.processCommand(context);
                } catch (Exception e) {
                    log.info("Ошибка при обработке команды {}", context.getMessageText(), e);
                }
            } else {
                //todo отображать кнопки
            }

            try {
                if(context.getResponse().getText()!=null) {
                    execute(context.getResponse());
                } else if(context.getImgResponse().getPhoto()!=null) {
                    execute(context.getImgResponse());
                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClosing() {
        super.onClosing();
    }
}
