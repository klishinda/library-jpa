package ru.otus.homework.mongoService;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface MongoService {
    void createCollection();
    void addTestData() throws IOException;
}
