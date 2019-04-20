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

import static org.springframework.http.ResponseEntity.ok;

@Component
public class LibraryServiceImpl implements LibraryService{
    private BookRepository bookRepository;

    @Autowired
    public LibraryServiceImpl( BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /*@Override
    public Set<Author> getAuthorByName(String surname) {
        return bookRepository.findBookByAuthorSurname(surname).getAuthors();
    }*/

    @Override
    //public Flux<Set<Author>> getAllAuthors() {
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

    private void getAuthors() {

    }

    /*@Override
    public void addAuthor(ObjectId bookId, Author author) {
        bookRepository.saveNewAuthor(bookId, author);
    }

    @Override
    public void removeAuthor(ObjectId bookId, Author author) {
        bookRepository.deleteAuthor(bookId, author);
    }*/

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

    /*@Override
    public void addGenre(ObjectId bookId, Genre genre) {
        bookRepository.saveNewGenre(bookId, genre);
    }

    @Override
    public void removeGenre(ObjectId bookId, Genre genre) {
        bookRepository.deleteGenre(bookId, genre);
    }

    @Override
    public Set<Comment> getAllComments() {
        List<Book> books = bookRepository.findAll();
        Set<Comment> comments = null;
        for (Book b : books) {
            if (b.getComments() != null) {
                if (comments != null) {
                    comments.addAll(b.getComments());
                } else {
                    comments = b.getComments();
                }
            }
        }
        return comments;
    }

    @Override
    public void addComment(ObjectId bookId, Comment comment) {
        bookRepository.saveNewComment(bookId, comment);
    }

    @Override
    public void removeComment(ObjectId bookId, Comment comment) {
        bookRepository.deleteComment(bookId, comment);
    }*/

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

    /*@Override
    public Double getAverageMarkByBook(ObjectId id) {
        Double mark = bookRepository.getAverageMarkByBook(id);
        if (mark == null) {
            return (double) 0;
        }
        else {
            return mark;
        }
    }*/

    @Override
    public Flux<Book> getAllCommentsByBook(String name) {
        return bookRepository.findCommentsByBook(name);
    }

    /*@Override
    public Book getBookByName(String name) {
        return bookRepository.findBookByName(name);
    }*/

    @Override
    public Mono<Book> getBookById(ObjectId id) {
        return bookRepository.findBookByDatabaseId(id);
    }

    @Override
    public Mono<UpdateResult> updateBook(Book book) {
        return bookRepository.updateBook(book);
    }
}
