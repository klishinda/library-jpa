package ru.otus.homework.libraryService;

import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Genre;

public interface LibraryService {
    //Set<Author> getAuthorByName(String name);

    Flux<Author> getAllAuthors();

    /*void addAuthor(ObjectId bookId, Author author);

    void removeAuthor(ObjectId bookId, Author author);*/

    Flux<Genre> getAllGenres();

    /*void addGenre(ObjectId bookId, Genre genre);

    void removeGenre(ObjectId bookId, Genre genre);

    Set<Comment> getAllComments();

    void addComment(ObjectId bookId, Comment comment);

    void removeComment(ObjectId bookId, Comment comment);*/

    Flux<Book> getAllBooks();

    Mono<Book> addBook(Book book);

    Mono<Long> removeBook(ObjectId id);

    //Double getAverageMarkByBook(ObjectId id);

    Flux<Book> getAllCommentsByBook(String name);

    //Book getBookByName(String name);

    Mono<Book> getBookById(ObjectId id);

    Mono<UpdateResult> updateBook(Book book);
}
