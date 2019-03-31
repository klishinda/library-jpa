package ru.otus.homework.bookRepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, Long> {
    List<Book> findAll();
    Book findBookByName(String name);
    Book findBookByGenres(Genre genre);
    Book findBookByComments(Comment comment);
}
