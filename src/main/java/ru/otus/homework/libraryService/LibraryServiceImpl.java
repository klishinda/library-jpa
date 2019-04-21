package ru.otus.homework.libraryService;

import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.bookRepository.BookRepository;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Genre;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class LibraryServiceImpl implements LibraryService{
    private BookRepository bookRepository;

    @Autowired
    public LibraryServiceImpl( BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Override
    public Flux<Author> getAllAuthors() {
        Set<Author> allAuthors = null;
        List<Set<Author>> authors =  bookRepository.findAll().map(v -> {
            if (v.getAuthors() != null) {
                return v.getAuthors();
            }
            else  {
                return new HashSet<Author>();
            }
        }).collectList().block();

        for (Set<Author> authorSets : authors) {
            if (authorSets != null) {
                if (allAuthors != null) {
                    allAuthors.addAll(authorSets);
                } else {
                    allAuthors = authorSets;
                }
            }
        }

        return Flux.fromIterable(allAuthors);
    }

    @Override
    public Flux<Genre> getAllGenres() {
        Set<Genre> allGenres = null;
        List<Set<Genre>> genres =  bookRepository.findAll().map(v -> {
            if (v.getGenres() != null) {
                return v.getGenres();
            }
            else  {
                return new HashSet<Genre>();
            }
        }).collectList().block();

        for (Set<Genre> genreSets : genres) {
            if (genreSets != null) {
                if (allGenres != null) {
                    allGenres.addAll(genreSets);
                } else {
                    allGenres = genreSets;
                }
            }
        }

        return Flux.fromIterable(allGenres);
    }

    @Override
    public Flux<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Mono<Book> addBook(Book book) {
        return bookRepository.saveNewBook(book);
    }

    @Override
    public Mono<Long> removeBook(ObjectId id) {
        return  bookRepository.removeBookByDatabaseId(id);
    }

    @Override
    public Flux<Book> getAllCommentsByBook(String name) {
        return bookRepository.findBooksByNameAndAllCommentsToFindingBooks(name);
    }

    @Override
    public Mono<Book> getBookById(ObjectId id) {
        return bookRepository.findBookByDatabaseId(id);
    }

    @Override
    public Mono<UpdateResult> updateBook(Book book) {
        return bookRepository.updateBook(book);
    }
}
