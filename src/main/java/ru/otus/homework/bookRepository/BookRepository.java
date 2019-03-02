package ru.otus.homework.bookRepository;

import ru.otus.homework.model.Book;

import java.util.List;

public interface BookRepository {
    Book getById(Long id);
    List<Book> getAllBooks();
    void addBook(Book book);
    void removeBook(Long id);
    Double getAverageMarkByBook(Long id);
    List<Book> getAllCommentsByBook(String bookName);
}
