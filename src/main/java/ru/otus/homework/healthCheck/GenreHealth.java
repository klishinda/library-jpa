package ru.otus.homework.healthCheck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.otus.homework.libraryService.LibraryService;
import ru.otus.homework.model.Genre;

import java.util.Set;
import java.util.regex.Pattern;

@Component
public class GenreHealth implements HealthIndicator {
    private final LibraryService libraryService;

    @Autowired
    public GenreHealth(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @Override
    public Health health() {
        try {
            Set<Genre> allGenres = libraryService.getAllGenres();
            if (allGenres == null || allGenres.size() == 0) {
                return Health.up().withDetail("All genres in library are empty!", 0).build();
            }

            if (allGenres.stream().anyMatch(g -> Pattern.compile("[~#@*+?%{}<>|_^]").matcher(g.getName()).find())) {
                return Health.up().withDetail("Genres have forbidden symbols!", 0).build();
            }

            return Health.up().build();
        } catch (Exception e) {
            return Health.down().withException(e).build();
        }
    }
}