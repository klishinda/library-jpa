package ru.otus.homework.GenreRepository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.model.Genre;

import java.util.List;

public interface GenreRepository extends CrudRepository<Genre, Long> {
    Genre findGenreById(Long id);
    List<Genre> findAll();
}
