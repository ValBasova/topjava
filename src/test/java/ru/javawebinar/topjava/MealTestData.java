package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

public class MealTestData {
    public static final Meal MEAL1 = new Meal(100002, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 500);
    public static final Meal MEAL2 = new Meal(100003, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL3 = new Meal(100004, LocalDateTime.of(2020, Month.JANUARY, 29, 15, 0), "Обед", 1000);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2020, Month.JUNE, 30, 9, 0), "New breakfast", 10000);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("Updated Description");
        updated.setCalories(333);
        updated.setDateTime(LocalDateTime.of(2020, Month.MAY, 9, 9, 0));
        return updated;
    }
}
