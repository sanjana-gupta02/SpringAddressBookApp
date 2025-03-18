package com.spring.AddressBook.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    // Define the Queue for User Registration
    @Bean
    public Queue userRegistrationQueue() {
        return new Queue("user.registration.queue", true); // durable queue
    }

    // Define the Queue for Contact Added
    @Bean
    public Queue contactAddedQueue() {
        return new Queue("contact.added.queue", true); // durable queue
    }

    // Define the Topic Exchange
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("app.exchange");
    }

    // Bind the User Registration Queue to the Exchange
    @Bean
    public Binding userRegistrationBinding(Queue userRegistrationQueue, TopicExchange exchange) {
        return BindingBuilder.bind(userRegistrationQueue).to(exchange).with("user.registration");
    }

    // Bind the Contact Added Queue to the Exchange
    @Bean
    public Binding contactAddedBinding(Queue contactAddedQueue, TopicExchange exchange) {
        return BindingBuilder.bind(contactAddedQueue).to(exchange).with("contact.added");
    }
}
