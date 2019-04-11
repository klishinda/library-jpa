package ru.otus.homework;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework.bookRepository.BookRepository;
import ru.otus.homework.bookRepository.BookRepositoryImpl;
import ru.otus.homework.libraryService.LibraryService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(LibraryController.class)
public class LibraryControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private LibraryService libraryService;

    @MockBean
    private BookRepositoryImpl bookRepositoryImpl;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private LibraryController libraryController;

    @Test
    public void listBook() throws Exception {
        this.mvc.perform(get("/api/books").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    public void addBook() throws Exception {
        this.mvc.perform(post("/api/books").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    public void updateBook() throws Exception {
        this.mvc.perform(put("/api/books").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    public void deleteBook() throws Exception {
        this.mvc.perform(delete("/api/books/id=5cafaa770785e70520f498f8").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }
}
