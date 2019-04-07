package ru.otus.homework.mongoService;

import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
@Component
public class MongoServiceImpl implements MongoService {

    private final MongoTemplate mongoTemplate;
    private final static String COLLECTION = "library";
    private static final String FILES_PATH = "scripts/testData/";

    public MongoServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void createCollection() {
        if (mongoTemplate.getCollectionNames().contains(COLLECTION)) {
            mongoTemplate.dropCollection(COLLECTION);
            log.info("Collection is dropped");
        }

        mongoTemplate.createCollection(COLLECTION);
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
                BufferedReader br = new BufferedReader(new FileReader(folder + "/" + file.getName()));
                String st;
                StringBuilder authors = new StringBuilder();
                while ((st = br.readLine()) != null) {
                    authors.append(st);
                }

                Document doc = Document.parse(authors.toString());
                mongoTemplate.insert(doc, COLLECTION);
            }
        }
    }
}
