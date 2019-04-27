package ru.otus.homework;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.otus.homework.controllers.BookController;
import ru.otus.homework.libraryService.LibraryServiceImpl;
import ru.otus.homework.model.Book;
import ru.otus.homework.repositories.bookRepository.BookRepository;
import ru.otus.homework.security.UserDetailsServiceImpl;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private MongoTemplate mongoTemplate;

    @MockBean
    private BookController bookController;

    @MockBean
    private LibraryServiceImpl libraryServiceImpl;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private ru.otus.homework.repositories.UserRepository userRepository;

    @MockBean
    private UserDetailsServiceImpl userDetails;

    @Test
    @WithMockUser(username = "ADMIN", password = "admin")
    void listBookAccessOk() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        mockMvc.perform(get("/api/books").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    void listBookAccessForbidden() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        mockMvc.perform(get("/api/books").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().is3xxRedirection());

    }

    @Test
    @WithMockUser(username = "ADMIN", password = "admin")
    void addBook() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        Book book = new Book("Test1", 555);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/api/books").contentType(APPLICATION_JSON_UTF8).content(objectMapper.writeValueAsString(book))).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "ADMIN", password = "admin")
    void updateBook() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        mockMvc.perform(put("/api/books").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "ADMIN", password = "admin")
    void deleteBook() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        mockMvc.perform(delete("/api/books/5cafaa770785e70520f498f8").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }
}
