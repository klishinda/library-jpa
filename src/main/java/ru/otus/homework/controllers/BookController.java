package ru.otus.homework.controllers;

import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.dto.BookCommentsDto;
import ru.otus.homework.dto.BookDto;
import ru.otus.homework.dto.CommentsDto;
import ru.otus.homework.integrationController.book.BookGateway;
import ru.otus.homework.libraryService.LibraryService;
import ru.otus.homework.model.Book;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {
    private LibraryService libraryService;
    private BookGateway bookGateway;

    public BookController(LibraryService libraryService, BookGateway bookGateway) {
        this.libraryService = libraryService;
        this.bookGateway = bookGateway;
    }

    @GetMapping("/api/books")
    public List<BookDto> listBook() {
        return libraryService.getAllBooks().stream().map(BookDto::toDto).collect(Collectors.toList());
    }

    @GetMapping("/api/books/{id}")
    public BookDto getBook(@PathVariable("id") ObjectId id) {
        return BookDto.toDto(libraryService.getBookById(id));
    }

    @PostMapping("/api/books")
    public void addBook(@RequestBody Book book) {
        bookGateway.addBook(book);
    }

    @PutMapping("/api/books")
    public void updateBook(@ModelAttribute Book book) {
        bookGateway.updateBook(book);
    }

    @DeleteMapping("/api/books/{id}")
    public void deleteBook(@PathVariable("id") String id) {
        bookGateway.removeBook(new ObjectId(id));
    }

    @GetMapping("/api/books/{id}/comments")
    public List<CommentsDto> listCommentsByBookId(@PathVariable("id") ObjectId id) {
        Book book = libraryService.getBookById(id);
        return book.getComments().stream().map(CommentsDto::toDto).collect(Collectors.toList());
    }

    @GetMapping("/api/books/{name}/book-comments")
    public List<BookCommentsDto> listComments(@PathVariable("name") String name) {
        return libraryService.getAllCommentsByBook(name).stream().map(BookCommentsDto::toDto).collect(Collectors.toList());
    }
}
