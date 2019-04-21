package ru.otus.homework.bookRepository;

import com.mongodb.client.result.UpdateResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Genre;

public interface BookRepositoryCustom {
    Flux<Book> findBooksByNameAndAllCommentsToFindingBooks(String bookName);
    Mono<Book> saveNewBook(Book book);
    Mono<UpdateResult> updateBook(Book book);

    Flux<Author> findAuthorsFromAllBooks();
    Flux<Genre> findGenresFromAllBooks();
}
