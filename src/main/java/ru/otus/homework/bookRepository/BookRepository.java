package ru.otus.homework.bookRepository;

import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.model.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, Long>, BookRepositoryCustom {
    Flux<Book> findAll();
    Mono<Book> findBookByDatabaseId(ObjectId id);
    Mono<Long> removeBookByDatabaseId(ObjectId id);

    Flux<Book> findBooksByNameAndAllCommentsToFindingBooks(String bookName);
    Mono<Book> saveNewBook(Book book);
    Mono<UpdateResult> updateBook(Book book);
}
