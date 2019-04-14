package ru.otus.homework.libraryService;

import org.bson.types.ObjectId;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;

import java.util.List;
import java.util.Set;

public interface LibraryService {
    Set<Author> getAuthorByName(String name);

    Set<Author> getAllAuthors();

    void addAuthor(ObjectId bookId, Author author);

    void removeAuthor(ObjectId bookId, Author author);

    Set<Genre> getAllGenres();

    void addGenre(ObjectId bookId, Genre genre);

    void removeGenre(ObjectId bookId, Genre genre);

    Set<Comment> getAllComments();

    void addComment(ObjectId bookId, Comment comment);

    void removeComment(ObjectId bookId, Comment comment);

    List<Book> getAllBooks();

    void addBook(String title, int pages, Set<Author> authors, Set<Genre> genres);

    void removeBook(ObjectId id);

    Double getAverageMarkByBook(ObjectId id);

    List<Book> getAllCommentsByBook(String name);

    Book getBookByName(String name);

    Book getBookById(ObjectId id);

    void updateBook(Book book);
}
