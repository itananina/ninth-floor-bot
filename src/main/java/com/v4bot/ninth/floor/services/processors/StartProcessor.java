package com.v4bot.ninth.floor.services.processors;

import com.v4bot.ninth.floor.data.Context;
import com.v4bot.ninth.floor.enums.Command;
import com.v4bot.ninth.floor.enums.MissionStatus;
import com.v4bot.ninth.floor.services.ButtonsService;
import com.v4bot.ninth.floor.services.CommandProcessor;
import com.v4bot.ninth.floor.services.ImagesService;
import com.v4bot.ninth.floor.services.MissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Objects;

@Component(Command.START)
@RequiredArgsConstructor
@Slf4j
public class StartProcessor implements CommandProcessor {
    private final MissionService missionService;
    private final ImagesService imagesService;
    private final ButtonsService buttonsService;

    @Override
    public void processCommand(Context context) {
        MissionStatus currentStatus = missionService.getMissionStatusForChat(context.getChatId());

        if(MissionStatus.Absent.equals(currentStatus)) {
            try {
                File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("files/setting.txt")).getFile());
                String caption = FileUtils.readFileToString(file, "UTF-8");
                imagesService.setPhotoWithCaptionByFilesPath(context.getImgResponse(), "setting", caption);
                buttonsService.setReplyButtonsAfterStart(context.getImgResponse());
            } catch (Exception e) {
                log.info("Ошибка при попытке подготовить settings" , e);
            }
        } else {
            context.getResponse().setText("Игра уже начата!"); //todo инфо об игре
        }
    }
}
