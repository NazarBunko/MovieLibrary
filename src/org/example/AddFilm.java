package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/** Нижче реалізований клас для додавання нового фільму в список */
public class AddFilm extends JFrame {
    public String filmName; // Назва фільму
    public String filmDirector; // Режисер
    public int filmYear; // Рік випуску
    public String filmCountry; // Країна виробник
    public int filmBudget; // Бюджет
    public int filmDuration; // Тривалість в хвилинах

    /** Поля для вводу вищенаведених змінних */
    public JTextField name_field;
    public JTextField director_field;
    public JTextField year_field;
    public JTextField country_field;
    public JTextField budget_field;
    public JTextField duration_field;
    public JTextField actors_field;
    public char[] actorsChar; // Масив акторів посимвольно
    public String[] filmActors; // Масив готових акторів, після обробки
    public int NumberOfActors; // Кількість акторів
    public AddFilm() {
        // Створення вікна та задання його розмірів та локації
        super("Новий Фільм");
        super.setBounds(810, 380, 300, 500);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = super.getContentPane();

        // Створення тексту та поля для вводу назви фільму
        JLabel name = new JLabel("Назва фільму");
        name.setLocation(70, 11);
        name.setSize(100, 14);
        name_field = new JTextField("", 1);
        name_field.setLocation(70, 26);
        name_field.setSize(150, 25);

        // Створення тексту та поля для вводу режисера
        JLabel director = new JLabel("Режисер");
        director.setLocation(70, 56);
        director.setSize(70, 14);
        director_field = new JTextField("", 1);
        director_field.setLocation(70, 72);
        director_field.setSize(150, 25);

        // Створення тексту та поля для вводу року випуску
        JLabel year = new JLabel("Рік випуску");
        year.setLocation(70, 101);
        year.setSize(70, 16);
        year_field = new JTextField("", 1);
        year_field.setLocation(70, 118);
        year_field.setSize(150, 25);

        // Створення тексту та поля для вводу бюджету
        JLabel budget = new JLabel("Бюджет");
        budget.setLocation(70, 146);
        budget.setSize(70, 16);
        budget_field = new JTextField("", 1);
        budget_field.setLocation(70, 164);
        budget_field.setSize(150, 25);

        // Створення тексту та поля для вводу країни виробника
        JLabel country = new JLabel("Країна");
        country.setLocation(70, 195);
        country.setSize(70, 14);
        country_field = new JTextField("", 1);
        country_field.setLocation(70, 210);
        country_field.setSize(150, 25);

        // Створення тексту та поля для вводу тривалості
        JLabel duration = new JLabel("Тривалість(в хвилинах)");
        duration.setLocation(70, 240);
        duration.setSize(170, 14);
        duration_field = new JTextField("", 1);
        duration_field.setLocation(70, 256);
        duration_field.setSize(150, 25);

        // Створення тексту та поля для вводу акторів, обов'язково через кому
        JLabel actor = new JLabel("Введіть акторів через кому");
        actor.setLocation(70, 288);
        actor.setSize(170, 14);
        actors_field = new JTextField("", 1);
        actors_field.setLocation(70, 304);
        actors_field.setSize(150, 25);

        // Додавання усіх елементів на екран
        container.add(name);
        container.add(name_field);
        container.add(director);
        container.add(director_field);
        container.add(year);
        container.add(year_field);
        container.add(budget);
        container.add(budget_field);
        container.add(country);
        container.add(country_field);
        container.add(duration);
        container.add(duration_field);
        container.add(actor);
        container.add(actors_field);

        // Створення кнопки для збереження введених даних
        JButton buttonAddActors = new JButton("Додати фільм");
        buttonAddActors.setLocation(70, 340);
        buttonAddActors.setSize(150, 30);
        container.add(buttonAddActors);
        buttonAddActors.addActionListener(new AddFilm.ButtonNewFilm());

        // Допоміжна пуста стрічка, для коректної роботи вікна
        JLabel empty = new JLabel("  ");
        container.add(empty);
    }

    class ButtonNewFilm implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            // Перевірка на пустий рядок
            if(year_field.getText().isEmpty() || budget_field.getText().isEmpty() || duration_field.getText().isEmpty() ||
                    name_field.getText().isEmpty() || director_field.getText().isEmpty() || country_field.getText().isEmpty()  || actors_field.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ви не ввели дані", "Помилка", JOptionPane.PLAIN_MESSAGE);
                return;
            }

            // Перевірка на правильність введених символів, тобто, введені цифри мають правильно перевестись в тип int
            try {
                // Перевід стрічок в тип int
                filmYear = Integer.parseInt(year_field.getText());
                filmBudget = Integer.parseInt(budget_field.getText());
                filmDuration = Integer.parseInt(duration_field.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Помилка вхідних даних", "Помилка", JOptionPane.PLAIN_MESSAGE);
                throw new RuntimeException(ex);
            }

            // Створюємо змінні, в які записуємо дані з полів
            filmName = name_field.getText();
            filmDirector = director_field.getText();
            filmCountry = country_field.getText();
            actorsChar = actors_field.getText().toCharArray();
            // За допомогою відділення комою рахуємо кількість акторів
            for (char c : actorsChar) {
                if (c == ',') {
                    NumberOfActors++;
                }
            }
            NumberOfActors++; // Кількість акторів завжди більше на один відносно кількості ком

            if(filmYear < 1800 || filmYear > 2023 || filmBudget < 10000 || filmDuration < 10 || filmDuration > 300 || actorsChar.length < 4 || filmCountry.length() < 2){
                JOptionPane.showMessageDialog(null, "Помилка вхідних даних", "Помилка", JOptionPane.PLAIN_MESSAGE);
                return;
            }

            filmActors = actors_field.getText().split(",\\s*"); // Записуємо готовий масив акторів
            Film film = new Film(filmName, filmDirector, filmYear, filmActors, filmBudget, filmCountry, filmDuration); // Створення нового фільму
            dispose();
            Window window; // Створення об'єкта головного вікна
            try {
                window = new Window(film); // Виділення пам'яті під об'єкт головного вікна
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            // Визначення властивостей вікна, таких як: неможливість змінити його розмір, видимість та центрування
            window.setResizable(false);
            window.setVisible(true);
            window.setLocationRelativeTo(null);
        }
    }
}
