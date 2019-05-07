package ru.otus.homework.integrationController.book;

import org.bson.types.ObjectId;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.homework.model.Book;

@MessagingGateway
public interface BookGateway {
    @Gateway(requestChannel = "AddBookChannel", replyChannel = "LogBookChannel")
    void addBook(Book book);
    @Gateway(requestChannel = "UpdateBookChannel", replyChannel = "LogBookChannel")
    void updateBook(Book book);
    @Gateway(requestChannel = "RemoveBookChannel", replyChannel = "LogRemoveBookChannel")
    void removeBook(ObjectId id);
}