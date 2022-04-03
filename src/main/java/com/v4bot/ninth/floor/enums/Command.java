package com.v4bot.ninth.floor.enums;

import com.v4bot.ninth.floor.services.CommandProcessor;
import com.v4bot.ninth.floor.services.processors.ChangeNameProcessor;
import com.v4bot.ninth.floor.services.processors.GetCharacterProcessor;
import com.v4bot.ninth.floor.services.processors.HelpProcessor;
import com.v4bot.ninth.floor.services.processors.StartProcessor;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public enum Command {
    Start(Command.START, "Начать игру", StartProcessor.class),
    GetCharacter(Command.GETCHARACTER, "Получить игрового персонажа", GetCharacterProcessor.class),
    Help(Command.HELP,"Справка", HelpProcessor.class),
    ChangeName(Command.CHANGENAME, "Задать имя персонажу", ChangeNameProcessor.class);

    private String command;
    private String description;
    private Class commandProcessor;

    public static final String START = "/start";
    public static final String GETCHARACTER = "/getcharacter";
    public static final String HELP = "/help";
    public static final String CHANGENAME = "/changename";

    public Class getCommandProcessor() {
        return commandProcessor;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

}
