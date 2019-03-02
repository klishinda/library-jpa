package ru.otus.homework.authorRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.model.Author;

import java.util.List;

public interface AuthorRepositoryCrud extends CrudRepository<Author, Long> {
    @Query(value = "select a.* from authors a where not exists (select 1 from book_authors b where b.id_author = a.id)", nativeQuery = true)
    List<Author> getUnusedAuthors();
}
