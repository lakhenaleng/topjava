package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(10, 0), LocalTime.of(13, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles

        List<UserMealWithExcess> userMealsWithExcess = new ArrayList<>();

//        keep track of calorie count for each day

        HashMap<LocalDate, Integer> calorieCountPerDay = new HashMap<>();

        for (UserMeal meal : meals) {

            LocalDate date = meal.getDateTime().toLocalDate();
            if (!calorieCountPerDay.containsKey(date)) {
                calorieCountPerDay.put(date, 0);
            }

            calorieCountPerDay.put(date, calorieCountPerDay.get(date) + meal.getCalories());
        }

//        add meals between filters to list and excess flag

        for (UserMeal meal : meals) {

            int totalCaloriesPerDate = calorieCountPerDay.get(meal.getDateTime().toLocalDate());

            boolean excessFlag = totalCaloriesPerDate > caloriesPerDay;

            boolean isAfterInclusive = meal.getDateTime().toLocalTime().isAfter(startTime) || meal.getDateTime().toLocalTime().equals(startTime);
            boolean isBeforeExclusive = meal.getDateTime().toLocalTime().isBefore(endTime);

            if (isAfterInclusive && isBeforeExclusive) {
                userMealsWithExcess.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excessFlag));
            }
        }

        return userMealsWithExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }
}
