package ru.otus.homework.libraryService;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.homework.bookRepository.BookRepository;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;

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
    public Set<Author> getAuthorByName(String surname) {
        return bookRepository.findBookByAuthorSurname(surname).getAuthors();
    }

    @Override
    public Set<Author> getAllAuthors() {
        List<Book> books = bookRepository.findAll();
        Set<Author> authors = null;
        for (Book b : books) {
            if (b.getAuthors() != null) {
                if (authors != null) {
                    authors.addAll(b.getAuthors());
                } else {
                    authors = b.getAuthors();
                }
            }
        }
        return authors;
    }

    @Override
    public void addAuthor(ObjectId bookId, Author author) {
        bookRepository.saveNewAuthor(bookId, author);
    }

    @Override
    public void removeAuthor(ObjectId bookId, Author author) {
        bookRepository.deleteAuthor(bookId, author);
    }

    @Override
    public Set<Genre> getAllGenres() {
        List<Book> books = bookRepository.findAll();
        Set<Genre> genres = null;
        for (Book b : books) {
            if (b.getGenres() != null) {
                if (genres != null) {
                    genres.addAll(b.getGenres());
                } else {
                    genres = b.getGenres();
                }
            }
        }
        return genres;
    }

    @Override
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
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public void addBook(String title, int pages) {
        Book book = new Book(title, pages);
        bookRepository.saveNewBook(book);
    }

    @Override
    public void removeBook(ObjectId id) {
        bookRepository.removeBookByDatabaseId(id);
    }

    @Override
    public Double getAverageMarkByBook(ObjectId id) {
        Double mark = bookRepository.getAverageMarkByBook(id);
        if (mark == null) {
            return (double) 0;
        }
        else {
            return mark;
        }
    }

    @Override
    public List<Book> getAllCommentsByBook(String name) {
        return bookRepository.findCommentsByBook(name);
    }

    @Override
    public Book getBookByName(String name) {
        return bookRepository.findBookByName(name);
    }

    @Override
    public Book getBookById(ObjectId id) {
        return bookRepository.findBookByDatabaseId(id);
    }

    @Override
    public void updateBook(Book book) {
        bookRepository.updateBook(book);
    }
}
