package ru.otus.homework.authorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.otus.homework.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class AuthorRepositoryJpa implements AuthorRepository {
    @PersistenceContext
    private EntityManager em;

    private final AuthorRepositoryCrud authorRepositoryCrud;

    @Autowired
    public AuthorRepositoryJpa(AuthorRepositoryCrud authorRepositoryCrud) {
        this.authorRepositoryCrud = authorRepositoryCrud;
    }

    @Override
    public List<Author> getUnusedAuthors() {
        return authorRepositoryCrud.getUnusedAuthors();
    }

    @Override
    public Author getById(Long id) { return em.find(Author.class, id); }

    @Override
    public void addAuthor(Author author) {
        em.persist(author);
    }

    @Override
    public void removeAuthor(Long id) {
        em.remove(em.find(Author.class, id));
    }

    @Override
    public List<Author> getAllAuthors() {
        TypedQuery<Author> query = em.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }
}
