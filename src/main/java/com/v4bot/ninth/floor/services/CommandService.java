package com.v4bot.ninth.floor.services;

import com.v4bot.ninth.floor.data.Context;
import com.v4bot.ninth.floor.enums.Command;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommandService implements CommandProcessor {

    private final List<CommandProcessor> processorList;

    @Override
    public void processCommand(Context context) {
        Arrays.stream(Command.values())
                .filter(command -> context.getMessageText().split("\\s+", 1)[0].toLowerCase(Locale.ROOT).equals(command.getCommand().toLowerCase(Locale.ROOT)))
                .findFirst()
                .flatMap(command -> processorList.stream()
                        .filter(processor -> command.getCommandProcessor().equals(processor.getClass()))
                        .findFirst())
                .ifPresent(processor -> processor.processCommand(context));

    }
}
