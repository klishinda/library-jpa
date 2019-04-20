package ru.otus.homework.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.homework.dto.AuthorDto;
import ru.otus.homework.libraryService.LibraryService;

@RestController
public class AuthorController {
    private final LibraryService libraryService;

    public AuthorController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/api/authors")
    public Flux<AuthorDto> listAuthor() {
        return libraryService.getAllAuthors().map(AuthorDto::toDto);
    }
}
