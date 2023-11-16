package org.example.logic;

import junit.framework.TestCase;

public class LogicTest extends TestCase {

    public void testHandleMessage() {
        Logic logic = new Logic();
        String message = "Hello!";
        String actual = logic.handleMessage(message);
        assertEquals(String.format("Ваше сообщение: %s", message), actual);
    }
}