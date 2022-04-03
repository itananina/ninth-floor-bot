package com.v4bot.ninth.floor.services;

import com.v4bot.ninth.floor.data.Context;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReplyService implements ReplyProcessor {

    private final Map<String, ReplyProcessor> replyProcessorsMap;

    @Override
    public void processReply(Context context) {
        Optional.ofNullable(replyProcessorsMap.get(context.getPlayer().getReplyType().getCommand()))
                .ifPresent(processor-> processor.processReply(context));
    }
}
