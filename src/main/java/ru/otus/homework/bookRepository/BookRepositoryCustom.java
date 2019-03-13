package ru.otus.homework.bookRepository;

import org.bson.types.ObjectId;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;

import java.util.List;

public interface BookRepositoryCustom {
    Book findBookByAuthorSurname(String surname);
    void saveNewAuthor(ObjectId bookId, Author author);
    void deleteAuthor(ObjectId bookId, Author author);

    void saveNewGenre(ObjectId bookId, Genre genre);
    void deleteGenre(ObjectId bookId, Genre genre);

    List<Book> findCommentsByBook(String bookName);
    Double getAverageMarkByBook(ObjectId bookId);
    void saveNewComment(ObjectId bookId, Comment comment);
    void deleteComment(ObjectId bookId, Comment comment);


    void deleteBook(ObjectId bookId);
    void saveNewBook(Book book);
}
