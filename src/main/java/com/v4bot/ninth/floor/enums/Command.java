package com.v4bot.ninth.floor.enums;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public enum Command {
    Start(Command.START, "Начать игру"),
    GetCharacter(Command.GETCHARACTER, "Получить игрового персонажа"),
    Help(Command.HELP,"Справка"),
    ChangeName(Command.CHANGENAME, "Задать имя персонажу");

    private String command;
    private String description;

    public static final String START = "/start";
    public static final String GETCHARACTER = "/getcharacter";
    public static final String HELP = "/help";
    public static final String CHANGENAME = "/changename";

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

}
