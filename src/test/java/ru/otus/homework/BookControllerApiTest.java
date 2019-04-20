package ru.otus.homework;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework.bookRepository.BookRepository;
import ru.otus.homework.bookRepository.BookRepositoryImpl;
import ru.otus.homework.controllers.BookController;
import ru.otus.homework.libraryService.LibraryService;
import ru.otus.homework.model.Book;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
class BookControllerApiTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private LibraryService libraryService;

    @MockBean
    private BookRepositoryImpl bookRepositoryImpl;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private BookController bookController;

    @Test
    void listBook() throws Exception {
        this.mvc.perform(get("/api/books").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    void addBook() throws Exception {
        Book book = new Book("Test", 555);
        ObjectMapper objectMapper = new ObjectMapper();
        this.mvc.perform(post("/api/books").contentType(APPLICATION_JSON_UTF8).content(objectMapper.writeValueAsString(book))).andExpect(status().isOk());
    }

    @Test
    void updateBook() throws Exception {
        this.mvc.perform(put("/api/books").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    void deleteBook() throws Exception {
        mvc.perform(delete("/api/books/").param("id", "5cafaa770785e70520f498f8").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }


}
