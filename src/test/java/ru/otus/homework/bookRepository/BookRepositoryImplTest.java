package ru.otus.homework.bookRepository;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookRepositoryImplTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void aTest() {
        DBObject objectToSave = BasicDBObjectBuilder.start().add("key", "value").get();
        mongoTemplate.save(objectToSave, "collection");
        assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("key").containsOnly("value");
    }

    @Test
    @BeforeEach
    public void bTestNewBook() {
        Book testBook = bookRepository.findBookByName("Test Book");
        if (testBook != null) {
            bookRepository.removeBookByDatabaseId(testBook.getDatabaseId());
        }
        bookRepository.saveNewBook(new Book("Test Book", 999));
        testBook = bookRepository.findBookByName("Test Book");
        assertThat(testBook.getPages()).isEqualTo(999);
    }

    @Test
    public void testNewAuthor() {
        Book testBook = bookRepository.findBookByName("Test Book");

        Author testAuthor = new Author("Test", "Author");
        bookRepository.saveNewAuthor(testBook.getDatabaseId(), testAuthor);
        testBook = bookRepository.findBookByAuthorSurname("Test");
        assertThat(testBook.getPages()).isEqualTo(999);
        bookRepository.deleteAuthor(testBook.getDatabaseId(), testAuthor);
        testBook = bookRepository.findBookByAuthorSurname("Test");
        assertThat(testBook).isNull();
    }

    @Test
    public void testNewGenre() {
        Book testBook = bookRepository.findBookByName("Test Book");

        Genre testGenre = new Genre("Test Genre");
        bookRepository.saveNewGenre(testBook.getDatabaseId(), testGenre);
        testBook = bookRepository.findBookByGenres(testGenre);
        assertThat(testBook.getPages()).isEqualTo(999);
        bookRepository.deleteGenre(testBook.getDatabaseId(), testGenre);
        testBook = bookRepository.findBookByGenres(testGenre);
        assertThat(testBook).isNull();
    }

    @Test
    public void testNewComment() {
        Book testBook = bookRepository.findBookByName("Test Book");

        Comment testComment = new Comment((byte) 6, "TestUser", "TestComment", new Date());
        bookRepository.saveNewComment(testBook.getDatabaseId(), testComment);
        testBook = bookRepository.findBookByComments(testComment);
        assertThat(testBook.getPages()).isEqualTo(999);
        bookRepository.deleteComment(testBook.getDatabaseId(), testComment);
        testBook = bookRepository.findBookByComments(testComment);
        assertThat(testBook).isNull();
    }
}