package com.v4bot.ninth.floor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import com.v4bot.ninth.floor.controllers.MessageController;

//https://github.com/rubenlagus/TelegramBots/wiki/Getting-Started
@SpringBootApplication
public class MainApp {

    public static void main(String[] args) throws TelegramApiException {
        ApplicationContext app = SpringApplication.run(MainApp.class, args); //init the context
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        //a single instance can handle different bots but each bot can run only once
        // (Telegram doesn't support concurrent calls to GetUpdates)
        botsApi.registerBot(app.getBean(MessageController.class));
    }
}
