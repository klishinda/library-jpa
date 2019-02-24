package ru.otus.homework.bookRepository;

import org.springframework.stereotype.Repository;
import ru.otus.homework.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class BookRepositoryJpa implements BookRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Book getById(Long id) {
        return em.find(Book.class, id);
    }

    @Override
    public List<Book> getAllBooks() {
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        return query.getResultList();
    }

    @Override
    public void addBook(Book book) {
        em.persist(book);
    }

    @Override
    public void removeBook(Long id) {
        em.remove(em.find(Book.class, id));
    }

    @Override
    public Double getAverageMarkByBook(Long id) {
        TypedQuery<Double> query = em.createQuery("select AVG(c.mark) from Book b LEFT JOIN b.comments c WHERE b.id = :id", Double.class);
        return query.setParameter("id", id).getSingleResult();
    }

    @Override
    public List<Book> getAllCommentsByBook(String bookName) {
        TypedQuery<Book> query = em.createQuery("select b from Book b WHERE b.name LIKE :name", Book.class);
        return query.setParameter("name", "%" + bookName + "%").getResultList();
    }
}
