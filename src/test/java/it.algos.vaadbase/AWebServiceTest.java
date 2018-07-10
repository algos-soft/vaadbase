package it.algos.vaadbase;

import it.algos.vaadbase.web.AWebService;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: ven, 06-lug-2018
 * Time: 13:36
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("web")
@DisplayName("Test sulle request web")
public class AWebServiceTest extends ATest {


    @InjectMocks
    public AWebService web;


    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(web);
    }// end of method


    @SuppressWarnings("javadoc")
    /**
     * Request principale (GET)
     * Legge una pagina internet (qualsiasi)
     * Accetta SOLO un domain (indirizzo) completo
     *
     * @param webPage titolo completo della pagina web generica
     */
    @Test
    @DisplayName("Controllo una pagina qualsiasi")
    public void urlRequest() {
        ottenuto = web.urlRequest(TITOLO_WEB_ERRATO);
        assertEquals(ottenuto, "");

        ottenuto = web.urlRequest(TITOLO_WEB);
        assertNotNull(ottenuto);
    } // fine del metodo


}// end of class
