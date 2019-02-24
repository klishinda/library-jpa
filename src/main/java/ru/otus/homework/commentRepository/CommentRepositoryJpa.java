package ru.otus.homework.commentRepository;

import org.springframework.stereotype.Repository;
import ru.otus.homework.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class CommentRepositoryJpa implements CommentRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Comment getById(Long id) {
        return em.find(Comment.class, id);
    }

    @Override
    public List<Comment> getAllComments() {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c", Comment.class);
        return query.getResultList();
    }

    @Override
    public void addComment(Comment comment) {
        em.persist(comment);
    }

    @Override
    public void removeComment(Long id) {
        em.remove(em.find(Comment.class, id));
    }
}
