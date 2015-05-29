package com.igitras.spring.boot.starter.axon;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.domain.IdentifierFactory;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.fs.EventFileResolver;
import org.axonframework.eventstore.fs.FileSystemEventStore;
import org.axonframework.eventstore.fs.SimpleEventFileResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.Files;

/**
 * Created by meidongxu on 5/29/15.
 */
@Configuration
@ConditionalOnClass({
        CommandBus.class,
        EventStore.class
})
public class AxonAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CommandBus commandBus() {
        return new SimpleCommandBus();
    }

    @Bean
    @ConditionalOnMissingBean
    public EventBus eventBus() {
        return new SimpleEventBus();
    }

    @Bean
    @ConditionalOnMissingBean
    public CommandGateway commandGateway(CommandBus commandBus) {
        return new DefaultCommandGateway(commandBus);
    }

    @Bean
    @ConditionalOnMissingBean
    public EventStore eventStore() throws Exception {
        File baseDirectory = Files.createTempDirectory("events").toFile();
        EventFileResolver eventFileResolver = new SimpleEventFileResolver(baseDirectory);
        return new FileSystemEventStore(eventFileResolver);
    }

    @Bean
    @ConditionalOnMissingBean
    public AnnotationCommandHandlerBeanPostProcessor annotationCommandHandlerBeanPostProcessor(CommandBus commandBus) {
        AnnotationCommandHandlerBeanPostProcessor postProcessor = new AnnotationCommandHandlerBeanPostProcessor();
        postProcessor.setCommandBus(commandBus);
        return postProcessor;
    }

    @Bean
    @ConditionalOnMissingBean
    public AnnotationEventListenerBeanPostProcessor annotationEventListenerBeanPostProcessor(EventBus eventBus) {
        AnnotationEventListenerBeanPostProcessor postProcessor = new AnnotationEventListenerBeanPostProcessor();
        postProcessor.setEventBus(eventBus);
        return postProcessor;
    }

    @Bean
    @ConditionalOnMissingBean
    public IdentifierFactory identifierFactory() {
        return IdentifierFactory.getInstance();
    }
}
