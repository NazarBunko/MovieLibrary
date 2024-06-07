package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

/** Головне вікно, яке містить основний список та всі кнопки */
public class Window extends JFrame {
    public static int NumberOfFilms = 0; // Число фільмів
    public static ArrayList<Film> film = new ArrayList<>(); // Масив фільмів
    public static JFileChooser fc = new JFileChooser(); // Зміна для вибору файлу
    public JTextField name_field, director_field, country_field, actor_field; // Поля для вводу умов для різних функцій
    public Container container = super.getContentPane(); // Контейнер, який містить всі об'єкти вікна

    /** Функція для додавання будь-якого об'єкта на екран та задання його розмірів і локації.
     * Функція приймає об'єкт будь-якого типу даної бібліотеки, координати Х та У, ширину та довжину */
    public void addToContainer(JComponent component, int x, int y, int width, int height) {
        component.setSize(width, height); // Розміри
        component.setLocation(x, y); // Локація
        container.add(component); // Додавання на екран
    }

    /** Створення основного списку. Приймає новий доданий фільм, якщо він не існує не додає*/
    public void createList(Film newFilm){
        // Створення стрічки з назвою колон списку
        String[] columnsNames = {"Назва Фільму", "Режисер", "Рік випуску", "Бюджет", "Країна", "Тривалість"};

        // Якщо переданий фільм існує ми додамо його в список
        if(newFilm != null){
            film.add(newFilm);
            NumberOfFilms = film.size();
        }

        // Створення масиву, який буде нашим списком
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
        addToContainer(scrollPanel, 15, 20, 965, 670);
    }

