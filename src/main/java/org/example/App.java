package org.example;

import org.example.Telegram.TelegramBot;
import org.example.Vk.VkBot;
import org.example.config.BotConfig;
import org.example.logic.Logic;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Logic logic  = new Logic();
        BotConfig tgConfig = new BotConfig(System.getenv("BOT_TG_NAME"), System.getenv("BOT_TG_TOKEN"));
        BotConfig vkConfig = new BotConfig(System.getenv("BOT_VK_ID"), System.getenv("BOT_VK_TOKEN"));
        TelegramBot telegramBot = new TelegramBot(tgConfig, logic);
        telegramBot.startBot();
        VkBot vkBot = new VkBot(vkConfig, logic);
        vkBot.listenForMessages();
    }

}
