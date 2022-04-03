package com.v4bot.ninth.floor.services.processors;

import com.v4bot.ninth.floor.data.Context;
import com.v4bot.ninth.floor.enums.ReplyType;
import com.v4bot.ninth.floor.services.ChatPlayersService;
import com.v4bot.ninth.floor.services.CommandProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChangeNameProcessor implements CommandProcessor {
    private final ChatPlayersService chatPlayersService;

    @Override
    public void processCommand(Context context) {
        chatPlayersService.setPlayerReplyingFlag(context.getPlayer(), true, ReplyType.ChangeName);
        context.getResponse().setText("Какое будет имя?");
    }
}
