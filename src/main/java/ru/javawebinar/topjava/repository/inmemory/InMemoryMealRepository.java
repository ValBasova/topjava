package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    private static final Comparator<Meal> DATE_COMPARATOR = Comparator.comparing(Meal::getDateTime);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (userId == meal.getUserId()) {
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
                repository.put(meal.getId(), meal);
                return meal;
            }
            // handle case: update, but not present in storage
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        } else {
            return null;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        if (userId == repository.get(id).getUserId()) {
            return repository.remove(id) != null;
        } else return false;
    }

    @Override
    public Meal get(int id, int userId) {
        if (userId == repository.get(id).getUserId()) {
            return repository.get(id);
        } else return null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.values().
                stream().
                filter(meal -> userId == meal.getUserId()).
                sorted(DATE_COMPARATOR.reversed()).
                collect(Collectors.toList());
    }
}

