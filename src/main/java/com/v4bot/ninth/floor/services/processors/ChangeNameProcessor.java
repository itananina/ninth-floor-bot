package com.v4bot.ninth.floor.services.processors;

import com.v4bot.ninth.floor.data.Context;
import com.v4bot.ninth.floor.entities.PlayableCharacter;
import com.v4bot.ninth.floor.enums.Command;
import com.v4bot.ninth.floor.enums.ReplyType;
import com.v4bot.ninth.floor.services.CharactersService;
import com.v4bot.ninth.floor.services.ChatPlayersService;
import com.v4bot.ninth.floor.services.CommandProcessor;
import com.v4bot.ninth.floor.services.ReplyProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component(Command.CHANGENAME)
@RequiredArgsConstructor
public class ChangeNameProcessor implements CommandProcessor, ReplyProcessor {
    private final ChatPlayersService chatPlayersService;
    private final CharactersService charactersService;

    @Override
    public void processCommand(Context context) {
        chatPlayersService.setPlayerReplyingFlag(context.getPlayer(), true, ReplyType.ChangeName);
        context.getResponse().setText("Какое будет имя?");
    }

    @Override
    public void processReply(Context context) {
        PlayableCharacter character = context.getPlayer().getCharacter();
        character.setName(context.getMessageText());
        charactersService.saveCharacter(character);
        chatPlayersService.setPlayerReplyingFlag(context.getPlayer(), false, null);
        charactersService.getPlayableCharacterInfoByPlayerUsername(context, "Имя обновлено!");
    }
}
