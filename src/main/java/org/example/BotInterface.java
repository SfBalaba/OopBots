package org.example;

/**
 * интерфейс абстрактного бота
 */
public interface BotInterface {
    /**
     * запускает бота
     */
    abstract void run();


    default String formatResponse(String textMrssage){
        return String.format("Ваше сообщение: %s", textMrssage);
    }
}
