package com.v4bot.ninth.floor.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReplyType {
    ChangeName(Command.CHANGENAME);

    private String command;

    public String getCommand() {
        return command;
    }
}
