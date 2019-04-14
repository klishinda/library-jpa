package ru.otus.homework.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework.dto.AuthorDto;
import ru.otus.homework.libraryService.LibraryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthorController {
    private final LibraryService libraryService;

    public AuthorController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/api/authors")
    public List<AuthorDto> listAuthor() {
        return libraryService.getAllAuthors().stream().map(AuthorDto::toDto).collect(Collectors.toList());
    }
}
