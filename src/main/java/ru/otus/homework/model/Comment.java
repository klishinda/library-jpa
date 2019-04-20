package ru.otus.homework.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Comment {
    @Id
    private Long id;
    private byte mark;
    private String userName;
    private String comment;
    private Date createDate;

    public Comment(byte mark, String userName, String comment, Date createDate) {
        this.mark = mark;
        this.userName = userName;
        this.comment = comment;
        this.createDate = createDate;
    }
}
