package com.v4bot.ninth.floor.services;

import com.v4bot.ninth.floor.data.Context;
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

    private final Map<String, CommandProcessor> processorMap;

    @Override
    public void processCommand(Context context) {
        Optional.ofNullable(processorMap.get(context.getMessageText().split("\\s+", 1)[0].toLowerCase(Locale.ROOT)))
                .ifPresent(processor-> processor.processCommand(context));
    }
}
