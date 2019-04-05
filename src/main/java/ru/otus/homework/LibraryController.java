package ru.otus.homework;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.homework.libraryService.LibraryService;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Genre;

import java.util.List;
import java.util.Set;

@Controller
public class LibraryController {
    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
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
        model.addAttribute("books", books);
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
