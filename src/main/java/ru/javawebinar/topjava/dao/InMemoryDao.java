package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryDao implements Dao {
    private static AtomicInteger counter = new AtomicInteger(0);
    private static List<Meal> mealList = Collections.synchronizedList(MealsUtil.MEALS_LIST);

    static {
        for (Meal meal : mealList) {
            meal.setId(counter.incrementAndGet());
        }
    }

    @Override
    public void create(Meal meal) {
        if (!mealList.contains(meal)) {
            Meal newMeal = new Meal(meal.getDateTime(), meal.getDescription(), meal.getCalories());
            newMeal.setId(counter.incrementAndGet());
            mealList.add(newMeal);
        }
    }

    @Override
    public Meal read(Integer id) {
        return mealList.get(findIndex(id));
    }

    @Override
    public void update(Meal meal) {
        mealList.set(findIndex(meal.getId()), meal);
    }

    @Override
    public void delete(Integer id) {
        mealList.remove(mealList.get(findIndex(id)));
    }

    @Override
    public List<Meal> getAll() {
        return mealList;
    }

    private int findIndex(Integer id) {
        for (int i = 0; i < mealList.size(); i++) {
            if (mealList.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}
