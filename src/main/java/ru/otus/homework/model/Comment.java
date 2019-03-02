package ru.otus.homework.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book_comments")
public class Comment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Long id_book;
    private byte mark;
    private String userName;
    private String comment;
    private Date createDate;

    public Comment(Long id_book, byte mark, String userName, String comment, Date createDate) {
        this.id_book = id_book;
        this.mark = mark;
        this.userName = userName;
        this.comment = comment;
        this.createDate = createDate;
    }
}
