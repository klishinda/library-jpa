package ru.otus.homework.healthCheck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.otus.homework.libraryService.LibraryService;
import ru.otus.homework.model.Book;

import java.util.List;

@Component
public class BookHealth implements HealthIndicator {
    private final LibraryService libraryService;

    @Autowired
    public BookHealth(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @Override
    public Health health() {
        try {
            List<Book> allBooks = libraryService.getAllBooks();
            if (allBooks == null || allBooks.size() == 0) {
                return Health.up().withDetail("No books in library!", 0).build();
            }
            return Health.up().build();
        } catch (Exception e) {
            return Health.down().withException(e).build();
        }
    }
}
