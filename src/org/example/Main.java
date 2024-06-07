package org.example;

import java.io.IOException;

public class Main { // Основний клас
    public static void main(String[] args) throws IOException { // Головна функція, яка викликає основне вікно

        Window window = new Window(null); // Створення основного вікна

        // Визначення властивостей вікна, таких як: неможливість змінити його розмір, видимість та центрування
        window.setResizable(false);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }
}