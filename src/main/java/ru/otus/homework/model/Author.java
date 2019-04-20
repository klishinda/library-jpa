package ru.otus.homework.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Author {
    @Id
    private Long id;
    private String surname;
    private String name;

    public Author(String surname, String name) {
        this.surname = surname;
        this.name = name;
    }

    public Author(Long id) {
        this.id = id;
    }

}