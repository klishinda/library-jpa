package ru.otus.homework;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.Table;
import ru.otus.homework.libraryService.LibraryService;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;
import ru.otus.homework.printer.ResultsPrinter;

import java.util.*;
import java.util.stream.Collectors;


@ShellComponent
public class ShellCommands {
    private LibraryService libraryService;
    private ResultsPrinter printer;

    public ShellCommands(LibraryService libraryService, ResultsPrinter printer) {
        this.libraryService = libraryService;
        this.printer = printer;
    }

    // AUTHORS
    @ShellMethod("Get author by id")
    private Table getAuthorById(@ShellOption Long id) {
        Author author = libraryService.getAuthorById(id);
        List<Author> oneAuthor = new ArrayList<>();
        oneAuthor.add(author);
        return printer.printAuthors(oneAuthor);
    }

    @ShellMethod("Get all authors")
    private Table getAllAuthors() {
        List<Author> au = libraryService.getAllAuthors();
        return printer.printAuthors(au);
    }

    @ShellMethod("Add author")
    private void addAuthor(@ShellOption String name, @ShellOption String surname) {
        libraryService.addAuthor(new Author(name, surname));
    }

    @ShellMethod("Remove author")
    private void deleteAuthor(@ShellOption Long id) {
        libraryService.removeAuthor(id);
    }

    @ShellMethod("Get unused authors")
    private Table getUnusedAuthors() {
        List<Author> au = libraryService.getUnusedAuthors();
        return printer.printAuthors(au);
    }

    // GENRES
    @ShellMethod("Get genre by id")
    private Table getGenreById(@ShellOption Long id) {
        Genre genre = libraryService.getGenreById(id);
        List<Genre> oneGenre = new ArrayList<>();
        oneGenre.add(genre);
        return printer.printGenres(oneGenre);
    }

    @ShellMethod("Get all genres")
    private Table getAllGenres() {
        List<Genre> genres = libraryService.getAllGenres();
        return printer.printGenres(genres);
    }

    @ShellMethod("Add genre")
    private void addGenre(@ShellOption String name) {
        libraryService.addGenre(new Genre(name));
    }

    @ShellMethod("Remove genre")
    private void deleteGenre(@ShellOption Long id) {
        libraryService.removeGenre(id);
    }

    // COMMENTS
    @ShellMethod("Get comment by id")
    private Table getCommentById(@ShellOption Long id) {
        Comment comment = libraryService.getCommentById(id);
        List<Comment> oneComment = new ArrayList<>();
        oneComment.add(comment);
        return printer.printComments(oneComment);
    }

    @ShellMethod("Get all comments")
    private Table getAllComments() {
        List<Comment> comment = libraryService.getAllComments();
        return printer.printComments(comment);
    }

    @ShellMethod("Get all comments by mark")
    private Table getCommentsByMark(@ShellOption byte mark) {
        List<Comment> comment = libraryService.getCommentsByMark(mark);
        return printer.printComments(comment);
    }

    @ShellMethod("Add comment")
    private void addComment(@ShellOption Long book_id,
                            @ShellOption byte mark,
                            @ShellOption String username,
                            @ShellOption String comment) {
        libraryService.addComment(new Comment(book_id, mark, username, comment, new Date()));
    }

    @ShellMethod("Remove comment")
    private void deleteComment(@ShellOption Long id) {
        libraryService.removeComment(id);
    }

    // Books
    @ShellMethod("Get book by id")
    private Table getBookById(@ShellOption Long id) {
        Book book = libraryService.getBookById(id);
        List<Book> oneBook = new ArrayList<>();
        oneBook.add(book);
        return printer.printBooks(oneBook);
    }

    @ShellMethod("Get all books")
    private Table getAllBooks() {
        List<Book> books = libraryService.getAllBooks();
        return printer.printBooks(books);
    }

    @ShellMethod("Get average mark")
    private Double getAverageMarkByBook(@ShellOption Long id) {
        return libraryService.getAverageMarkByBook(id);
    }

    @ShellMethod("Get comments by part of book name")
    private Table getCommentsByBook(@ShellOption String name) {
        return printer.printBookComments(libraryService.getAllCommentsByBook(name));
    }

    @ShellMethod("Add book")
    private void addBook(@ShellOption String name,
                         @ShellOption int pages,
                         @ShellOption String authorsId,
                         @ShellOption String genresId) {
        Scanner authorScanner = new Scanner(authorsId);
        List<Long> authorList = new ArrayList<>();
        while (authorScanner.hasNextInt()) {
            authorList.add(authorScanner.nextLong());
        }
        Scanner genreScanner = new Scanner(genresId);
        List<Long> genreList = new ArrayList<>();
        while (genreScanner.hasNextInt()) {
            genreList.add(genreScanner.nextLong());
        }

        authorList = Arrays.stream(authorsId.replace(",", " ").split("\\s"))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        genreList = Arrays.stream(genresId.replace(",", " ").split("\\s"))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        libraryService.addBook(name, pages, authorList, genreList);
    }

    @ShellMethod("Remove book")
    private void deleteBook(@ShellOption Long id) {
        libraryService.removeBook(id);
    }
}
