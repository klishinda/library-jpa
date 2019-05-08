package ru.otus.homework.libraryService;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;
import ru.otus.homework.repositories.bookRepository.BookRepository;

import java.util.*;

@Service("libraryService")
@Slf4j
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
        return new HashSet(bookRepository.findAuthorsFromAllBooks());
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
        return new HashSet(bookRepository.findGenresFromAllBooks());
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
    public void addBook(Book book) {
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
        return bookRepository.findBooksByNameAndAllCommentsToFindingBooks(name);
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

    @Override
    public Book generateNewPositiveComment(Book book) {
        Comment newComment = new Comment();
        final String username = "user-" + UUID.randomUUID();

        if (book.getAuthors() == null) {
            newComment.setMark((byte) 8);
            newComment.setUserName(username);
        }
        else {
            List<Book> authorBooks = bookRepository.findBooksWritingByAuthor(book.getAuthors().iterator().next());
            if (authorBooks.size() == 0) {
                newComment.setMark((byte) 10);
                newComment.setUserName(username);
                newComment.setComment("Прекрасный литературный дебют!");
                newComment.setCreateDate(new Date());
            }
            else if (authorBooks.size() == 1) {
                newComment.setMark((byte) 10);
                newComment.setUserName(username);
                newComment.setComment("Читал книгу " + authorBooks.get(0).getName() + " с этого сайта, очень понравилась. Рад, что автор продолжает писать!");
                newComment.setCreateDate(new Date());
            }
            else {
                newComment.setMark((byte) 10);
                newComment.setUserName(username);
                Random rand = new Random();
                newComment.setComment("Являюсь поклонником автора, больше всего нравится его произведение " + authorBooks.get(rand.nextInt(authorBooks.size())).getName());
                newComment.setCreateDate(new Date());
            }
        }
        book.setComments(Collections.singleton(newComment));
        return book;
    }

    @Override
    public void log(Book book) {
        log.info("action with " + book.getName() + ", id " + book.getDatabaseId());
    }

    @Override
    public void log(ObjectId id) {
        log.info("removed book " + id);
        log.info("*sending message to administrators*");
    }
}
