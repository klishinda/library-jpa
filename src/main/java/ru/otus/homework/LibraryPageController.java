package ru.otus.homework;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.libraryService.LibraryService;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Genre;
import ru.otus.homework.mongoService.MongoService;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Controller
public class LibraryPageController {
    private final LibraryService libraryService;
    private final MongoService mongoService;

    @Autowired
    public LibraryPageController(LibraryService libraryService, MongoService mongoService) {
        this.libraryService = libraryService;
        this.mongoService = mongoService;
    }

    ////////////////////////// CREATE AND FILL DATABASE
    @GetMapping("/initialization")
    public String initiateDatabaseGet() {
        return "initiate-database";
    }

    @PostMapping("/initialization")
    public String initiateDatabasePost() throws IOException {
        mongoService.createCollection();
        mongoService.addTestData();
        return "result-initiate-database";
    }

    ////////////////////////// READ
    @GetMapping("/")
    public String listBook(Model model) {
        List<Book> books = libraryService.getAllBooks();
        model.addAttribute("books", books);
        return "all-books";
    }

    @GetMapping("/all-authors")
    public String listAuthor(Model model) {
        Set<Author> authors = libraryService.getAllAuthors();
        model.addAttribute("authors", authors);
        return "all-authors";
    }

    @GetMapping("/all-genres")
    public String listGenre(Model model) {
        Set<Genre> genres = libraryService.getAllGenres();
        model.addAttribute("genres", genres);
        return "all-genres";
    }

    @GetMapping("/all-comments-by-book")
    public String listCommentByBookName(@RequestParam("name") String name, Model model) {
        List<Book> books = libraryService.getAllCommentsByBook(name);
        //model.addAttribute("books", books);
        //model.addAttribute("name", name);
        return "all-comments-by-book";
    }

    ////////////////////////// CREATE
    @GetMapping("/add-book")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        return "add-book";
    }

    @PostMapping("/add-book")
    public String addBook(@ModelAttribute Book book) {
        libraryService.addBook(book.getName(), book.getPages());
        return "result-add-book";
    }

    ////////////////////////// UPDATE
    @GetMapping("/update-book")
    public String updateBook(@RequestParam("id") ObjectId id, Model model) {
        Book book = libraryService.getBookById(id);
        model.addAttribute("currentBook", book);
        model.addAttribute("newBook", new Book());
        return "update-book";
    }

    @PostMapping("/update-book")
    public String updateBook(@RequestParam("id") ObjectId id, @ModelAttribute Book book) {
        libraryService.updateBook(book);
        return "result-update-book";
    }

    ////////////////////////// DELETE
    @GetMapping("/delete-book")
    public String deleteBook(@RequestParam("id") ObjectId id, Model model) {
        Book book = libraryService.getBookById(id);
        model.addAttribute("book", book);
        return "delete-book";
    }

    @PostMapping("/delete-book")
    public String deleteBook(@RequestParam("id") ObjectId id, @ModelAttribute Book book) {
        libraryService.removeBook(id);
        return "result-delete-book";
    }
}
