package ru.otus.homework.repositories.bookRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

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

        if (book.getAuthors() != null) {
            book.getAuthors().add(author);
        }
        else {
            Set<Author> newAuthor = new HashSet<>();
            newAuthor.add(author);
            book.setAuthors(new HashSet<>(newAuthor));
        }
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

        if (book.getGenres() != null) {
            book.getGenres().add(genre);
        }
        else {
            Set<Genre> newGenre = new HashSet<>();
            newGenre.add(genre);
            book.setGenres(newGenre);
        }
        mongoTemplate.save(book);
    }

    @Override
    public void deleteGenre(ObjectId bookId, Genre genre) {
        Book book = getBookById(bookId);

        book.getGenres().removeIf(g -> g.getName().equals(genre.getName()));

        mongoTemplate.save(book);
    }

    @Override
    public List<Book> findBooksByNameAndAllCommentsToFindingBooks(String bookName) {
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

        if (book.getComments() != null) {
            book.getComments().add(comment);
        }
        else {
            Set<Comment> newComment = new HashSet<>();
            newComment.add(comment);
            book.setComments(new HashSet<>(newComment));
        }
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
    public void updateBook(Book book) {
        Query query = new Query(Criteria.where("_id").is(book.getDatabaseId()));
        Update update = new Update();
        update.set("name", book.getName());
        update.set("pages", book.getPages());

        mongoTemplate.updateFirst(query, update, Book.class);
    }

    @Override
    public List<Author> findAuthorsFromAllBooks() {
        return mongoTemplate.aggregate(
                newAggregation(unwind("authors"), project().andInclude("authors.name").andInclude("authors.surname")), Book.class, Author.class).getMappedResults();
    }

    @Override
    public List<Genre> findGenresFromAllBooks() {
        return mongoTemplate.aggregate(
                newAggregation(unwind("genres"), project().andInclude("genres.name")), Book.class, Genre.class).getMappedResults();
    }

    @Override
    public List<Book> findBooksWritingByAuthor(Author author) {
        Query query = new Query(Criteria.where("authors.surname").is(author.getSurname()).and("authors.name").is(author.getName()));
        return mongoTemplate.find(query, Book.class);
    }

    private Book getBookById(ObjectId bookId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(bookId));
        return mongoTemplate.findOne(query, Book.class);
    }
}
