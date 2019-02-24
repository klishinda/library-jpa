package ru.otus.homework.commentRepository;

import ru.otus.homework.model.Comment;

import java.util.List;

public interface CommentRepository {
    Comment getById(Long id);
    List<Comment> getAllComments();
    void addComment(Comment comment);
    void removeComment(Long id);
}
