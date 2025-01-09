package com.example.demo.observer;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PopularDishObserverTest {
    @Test
    void update_ShouldPrintNotificationForPopularDish() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        PopularDishObserver observer = new PopularDishObserver();

        String testMessage = "El plato Pizza ahora es popular.";
        observer.update(testMessage);

        System.setOut(originalOut);

        String output = outputStream.toString();
        assertTrue(output.contains("Notificación para plato popular: " + testMessage));
    }
}