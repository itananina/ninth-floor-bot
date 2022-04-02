package com.v4bot.ninth.floor.services;

import com.v4bot.ninth.floor.enums.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ButtonsService {

    public void setReplyButtonsAfterStart(SendPhoto image) {
        List<Command> commands = new ArrayList<>(Arrays.asList(Command.GetCharacter, Command.Help));
        setButtons(image, commands);
    }

    public void setButtons(SendMessage sendMessage, List<Command> commands) {
        sendMessage.setReplyMarkup(prepareReplyKeyboardMarkup(commands));
    }

    public void setButtons(SendPhoto image, List<Command> commands) {
        image.setReplyMarkup(prepareReplyKeyboardMarkup(commands));
    }


    private ReplyKeyboardMarkup prepareReplyKeyboardMarkup(List<Command> commands) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        commands.forEach(command -> {
                    KeyboardButton button = new KeyboardButton();
                    button.setText(command.getCommand());
                    keyboardFirstRow.add(button);
                }
        );
        keyboard.add(keyboardFirstRow); //todo больше одного ряда
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }
}
