package ru.otus.homework.libraryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.homework.GenreRepository.GenreRepository;
import ru.otus.homework.authorRepository.AuthorRepository;
import ru.otus.homework.bookRepository.BookRepository;
import ru.otus.homework.commentRepository.CommentRepository;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LibraryServiceImpl implements LibraryService{
    private GenreRepository genreRepository;
    private AuthorRepository authorRepository;
    private CommentRepository commentRepository;
    private BookRepository bookRepository;

    @Autowired
    public LibraryServiceImpl(GenreRepository genreRepository, AuthorRepository authorRepository, CommentRepository commentRepository, BookRepository bookRepository) {
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Author> getUnusedAuthors() {
        return authorRepository.getUnusedAuthors();
    }

    @Override
    public Author getAuthorById(Long id) {
        return authorRepository.getById(id);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.getAllAuthors();
    }

    @Override
    public void addAuthor(Author author) {
        authorRepository.addAuthor(author);
    }

    @Override
    public void removeAuthor(Long id) {
        authorRepository.removeAuthor(id);
    }

    @Override
    public Genre getGenreById(Long id) {
        return genreRepository.getById(id);
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.getAllGenres();
    }

    @Override
    public void addGenre(Genre genre) {
        genreRepository.addGenre(genre);
    }

    @Override
    public void removeGenre(Long id) {
        genreRepository.removeGenre(id);
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.getById(id);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.getAllComments();
    }

    @Override
    public void addComment(Comment comment) {
        commentRepository.addComment(comment);
    }

    @Override
    public void removeComment(Long id) {
        commentRepository.removeComment(id);
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.getById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    @Override
    public void addBook(String title, int pages, List<Long> idAuthors, List<Long> idGenres) {
        Book book = new Book(title, pages);
        book.setAuthors(idAuthors.stream().map(Author::new).collect(Collectors.toSet()));
        book.setGenres(idGenres.stream().map(Genre::new).collect(Collectors.toSet()));
        bookRepository.addBook(book);
    }

    @Override
    public void removeBook(Long id) {
        bookRepository.removeBook(id);
    }

    @Override
    public Double getAverageMarkByBook(Long id) {
        Double mark = bookRepository.getAverageMarkByBook(id);
        if (mark == null) {
            return (double) 0;
        }
        else {
            return bookRepository.getAverageMarkByBook(id);
        }
    }

    @Override
    public List<Book> getAllCommentsByBook(String name) {
        return bookRepository.getAllCommentsByBook(name);
    }
}
