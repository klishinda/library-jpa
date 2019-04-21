package ru.otus.homework.bookRepository;

import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Genre;

import java.util.List;
import java.util.Set;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
@Transactional
public class BookRepositoryImpl implements BookRepositoryCustom{
    private final ReactiveMongoTemplate mongoTemplate;

    @Autowired
    public BookRepositoryImpl(ReactiveMongoTemplate  mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Flux<Book> findBooksByNameAndAllCommentsToFindingBooks(String bookName) {
        Query query = new Query(Criteria.where("name").regex(bookName).and("comments").ne(null));
        return mongoTemplate.find(query, Book.class);
    }

    @Override
    public Mono<Book> saveNewBook(Book book) {
        return mongoTemplate.save(book);
    }

    @Override
    public Mono<UpdateResult> updateBook(Book book) {
        Query query = new Query(Criteria.where("_id").is(book.getDatabaseId()));
        Update update = new Update();
        update.set("name", book.getName());
        update.set("pages", book.getPages());

        return mongoTemplate.updateFirst(query, update, Book.class);
    }

    @Override
    public Flux<Author> findAuthorsFromAllBooks() {
        return mongoTemplate.aggregate(
                newAggregation(unwind("authors"), project().andInclude("authors.name").andInclude("authors.surname")), Book.class, Author.class);
    }

    @Override
    public Flux<Genre> findGenresFromAllBooks() {
        return mongoTemplate.aggregate(
                newAggregation(unwind("genres"), project().andInclude("genres.name")), Book.class, Genre.class);
    }
}
