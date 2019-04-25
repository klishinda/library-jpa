package ru.otus.homework.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.homework.model.User;
import ru.otus.homework.repositories.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;


@DataMongoTest
class UserRepositoryTest {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private UserRepository repository;

    @Test
    void findByUsernameTest() {
        User newUser = new User();
        newUser.setUsername("Test");
        newUser.setPassword("Pass");
        mongoTemplate.save(newUser);
        assertThat(repository.findByUsername("Test").getPassword()).isEqualTo("Pass");
    }
}
