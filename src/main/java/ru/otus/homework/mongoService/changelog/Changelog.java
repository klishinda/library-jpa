package ru.otus.homework.mongoService.changelog;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import org.bson.Document;

import java.io.File;
import java.io.IOException;

import static ru.otus.homework.mongoService.MongoServiceImpl.ReadTestDataJsons;

@ChangeLog
public class Changelog {
    private static final String FILES_PATH = "scripts/testData/";
    private final static String COLLECTION = "library";

    @ChangeSet(order = "001", id = "insert", author = "dklishin")
    public void insert(DB db) throws IOException {
        DBCollection myCollection = db.getCollection(COLLECTION);

        File folder = new File(FILES_PATH);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                Document doc = ReadTestDataJsons(folder, file);
                BasicDBObject basicDBObject = new BasicDBObject(doc);
                myCollection.insert(basicDBObject);
            }
        }
    }
}
