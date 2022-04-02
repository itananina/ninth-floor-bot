package com.v4bot.ninth.floor.services;

import com.v4bot.ninth.floor.data.Context;
import com.v4bot.ninth.floor.entities.PlayableCharacter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final CharactersService charactersService;
    private final ChatPlayersService chatPlayersService;

    public void processReply(Context context) {
        switch (context.getPlayer().getReplyType()) {
            case ChangeName:
                processChangeNameReply(context);
                break;
            default:
                break;
        }
    }

    private void processChangeNameReply(Context context) {
        PlayableCharacter character = context.getPlayer().getCharacter();
        character.setName(context.getMessageText());
        charactersService.saveCharacter(character);

        chatPlayersService.setPlayerReplyingFlag(context.getPlayer(), false, null);
    }
}
