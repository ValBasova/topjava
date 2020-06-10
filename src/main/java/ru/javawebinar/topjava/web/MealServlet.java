package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.Dao;
import ru.javawebinar.topjava.dao.InMemoryDaoMeals;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    private Dao dao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dao = new InMemoryDaoMeals();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Integer id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException | NullPointerException e) {
            id = null;
        }
        Meal meal = new Meal(id,
                TimeUtil.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (id == null) {
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
            request.getRequestDispatcher(mealsJsp).forward(request, response);
            return;
        }
        switch (action.toLowerCase()) {
            case "delete":
                dao.delete(Integer.parseInt(request.getParameter("id")));
                response.sendRedirect("meals");
                return;
            case "update":
                forward = addOrEditJsp;
                meal = dao.read(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("meal", meal);
                break;
            case "add":
                forward = addOrEditJsp;
                meal = new Meal();
                request.setAttribute("meal", meal);
                break;
        }
        request.getRequestDispatcher(forward).forward(request, response);
    }
}