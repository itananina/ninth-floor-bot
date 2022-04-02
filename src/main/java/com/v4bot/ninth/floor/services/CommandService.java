package com.v4bot.ninth.floor.services;

import com.v4bot.ninth.floor.data.Context;
import com.v4bot.ninth.floor.enums.MissionStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommandService {

    private final CharactersService charactersService;
    private final ButtonsService buttonsService;
    private final MissionService missionService;
    private final ImagesService imagesService;

    public void processCommand(Context context) throws Exception {
        MissionStatus currentStatus = missionService.getMissionStatusForChat(context.getChatId());

        //todo присобачить проверку через Command enum
        switch (context.getMessageText().split("\\s+", 1)[0].toLowerCase(Locale.ROOT)) {
            case "/start":
                processStartCommand(context, currentStatus);
                break;
            case "/getcharacter":
                processGetCharacterCommand(context, currentStatus);
                break;
            default:
                break;
        }
//            buttonsService.setButtons(message);
    }

    private void processGetCharacterCommand(Context context, MissionStatus currentStatus) {
        if(MissionStatus.Absent.equals(currentStatus) && context.getPlayer().getCharacter()==null) {
            charactersService.getNewPlayableCharacter(context);
        } else {
            String description = charactersService.getPlayableCharacterInfoByPlayerUsername(context.getPlayer().getUsername());
            if(description!=null && description.length()>0) {
                context.getResponse().setText("Вы уже получили своего персонажа! \n" + description);
            } else {
                log.info("Ошибка при поиске перснажа игрока {}", context.getPlayer().getUsername());
            }
        }
    }

    private void processStartCommand(Context context, MissionStatus currentStatus) throws IOException {
        if(MissionStatus.Absent.equals(currentStatus)) {
            File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("files/setting.txt")).getFile());
            String caption = FileUtils.readFileToString(file, "UTF-8");

            imagesService.setPhotoWithCaptionByFilesPath(context.getImgResponse(), "setting", caption);
            buttonsService.setReplyButtonsAfterStart(context.getImgResponse());
        } else {
            context.getResponse().setText("Игра уже начата!"); //todo инфо об игре
        }
    }
}
