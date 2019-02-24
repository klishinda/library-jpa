package ru.otus.homework.GenreRepository;

import ru.otus.homework.model.Genre;

import java.util.List;

public interface GenreRepository {
    Genre getById(Long id);
    List<Genre> getAllGenres();
    void addGenre(Genre genre);
    void removeGenre(Long id);
}
