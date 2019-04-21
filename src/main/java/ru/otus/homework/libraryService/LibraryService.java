package ru.otus.homework.libraryService;

import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Genre;

public interface LibraryService {

    Flux<Author> getAllAuthors();

    Flux<Genre> getAllGenres();

    Flux<Book> getAllBooks();

    Mono<Book> addBook(Book book);

    Mono<Long> removeBook(ObjectId id);

    Flux<Book> getAllCommentsByBook(String name);

    Mono<Book> getBookById(ObjectId id);

    Mono<UpdateResult> updateBook(Book book);
}
