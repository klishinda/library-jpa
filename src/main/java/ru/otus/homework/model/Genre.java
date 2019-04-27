package ru.otus.homework.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Genre {
    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
