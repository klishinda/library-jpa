package ru.otus.homework.bookRepository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, Long>, BookRepositoryCustom {
    List<Book> findAll();
    Book findBookByName(String name);
    Book findBookByGenres(Genre genre);
    Book findBookByComments(Comment comment);
    Book findBookByDatabaseId(ObjectId id);
    void removeBookByDatabaseId(ObjectId id);

    Book findBookByAuthorSurname(String surname);
    void saveNewAuthor(ObjectId bookId, Author author);
    void deleteAuthor(ObjectId bookId, Author author);

    void saveNewGenre(ObjectId bookId, Genre genre);
    void deleteGenre(ObjectId bookId, Genre genre);

    List<Book> findCommentsByBook(String bookName);
    Double getAverageMarkByBook(ObjectId bookId);
    void saveNewComment(ObjectId bookId, Comment comment);
    void deleteComment(ObjectId bookId, Comment comment);

    void saveNewBook(Book book);
    void updateBook(Book book);
}
