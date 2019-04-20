package ru.otus.homework.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.homework.dto.GenreDto;
import ru.otus.homework.libraryService.LibraryService;

@RestController
public class GenreController {
    private final LibraryService libraryService;

    public GenreController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/api/genres")
    public Flux<GenreDto> listGenre() {
        return libraryService.getAllGenres().map(GenreDto::toDto);
    }
}