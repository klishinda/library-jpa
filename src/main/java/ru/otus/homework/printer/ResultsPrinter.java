package ru.otus.homework.printer;

import org.springframework.shell.table.Table;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;

import java.util.List;
import java.util.Set;

public interface ResultsPrinter {
    Table printAuthors(Set<Author> list);
    Table printGenres(Set<Genre> list);
    Table printComments(Set<Comment> list);
    Table printBooks(List<Book> list);
    Table printBookComments(List<Book> list);
}
