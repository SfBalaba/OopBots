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
        runAllBots();
    }

    private static void runAllBots() {
        BotConfig tgConfig = new BotConfig(System.getenv("BOT_TG_NAME"), System.getenv("BOT_TG_TOKEN"));
        BotConfig vkConfig = new BotConfig(System.getenv("BOT_VK_ID"), System.getenv("BOT_VK_TOKEN"));
        TelegramBot telegramBot = new TelegramBot(tgConfig);
        telegramBot.run();
        VkBot vkBot = new VkBot(vkConfig);
        vkBot.run();
    }
}
