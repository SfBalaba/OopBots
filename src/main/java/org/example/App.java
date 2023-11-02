package org.example;

import org.example.Telegram.TelegramBot;
import org.example.Vk.VkBot;
import org.example.config.BotConfig;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        TelegramBot telegramBot = new TelegramBot(System.getenv("BOT_TG_NAME"), System.getenv("BOT_TG_TOKEN"));
        VkBot vkBot = new VkBot(System.getenv("BOT_VK_ID"), System.getenv("BOT_VK_TOKEN"));

        System.out.println(telegramBot.getConfig());
        System.out.println(vkBot.getConfig());
        System.out.println(telegramBot.formatResponse("test messaging"));
    }
}
