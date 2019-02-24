package ru.otus.homework.GenreRepository;

import org.springframework.stereotype.Repository;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class GenreRepositoryJpa implements GenreRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Genre getById(Long id) {
        return em.find(Genre.class, id);
    }

    @Override
    public List<Genre> getAllGenres() {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g", Genre.class);
        return query.getResultList();
    }

    @Override
    public void addGenre(Genre genre) {
        em.persist(genre);
    }

    @Override
    public void removeGenre(Long id) {
        em.remove(em.find(Author.class, id));
    }
}
