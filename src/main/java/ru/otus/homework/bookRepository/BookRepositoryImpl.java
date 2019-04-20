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
import ru.otus.homework.model.Book;

@Repository
@Transactional
public class BookRepositoryImpl implements BookRepositoryCustom{
    private final ReactiveMongoTemplate mongoTemplate;

    @Autowired
    public BookRepositoryImpl(ReactiveMongoTemplate  mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /*@Override
    public Mono<Book> findBookByAuthorSurname(String surname) {
        Query query = new Query(Criteria.where("authors.surname").is(surname));
        return mongoTemplate.findOne(query, Book.class);
    }

    @Override
    public Mono<Void> saveNewAuthor(ObjectId bookId, Author author) {
        Book book = getBookById(bookId);

        if (book.getAuthors() != null) {
            book.getAuthors().add(author);
        }
        else {
            Set<Author> newAuthor = new HashSet<>();
            newAuthor.add(author);
            book.setAuthors(new HashSet<>(newAuthor));
        }
        mongoTemplate.save(book);       //updateFirst
    }

    @Override
    public Mono<Void> deleteAuthor(ObjectId bookId, Author author) {
        Book book = getBookById(bookId);

        book.getAuthors().removeIf(a -> a.getSurname().equals(author.getSurname()) && a.getName().equals(author.getName()));

        mongoTemplate.save(book);
    }

    @Override
    public Mono<Void> saveNewGenre(ObjectId bookId, Genre genre) {
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
    public Mono<Void> deleteGenre(ObjectId bookId, Genre genre) {
        Book book = getBookById(bookId);

        book.getGenres().removeIf(g -> g.getName().equals(genre.getName()));

        mongoTemplate.save(book);
    }*/

    @Override
    public Flux<Book> findCommentsByBook(String bookName) {
        Query query = new Query(Criteria.where("name").regex(bookName).and("comments").ne(null));
        return mongoTemplate.find(query, Book.class);
    }

    /*@Override
    public Mono<Void> saveNewComment(ObjectId bookId, Comment comment) {
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
    public Mono<Void> deleteComment(ObjectId bookId, Comment comment) {
        Book book = getBookById(bookId);

        book.getComments().removeIf(c -> c.getComment().equals(comment.getComment()));

        mongoTemplate.save(book);
    }*/

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

    /*private Mono<Book> getBookById(ObjectId bookId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(bookId));
        return mongoTemplate.findOne(query, Book.class);
    }*/
}
