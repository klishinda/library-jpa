package ru.otus.homework.mongoService;

import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.otus.homework.model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
@Component
public class MongoServiceImpl implements MongoService {

    private final MongoTemplate mongoTemplate;
    private final static String LIBRARY_COLLECTION = "library";
    private final static String USERS_COLLECTION = "users";
    private static final String FILES_PATH = "scripts/testData/";

    public MongoServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void createCollection() {
        if (mongoTemplate.getCollectionNames().contains(LIBRARY_COLLECTION)) {
            mongoTemplate.dropCollection(LIBRARY_COLLECTION);
            log.info("Library collection is dropped");
        }
        if (mongoTemplate.getCollectionNames().contains(USERS_COLLECTION)) {
            mongoTemplate.dropCollection(USERS_COLLECTION);
            log.info("Users collection is dropped");
        }

        mongoTemplate.createCollection(LIBRARY_COLLECTION);
        mongoTemplate.createCollection(USERS_COLLECTION);
    }

    @Override
    public void addTestData() throws IOException {
        File folder = new File(FILES_PATH);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    log.info("Reading file " + folder + "/" + file.getName());
                }
                Document doc = ReadTestDataJsons(folder, file);
                mongoTemplate.insert(doc, LIBRARY_COLLECTION);
            }
        }

        mongoTemplate.insert(new User("DKLISHIN", new BCryptPasswordEncoder().encode("password")), USERS_COLLECTION);
        mongoTemplate.insert(new User("ADMIN", new BCryptPasswordEncoder().encode("admin")), USERS_COLLECTION);
    }

    public static Document ReadTestDataJsons(File folder, File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(folder + "/" + file.getName()));
        String st;
        StringBuilder authors = new StringBuilder();
        while ((st = br.readLine()) != null) {
            authors.append(st);
        }

        return Document.parse(authors.toString());
    }
}
