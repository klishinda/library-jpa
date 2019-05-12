package ru.otus.homework;

import org.bson.types.ObjectId;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.Table;
import ru.otus.homework.libraryService.LibraryService;
import ru.otus.homework.model.*;
import ru.otus.homework.mongoService.MongoService;
import ru.otus.homework.printer.ResultsPrinter;
import ru.otus.homework.repositories.UserRepository;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@ShellComponent
public class ShellCommands {
    private LibraryService libraryService;
    private MongoService mongoService;
    private UserRepository userRepository;
    private ResultsPrinter printer;

    public ShellCommands(LibraryService libraryService, MongoService mongoService, UserRepository userRepository, ResultsPrinter printer) {
        this.libraryService = libraryService;
        this.mongoService = mongoService;
        this.userRepository = userRepository;
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

    @ShellMethod("Remove book")
    private void deleteBook(@ShellOption ObjectId id) {
        libraryService.removeBook(id);
    }

    @ShellMethod("Add test data")
    private void initializeDb() throws IOException {
        mongoService.createCollection();
        mongoService.addTestData();
    }

    @ShellMethod("Get all users")
    private List<String> getAllUsers() {
        return userRepository.findAll().stream().map(User::getUsername).collect(Collectors.toList());
    }
}