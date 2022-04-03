package com.v4bot.ninth.floor.enums;

import com.v4bot.ninth.floor.services.processors.ChangeNameProcessor;
import com.v4bot.ninth.floor.services.processors.GetCharacterProcessor;
import com.v4bot.ninth.floor.services.processors.HelpProcessor;
import com.v4bot.ninth.floor.services.processors.StartProcessor;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public enum Command {
    Start("/start", "Начать игру", StartProcessor.class),
    GetCharacter("/getCharacter", "Получить игрового персонажа", GetCharacterProcessor.class),
    Help("/help","Справка", HelpProcessor.class),
    ChangeName("/changeName", "Задать имя персонажу", ChangeNameProcessor.class);

    private String command;
    private String description;
    private Class commandProcessor;

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
