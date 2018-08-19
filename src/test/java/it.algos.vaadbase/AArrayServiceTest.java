package it.algos.vaadbase;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import it.algos.vaadbase.service.AArrayService;
import it.algos.vaadbase.service.ATextService;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Project springvaadin
 * Created by Algos
 * User: gac
 * Date: ven, 08-dic-2017
 * Time: 10:28
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("array")
@DisplayName("Test sul service di utility per gli arry")
public class AArrayServiceTest extends ATest {


    private final static String[] stringArray = {"primo", "secondo", "quarto", "quinto", "1Ad", "terzo", "a10"};
    private final static List<String> stringList = Arrays.asList(stringArray);
    private final static Object[] objArray = {new Label("Alfa"), new Button()};
    private final static List<Object> objList = Arrays.asList(objArray);
    private final static Long[] longArray = {234L, 85L, 151099L, 123500L, 3L, 456772L};
    private final static List<Long> longList = Arrays.asList(longArray);
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

    /**
     * Costruisce una stringa con i singoli valori divisi da un pipe
     * <p>
     *
     * @param array lista di valori
     *
     * @return stringa con i singoli valori divisi da un separatore
     */
    @Test
    public void toStringaPipe() {
        previsto = "primo|secondo|quarto|quinto|1Ad|terzo|a10";
        ottenuto = service.toStringaPipe(stringList);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
    }// end of method


    /**
     * Costruisce una stringa con i singoli valori divisi da un separatore
     * <p>
     *
     * @param array lista di valori
     * @param sep   carattere separatore
     *
     * @return stringa con i singoli valori divisi da un separatore
     */
    @Test
    public void toStringa() {
        previsto = "primo|secondo|quarto|quinto|1Ad|terzo|a10";
        ottenuto = service.toStringa(stringList, "|");
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);

        previsto = "primo,secondo,quarto,quinto,1Ad,terzo,a10";
        ottenuto = service.toStringa(stringList, ",");
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
    }// end of method

    /**
     * Numero di cicli
     *
     * @param totale da dividere
     * @param blocco divisore
     *
     * @return numero di cicli
     */
    @Test
    public void numCicli() {
        previstoIntero = 5;
        ottenutoIntero = service.numCicli(50, 10);
        assertNotNull(ottenuto);
        assertEquals(previstoIntero, ottenutoIntero);

        previstoIntero = 6;
        ottenutoIntero = service.numCicli(51, 10);
        assertNotNull(ottenuto);
        assertEquals(previstoIntero, ottenutoIntero);
    }// end of method

    /**
     * Estra un subset dalla lista
     *
     * @param listatTotale  da suddividere
     * @param dimBlocco     di suddivisione
     * @param cicloCorrente attuale
     *
     * @return sublista corrente del ciclo
     */
    @Test
    public void estraeSublista() {
        List listaOttenuta;
        List listaPrevista;

        String[] subStringa = {"primo", "secondo"};
        listaPrevista = Arrays.asList(subStringa);
        listaOttenuta = service.estraeSublista(stringList, 2, 1);
        assertNotNull(listaOttenuta);
        assertEquals(listaPrevista, listaOttenuta);

        String[] subStringa2 = {"quinto", "1Ad", "terzo"};
        listaPrevista = Arrays.asList(subStringa2);
        listaOttenuta = service.estraeSublista(stringList, 3, 2);
        assertNotNull(listaOttenuta);
        assertEquals(listaPrevista, listaOttenuta);

        listaOttenuta = service.estraeSublista(stringList, 2, 0);
        assertNull(listaOttenuta);

        listaOttenuta = service.estraeSublista(null, 2, 1);
        assertNull(listaOttenuta);

        listaOttenuta = service.estraeSublista(stringList, 9, 1);
        assertNotNull(listaOttenuta);
        assertEquals(stringList, listaOttenuta);
    }// end of method

    /**
     * Estra un subset dalla lista
     *
     * @param listatTotale  da suddividere
     * @param dimBlocco     di suddivisione
     * @param cicloCorrente attuale
     *
     * @return sublista corrente del ciclo
     */
    @Test
    public void estraeSublistaLong() {
        previsto = "234L,85L,151099L,123500L,3L,456772L";
        List<Long> listaOttenuta;
        List<Long> listaPrevista;

        Long[] subLong = {234L, 85L};
        listaPrevista = Arrays.asList(subLong);
        listaOttenuta = service.estraeSublistaLong(longList, 2, 1);
        assertNotNull(listaOttenuta);
        assertEquals(listaPrevista, listaOttenuta);

        Long[] subLong2 = {123500L, 3L, 456772L};
        listaPrevista = Arrays.asList(subLong2);
        listaOttenuta = service.estraeSublistaLong(longList, 3, 2);
        assertNotNull(listaOttenuta);
        assertEquals(listaPrevista, listaOttenuta);

    }// end of method

}// end of class
