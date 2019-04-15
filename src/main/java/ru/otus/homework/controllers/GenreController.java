package ru.otus.homework.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework.dto.GenreDto;
import ru.otus.homework.libraryService.LibraryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GenreController {
    private final LibraryService libraryService;

    public GenreController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/api/genres")
    public List<GenreDto> listGenre() {
        return libraryService.getAllGenres().stream().map(GenreDto::toDto).collect(Collectors.toList());
    }
}