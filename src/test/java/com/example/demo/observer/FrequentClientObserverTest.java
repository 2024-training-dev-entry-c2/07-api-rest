package com.example.demo.observer;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FrequentClientObserverTest {
    @Test
    void update_ShouldPrintNotificationForFrequentClient() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        FrequentClientObserver observer = new FrequentClientObserver();

        String testMessage = "El cliente Mario ahora es un cliente frecuente.";
        observer.update(testMessage);


        System.setOut(originalOut);


        String output = outputStream.toString();
        assertTrue(output.contains("Notificación para cliente frecuente: " + testMessage));
    }
}