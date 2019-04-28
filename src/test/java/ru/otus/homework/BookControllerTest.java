package ru.otus.homework;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

    private MockMvc mockMvc;

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

    @MockBean
    private LibraryPageController libraryPageController;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    // ADMIN ROLE TESTS
    @ParameterizedTest
    @ValueSource(strings = {"/api/books", "/all-authors", "/all-genres", "/comments-by-book-id?id=5cc1fe960785e732705357fb", "/all-comments-by-book?name=test",
                            "/add-book", "/update-book?id=5cc1fe960785e732705357fb", "/delete-book?id=5cc1fe960785e732705357fb"})
    @WithMockUser(username = "ADMIN", password = "admin", roles = "ADMIN")
    void checkAccessAdminGet(String url) throws Exception {
        mockMvc.perform(get(url)).andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/api/books", "/add-book"})
    @WithMockUser(username = "ADMIN", password = "admin", roles = "ADMIN")
    void checkAccessAdminPost(String url) throws Exception {
        mockMvc.perform(post(url).content("{\"name\":\"Book\",\"pages\":100}").contentType(APPLICATION_JSON_UTF8)).andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/api/books", "/update-book?id=5cc1fe960785e732705357fb"})
    @WithMockUser(username = "ADMIN", password = "admin", roles = "ADMIN")
    void checkAccessAdminPut(String url) throws Exception {
        mockMvc.perform(put(url)).andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/api/books/5cc1fe960785e732705357fb", "/delete-book?id=5cc1fe960785e732705357fb"})
    @WithMockUser(username = "ADMIN", password = "admin", roles = "ADMIN")
    void checkAccessAdminDelete(String url) throws Exception {
        mockMvc.perform(delete(url)).andExpect(status().isOk());
    }

    // USER ROLE TESTS
    @Test
    @WithMockUser(username = "USER", password = "user", roles = "USER")
    void userApiAccess() throws Exception {
        mockMvc.perform(get("/api/books")).andExpect(status().isOk());
        mockMvc.perform(post("/api/books").contentType(APPLICATION_JSON_UTF8).content(createTestBook())).andExpect(status().isForbidden());
        mockMvc.perform(put("/api/books")).andExpect(status().isForbidden());
        mockMvc.perform(delete("/api/books/5cafaa770785e70520f498f8")).andExpect(status().isForbidden());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/add-book", "/update-book?id=5cc1fe960785e732705357fb", "/delete-book?id=5cc1fe960785e732705357fb"})
    @WithMockUser(username = "USER", password = "user", roles = "USER")
    void checkAccessUserGet(String url) throws Exception {
        mockMvc.perform(get(url)).andExpect(status().isForbidden());
    }

    // GUEST ROLE TESTS
    @Test
    @WithMockUser(username = "GUEST", password = "guest", roles = "GUEST")
    void guestApiAccess() throws Exception {
        mockMvc.perform(get("/api/books")).andExpect(status().isOk());
        mockMvc.perform(post("/api/books").contentType(APPLICATION_JSON_UTF8).content(createTestBook())).andExpect(status().isForbidden());
        mockMvc.perform(put("/api/books")).andExpect(status().isForbidden());
        mockMvc.perform(delete("/api/books/5cafaa770785e70520f498f8")).andExpect(status().isForbidden());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/all-authors", "/all-genres", "/comments-by-book-id?id=5cc1fe960785e732705357fb", "/all-comments-by-book?name=test",
                            "/add-book", "/update-book?id=5cc1fe960785e732705357fb", "/delete-book?id=5cc1fe960785e732705357fb"})
    @WithMockUser(username = "GUEST", password = "guest", roles = "GUEST")
    void checkAccessGuestGet(String url) throws Exception {
        mockMvc.perform(get(url)).andExpect(status().isForbidden());
    }

    // NOT AUTHORIZED ROLE TESTS
    @Test
    void noRoleApiAccess() throws Exception {
        mockMvc.perform(get("/api/books")).andExpect(status().is3xxRedirection());
        mockMvc.perform(post("/api/books").contentType(APPLICATION_JSON_UTF8).content(createTestBook())).andExpect(status().is3xxRedirection());
        mockMvc.perform(put("/api/books")).andExpect(status().is3xxRedirection());
        mockMvc.perform(delete("/api/books/5cafaa770785e70520f498f8")).andExpect(status().is3xxRedirection());
    }

    private String createTestBook() throws JsonProcessingException {
        Book book = new Book("Test1", 555);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(book);
    }
}
