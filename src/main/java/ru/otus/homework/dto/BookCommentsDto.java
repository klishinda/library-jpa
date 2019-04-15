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
public class BookCommentsDto {
    private String name;
    private Set<Comment> comments;

    public BookCommentsDto(String name, Set<Comment> comments) {
        this.name = name;
        this.comments = comments;
    }

    public static BookCommentsDto toDto(Book book) {
        return new BookCommentsDto(book.getName(), book.getComments());
    }
}
