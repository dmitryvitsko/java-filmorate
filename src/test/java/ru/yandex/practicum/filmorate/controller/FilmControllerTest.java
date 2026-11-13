package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    private FilmController filmController;

    @BeforeEach
    public void setUp() {
        filmController = new FilmController();
    }

    @Test
    public void shouldCreateFilmWithValidData() {
        Film film = new Film();
        film.setName("Интерстеллар");
        film.setDescription("Крутой фильм про космос");
        film.setReleaseDate(LocalDate.of(2014, 11, 5));
        film.setDuration(169);

        Film savedFilm = filmController.create(film);

        assertEquals(1, savedFilm.getId());
        assertEquals(1, filmController.findAll().size());
    }

    @Test
    public void shouldThrowExceptionWhenNameIsBlank() {
        Film film = new Film();
        film.setName("   ");
        film.setDescription("Описание");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(120);

        assertThrows(ValidationException.class, () -> filmController.create(film));
        assertEquals(0, filmController.findAll().size());
    }

    @Test
    public void shouldThrowExceptionWhenDescriptionIsTooLong() {
        Film film = new Film();
        film.setName("Название");
        film.setDescription("a".repeat(201));
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(120);

        assertThrows(ValidationException.class, () -> filmController.create(film));
        assertEquals(0, filmController.findAll().size());
    }

    @Test
    public void shouldThrowExceptionWhenReleaseDateIsTooOld() {
        Film film = new Film();
        film.setName("Название");
        film.setDescription("Описание");
        film.setReleaseDate(LocalDate.of(1800, 1, 1));
        film.setDuration(120);

        assertThrows(ValidationException.class, () -> filmController.create(film));
        assertEquals(0, filmController.findAll().size());
    }

    @Test
    public void shouldThrowExceptionWhenDurationIsNegative() {
        Film film = new Film();
        film.setName("Название");
        film.setDescription("Описание");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(-100);

        assertThrows(ValidationException.class, () -> filmController.create(film));
        assertEquals(0, filmController.findAll().size());
    }
}