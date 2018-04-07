package it.algos.vaadtest;

import it.algos.vaadbase.ui.MainView;
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
 * Date: gio, 05-apr-2018
 * Time: 18:43
 */
@Slf4j
@SpringBootApplication(scanBasePackageClasses = {MainView.class, it.algos.vaadtest.TestApplication.class})
@ComponentScan({"it.algos"})
@EntityScan({"it.algos"})
@EnableMongoRepositories({"it.algos"})
public class TestApplication extends SpringBootServletInitializer {

    /**
     * Constructor
     *
     * @param args eventuali parametri in ingresso
     */
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(it.algos.vaadtest.TestApplication.class, args);
    }// end of constructor


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
        return applicationBuilder.sources(it.algos.vaadtest.TestApplication.class);
    }// end of method

}// end of main class
