package com.v4bot.ninth.floor.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Command {
    Start("/start", "Начать игру"),
    GetCharacter("/getCharacter", "Получить игрового персонажа"),
    Help("/help","Справка");

    private String command;
    private String description;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
