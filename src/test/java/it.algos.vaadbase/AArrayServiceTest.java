package it.algos.vaadbase;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import it.algos.vaadbase.service.AArrayService;
import it.algos.vaadbase.service.ATextService;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * Project springvaadin
 * Created by Algos
 * User: gac
 * Date: ven, 08-dic-2017
 * Time: 10:28
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Test sul service di utility per gli arry")
public class AArrayServiceTest extends ATest{


    private final static String[] stringArray = {"primo", "secondo", "quarto", "quinto", "1Ad", "terzo", "a10"};
    private final static List<String> stringList = Arrays.asList(stringArray);
    private final static Object[] objArray = {new Label("Alfa"), new Button()};
    private final static List<Object> objList = Arrays.asList(objArray);
    private List<String> prevista;
    private List<String> ottenuta;


    @InjectMocks
    private AArrayService service;


    @InjectMocks
    private ATextService textService;


    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(service);
        service.text = textService;
    }// end of method


    @SuppressWarnings("javadoc")
    /**
     * Controlla la validità dell'array
     * Deve essitere (not null)
     * Deve avere degli elementi (size > 0)
     * Il primo elemento deve essere valido
     *
     * @param array (List) in ingresso da controllare
     *
     * @return vero se l'array soddisfa le condizioni previste
     */
    @Test
    public void isValid() {
        previstoBooleano = true;
        ottenutoBooleano = service.isValid(stringList);
        assertEquals(previstoBooleano, ottenutoBooleano);

        previstoBooleano = false;
        ottenutoBooleano = service.isValid((List) null);
        assertEquals(previstoBooleano, ottenutoBooleano);

        previstoBooleano = false;
        ottenutoBooleano = service.isValid(new ArrayList());
        assertEquals(previstoBooleano, ottenutoBooleano);

        String[] stringArray = {"", "secondo", "quarto", "quinto", "1Ad", "terzo", "a10"};
        List<String> lista = Arrays.asList(stringArray);
        previstoBooleano = false;
        ottenutoBooleano = service.isValid(lista);
        assertEquals(previstoBooleano, ottenutoBooleano);

        previstoBooleano = true;
        ottenutoBooleano = service.isValid(objList);
        assertEquals(previstoBooleano, ottenutoBooleano);

        Object[] objArray = {null, new Button()};
        List<Object> objList = Arrays.asList(objArray);
        previstoBooleano = false;
        ottenutoBooleano = service.isValid(objList);
        assertEquals(previstoBooleano, ottenutoBooleano);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Controlla la validità dell'array
     * Deve essitere (not null)
     * Deve avere degli elementi (length > 0)
     * Il primo elemento deve essere una stringa valida
     *
     * @param array (String[]) in ingresso da controllare
     *
     * @return vero se l'array soddisfa le condizioni previste
     */
    @Test
    public void isValid2() {
        previstoBooleano = true;
        ottenutoBooleano = service.isValid(stringArray);
        assertEquals(previstoBooleano, ottenutoBooleano);

        previstoBooleano = false;
        ottenutoBooleano = service.isValid((String[]) null);
        assertEquals(previstoBooleano, ottenutoBooleano);

        String[] stringArray = {"", "secondo", "quarto", "quinto", "1Ad", "terzo", "a10"};
        previstoBooleano = false;
        ottenutoBooleano = service.isValid(stringArray);
        assertEquals(previstoBooleano, ottenutoBooleano);

        String[] stringArray2 = {};
        previstoBooleano = false;
        ottenutoBooleano = service.isValid(stringArray2);
        assertEquals(previstoBooleano, ottenutoBooleano);
    }// end of single test

    @Test
    public void contains() {
        previstoBooleano = true;
        ottenutoBooleano = stringList.contains("secondo");
        assertEquals(previstoBooleano, ottenutoBooleano);

        previstoBooleano = false;
        ottenutoBooleano = stringList.contains("pippoz");
        assertEquals(previstoBooleano, ottenutoBooleano);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Aggiunge un elemento ad una List (di per se immutabile)
     * Deve esistere (not null)
     *
     * @param arrayIn (List) ingresso da incrementare
     *
     * @return la lista aumentata di un elemento
     */
    @Test
    public void add() {
        List<Object> objListOttenuta;
        Object newObj = new Object();

        int previstoInt = objList.size() + 1;
        objListOttenuta = service.add(objList, newObj);
        assertNotNull(objListOttenuta);
        int ottenutoInt = objListOttenuta.size();
        assertEquals(previstoInt, ottenutoInt);
    }// end of single test

}// end of class
