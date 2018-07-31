package it.algos.vaadbase;

import it.algos.vaadbase.backend.service.AService;
import it.algos.vaadbase.modules.company.Company;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: dom, 22-lug-2018
 * Time: 19:05
 */

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Test sul service principale")
public class AServiceTest extends ATest {

    @InjectMocks
    public AService service;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(service);
    }// end of method


    @SuppressWarnings("javadoc")
    /**
     * Returns the number of entities available for the company
     *
     * @return the number of selected entities
     */
    @Test
    public void countByCompany() {
        Company company = null;
        previstoIntero = 5;
        ottenutoIntero = service.countByCompany(company);


        assertNotNull(ottenuto);
    }// end of single test

}// end of class
