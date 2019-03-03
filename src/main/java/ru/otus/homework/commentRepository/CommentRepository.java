package ru.otus.homework.commentRepository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.model.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    Comment findCommentById(Long id);
    List<Comment> findAll();
    List<Comment> findByMark(byte mark);
}
