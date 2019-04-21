package ru.otus.homework.controllers;


import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.dto.BookDto;
import ru.otus.homework.libraryService.LibraryService;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;

import java.util.*;

import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@WebFluxTest(BookController.class)
public class BookControllerTest {

    @MockBean
    private LibraryService libraryService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void listBook() {
        Book newBook1 = new Book("Test1", 1);
        Book newBook2 = new Book("Test2", 2);
        List<Book> bookList = Arrays.asList(newBook1, newBook2);

        when(libraryService.getAllBooks()).thenReturn(Flux.fromIterable(bookList));
        webTestClient.get().uri("/api/books")
                     .exchange()
                     .expectStatus().isOk()
                     .expectBodyList(Book.class)
                     .contains(newBook1)
                     .contains(newBook2);
    }

    @Test
    public void addBook() {
        Book newBook = new Book("Test1", 1);
        when(libraryService.addBook(newBook)).thenReturn(Mono.just(newBook));
        webTestClient.post().uri("/api/books")
                     .body(Mono.just(newBook), Book.class)
                     .exchange()
                     .expectStatus().isOk();
    }

    @Test
    public void deleteBook() {
        ObjectId id = new ObjectId();
        when(libraryService.removeBook(id)).thenReturn(Mono.empty());
        webTestClient.delete().uri("/api/books/" + id)
                     .exchange()
                     .expectStatus().isOk();
    }
}
