package ru.practicum.common.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ReflectionChange {
    public static <T1, T2> T1 go(T1 goal, T2 hunter) {
        Field[] declaredFieldsHunter = hunter.getClass().getDeclaredFields();
        Field[] declaredFieldsGoal = goal.getClass().getDeclaredFields();
        List<Field> notNullFields = getNotNullFields(declaredFieldsHunter, hunter);

        notNullFields.stream().forEach(e -> {
            Arrays.stream(declaredFieldsGoal).filter(x -> x.getName().equals(e.getName())).forEach(r -> {
                if (checkTypes(e.getType(), r.getType())) {
                    e.setAccessible(true);
                    r.setAccessible(true);
                    try {
                        r.set(goal, e.get(hunter));
                    } catch (IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        });
        return goal;
    }

    private static <T> List<Field> getNotNullFields(Field[] fields, T type) {
        return Arrays.stream(fields).filter(e -> {
            e.setAccessible(true);
            try {
                return Objects.nonNull(e.get(type));
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }).collect(Collectors.toList());
    }

    private static boolean checkTypes(Class<?> aType, Class<?> bType) {
        return aType == bType;
    }
}