package com.example.demobot.service.impl;

import com.example.demobot.config.BotConfig;
import com.example.demobot.model.DepartmentName;
import com.example.demobot.model.LectorDegree;
import com.example.demobot.repository.DepartmentDAO;
import com.example.demobot.service.TelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TelegramBotImpl extends TelegramLongPollingBot implements TelegramBot {

    final BotConfig botConfig;
    final DepartmentDAO repository;

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()){
            String message =  update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            Optional<DepartmentName> departmentName = parseForStatistic(message);

            if (departmentName.isPresent()) {
                startCommand(chatId, responseStatistic(departmentName.get()));
            } else {
                sendMessage(chatId, "Sorry command don't use");
            }

        }
    }

    @Override
    public String responseStatistic(DepartmentName name) {
        StringBuilder stringBuilder = new StringBuilder();
        for (LectorDegree degree:
             lectorDegrees) {
            Long count = repository.statistic(name, degree);
            stringBuilder.append(degree.toString().toLowerCase(Locale.ROOT)).append("-").append(count).append(", ");
        }
        stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), "");
        return stringBuilder.toString();
    }

    Optional<DepartmentName> parseForStatistic(String message) {
        String[] array;
        Optional<DepartmentName> name = Optional.empty();

        try {
            array = message.split(" ");
            name = Arrays.stream(DepartmentName.values()).filter(v -> String.valueOf(v).equals(array[1].toUpperCase(Locale.ROOT))).findFirst();
        } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
            log.error(ex.getMessage());
        }

        return name;
    }


    private void startCommand(long id, String name) {
        sendMessage(id, name);
    }

    private void sendMessage(long id, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(id));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException ex) {
            log.error(ex.getMessage());
        }
    }

}
