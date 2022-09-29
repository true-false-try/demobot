package com.example.demobot.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("/application.properties")
@Data
@RequiredArgsConstructor
public class BotConfig {
   @Value("${bot.name}")
   private String botName;
   @Value("${bot.key}")
   private String token;
}
