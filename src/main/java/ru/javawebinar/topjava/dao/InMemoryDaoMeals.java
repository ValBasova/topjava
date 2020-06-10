package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryDaoMeals implements DaoMeal {
    private AtomicInteger counter = new AtomicInteger(0);
    private Map<Integer, Meal> mealsMap = new ConcurrentHashMap<>();

    {
        for (Meal meal : MealsUtil.MEALS_LIST) {
            create(meal);
        }
    }

    @Override
    public Meal create(Meal meal) {
        meal.setId(counter.incrementAndGet());
        mealsMap.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal read(Integer id) {
        return mealsMap.get(id);
    }

    @Override
    public Meal update(Meal meal) {
        return mealsMap.computeIfPresent(meal.getId(), (key, value) -> meal);
    }

    @Override
    public void delete(Integer id) {
        mealsMap.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealsMap.values());
    }
}
