package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/** Нижче представлений клас, який створює вікно з переданим до класу списком фільмів */
public class ShowList extends JFrame {
    public ShowList(ArrayList<Film> film) {
        // Створення вікна та задання його розмірів та локації
        super("Список Фільмів");
        super.setBounds(810, 380, 1000, 450);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = super.getContentPane();

        // Створення стрічки з назвою колон списку
        String[] columnsNames = {"Назва Фільму", "Режисер", "Рік випуску", "Бюджет", "Країна", "Тривалість"};

        // Створення масиву, який буде нашим списком
        int NumberOfFilms = film.size();
        Object[][] data = new Object[NumberOfFilms][6];
        for (int i = 0; i < NumberOfFilms; i++) {
            data[i][0] = film.get(i).getNameOfFilm();
            data[i][1] = film.get(i).getNameOfDirector();
            data[i][2] = film.get(i).getYear();
            data[i][3] = film.get(i).getBudgetString();
            data[i][4] = film.get(i).getCountry();
            data[i][5] = film.get(i).getDurationMin();
        }

        // Створення таблиці та задання розмірів деяких колон
        JTable list = new JTable(data, columnsNames);
        list.getColumnModel().getColumn(0).setPreferredWidth(250);
        list.getColumnModel().getColumn(1).setPreferredWidth(100);
        list.getColumnModel().getColumn(2).setPreferredWidth(25);
        list.getColumnModel().getColumn(5).setPreferredWidth(20);

        // Створення списку(який має можливість прокручування) і задання його розмірів
        JScrollPane scrollPanel = new JScrollPane(list);
        scrollPanel.setLocation(20, 20);
        scrollPanel.setSize(950, 300);
        container.add(scrollPanel);

        // Створення кнопки для повернення в основне вікно
        JButton buttonAddActors = new JButton("Ок");
        buttonAddActors.setLocation(450, 340);
        buttonAddActors.setSize(100, 30);
        container.add(buttonAddActors);
        buttonAddActors.addActionListener(new ShowList.Button());

        // Допоміжна пуста стрічка, для коректної роботи вікна
        JLabel empty = new JLabel("  ");
        container.add(empty);
    }

    /** Реалізація кнопки для повернення в головне вікно */
    class Button implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dispose(); // Закриття поточного вікна
        }
    }
}
