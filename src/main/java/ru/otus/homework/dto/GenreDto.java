package ru.otus.homework.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.homework.model.Genre;

@Getter
@Setter
@NoArgsConstructor
public class GenreDto {

    private String name;

    private GenreDto(String name) {
        this.name = name;
    }

    public static GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getName());
    }
}