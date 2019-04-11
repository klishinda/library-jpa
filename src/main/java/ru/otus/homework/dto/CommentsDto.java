package ru.otus.homework.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.homework.model.Comment;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CommentsDto {
    private byte mark;
    private String userName;
    private String comment;
    private Date createDate;

    private CommentsDto(byte mark, String userName, String comment, Date createDate) {
        this.mark = mark;
        this.userName = userName;
        this.comment = comment;
        this.createDate = createDate;
    }

    public static CommentsDto toDto(Comment comment) {
        return new CommentsDto(comment.getMark(), comment.getUserName(), comment.getComment(), comment.getCreateDate());
    }
}
