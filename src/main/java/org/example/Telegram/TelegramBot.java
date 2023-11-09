package org.example.Telegram;


import org.example.Bot;
import org.example.config.BotConfig;
import org.example.logic.Logic;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * класс телеграм бота
 */
public class TelegramBot extends TelegramLongPollingBot implements Bot {

    /**
     * реализует логику телеграм бота
     */
    private final Logic logic;
    /**
     * конфигурация бота
     */
    private final BotConfig config;

    public TelegramBot(BotConfig config) {
        this.config = config;
        this.logic = new Logic();
    }

    /**
     * @return имя бота
     */
    @Override
    public String getBotUsername() {
        return this.config.getBotName();
    }

    /**
     * @return токен бота
     */
    @Override
    public String getBotToken() {
        return this.config.getToken();
    }

    /**
     * Отслеживает событие и отвечает на него в соответсвии логике
     * @param update объект, хранящий в себе информацию о происходящих событиях
     */
    @Override
    public void onUpdateReceived(Update update) {
        Message msg = update.getMessage();
        String response = logic.handleMessage(msg.getText());
        sendText(msg.getFrom().getId(), response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendText(Long userId, String message){
        SendMessage sm = SendMessage.builder()
                .chatId(userId.toString())
                .text(message).build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


}
