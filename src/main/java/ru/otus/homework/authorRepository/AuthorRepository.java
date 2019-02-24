package ru.otus.homework.authorRepository;

import ru.otus.homework.model.Author;

import java.util.List;

public interface AuthorRepository {
    List<Author> getUnusedAuthors();
    Author getById(Long id);
    List<Author> getAllAuthors();
    void addAuthor(Author author);
    void removeAuthor(Long id);
}
