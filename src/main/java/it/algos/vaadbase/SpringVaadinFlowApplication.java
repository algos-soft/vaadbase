package it.algos.vaadbase;

import it.algos.vaadbase.ui.MainLayout;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: mer, 21-mar-2018
 * Time: 08:14
 * <p>
 * Spring boot web application initializer.
 * The entry point of the Spring Boot application.
 * <p>
 * Questa classe contiene il metodo 'main' che è il punto di ingresso dell'applicazione Java
 * In fase di sviluppo si possono avere diverse configurazioni, ognuna delle quali punta un ''main' diverso
 * Nel JAR finale (runtime) si può avere una sola classe col metodo 'main'.
 * Nel WAR finale (runtime) occorre (credo) inserire dei servlet di context diversi
 * Senza @ComponentScan, SpringBoot non 'vede' le classi con @SpringView
 * che sono in una directory diversa da questo package
 * <p>
 * Questa classe non fa praticamente niente se non avere le Annotation riportate qui
 * Annotated with @SpringBootApplication (obbligatorio)
 * Annotated with @ComponentScan (obbligatorio, se non già specificato il path in @SpringBootApplication)
 * Annotated with @EntityScan (obbligatorio, se non già specificato il path in @SpringBootApplication)
 * Annotated with @EnableMongoRepositories (obbligatorio, se non già specificato il path in @SpringBootApplication)
 * <p>
 * Tutte le view devono essere comprese nel path di questa classe (directory interne incluse)
 * Una sola view può avere @Route("")
 * The @SpringBootApplication annotation is equivalent to using @Configuration, @EnableAutoConfiguration and @ComponentScan with their default attributes:
 */
@Slf4j
@SpringBootApplication(scanBasePackageClasses = {MainLayout.class,SpringVaadinFlowApplication.class})
@ComponentScan({"it.algos.vaadbase","it.algos.vaadtest.modules"})
@EntityScan({"it.algos.vaadbase.modules","it.algos.vaadtest.modules"})
@EnableMongoRepositories({"it.algos.vaadbase.modules","it.algos.vaadtest.modules"})
public class SpringVaadinFlowApplication extends SpringBootServletInitializer {

    /**
     * Constructor
     *
     * @param args eventuali parametri in ingresso
     */
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringVaadinFlowApplication.class, args);
    }// end of constructor


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
        return applicationBuilder.sources(SpringVaadinFlowApplication.class);
    }// end of method

}// end of main class
