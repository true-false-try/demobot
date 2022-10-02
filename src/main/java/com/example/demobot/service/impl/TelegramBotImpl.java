package com.example.demobot.service.impl;

import com.example.demobot.config.BotConfig;
import com.example.demobot.model.DepartmentName;
import com.example.demobot.model.Lector;
import com.example.demobot.model.LectorDegree;
import com.example.demobot.repository.DepartmentDAO;
import com.example.demobot.repository.LectorDAO;
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
    final DepartmentDAO repositoryDepartment;
    final LectorDAO repositoryLector;

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

            int result = isTheSame(message);
            try {

                switch (result) {
                    case 1:
                        Optional<DepartmentName> headOfDepartmentResponse = parseMessageWithDepartmentName(message, 5);
                        startCommand(chatId, responseHeadOfDepartments(headOfDepartmentResponse.get()));
                        break;
                    case 2:
                        Optional<DepartmentName> statisticResponse = parseMessageWithDepartmentName(message, 1);
                        startCommand(chatId, responseStatistic(statisticResponse.get()));
                        break;
                    case 3:
                        Optional<DepartmentName> avgSalaryResponse = parseMessageWithDepartmentName(message, 7);
                        startCommand(chatId, responseAvgSalaryOfDepartmentName(avgSalaryResponse.get()));
                        break;

                    case 4:
                        Optional<DepartmentName> countEmployee = parseMessageWithDepartmentName(message, 5);
                        startCommand(chatId, responseCountEmployeeForDepartmentName(countEmployee.get()));
                        break;
                    case 5:
                        startCommand(chatId, responseGlobalSearch(parseMessageWithTemplate(message, 3)));
                        break;

                    default:
                        sendMessage(chatId, "Sorry, but this command don't find");

                }
            } catch (NoSuchElementException ex) {
                log.error(ex.getMessage());
                sendMessage(chatId, ex.getMessage());
            }
        }
    }

    @Override
    public String responseHeadOfDepartments(DepartmentName name) {
        StringBuilder stringBuilder = new StringBuilder();
        Lector lector = repositoryDepartment.headOfDepartment(name).get();
        stringBuilder.append("Head of ").append(name).append(" department is ").append(lector.getName()).append(" ").append(lector.getSurname());

        return stringBuilder.toString();
    }

    @Override
    public String responseStatistic(DepartmentName name) {
        StringBuilder stringBuilder = new StringBuilder();
        for (LectorDegree degree:
             lectorDegrees) {
            Long count = repositoryDepartment.statistic(name, degree);
            stringBuilder.append(degree.toString().toLowerCase(Locale.ROOT)).append("-").append(count).append(", ");
        }
        stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), "");
        return stringBuilder.toString();
    }

    @Override
    public String responseAvgSalaryOfDepartmentName(DepartmentName name) {
        StringBuilder stringBuilder = new StringBuilder();
        Long avgSalary = repositoryDepartment.avgSalaryOfDepartmentName(name);
        stringBuilder.append("The average salary of ").append(name).append(" is ").append(avgSalary);

        return stringBuilder.toString();
    }

    @Override
    public String responseCountEmployeeForDepartmentName(DepartmentName name) {
        return String.valueOf(repositoryDepartment.countForEmployeeForDepartmentName(name));
    }

    @Override
    public String responseGlobalSearch(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<Long,Lector> lectorsMapSurname = new TreeMap<>();

        List<Lector> lectorsName = repositoryLector.findByNameLike(str);
        List<Lector> lectorsSurname = repositoryLector.findBySurnameLike(str);

        try {
            lectorsSurname
                    .forEach(v -> lectorsMapSurname.put(v.getId(), v));

            lectorsName
                    .forEach(v -> stringBuilder.append(v.getName()).append(" ").append(v.getSurname()).append(", "));
            lectorsSurname.stream()
                    .filter(v -> !lectorsMapSurname.containsKey(v.getId()))
                    .forEach(v -> stringBuilder.append(v.getName()).append(" ").append(v.getSurname()).append(", "));

            stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), "");
        } catch (StringIndexOutOfBoundsException exception) {
            log.error("Dont find anything lectors");
        }

        return stringBuilder.toString();
    }

   private Optional<DepartmentName> parseMessageWithDepartmentName(String message, int number) {
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
   private String parseMessageWithTemplate(String message, int number) {
        String[] array;
        String str = null;
        try {
            array = message.split(" ");
            str = "%".concat(Arrays.stream(array).filter(v -> v.equals(array[number])).findFirst().get()).concat("%");
        } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
            log.error(ex.getMessage());
        }

        return str;
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
