package org.example;

import org.example.logic.Logic;

/**
 * <p>интерфейс абстрактного бота</p>
 * <p>общие методы для каждого бота</p>
 * <p>базовый функционал, который унаследует каждый бот, т.е. запуск и отправка сообщения</p>
 */
public interface Bot {

    /**
     * Запускает бота
     */
    void run();

    /**
     * Отправляет сообщение пользователю в ответ
     * @param chatId чат в который отправляется сообщение
     * @param message сообщение от бота
     */
    void sendText(Long chatId, String message);
}
