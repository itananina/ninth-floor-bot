package com.v4bot.ninth.floor.services.processors;

import com.v4bot.ninth.floor.data.Context;
import com.v4bot.ninth.floor.enums.Command;
import com.v4bot.ninth.floor.enums.MissionStatus;
import com.v4bot.ninth.floor.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component(Command.GETCHARACTER)
@RequiredArgsConstructor
@Slf4j
public class GetCharacterProcessor implements CommandProcessor {
    private final MissionService missionService;
    private final CharactersService charactersService;
    private final ButtonsService buttonsService;

    @Override
    public void processCommand(Context context) {
        MissionStatus currentStatus = missionService.getMissionStatusForChat(context.getChatId());

        if(MissionStatus.Absent.equals(currentStatus) && context.getPlayer().getCharacter()==null) {
            charactersService.getNewPlayableCharacter(context);
        } else {
            charactersService.getPlayableCharacterInfoByPlayerUsername(context, "Вы уже получили своего персонажа!");
        }
        buttonsService.setReplyButtonsAfterGetCharacter(context.getImgResponse());
    }
}