    public Window(Film newFilm) throws IOException {
        // Створення вікна та задання його розмірів та локації
        super("Бібліотека Фільмів");
        super.setBounds(810, 380, 1200, 750);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Створюємо список
        createList(newFilm);

        // Поле для назви фільму
        JLabel deleteFilmLabel = new JLabel("Назва фільму:");
        addToContainer(deleteFilmLabel, 1000, 15, 165, 13);
        name_field = new JTextField("", 1);
        addToContainer(name_field, 1000,30,165,30);

        // Кнопка видалення фільму
        JButton deleteFilmButton = new JButton("Видал.");
        addToContainer(deleteFilmButton,1000,70, 80, 30);
        deleteFilmButton.addActionListener(new ButtonDeleteFilm());

        // Кнопка для перегляду акторів фільму
        JButton checkActors = new JButton("Актори");
        addToContainer(checkActors,1085,70, 80, 30);
        checkActors.addActionListener(new ButtonCheckActors());

        // Кнопка додавання фільму
        JButton addFilmButton = new JButton("Додати новий фільм");
        addToContainer(addFilmButton, 1000, 110, 165, 30);
        addFilmButton.addActionListener(new ButtonAddFilm());

        // Кнопка для зчитування з файлу
        JButton readFromFile = new JButton("Зчитати з файлу");
        addToContainer(readFromFile, 1000, 150, 165, 30);
        readFromFile.addActionListener(new ButtonReadFile());

        // Кнопка для запису в файл
        JButton writeToFile = new JButton("Записати в файл");
        addToContainer(writeToFile, 1000, 190, 165, 30);
        writeToFile.addActionListener(new ButtonWriteFile());

        // Кнопка для очищення списку
        JButton allFilmDeleteButton = new JButton("Очистити список");
        addToContainer(allFilmDeleteButton, 1000, 230, 165, 30);
        allFilmDeleteButton.addActionListener(new ButtonDeleteAllFilms());

        // Алгоритмом простої вибірки відсортувати записи за Країною виробництва
        JButton countrySort = new JButton("Функція 1");
        addToContainer(countrySort, 1000, 270, 165, 30);
        countrySort.addActionListener(new ButtonCountrySort());

        // Назви фільмів, в яких однакові режисери та найменші бюджети одночасно
        JLabel searchDirectorBudgetLabel = new JLabel("Режисер:");
        addToContainer(searchDirectorBudgetLabel, 1000, 303, 165, 15);
        director_field = new JTextField("", 1);
        addToContainer(director_field, 1000, 320, 165, 30);
        JButton directorsMinBudget = new JButton("Функція 2");
        addToContainer(directorsMinBudget, 1000, 360, 165, 30);
        directorsMinBudget.addActionListener(new ButtonShowDirectorsMinBudget());

        // За заданою країною виробництва знайти всі фільми, в яких найбільші бюджети та найменша тривалість одночасно
        JLabel searchCountryLabel = new JLabel("Країна:");
        addToContainer(searchCountryLabel, 1000, 393, 165, 15);
        country_field = new JTextField("", 1);
        addToContainer(country_field, 1000, 410, 165, 30);
        JButton searchCountryButton = new JButton("Функція 3");
        addToContainer(searchCountryButton, 1000, 450, 165, 30);
        searchCountryButton.addActionListener(new ButtonCountryMaxBudgetMinDuration());

        // Для кожного Режисера визначити фільм з найбільшою тривалістю
        JButton directorDurationSortButton = new JButton("Функція 4");
        addToContainer(directorDurationSortButton, 1000, 490, 165, 30);
        directorDurationSortButton.addActionListener(new ButtonDirectorDurationShow());

        // Знайти найдорожчий та найстарший фільм одночасно
        JButton searchBudgetYearButton = new JButton("Функція 5");
        addToContainer(searchBudgetYearButton, 1000, 530, 165, 30);
        searchBudgetYearButton.addActionListener(new ButtonBudgetYearsShow());

        // За заданим актором визначити всі фільми, в яких він (вона) знімались
        JLabel searchActorLabel = new JLabel("Актор:");
        addToContainer(searchActorLabel, 1000, 563, 165, 15);
        actor_field = new JTextField("", 1);
        addToContainer(actor_field, 1000, 580, 165, 30);
        JButton searchActorFilmButton = new JButton("Функція 6");
        addToContainer(searchActorFilmButton, 1000, 620, 165, 30);
        searchActorFilmButton.addActionListener(new ButtonActorFilmShow());

        // Встановити найбільш популярного актора
        JButton searchActorButton = new JButton("Функція 7");
        addToContainer(searchActorButton, 1000, 660, 165, 30);
        searchActorButton.addActionListener(new ButtonPopularActorShow());

        // Допоміжна пуста стрічка, для коректної роботи вікна
        JLabel label = new JLabel(" ");
        container.add(label);
    }
    /** Сортування за країною виробництва в порядку спадання */
    public class ButtonCountrySort implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Перевірка чи список не пустий
            if(film.isEmpty()){
                JOptionPane.showMessageDialog(null, "Список пустий.", "Помилка", JOptionPane.PLAIN_MESSAGE);
            }
            else{
                int n = film.size();

                for (int i = 0; i < n - 1; i++) {
                    int minIndex = i;

                    for (int j = i + 1; j < n; j++) {
                        // Порівнюємо країни за допомогою порівняння рядків
                        String country1 = film.get(j).getCountry();
                        String country2 = film.get(minIndex).getCountry();
                        if (country1.compareTo(country2) < 0) {
                            minIndex = j;
                        }
                    }

                    if (minIndex != i) {
                        // Міняємо місцями об'єкти в списку
                        Film temp = film.get(i);
                        film.set(i, film.get(minIndex));
                        film.set(minIndex, temp);
                    }
                }
                dispose();
                Window window; // Створення об'єкта головного вікна
                try {
                    window = new Window(null); // Виділення пам'яті під об'єкт головного вікна
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

    /** Назви фільмів, в яких однакові режисери та найменші бюджети одночасно */
    public class ButtonShowDirectorsMinBudget implements ActionListener {

        /** Пошук серед масиву бюджетів найменший */
        public static int findMinNumber(ArrayList<Integer> numbers) {
            // Перевірка на пустий список
            if (numbers.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Режисер не знайдений.", "Помилка", JOptionPane.PLAIN_MESSAGE);
            }

            // Вважаємо, що найменший бюджет це перший
            int min = numbers.get(0);

            // Шукаємо найменший
            for (int i = 1; i < numbers.size(); i++) {
                int current = numbers.get(i);
                if (current < min) {
                    min = current;
                }
            }

            // Повертаємо знайдений бюджет
            return min;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Перевірка на пустий список
            if(film.isEmpty()){
                JOptionPane.showMessageDialog(null, "Список пустий.", "Помилка", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            // Перевірка на пусте поле
            if(director_field.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Поле пусте.", "Помилка", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            String mostPopularDirector = director_field.getText(); // Створюємо зміну, куди записуємо інформацію з поля
            ArrayList<Integer> budgets = new ArrayList<>(); // Масив бюджетів
            ArrayList<Film> newFilms = new ArrayList<>(); // Масив фільмів з заданим режисером
            StringBuilder str = new StringBuilder();
            // Наповнюємо масив фільмами з заданим режисером
            for(int i = 0; i < NumberOfFilms; i++){
                if(film.get(i).getNameOfDirector().equals(mostPopularDirector)){
                    budgets.add(Integer.parseInt(film.get(i).getBudgetString()));
                    newFilms.add(film.get(i));
                }
            }
            int minBudget = findMinNumber(budgets); // Знаходимо найменший бюджет

            // Записуємо фільми з найменшим бюджетом в стрічку
            for(int i = 0; i < budgets.size(); i++){
                if((Integer.parseInt(newFilms.get(i).getBudgetString())) == (minBudget)){
                    str.append(newFilms.get(i).getNameOfFilm()).append("\n").append(newFilms.get(i).getNameOfDirector()).append("\n").append(newFilms.get(i).getCountry()).append("\n").append(newFilms.get(i).getYear()).append("\n").append(newFilms.get(i).getBudgetString()).append("\n").append(newFilms.get(i).getDurationMin()).append("\n");
                    String[] actors = newFilms.get(i).getActors();
                    for(int j = 0; j < newFilms.get(i).getNumberOfActors(); j++){
                        str.append(actors[j]).append("\n");
                    }
                    str.append("\n");
                }
            }

            // Виводимо на екран стрічку з записаним фільмом
            JOptionPane.showMessageDialog(null, str.toString(), "Однаковий режисер і нейменший бюджет", JOptionPane.PLAIN_MESSAGE);
        }
    }

    /** За заданою країною виробництва знаходимо всі фільми, в яких найбільші бюджети та найменша тривалість одночасно */
    public class ButtonCountryMaxBudgetMinDuration implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Перевірка на пустий список
            if(film.isEmpty()){
                JOptionPane.showMessageDialog(null, "Список пустий.", "Помилка", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            // Перевірка на пусте поле
            if(country_field.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Поле пусте.", "Помилка", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            ArrayList<Film> newFilm1 = new ArrayList<>();
            for(int i = 0; i < NumberOfFilms; i++){
                if(film.get(i).getCountry().equals(country_field.getText())){
                    newFilm1.add(film.get(i));
                }
            }
            // Перевірка на фільми заданої країни
            if(newFilm1.isEmpty()){
                JOptionPane.showMessageDialog(null, "Не знайдено жодного фільму заданої країни.", "Фільми заданої країни", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            // Пошук найбільшого бюджету
            ArrayList<Film> newFilm2 = new ArrayList<>();
            int maxBudget = newFilm1.get(0).getBudget();
            for (Film value : newFilm1) {
                if (maxBudget <= value.getBudget()) {
                    maxBudget = value.getBudget();
                }
            }
            // Записуємо усі фільми з найбільшим бюджетом
            for (Film value : newFilm1) {
                if (maxBudget == value.getBudget()) {
                    newFilm2.add(value);
                }
            }
            // Шукаємо найменшу тривалість
            int minDuration = newFilm2.get(0).getDurationMin();
            ArrayList<Film> newFilm3 = new ArrayList<>();
            for (Film value : newFilm2) {
                if (minDuration >= value.getDurationMin()) {
                    minDuration = value.getDurationMin();
                }
            }
            // Записуємо всі фільми з найменшою тривалістю і найбільшим бюджетом
            for (Film value : newFilm2) {
                if (minDuration == value.getDurationMin()) {
                    newFilm3.add(value);
                }
            }
            // Записуємо знайдений фільм в стрічку
            StringBuilder str = new StringBuilder();
            for (Film value : newFilm3) {
                str.append(value.getNameOfFilm()).append("\n").append(value.getNameOfDirector()).append("\n").append(value.getCountry()).append("\n").append(value.getYear()).append("\n").append(value.getBudgetString()).append("\n").append(value.getDurationMin()).append("\n");
                String[] actors = value.getActors();
                for (int j = 0; j < value.getNumberOfActors(); j++) {
                    str.append(actors[j]).append("\n");
                }
                str.append("\n");
            }
            // Виводимо на екран
            JOptionPane.showMessageDialog(null, str.toString(), "Фільми заданої країни", JOptionPane.PLAIN_MESSAGE);
        }
    }

    /** Для кожного Режисера визначаємо фільм з найбільшою тривалістю */
    public static class ButtonDirectorDurationShow implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            // Перевірка на пустий список
            if(film.isEmpty()){
                JOptionPane.showMessageDialog(null, "Список пустий.", "Помилка", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            // Створення мапи
            Map<String, Film> longestFilmsByDirector = new HashMap<>();

            // Запис фільмів кожного режисера з найменшою тривалістю
            for (Film film : film) {
                String director = film.getNameOfDirector();
                int duration = film.getDurationMin();

                if (!longestFilmsByDirector.containsKey(director) ||
                        duration > longestFilmsByDirector.get(director).getDurationMin()) {
                    longestFilmsByDirector.put(director, film);
                }
            }

            // Записуємо знайдені фільми в список і виводимо в окремому вікні
            ArrayList<Film> newFilm = new ArrayList<>(longestFilmsByDirector.values());
            ShowList list = new ShowList(newFilm);
            list.setResizable(false);
            list.setVisible(true);
            list.setLocationRelativeTo(null);
        }
    }

    /** Знаходимо найдорожчий та найстарший фільм одночасно */
    public static class ButtonBudgetYearsShow implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            // Перевірка на пустий список
            if(film.isEmpty()){
                JOptionPane.showMessageDialog(null, "Список пустий.", "Помилка", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            // Шукаємо найбільший бюджет
            ArrayList<Film> newFilm = new ArrayList<>();
            int budget = film.get(0).getBudget();
            for(int i = 0; i < NumberOfFilms; i++){
                if(budget <= film.get(i).getBudget()){
                    budget = film.get(i).getBudget();
                }
            }
            // Створюємо список з найбільшим бюджетом
            for(int i = 0; i < NumberOfFilms; i++){
                if(film.get(i).getBudget() == budget){
                    newFilm.add(film.get(i));
                    System.out.println(film.get(i).getBudget());
                }
            }
            // З попереднього списку шукаємо найстаріший фільм
            int year = newFilm.get(0).getYear();
            for (Film value : newFilm) {
                if (year >= newFilm.get(0).getYear()) {
                    year = value.getYear();
                }
            }
            // Записуємо знайдені фільми в стрічку
            StringBuilder str = new StringBuilder();
            for (Film value : newFilm) {
                if (value.getYear() == year) {
                    str.append(value.getNameOfFilm()).append("\n").append(value.getNameOfDirector()).append("\n").append(value.getCountry()).append("\n").append(value.getYear()).append("\n").append(value.getBudgetString()).append("\n").append(value.getDurationMin()).append("\n");
                    String[] actors = value.getActors();
                    for (int j = 0; j < value.getNumberOfActors(); j++) {
                        str.append(actors[j]).append("\n");
                    }
                    str.append("\n");
                }
            }
            // Виводимо на екран
            JOptionPane.showMessageDialog(null, str.toString(), "Найдорожчий та найстаріший", JOptionPane.PLAIN_MESSAGE);
        }
    }

    /** За заданим актором визначаємо всі фільми, в яких він (вона) знімались */
    public class ButtonActorFilmShow implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String actor = actor_field.getText();
            // Перевірка на пустий список
            if(film.isEmpty()){
                JOptionPane.showMessageDialog(null, "Список пустий.", "Помилка", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            // Перевірка на пусте поле
            if(actor.isEmpty()){
                JOptionPane.showMessageDialog(null, "Поле пусте.", "Помилка", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            // Записуємо в стрічку фільми, де знаходимо даного актора
            StringBuilder str = new StringBuilder();
            for(int i = 0; i < NumberOfFilms; i++){
                for(int j = 0; j < film.get(i).getNumberOfActors(); j++){
                    if(actor.equals(film.get(i).getActors()[j])){
                        str.append(film.get(i).getNameOfFilm()).append("\n");
                    }
                }
            }
            // Перевірка на пусте поле
            if(str.toString().equals("")){
                JOptionPane.showMessageDialog(null, "Актора не знайдено.", "Помилка", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            // Виводимо на екран
            JOptionPane.showMessageDialog(null, str.toString(), "Усі фільми " + actor, JOptionPane.PLAIN_MESSAGE);
        }
    }

    /** Встановлюємо найбільш популярного актора. */
    public static class ButtonPopularActorShow implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Перевірка на пустий список
            if(film.isEmpty()){
                JOptionPane.showMessageDialog(null, "Список пустий.", "Помилка", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            // Рахуємо кількість акторів
            int number = 0;
            for (Film value : film) {
                number += value.getActors().length;
            }
            // Записуємо кожного актора
            String[] strings = new String[number];
            for(int i = 0, j = 0; j < number; i++){
                for(int k = 0; k < film.get(i).getActors().length; k++){
                    strings[j] = film.get(i).getActors()[k];
                    j++;
                }
            }
            // Створюємо мапу, де обраховуємо найпопулярнішого актора
            Map<String, Integer> stringCounts = new HashMap<>();

            for (String str : strings) {
                stringCounts.put(str, stringCounts.getOrDefault(str, 0) + 1);
            }

            String mostPopularActor = null;
            int maxCount = 0;

            for (Map.Entry<String, Integer> entry : stringCounts.entrySet()) {
                if (entry.getValue() >= maxCount) {
                    maxCount = entry.getValue();
                    mostPopularActor = entry.getKey();
                }
            }
            // Виводимо на екран найпопулярнішого актора
            JOptionPane.showMessageDialog(null, mostPopularActor, "Найпопулярніший актор", JOptionPane.PLAIN_MESSAGE);
        }
    }

    /** Читаємо з файлу */
    public class ButtonReadFile implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            // Створюємо діалогове вікно для вибору файлу
            fc.showSaveDialog(null);
            File file = fc.getSelectedFile(); // Створюємо файл на основі того, який вибрали
            if(file != null && file.isFile() && file.getName().endsWith(".txt")){
                BufferedReader bufferedReader; // Створюємо змінну для буферного читання
                try {
                    bufferedReader = new BufferedReader(new FileReader(file));
                    String test = bufferedReader.readLine();
                    while (test != null) { // Поки файл існує - читаємо його
                        int NumberOfString = 0;
                        ArrayList<String> fileData = new ArrayList<>(); // Створюємо список стрічок
                        test = bufferedReader.readLine();
                        while (test != null && !test.isEmpty()) { // Записуємо всі стрічки до пустої стрічки
                            fileData.add(test);
                            NumberOfString++;
                            test = bufferedReader.readLine();
                        }
                        int NumberOfActors = NumberOfString - 5; // Обчислюємо число акторів

                        String[] Actor = new String[NumberOfActors]; // Створюємо масив авторів

                        for (int i = 0; i < NumberOfActors - 1; i++) {
                            Actor[i] = fileData.get(6 + i); // Записуємо акторів
                        }

                        // Копіюємо в акторів без останньої нульової стрічки
                        String[] newActors = new String[NumberOfActors - 1];
                        System.arraycopy(Actor, 0, newActors, 0, NumberOfActors - 1);

                        // Переводимо необхідні значення в тип int
                        int Year = Integer.parseInt(fileData.get(3));
                        int Budget = Integer.parseInt(fileData.get(4));
                        int DurationMin = Integer.parseInt(fileData.get(5));

                        // Створюємо об'єкт Film, додаємо його до загального списку та додаємо 1 до кількості фільмів
                        Film TestFilm = new Film(fileData.get(0), fileData.get(1), Year, newActors, Budget, fileData.get(2), DurationMin);
                        film.add(TestFilm);
                        NumberOfFilms++;
                    }
                    dispose();
                    Window window; // Створення об'єкта головного вікна
                    try {
                        window = new Window(null); // Виділення пам'яті під об'єкт головного вікна
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    // Визначення властивостей вікна, таких як: неможливість змінити його розмір, видимість та центрування
                    window.setResizable(false);
                    window.setVisible(true);
                    window.setLocationRelativeTo(null);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Помилка файлу.", "Помилка", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    /** Записуємо в файл */
    public static class ButtonWriteFile implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Перевірка на пустий список
            if(film.isEmpty()){
                JOptionPane.showMessageDialog(null, "Список пустий.", "Помилка", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            // Створюємо діалогове вікно для вибору файлу
            fc.showSaveDialog(null);
            File file = fc.getSelectedFile();
            // Перевірки на існування та правильність файлу
            if(file != null && file.isFile() && file.getName().endsWith(".txt") && NumberOfFilms > 0) {
                // Створюємо змінну буферного запису
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    for (int i = 0; i < NumberOfFilms; i++) {
                        // Записуємо всі елементи кожного фільму
                        writer.write("\n" + film.get(i).getNameOfFilm() + "\n");
                        writer.write(film.get(i).getNameOfDirector() + "\n");
                        writer.write(film.get(i).getCountry() + "\n");
                        writer.write(film.get(i).getYear() + "\n");
                        writer.write(film.get(i).getBudgetString() + "\n");
                        writer.write(film.get(i).getDurationMin() + "\n");
                        for(int j = 0; j < film.get(i).getNumberOfActors(); j++){
                            if(film.get(i).getActors()[j] != null){
                                writer.write(film.get(i).getActors()[j] + "\n");
                            }
                        }
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Помилка файлу.", "Помилка", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    /** Дивимось акторів заданого фільму */

    public class ButtonCheckActors implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Перевірка на пусте поле
            if(name_field.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Поле пусте.", "Помилка", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            // Перевірка на пустий список
            if(film.isEmpty()){
                JOptionPane.showMessageDialog(null, "Список пустий.", "Помилка", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            Film film1 = new Film();
            for (Film value : film) {
                if (value.getNameOfFilm().equals(name_field.getText())) {
                    film1 = value;
                }
            }
            // Перевірка на пустий список
            if(film1.getNameOfFilm() == null){
                JOptionPane.showMessageDialog(null, "Фільм не знайдено.", "Помилка", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            StringBuilder str = new StringBuilder();
            for(int i = 0; i < film1.getNumberOfActors(); i++){
                str.append(film1.getActors()[i]).append("\n");
            }
            JOptionPane.showMessageDialog(null, str, film1.getNameOfFilm(), JOptionPane.PLAIN_MESSAGE);
        }
    }

    /** Додаємо фільм до списку */
    public class ButtonAddFilm implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dispose(); // Закриваємо поточне вікно
            AddFilm newFilm = new AddFilm(); // Створюємо вікно для додавання нового фільму
            newFilm.setResizable(false); // Вікно не можна рухати
            newFilm.setVisible(true); // Вікно видно на екрані
            newFilm.setLocationRelativeTo(null); // Неможливість зміни розмірів вікна
        }
    }

    /** Видаляємо фільм за назвою */
    public class ButtonDeleteFilm implements ActionListener  {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Перевірка на пустий список
            if(film.isEmpty()){
                JOptionPane.showMessageDialog(null, "Список пустий.", "Помилка", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            // Перевірка на пусте поле
            if(name_field.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Поле пусте.", "Помилка", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            // Записуємо всі фільми, крім заданого в новий список
            int number = NumberOfFilms;
            ArrayList<Film> film1 = new ArrayList<>();
            for(int i = 0; i < NumberOfFilms; i++){
                if(film.get(i).getNameOfFilm().equals(name_field.getText())){
                    number--;
                }
                else{
                    film1.add(film.get(i));
                }
            }
            // Перевірка на присутність заданого фільму
            if(film.equals(film1)){
                JOptionPane.showMessageDialog(null, "Заданого вами фільму не знайдено.", "Помилка", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            // Оновлюємо наш список
            film = film1;
            NumberOfFilms = number;
            // Перезавантажуємо наше головне вікно
            dispose();
            Window window; // Створення об'єкта головного вікна
            try {
                window = new Window(null); // Виділення пам'яті під об'єкт головного вікна
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            // Визначення властивостей вікна, таких як: неможливість змінити його розмір, видимість та центрування
            window.setResizable(false);
            window.setVisible(true);
            window.setLocationRelativeTo(null);
        }
    }

    /** Очищуємо весь список */
    public class ButtonDeleteAllFilms implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Перевірка на пустий список
            if(film.isEmpty()){
                JOptionPane.showMessageDialog(null, "Список пустий.", "Помилка", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            // Очищаємо наш список та кількість фільмів
            film.clear();
            NumberOfFilms = 0;
            // Перезавантажуємо наше головне вікно
            dispose();
            Window window; // Створення об'єкта головного вікна
            try {
                window = new Window(null); // Виділення пам'яті під об'єкт головного вікна
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