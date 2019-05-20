package ru.otus.homework.controllers;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework.dto.GenreDto;
import ru.otus.homework.libraryService.LibraryService;
import ru.otus.homework.model.Genre;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class GenreController {
    private final LibraryService libraryService;

    public GenreController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/api/genres")
    @HystrixCommand(fallbackMethod = "reserveGenre")
    public List<GenreDto> listGenre() {
        return libraryService.getAllGenres().stream().map(GenreDto::toDto).collect(Collectors.toList());
    }

    @HystrixCommand
    public List<GenreDto> reserveGenre() {
        return Stream.of(new Genre("Genre Name")).map(GenreDto::toDto).collect(Collectors.toList());
    }
}