package ru.otus.homework;

import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.dto.*;
import ru.otus.homework.libraryService.LibraryService;
import ru.otus.homework.model.Book;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LibraryController {

    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/api/all-authors")
    public List<AuthorDto> listAuthor() {
        return libraryService.getAllAuthors().stream().map(AuthorDto::toDto).collect(Collectors.toList());
    }

    @GetMapping("/api/all-genres")
    public List<GenreDto> listGenre() {
        return libraryService.getAllGenres().stream().map(GenreDto::toDto).collect(Collectors.toList());
    }

    @GetMapping("/api/all-comments-by-book")
    public List<BookCommentsDto> listComments(@RequestParam("name") String name) {
        return libraryService.getAllCommentsByBook(name).stream().map(BookCommentsDto::toDto).collect(Collectors.toList());
    }

    @GetMapping("/api/comments-by-book-id")
    public List<CommentsDto> listCommentsByBookId(@RequestParam("id") ObjectId id) {
        Book book = libraryService.getBookById(id);
        return book.getComments().stream().map(CommentsDto::toDto).collect(Collectors.toList());
    }

    @GetMapping("/api/books")
    public List<BookDto> listBook() {
        return libraryService.getAllBooks().stream().map(BookDto::toDto).collect(Collectors.toList());
    }

    @PostMapping("/api/books")
    public void addBook(@ModelAttribute Book book) {
        libraryService.addBook(book.getName(), book.getPages());
    }

    @PutMapping("/api/books")
    public void updateBook(@ModelAttribute Book book) {
        libraryService.updateBook(book);
    }

    @DeleteMapping("/api/books")
    public void deleteBook(@RequestParam("id") String id) {
        libraryService.removeBook(new ObjectId(id));
    }
}