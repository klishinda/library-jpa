package ru.otus.homework.bookRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.homework.GenreRepository.GenreRepositoryJpa;
import ru.otus.homework.authorRepository.AuthorRepositoryJpa;
import ru.otus.homework.commentRepository.CommentRepositoryJpa;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Genre;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@JdbcTest
@AutoConfigureTestDatabase(replace = NONE)
@Import({AuthorRepositoryJpa.class, GenreRepositoryJpa.class, CommentRepositoryJpa.class, BookRepositoryJpa.class})
@AutoConfigureTestEntityManager
//@EntityScan( basePackages = {"model"} )
public class BookRepositoryJpaTest {

    private void addNewBook() {
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre("Genre1"));
        genres.add(new Genre("Genre2"));
        Set<Author> authors = new HashSet<>();
        authors.add(new Author("Surname", "Name"));
        authors.add(new Author("Surname2", "Name2"));
        //libraryService.addBook("Test book", 1000, authors, genres);
        Book book = new Book("Test book", 1000, authors, genres);
        entityManager.persist(book);
        entityManager.flush();
    }

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void getAllBooks() {
        addNewBook();
    }
}
