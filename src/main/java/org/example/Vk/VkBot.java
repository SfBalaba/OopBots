package org.example.Vk;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import org.example.Bot;
import org.example.config.BotConfig;
import org.example.logic.Logic;

import java.util.List;
import java.util.Random;

/**
 * класс VK бота
 */
public class VkBot implements Bot {
    /**
     * Реализует логику бота
     */
    private final Logic logic;
    /**
     * конфигурация бота
     */
    private final BotConfig config;
    /**
     * объект сообщества, именно оно отвечает на запросы(сообщения)
     */
    private final GroupActor actor;
    /**
     * объект транспортного клиента
     */
    private final VkApiClient vk;
    /**
     * сонхронизация запросов
     */
    private Integer ts;
    /**
     * объект случайной генерации
     */
    private final Random random;
    public VkBot(BotConfig config, Logic logic) {
        this.config = config;
        this.logic = logic;
        TransportClient transportClient = new HttpTransportClient();
        this.vk = new VkApiClient(transportClient);
        logic = new Logic();
        try {
            this.actor = new GroupActor(Integer.valueOf(config.getBotName()), config.getToken());
            this.ts = vk.messages().getLongPollServer(actor).execute().getTs();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.random = new Random();
    }


    /**
     * @return историю запросов в json формате
     * очередь запросов
     */
    private List<Message> getMessages() {
        MessagesGetLongPollHistoryQuery historyQuery =  vk.messages().getLongPollHistory(actor).ts(ts);
        try {
            return historyQuery.execute().getMessages().getItems();
        } catch (ApiException | ClientException e) {e.printStackTrace();}
        return null;
    }

    /**
     * <p>Запускает бота</p>
     * <p>Отслеживает новые сообщения от пользователя и реализует логику</p>
     */
    public void listenForMessages() {
        while (true) {
            List<Message> messages = getMessages();
            if (messages != null && !messages.isEmpty()) {
                messages.forEach(message -> {
                    String response = logic.handleMessage(message.getText());
                    sendText(message.getFromId().longValue(), response);
                });
            }
            try {
                ts = vk.messages().getLongPollServer(actor).execute().getTs();
            } catch (ApiException | ClientException e) {e.printStackTrace();}

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * {@inheritDoc}
     * @param response форматированный ответ
     */
    @Override
    public void sendText(Long chatId, String response) {
        try {
            vk.messages().send(actor).
                    message(response).
                    userId(chatId.intValue()).
                    randomId(random.nextInt(10000)).
                    execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }

}
