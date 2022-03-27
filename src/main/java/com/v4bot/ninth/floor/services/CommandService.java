package com.v4bot.ninth.floor.services;

import com.v4bot.ninth.floor.entities.Player;
import com.v4bot.ninth.floor.enums.MissionStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommandService {

    private final ChatPlayersService chatPlayersService;
    private final CharactersService charactersService;
    private final ButtonsService buttonsService;
    private final MissionService missionService;
    private final ImagesService imagesService;

    public void processCommand(Message input, SendMessage output, SendPhoto imgOutput) throws Exception {
        chatPlayersService.upsertChatMembersByMessageInfo(input);
        MissionStatus currentStatus = missionService.getMissionStatusForChat(input.getChatId());

        //todo присобачить проверку через Command enum
        switch (input.getText().split("\\s+", 1)[0].toLowerCase(Locale.ROOT)) {
            case "/start":
                processStartCommand(input, output, imgOutput, currentStatus);
                break;
            case "/getcharacter":
                processGetCharacterCommand(input, output, imgOutput, currentStatus);
                break;
            default:
                break;
        }

//            buttonsService.setButtons(message);
    }

    private void processGetCharacterCommand(Message input, SendMessage output, SendPhoto imgOutput, MissionStatus currentStatus) {
        //todo Player ext User чтобы запихнуть его в контекст
        Player player = chatPlayersService.findPlayerByUsername(input.getFrom().getUserName());
        if(MissionStatus.Absent.equals(currentStatus) && player.getCharacter()==null) {
            charactersService.getNewPlayableCharacter(input, imgOutput);
        } else {
            String description = charactersService.getPlayableCharacterInfoByPlayerUsername(input.getFrom().getUserName());
            if(description!=null && description.length()>0) {
                output.setText("Вы уже получили своего персонажа! \n" + description);
            } else {
                log.info("Ошибка при поиске перснажа игрока {}", input.getFrom());
            }
        }
    }

    private void processStartCommand(Message input, SendMessage output, SendPhoto imgOutput, MissionStatus currentStatus) throws IOException, URISyntaxException {
        if(MissionStatus.Absent.equals(currentStatus)) {
            File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("files/setting.txt")).getFile());
            String caption = FileUtils.readFileToString(file, "UTF-8");

            imagesService.setPhotoWithCaptionByFilesPath(imgOutput, "setting", caption);
            buttonsService.setReplyButtonsAfterStart(imgOutput);
        } else {
            output.setText("Игра уже начата!"); //todo инфо об игре
        }
    }
}
