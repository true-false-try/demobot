package com.example.demobot.service.impl;

import com.example.demobot.config.BotConfig;
import com.example.demobot.service.TelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@RequiredArgsConstructor
@Slf4j
public class TelegramBotImpl extends TelegramLongPollingBot implements TelegramBot {

    final BotConfig botConfig;

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

            switch (message) {
                case "/start":
                    startCommand(chatId, update.getMessage().getChat().getFirstName());
                    break;
                default:
                    sendMessage(chatId, "Sorry command don't use");
            }

        }
    }

    private void startCommand(long id, String name) {
        String answer = "Hi ".concat(name);
        sendMessage(id, answer);
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
