package ru.practicum.mapper;

import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ModelMapper {
    public <R, E> R doMapping(E element, R returner) {
        return mapping(element, returner);
    }

    public <R, E> List<R> doListMapping(Collection<E> elements, Class<R> rClass) {
        return elements.stream()
                .map(e -> {
                    try {
                        Constructor<R> constructor = rClass.getConstructor();
                        return mapping(e, constructor.newInstance());
                    } catch (NoSuchMethodException ex) {
                        throw new RuntimeException(ex);
                    } catch (InvocationTargetException ex) {
                        throw new RuntimeException(ex);
                    } catch (InstantiationException ex) {
                        throw new RuntimeException(ex);
                    } catch (IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .collect(Collectors.toList());
    }

    private <R, E> R mapping(E element, R returner) {
        Class<?> eClass = element.getClass();
        Class<?> rClass = returner.getClass();

        Field[] elementFields = eClass.getDeclaredFields();
        Field[] returnerFields = rClass.getDeclaredFields();

        Arrays.stream(elementFields)
                .filter(Objects::nonNull)
                .forEach(e -> {
                    e.setAccessible(true);
                    Arrays.stream(returnerFields)
                            .filter(r -> r.getName().equals(e.getName()))
                            .forEach(r -> {
                                r.setAccessible(true);
                                try {
                                    r.set(returner, e.get(element));
                                } catch (IllegalAccessException ex) {
                                    throw new RuntimeException(ex);
                                }
                            });
                });
        return returner;
    }
}