package org.example;

import org.example.config.BotConfig;

/**
 * <p>базовый класс всех ботов</p>
 * <p>{@link Bot#config} конфигурация бота</p>
 */
abstract public class Bot {
    /**
     * @return конфигруацию бота
     */
    public BotConfig getConfig() {
        return config;
    }

    private final BotConfig config;


    protected Bot(String name, String token) {
        this.config = new BotConfig(name, token);
    }


    /**
     * запускает бота
     */
    abstract public void run();

    /**
     * регистрирует бота
     */
    abstract public void register();

    public String formatResponse(String textMrssage){
        return String.format("Ваше сообщение: %s", textMrssage);
    }


}
