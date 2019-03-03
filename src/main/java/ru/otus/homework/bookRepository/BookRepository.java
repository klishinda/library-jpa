package ru.otus.homework.bookRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.homework.model.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {
    Book findBookById(Long id);
    List<Book> findAll();
    @Query(value = "select AVG(c.mark) from book_comments c LEFT JOIN books b ON  b.id = c.id_book WHERE b.id = :id", nativeQuery = true)
    Double getAverageMarkByBook(@Param("id") Long id);
    @Query(value = "select b from Book b WHERE b.name LIKE %:name%")
    List<Book> getAllCommentsByBook(@Param("name") String bookName);
    Book findBookByName(String bookName);
}
