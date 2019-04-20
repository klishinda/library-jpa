package ru.otus.homework.controllers;

import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.dto.BookCommentsDto;
import ru.otus.homework.dto.BookDto;
import ru.otus.homework.libraryService.LibraryService;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;

@RestController
public class BookController {
    private final LibraryService libraryService;

    public BookController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/api/books")
    public Flux<BookDto> listBook() {
        return libraryService.getAllBooks().map(BookDto::toDto);
    }

    @PostMapping(value = "/api/books", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Book>> addBook(@RequestBody Book book) {
        Book newBook = new Book(book.getName(), book.getPages(), book.getAuthors(), book.getGenres());
        return Mono.just(newBook).flatMap(this.libraryService::addBook).map(ResponseEntity::ok);
    }

    @PutMapping("/api/books")
    public Mono<Boolean> updateBook(@ModelAttribute Book book) {
        return Mono.just(book).flatMap(this.libraryService::updateBook).map(UpdateResult::wasAcknowledged);
    }

    @DeleteMapping("/api/books/{id}")
    public Mono<ResponseEntity> deleteBook(@PathVariable("id") String id) {
        return libraryService.removeBook(new ObjectId(id)).map(ResponseEntity::ok);
    }

    @GetMapping("/api/books/{id}/comments")
    public Flux<Comment> listCommentsByBookId(@PathVariable("id") ObjectId id) {
        Mono<Book> book = libraryService.getBookById(id);
        return book.map(Book::getComments).flatMapMany(Flux::fromIterable);
    }

    @GetMapping("/api/books/{name}/book-comments")
    public Flux<BookCommentsDto> listComments(@PathVariable("name") String name) {
        return libraryService.getAllCommentsByBook(name).map(BookCommentsDto::toDto);
    }
}
