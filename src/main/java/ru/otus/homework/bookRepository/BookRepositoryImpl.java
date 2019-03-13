package ru.otus.homework.bookRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

//import org.springframework.data.mongodb.repository.Query;

@Repository
@Transactional
public class BookRepositoryImpl implements BookRepositoryCustom{
    private final MongoTemplate mongoTemplate;

    @Autowired
    public BookRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Book findBookByAuthorSurname(String surname) {
        Query query = new Query(Criteria.where("authors.surname").is(surname));
        return mongoTemplate.findOne(query, Book.class);
    }

    @Override
    public void saveNewAuthor(ObjectId bookId, Author author) {
        Book book = getBookById(bookId);

        book.getAuthors().add(author);
        mongoTemplate.save(book);
    }

    @Override
    public void deleteAuthor(ObjectId bookId, Author author) {
        Book book = getBookById(bookId);

        book.getAuthors().removeIf(a -> a.getSurname().equals(author.getSurname()) && a.getName().equals(author.getName()));

        mongoTemplate.save(book);
    }

    @Override
    public void saveNewGenre(ObjectId bookId, Genre genre) {
        Book book = getBookById(bookId);

        book.getGenres().add(genre);
        mongoTemplate.save(book);
    }

    @Override
    public void deleteGenre(ObjectId bookId, Genre genre) {
        Book book = getBookById(bookId);

        book.getGenres().removeIf(g -> g.getName().equals(genre.getName()));

        mongoTemplate.save(book);
    }

    @Override
    public List<Book> findCommentsByBook(String bookName) {
        Query query = new Query(Criteria.where("name").regex(bookName).and("comments").ne(null));
        return mongoTemplate.find(query, Book.class);
    }

    @Override
    public Double getAverageMarkByBook(ObjectId bookId) {
        GroupOperation groupByAuthorName = group("name").avg("comments.mark").as("averageMarkByBook");
        MatchOperation filterByAuthor = match(new Criteria("_id").is(bookId));
        Aggregation aggregation = newAggregation(groupByAuthorName, filterByAuthor);
        AggregationResults<Double> result = mongoTemplate.aggregate(aggregation, "library", Double.class);
        return result.getUniqueMappedResult();
    }

    @Override
    public void saveNewComment(ObjectId bookId, Comment comment) {
        Book book = getBookById(bookId);

        book.getComments().add(comment);
        mongoTemplate.save(book);
    }

    @Override
    public void deleteComment(ObjectId bookId, Comment comment) {
        Book book = getBookById(bookId);

        book.getComments().removeIf(c -> c.getComment().equals(comment.getComment()));

        mongoTemplate.save(book);
    }

    @Override
    public void saveNewBook(Book book) {
        mongoTemplate.save(book);
    }

    @Override
    public void deleteBook(ObjectId bookId) {
        Query query = new Query(Criteria.where("_id").is(bookId));
        mongoTemplate.findAndRemove(query, Book.class);
    }

    private Book getBookById(ObjectId bookId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(bookId));
        return mongoTemplate.findOne(query, Book.class);
    }
}
