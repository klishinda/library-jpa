package ru.otus.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.homework.model.Author;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
    private String surname;
    private String name;

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getName(), author.getSurname());
    }
}