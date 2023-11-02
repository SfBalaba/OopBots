package org.example.Vk;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import org.example.BotInterface;
import org.example.config.BotConfig;

import java.util.List;
import java.util.Random;

/**
 * класс VK бота
 */
public class VkBot implements BotInterface {
    private BotConfig config;
    GroupActor actor;
    VkApiClient vk;
    Integer ts;
    Random random;
    public VkBot(BotConfig config) {
        this.config = config;
        TransportClient transportClient = new HttpTransportClient();
        this.vk = new VkApiClient(transportClient);

        try {
            this.actor = new GroupActor(Integer.valueOf(config.getBotName()), config.getToken());
            this.ts = vk.messages().getLongPollServer(actor).execute().getTs();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.random = new Random();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        while (true) {
            List<Message> messages = getMessages();
            if (messages != null && !messages.isEmpty()) {
                messages.forEach(message -> {
                    String response = formatResponse(message.getText());
                    sendText(message, response);
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
     * @return историю запросов в json формате
     */
    private List<Message> getMessages() {
        MessagesGetLongPollHistoryQuery historyQuery =  vk.messages().getLongPollHistory(actor).ts(ts);
        try {
            return historyQuery.execute().getMessages().getItems();
        } catch (ApiException | ClientException e) {e.printStackTrace();}
        return null;
    }

    /**
     * оправляет пользователю ответ
     * @param response форматированный ответ
     */
    private void sendText(Message message, String response) {
        try {
            vk.messages().send(actor).
                    message(response).
                    userId(message.getFromId()).
                    randomId(random.nextInt(10000)).
                    execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "VkBot{" +
                "config=" + config +
                '}';
    }
}
