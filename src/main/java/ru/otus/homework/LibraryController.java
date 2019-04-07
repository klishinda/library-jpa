package ru.otus.homework;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.dto.AuthorDto;
import ru.otus.homework.dto.BookCommentsDto;
import ru.otus.homework.dto.GenreDto;
import ru.otus.homework.libraryService.LibraryService;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;

import java.util.List;
import java.util.Set;
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
    public List<BookCommentsDto> listBook(@ModelAttribute(value="name") String name) {
        return libraryService.getAllCommentsByBook(name).stream().map(BookCommentsDto::toDto).collect(Collectors.toList());
    }
}
