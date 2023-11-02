package org.example.config;

/**
 *
 * <p>класс конфигурации бота</p>
 * <p>{@link BotConfig#botName} название бота</p>
 * <p>{@link BotConfig#token} токен бота </p>
 */
public class BotConfig {

    private final String botName;


    private final String token;

    public BotConfig(String botName, String token) {
        this.botName = botName;
        this.token = token;
    }

    public String getBotName() {
        return botName;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "BotConfig{" +
                "botName='" + botName + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
