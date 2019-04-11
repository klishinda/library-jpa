package ru.otus.homework.dto;

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

    private BookDto(String id, String name, int pages, Set<Author> authors, Set<Genre> genres, Set<Comment> comments) {
        this.databaseId = id;
        this.name = name;
        this.pages = pages;
        this.authors = authors;
        this.genres = genres;
        this.comments = comments;
    }

    public static BookDto toDto(Book book) {
        return new BookDto(book.getDatabaseId().toString(), book.getName(), book.getPages(), book.getAuthors(), book.getGenres(), book.getComments());
    }
}
