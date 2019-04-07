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
@WebMvcTest(LibraryPageController.class)
public class LibraryPageControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private LibraryService libraryService;

    @MockBean
    private BookRepositoryImpl bookRepositoryImpl;

    @MockBean
    private BookRepository bookRepository;

    @Test
    public void listBook() throws Exception {
        this.mvc.perform(get("/").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk());
    }

    @Test
    public void addBook() throws Exception {
        this.mvc.perform(get("/add-book").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk());
    }

    @Test
    public void updateBook() throws Exception {
        this.mvc.perform(post("/update-book?id=5c8026306856e041c436319a&name=TEST&pages=44").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk());
    }

    @Test
    public void deleteBook() throws Exception {
        this.mvc.perform(post("/delete-book?id=5c8026306856e041c436319a").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk());
    }
}
