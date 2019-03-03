package ru.otus.homework.bookRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Genre;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryImplTest {

    @Autowired private TestEntityManager entityManager;
    @Autowired private BookRepository repository;

    private Book addNewBook() {
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre("Genre1"));
        genres.add(new Genre("Genre2"));
        Set<Author> authors = new HashSet<>();
        authors.add(new Author("Surname", "Name"));
        authors.add(new Author("Surname2", "Name2"));
        return new Book("Test book", 1000, authors, genres);
    }

    @Test
    public void getAllBooks() {
        Book newBook = addNewBook();
        entityManager.persist(newBook);

        List<Book> books = repository.findAll();
        assertThat(books.get(0).getName()).isEqualTo("Восточный экспресс");
        assertThat(books.get(1).getPages()).isEqualTo(700);
    }

    @Test
    public void findBookById() {
        Book book = repository.findBookById(3L);
        assertThat(book.getName()).isEqualTo("Координаты чудес света");
    }

    @Test
    public void getAverageMarkByBook() {
        Book newBook = addNewBook();
        entityManager.persist(newBook);

        Book book = repository.findBookByName("Test book");
        Double mark = repository.getAverageMarkByBook(book.getId());
        assertThat(mark).isEqualTo(null);

        book = repository.findBookByName("Восточный экспресс");
        mark = repository.getAverageMarkByBook(book.getId());
        assertThat(mark).isEqualTo(5.5);
    }

    @Test
    public void getAllCommentsByBook() {
        List<Book> books = repository.getAllCommentsByBook("ост");
        assertThat(books.get(0).getComments().size()).isEqualTo(2);
    }

    @Test
    public void save() {
        Book newBook = addNewBook();

        repository.save(newBook);
        Book book = repository.findBookByName("Test book");
        assertThat(book.getName()).isEqualTo("Test book");
    }

    @Test
    public void deleteById() {
        Book newBook = addNewBook();
        entityManager.persist(newBook);

        Book book = repository.findBookByName("Test book");
        assertThat(book.getName()).isEqualTo("Test book");

        repository.deleteById(book.getId());
        book = repository.findBookByName("Test book");
        assertThat(book).isEqualTo(null);
    }
}