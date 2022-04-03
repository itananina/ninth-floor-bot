package com.v4bot.ninth.floor.services.processors;

import com.v4bot.ninth.floor.data.Context;
import com.v4bot.ninth.floor.enums.MissionStatus;
import com.v4bot.ninth.floor.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetCharacterProcessor implements CommandProcessor {
    private final MissionService missionService;
    private final CharactersService charactersService;
    private final ImagesService imagesService;
    private final ButtonsService buttonsService;

    @Override
    public void processCommand(Context context) {
        MissionStatus currentStatus = missionService.getMissionStatusForChat(context.getChatId());

        if(MissionStatus.Absent.equals(currentStatus) && context.getPlayer().getCharacter()==null) {
            charactersService.getNewPlayableCharacter(context);
        } else {
            String description = charactersService.getPlayableCharacterInfoByPlayerUsername(context.getPlayer().getUsername());
            if(description!=null && description.length()>0) {
                imagesService.setPhotoWithCaptionByFilesPath(context.getImgResponse(),
                        "archetypes/"+context.getPlayer().getCharacter().getArchetype().getCode(),
                        "Вы уже получили своего персонажа! \n" + description);
            } else {
                log.info("Ошибка при поиске перснажа игрока {}", context.getPlayer().getUsername());
            }
        }
        buttonsService.setReplyButtonsAfterGetCharacter(context.getImgResponse());
    }
}
