package com.igitras.spring.boot.starter.axon;

import org.axonframework.commandhandling.AsynchronousCommandBus;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.domain.IdentifierFactory;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor;
import org.axonframework.eventstore.EventStore;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AxonAutoConfigurationTest {

    private AnnotationConfigApplicationContext context;

    @Before
    public void setUp() throws Exception {
        this.context = new AnnotationConfigApplicationContext();
    }

    @After
    public void tearDown() throws Exception {
        if (this.context != null) {
            this.context.close();
        }
    }

    @Test
    public void testAutoConfiguration() throws Exception {
        this.context.register(AxonAutoConfiguration.class);
        this.context.refresh();

        assertNotNull(this.context.getBean(CommandBus.class));
        assertNotNull(this.context.getBean(EventBus.class));
        assertNotNull(this.context.getBean(CommandGateway.class));
        assertNotNull(this.context.getBean(EventStore.class));
        assertNotNull(this.context.getBean(IdentifierFactory.class));
        assertNotNull(this.context.getBean(AnnotationCommandHandlerBeanPostProcessor.class));
        assertNotNull(this.context.getBean(AnnotationEventListenerBeanPostProcessor.class));
    }

    @Test
    public void testCustomConfiguration() throws Exception{
        this.context.register(AxonAutoConfiguration.class);
        this.context.register(CustomConfiguration.class);
        this.context.refresh();

        assertNotNull(this.context.getBean(CommandBus.class));
        assertEquals(AsynchronousCommandBus.class, this.context.getBean(CommandBus.class).getClass());
    }

}

@Configuration
class CustomConfiguration {
    @Bean
    public CommandBus commandBus(){
        return new AsynchronousCommandBus();
    }
}