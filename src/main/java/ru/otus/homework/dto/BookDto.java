package ru.otus.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class BookDto {
    String databaseId;
    private String name;
    private int pages;
    private Set<Author> authors;
    private Set<Genre> genres;
    private Set<Comment> comments;

    public BookDto(String id, String name, int pages, Set<Author> authors, Set<Genre> genres, Set<Comment> comments) {
        this.databaseId = id;
        this.name = name;
        this.pages = pages;
        this.authors = authors;
        this.genres = genres;
        this.comments = comments;
    }

    public static BookDto toDto(Book book) {
        BookDto newBook = new BookDto();
        if (book.getDatabaseId() != null) {
            newBook.setDatabaseId(book.getDatabaseId().toString());
        }
        if (book.getName() != null) {
            newBook.setName(book.getName());
        }
        newBook.setPages(book.getPages());
        if (book.getAuthors() != null) {
            newBook.setAuthors(book.getAuthors());
        }
        if (book.getGenres() != null) {
            newBook.setGenres(book.getGenres());
        }
        if (book.getComments() != null) {
            newBook.setComments(book.getComments());
        }
        return newBook;
    }
}
