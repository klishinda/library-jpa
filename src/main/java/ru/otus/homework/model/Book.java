package ru.otus.homework.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "library")
public class Book {
    @Id
    ObjectId databaseId;
    private String name;
    private int pages;
    private Set<Author> authors;
    private Set<Genre> genres;
    private Set<Comment> comments;

    public Book(String name, int pages) {
        this.name = name;
        this.pages = pages;
    }

    public Book(String name, int pages, Set<Author> authors, Set<Genre> genres) {
        this.name = name;
        this.pages = pages;
        this.authors = authors;
        this.genres = genres;
    }
}

