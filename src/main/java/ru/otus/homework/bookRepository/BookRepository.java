package ru.otus.homework.bookRepository;

import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.model.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, Long>, BookRepositoryCustom {
    Flux<Book> findAll();
    /*Mono<Book> findBookByName(String name);
    Mono<Book> findBookByGenres(Genre genre);
    Mono<Book> findBookByComments(Comment comment);*/
    Mono<Book> findBookByDatabaseId(ObjectId id);
    Mono<Long> removeBookByDatabaseId(ObjectId id);

    /*Mono<Book> findBookByAuthorSurname(String surname);
    Mono<Void> saveNewAuthor(ObjectId bookId, Author author);
    Mono<Void> deleteAuthor(ObjectId bookId, Author author);

    Mono<Void> saveNewGenre(ObjectId bookId, Genre genre);
    Mono<Void> deleteGenre(ObjectId bookId, Genre genre);*/

    Flux<Book> findCommentsByBook(String bookName);
    /*Mono<Double> getAverageMarkByBook(ObjectId bookId);
    Mono<Void> saveNewComment(ObjectId bookId, Comment comment);
    Mono<Void> deleteComment(ObjectId bookId, Comment comment);*/

    Mono<Book> saveNewBook(Book book);
    Mono<UpdateResult> updateBook(Book book);
}
