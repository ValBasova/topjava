package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Dao {

    Object create(Meal meal);

    Meal read(Integer id);

    Object update(Meal meal);

    void delete(Integer id);

    List<Meal> getAll();
}
