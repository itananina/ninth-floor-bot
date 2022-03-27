package com.v4bot.ninth.floor.services;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class CommandService {


    public void processCommand(Message input, SendMessage output, SendPhoto imgOutput) throws Exception {
        switch (input.getText().split("\\s+", 1)[0]) {
            case "/start" :
                processStartCommand(input, imgOutput);
                break;
            default:
                break;
        }
    }

    private void processStartCommand(Message input, SendPhoto imgOutput) throws IOException, URISyntaxException {
        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("files/setting.txt")).getFile());
        String data = FileUtils.readFileToString(file, "UTF-8");

        byte[] imageContent = Files.readAllBytes(Paths.get( ClassLoader.getSystemResource("files/setting.png").toURI()));
        ByteArrayInputStream bais = new ByteArrayInputStream(imageContent);
        InputFile photoSrc = new InputFile(bais, "setting");

        imgOutput.setPhoto(photoSrc);
        imgOutput.setCaption(data);
    }
}
