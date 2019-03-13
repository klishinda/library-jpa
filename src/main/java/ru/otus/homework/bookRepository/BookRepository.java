package ru.otus.homework.bookRepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.model.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, Long> {
    List<Book> findAll();
}
