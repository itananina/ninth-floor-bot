package com.v4bot.ninth.floor.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ImagesService {

    public void setPhotoWithCaptionByFilesPath(SendPhoto imgOutput, String path, String caption) {
        try {
            byte[] imageContent = Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("files/" + path+".png").toURI()));
            ByteArrayInputStream bais = new ByteArrayInputStream(imageContent);
            InputFile photoSrc = new InputFile(bais, "output");

            imgOutput.setPhoto(photoSrc);
            imgOutput.setCaption(caption);
        } catch (Exception e) {
            log.info("Ошибка при попытке подготовить изображение к отправке", e);
        }
    }
}
