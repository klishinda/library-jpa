package ru.otus.homework.printer;

import org.springframework.shell.table.Table;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;

import java.util.List;

public interface ResultsPrinter {
    Table printAuthors(List<Author> list);
    Table printGenres(List<Genre> list);
    Table printComments(List<Comment> list);
    Table printBooks(List<Book> list);
    Table printBookComments(List<Book> list);
}
