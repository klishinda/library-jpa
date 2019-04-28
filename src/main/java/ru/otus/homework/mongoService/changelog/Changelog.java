package ru.otus.homework.mongoService.changelog;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import org.bson.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;
import java.io.IOException;

import static ru.otus.homework.mongoService.MongoServiceImpl.*;

@ChangeLog
public class Changelog {
    private static final String FILES_PATH = "scripts/testData/";
    private final static String LIBRARY_COLLECTION = "library";
    private final static String USERS_COLLECTION = "users";

    @ChangeSet(order = "001", id = "insertTestData", author = "dklishin")
    public void insertData(DB db) throws IOException {
        DBCollection libraryCollection = db.getCollection(LIBRARY_COLLECTION);

        File folder = new File(FILES_PATH);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                Document doc = ReadTestDataJsons(folder, file);
                BasicDBObject basicDBObject = new BasicDBObject(doc);
                libraryCollection.insert(basicDBObject);
            }
        }
    }

    @ChangeSet(order = "002", id = "insertUser", author = "dklishin")
    public void insertUser(DB db) {
        System.out.println("adding users");
        DBCollection userCollection = db.getCollection(USERS_COLLECTION);
        BasicDBObject user = new BasicDBObject().append("username", USER_NAME).append("password", USER_PASSWORD).append("role", USER_ROLE);
        BasicDBObject admin = new BasicDBObject().append("username", ADMIN_NAME).append("password", ADMIN_PASSWORD).append("role", ADMIN_ROLE);
        BasicDBObject guest = new BasicDBObject().append("username", GUEST_NAME).append("password", GUEST_PASSWORD).append("role", GUEST_ROLE);
        userCollection.insert(user);
        userCollection.insert(admin);
        userCollection.insert(guest);
    }
}
