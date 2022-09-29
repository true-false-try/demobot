package com.example.demobot.service.impl;

import com.example.demobot.config.BotConfig;
import com.example.demobot.service.TelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class TelegramBotImpl extends TelegramLongPollingBot implements TelegramBot {

    private final BotConfig botConfig;

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return null;
    }

    @Override
    public void onUpdateReceived(Update update) {

    }
}
