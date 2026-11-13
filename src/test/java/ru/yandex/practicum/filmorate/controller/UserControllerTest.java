package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    private UserController userController;

    @BeforeEach
    public void setUp() {
        userController = new UserController();
    }

    @Test
    public void shouldCreateUserWithValidData() {
        User user = new User();
        user.setEmail("test@mail.ru");
        user.setLogin("testLogin");
        user.setName("Иван");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        User savedUser = userController.create(user);

        assertEquals(1, savedUser.getId());
        assertEquals(1, userController.findAll().size());
    }

    @Test
    public void shouldThrowExceptionWhenEmailDoesNotContainAtSymbol() {
        User user = new User();
        user.setEmail("testmail.ru");
        user.setLogin("testLogin");
        user.setName("Иван");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        assertThrows(ValidationException.class, () -> userController.create(user));
        assertEquals(0, userController.findAll().size());
    }

    @Test
    public void shouldThrowExceptionWhenLoginContainsSpace() {
        User user = new User();
        user.setEmail("test@mail.ru");
        user.setLogin("test login");
        user.setName("Иван");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        assertThrows(ValidationException.class, () -> userController.create(user));
        assertEquals(0, userController.findAll().size());
    }

    @Test
    public void shouldThrowExceptionWhenBirthdayIsInFuture() {
        User user = new User();
        user.setEmail("test@mail.ru");
        user.setLogin("testLogin");
        user.setName("Иван");
        user.setBirthday(LocalDate.now().plusDays(1));

        assertThrows(ValidationException.class, () -> userController.create(user));
        assertEquals(0, userController.findAll().size());
    }

    @Test
    public void shouldUseLoginAsNameWhenNameIsEmpty() {
        User user = new User();
        user.setEmail("test@mail.ru");
        user.setLogin("testLogin");
        user.setName("");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        User savedUser = userController.create(user);

        assertEquals("testLogin", savedUser.getName());
        assertEquals(1, userController.findAll().size());
    }
}