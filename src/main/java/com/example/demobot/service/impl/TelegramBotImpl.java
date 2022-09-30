package com.example.demobot.service.impl;

import com.example.demobot.config.BotConfig;
import com.example.demobot.model.DepartmentName;
import com.example.demobot.model.Lector;
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

            Optional<DepartmentName> statisticResponse = parseMessage(message,1);
            Optional<DepartmentName> headOfDepartmentResponse = parseMessage(message,5);
            Optional<DepartmentName> avgSalaryResponse = parseMessage(message,7);

            if (statisticResponse.isPresent()) {
                startCommand(chatId, responseStatistic(statisticResponse.get()));
            }
            else if (headOfDepartmentResponse.isPresent()) {
                startCommand(chatId, responseHeadOfDepartments(headOfDepartmentResponse.get()));
            }
            else if (avgSalaryResponse.isPresent()) {
                startCommand(chatId, responseAvgSalaryOfDepartmentName(avgSalaryResponse.get()));
            }
            else {
                sendMessage(chatId, "Sorry, but this command don't find");
            }

        }
    }

    @Override
    public String responseHeadOfDepartments(DepartmentName name) {
        StringBuilder stringBuilder = new StringBuilder();
        Lector lector;
        try {
            lector = repository.headOfDepartment(name).get();
            stringBuilder.append("Head of ").append(name).append(" department is ").append(lector.getName()).append(" ").append(lector.getSurname());
        } catch (NoSuchElementException ex) {
            log.error(ex.getMessage());
        }

        return stringBuilder.toString();
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

    @Override
    public String responseAvgSalaryOfDepartmentName(DepartmentName name) {
        StringBuilder stringBuilder = new StringBuilder();
        Long avgSalary = repository.avgSalaryOfDepartmentName(name);
        stringBuilder.append("The average salary of ").append(name).append(" is ").append(avgSalary);

        return stringBuilder.toString();
    }

    Optional<DepartmentName> parseMessage(String message, int number) {
        String[] array;
        Optional<DepartmentName> name = Optional.empty();
        try {
            array = message.split(" ");
            name = Arrays.stream(DepartmentName.values()).filter(v -> String.valueOf(v).equals(array[number].toUpperCase(Locale.ROOT))).findFirst();
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
