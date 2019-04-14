package ru.otus.homework;

import org.bson.types.ObjectId;
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
    private Table getAuthorBySurname(@ShellOption String name) {
        return printer.printAuthors(libraryService.getAuthorByName(name));
    }

    @ShellMethod("Get all authors")
    private Table getAllAuthors() {
        Set<Author> au = libraryService.getAllAuthors();
        return printer.printAuthors(au);
    }

    @ShellMethod("Add author")
    private void addAuthor(@ShellOption ObjectId id, @ShellOption String name, @ShellOption String surname) {
        libraryService.addAuthor(id, new Author(name, surname));
    }

    @ShellMethod("Remove author")
    private void deleteAuthor(@ShellOption ObjectId id, @ShellOption String name, @ShellOption String surname) {
        libraryService.removeAuthor(id, new Author(name, surname));
    }

    // GENRES
    @ShellMethod("Get all genres")
    private Table getAllGenres() {
        return printer.printGenres(libraryService.getAllGenres());
    }

    @ShellMethod("Add genre")
    private void addGenre(@ShellOption ObjectId id, @ShellOption String name) {
        libraryService.addGenre(id, new Genre(name));
    }

    @ShellMethod("Remove genre")
    private void deleteGenre(@ShellOption ObjectId id, @ShellOption String name) {
        libraryService.removeGenre(id, new Genre(name));
    }

    // COMMENTS
    @ShellMethod("Get all comments")
    private Table getAllComments() {
        Set<Comment> comment = libraryService.getAllComments();
        return printer.printComments(comment);
    }

    @ShellMethod("Add comment")
    private void addComment(@ShellOption ObjectId id,
                            @ShellOption byte mark,
                            @ShellOption String username,
                            @ShellOption String comment) {
        libraryService.addComment(id, new Comment(mark, username, comment, new Date()));
    }

    @ShellMethod("Remove comment")
    private void deleteComment(@ShellOption ObjectId id,
                               @ShellOption byte mark,
                               @ShellOption String username,
                               @ShellOption String comment) {
        libraryService.removeComment(id, new Comment(mark, username, comment, new Date()));
    }

    // Books
    @ShellMethod("Get all books")
    private Table getAllBooks() {
        List<Book> books = libraryService.getAllBooks();
        return printer.printBooks(books);
    }

    @ShellMethod("Get average mark")
    private Double getAverageMarkByBook(@ShellOption ObjectId id) {
        return libraryService.getAverageMarkByBook(id);
    }

    @ShellMethod("Get comments by part of book name")
    private Table getCommentsByBook(@ShellOption String name) {
        return printer.printBookComments(libraryService.getAllCommentsByBook(name));
    }

    /*@ShellMethod("Add book")
    private void addBook(@ShellOption String name,
                         @ShellOption int pages) {
        libraryService.addBook(name, pages);
    }*/

    @ShellMethod("Remove book")
    private void deleteBook(@ShellOption ObjectId id) {
        libraryService.removeBook(id);
    }
}
