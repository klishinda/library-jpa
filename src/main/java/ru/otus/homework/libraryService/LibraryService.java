package ru.otus.homework.libraryService;

import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;

import java.util.List;

public interface LibraryService {
    List<Author> getUnusedAuthors();

    Author getAuthorById(Long id);

    List<Author> getAllAuthors();

    void addAuthor(Author author);

    void removeAuthor(Long id);

    Genre getGenreById(Long id);

    List<Genre> getAllGenres();

    void addGenre(Genre genre);

    void removeGenre(Long id);

    Comment getCommentById(Long id);

    List<Comment> getAllComments();

    void addComment(Comment comment);

    void removeComment(Long id);

    Book getBookById(Long id);

    List<Book> getAllBooks();

    void addBook(String title, int pages, List<Long> idAuthors, List<Long> idGenres);

    void removeBook(Long id);

    Double getAverageMarkByBook(Long id);

    List<Book> getAllCommentsByBook(String name);
}
