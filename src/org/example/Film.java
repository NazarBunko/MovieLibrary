package org.example;

public class Film {
    private String NameOfFilm; // Назва фільму
    private String NameOfDirector; // Режисер фільму
    private int Year; // Рік випуску
    private String[] Actors; // Масив акторів
    private int NumberOfActors = 0; // Кількість акторів
    private int Budget; // Бюджет фільму
    private String Country; // Країна виробник фільму
    private int DurationMin; // Тривалість фільму у хвилинах

    /** Внизу представлені функції для встановлення або отримання значення тої чи іншої змінної */
    public String getNameOfFilm() {
        return NameOfFilm;
    }
    public String getNameOfDirector() {
        return NameOfDirector;
    }
    public int getYear() {
        return Year;
    }
    public String[] getActors() {
        return Actors;
    }
    public int getNumberOfActors() {
        return NumberOfActors;
    }
    public String getBudgetString() {
        return Integer.toString(this.Budget);
    }
    public int getBudget(){
        return this.Budget;
    }
    public String getCountry() {
        return Country;
    }
    public int getDurationMin() {
        return DurationMin;
    }

    public void setNameOfFilm(String nameOfFilm) {
        NameOfFilm = nameOfFilm;
    }

    public void setNameOfDirector(String nameOfDirector) {
        NameOfDirector = nameOfDirector;
    }

    public void setYear(int year) {
        Year = year;
    }

    public void setActors(String[] actors) {
        Actors = actors;
    }

    public void setNumberOfActors(int numberOfActors) {
        NumberOfActors = numberOfActors;
    }

    public void setBudget(int budget) {
        Budget = budget;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public void setDurationMin(int durationMin) {
        DurationMin = durationMin;
    }

    /** Констуктор за завмовчуванням */

    public Film(){}

    /** Базовий конструктор */
    public Film(String NameOfFilm, String NameOfDirector, int Year, String[] Actors, int Budget, String Country, int DurationMin){
        this.NameOfFilm = NameOfFilm;
        this.NameOfDirector = NameOfDirector;
        this.Year = Year;
        this.Actors = Actors;
        this.NumberOfActors = Actors.length;
        this.Budget = Budget;
        this.Country = Country;
        this.DurationMin = DurationMin;
    }

    /** Конструктор копіювання */

    public Film(Film film){
        this.NameOfFilm = film.getNameOfFilm();
        this.NameOfDirector = film.getNameOfDirector();
        this.Year = film.getYear();
        this.Actors = film.getActors();
        int n = 0;
        while(Actors[n] != null){
            n++;
        }
        this.NumberOfActors = n;
        this.Budget = film.getBudget();
        this.Country = film.getCountry();
        this.DurationMin = film.getDurationMin();
    }

    /** Функція для визначення параметрів об'єкта окремо */

    public void SetFilm(String NameOfFilm, String NameOfDirector, int Year, String[] Actors, int Budget, String Country, int DurationMin, int NumberOfActors){
        this.NameOfFilm = NameOfFilm;
        this.NameOfDirector = NameOfDirector;
        this.Year = Year;
        this.Actors = Actors;
        this.NumberOfActors = NumberOfActors;
        this.Budget = Budget;
        this.Country = Country;
        this.DurationMin = DurationMin;
    }
}
