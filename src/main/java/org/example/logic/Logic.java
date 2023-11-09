package org.example.logic;

/**
 * Класс логики бота
 * логика у каждого бота своя. Экземпляр этого класса присутсвует в классе любого бота
 */
public class Logic {

    /**
     * @param textMrssage сообщение от пользователя
     * @return форматированный ответ от бота
     */
    public String handleMessage(String textMrssage){
        return String.format("Ваше сообщение: %s", textMrssage);
    }
}
