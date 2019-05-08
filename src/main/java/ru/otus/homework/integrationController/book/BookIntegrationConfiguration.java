package ru.otus.homework.integrationController.book;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;

@Configuration
public class BookIntegrationConfiguration {
    @Bean
    public MessageChannel AddBookChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public MessageChannel UpdateBookChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public MessageChannel RemoveBookChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public MessageChannel LogBookChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public MessageChannel LogRemoveBookChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public IntegrationFlow addBookFlow() {
        return IntegrationFlows.from("AddBookChannel")
                .handle("libraryService", "generateNewPositiveComment")
                .channel("LogBookChannel")
                .handle("libraryService", "addBook")
                .get();
    }

    @Bean
    public IntegrationFlow updateBookFlow() {
        return IntegrationFlows.from("UpdateBookChannel")
                .channel("LogBookChannel")
                .handle("libraryService", "updateBook")
                .get();
    }

    @Bean
    public IntegrationFlow removeBookFlow() {
        return IntegrationFlows.from("RemoveBookChannel")
                .channel("LogRemoveBookChannel")
                .handle("libraryService", "removeBook")
                .get();
    }

    @Bean
    public IntegrationFlow logBookFlow() {
        return IntegrationFlows.from("LogBookChannel")
                .handle("libraryService", "log")
                .get();
    }

    @Bean
    public IntegrationFlow logRemoveBookFlow() {
        return IntegrationFlows.from("LogRemoveBookChannel")
                .handle("libraryService", "log")
                .get();
    }
}