package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.Dao;
import ru.javawebinar.topjava.dao.InMemoryDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    private Dao dao = new InMemoryDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        Meal meal;
        if (id == null || id.length() == 0) {
            meal = new Meal();
        } else {
            meal = dao.read(Integer.parseInt(id));
        }
        meal.setDateTime(TimeUtil.parse(request.getParameter("dateTime")));
        meal.setDescription(request.getParameter("description"));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));
        if ((id == null || id.length() == 0)) {
            dao.create(meal);
        } else {
            dao.update(meal);
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        List<MealTo> mealToList = MealsUtil.toMealTo(dao.getAll(), 2000);
        String forward = "";
        String action = request.getParameter("action");
        String mealsJsp = "/meals.jsp";
        String addOrEditJsp = "/addOrEditMeal.jsp";
        Meal meal;
        if (action == null) {
            request.setAttribute("mealsList", mealToList);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }
        if (action.equalsIgnoreCase("Delete")) {
            dao.delete(Integer.parseInt(request.getParameter("id")));
            response.sendRedirect("meals");
            return;
        } else if (action.equalsIgnoreCase("Update")) {
            forward = addOrEditJsp;
            meal = dao.read(Integer.parseInt(request.getParameter("id")));
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("Add")) {
            forward = addOrEditJsp;
            meal = new Meal();
            request.setAttribute("meal", meal);
        }
        request.getRequestDispatcher(forward).forward(request, response);
    }
}