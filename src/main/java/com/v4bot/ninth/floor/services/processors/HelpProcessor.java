package com.v4bot.ninth.floor.services.processors;

import com.v4bot.ninth.floor.data.Context;
import com.v4bot.ninth.floor.services.CommandProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HelpProcessor implements CommandProcessor {

    @Override
    public void processCommand(Context context) {
        //todo help processor
    }
}
