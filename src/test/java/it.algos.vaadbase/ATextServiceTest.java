package it.algos.vaadbase;


import com.vaadin.flow.component.html.Label;
import it.algos.vaadbase.service.ATextService;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Project springvaadin
 * Created by Algos
 * User: gac
 * Date: gio, 07-dic-2017
 * Time: 14:23
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Test sul service di elaborazione stringhe")
public class ATextServiceTest extends ATest{


    private String sorgente = "";
    private String previsto = "";
    private String ottenuto = "";
    private boolean previstoBooleano;
    private boolean ottenutoBooleano;
    private String tag = "";
    private String oldTag = "";
    private String newTag = "";
    private int pos;

    @InjectMocks
    private ATextService SERVICE;


    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(SERVICE);
    }// end of method


    @SuppressWarnings("javadoc")
    /**
     * Null-safe, short-circuit evaluation.
     *
     * @param stringa in ingresso da controllare
     *
     * @return vero se la stringa è vuota o nulla
     */
    @Test
    @DisplayName("Controllo la stringa nulla o vuota")
    public void isEmpty() {
        System.out.println("");
        System.out.println("Controllo la stringa nulla o vuota");

        sorgente = "Modifica scheda";
        previstoBooleano = false;
        ottenutoBooleano = SERVICE.isEmpty(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);
        print("Stringa vuota", sorgente, ottenutoBooleano);

        sorgente = "";
        previstoBooleano = true;
        ottenutoBooleano = SERVICE.isEmpty(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);
        print("Stringa vuota", sorgente, ottenutoBooleano);

        sorgente = null;
        previstoBooleano = true;
        ottenutoBooleano = SERVICE.isEmpty(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);
        print("Stringa vuota", sorgente, ottenutoBooleano);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Null-safe, short-circuit evaluation.
     *
     * @param stringa in ingresso da controllare
     *
     * @return vero se la stringa esiste è non è vuota
     */
    @Test
    @DisplayName("Controllo la stringa valida")
    public void isValid() {
        System.out.println("");
        System.out.println("Controllo la stringa valida");

        sorgente = "Modifica scheda";
        previstoBooleano = true;
        ottenutoBooleano = SERVICE.isValid(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);
        print("Stringa valida", sorgente, ottenutoBooleano);

        sorgente = "";
        previstoBooleano = false;
        ottenutoBooleano = SERVICE.isValid(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);
        print("Stringa valida", sorgente, ottenutoBooleano);

        sorgente = null;
        previstoBooleano = false;
        ottenutoBooleano = SERVICE.isValid(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);
        print("Stringa valida", sorgente, ottenutoBooleano);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Controlla che sia una stringa e che sia valida.
     *
     * @param obj in ingresso da controllare
     *
     * @return vero se la stringa esiste è non è vuota
     */
    @Test
    @DisplayName("Controllo l'oggetto valido")
    public void isValidObj() {
        System.out.println("");
        System.out.println("Controllo l'oggetto valido");

        Label label = new Label();
        previstoBooleano = false;
        ottenutoBooleano = SERVICE.isValid(label);
        assertEquals(previstoBooleano, ottenutoBooleano);
        print("Oggetto valido", label.getText(), ottenutoBooleano);

        Object objString = "";
        previstoBooleano = false;
        ottenutoBooleano = SERVICE.isValid(label);
        assertEquals(previstoBooleano, ottenutoBooleano);
        print("Oggetto valido", objString.toString(), ottenutoBooleano);

        Object objStringFull = "a";
        previstoBooleano = true;
        ottenutoBooleano = SERVICE.isValid(objStringFull);
        assertEquals(previstoBooleano, ottenutoBooleano);
        print("Oggetto valido", objStringFull.toString(), ottenutoBooleano);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Forza il primo carattere della stringa al carattere maiuscolo
     * <p>
     * Se la stringa è nulla, ritorna un nullo
     * Se la stringa è vuota, ritorna una stringa vuota
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testo in ingresso
     *
     * @return test formattato in uscita
     */
    @Test
    @DisplayName("Prima maiuscola")
    public void primaMaiuscola() {
        System.out.println("");
        System.out.println("Prima maiuscola");

        sorgente = "TUTTO MAIUSCOLO ";
        previsto = "TUTTO MAIUSCOLO";
        ottenuto = SERVICE.primaMaiuscola(sorgente);
        assertEquals(previsto, ottenuto);
        print("Prima maiuscola", sorgente, ottenuto);

        sorgente = " tutto minuscolo";
        previsto = "Tutto minuscolo";
        ottenuto = SERVICE.primaMaiuscola(sorgente);
        assertEquals(previsto, ottenuto);
        print("Prima maiuscola", sorgente, ottenuto);

        sorgente = " afRodiSiacHo ";
        previsto = "AfRodiSiacHo";
        ottenuto = SERVICE.primaMaiuscola(sorgente);
        assertEquals(previsto, ottenuto);
        print("Prima maiuscola", sorgente, ottenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Forza il primo carattere della stringa al carattere minuscolo
     * <p>
     * Se la stringa è nulla, ritorna un nullo
     * Se la stringa è vuota, ritorna una stringa vuota
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testo in ingresso
     *
     * @return test formattato in uscita
     */
    @Test
    @DisplayName("Prima minuscola")
    public void primaMinuscola() {
        System.out.println("");
        System.out.println("Prima minuscola");

        sorgente = "tutto minuscolo ";
        previsto = "tutto minuscolo";
        ottenuto = SERVICE.primaMinuscola(sorgente);
        assertEquals(previsto, ottenuto);
        print("Prima minuscola", sorgente, ottenuto);

        sorgente = " TUTTO MAIUSCOLO";
        previsto = "tUTTO MAIUSCOLO";
        ottenuto = SERVICE.primaMinuscola(sorgente);
        assertEquals(previsto, ottenuto);
        print("Prima minuscola", sorgente, ottenuto);

        sorgente = " AfRodiSiacHo ";
        previsto = "afRodiSiacHo";
        ottenuto = SERVICE.primaMinuscola(sorgente);
        assertEquals(previsto, ottenuto);
        print("Prima minuscola", sorgente, ottenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Elimina dal testo il tagIniziale, se esiste
     * <p>
     * Esegue solo se il testo è valido
     * Se tagIniziale è vuoto, restituisce il testo
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testoIn     ingresso
     * @param tagIniziale da eliminare
     *
     * @return test ridotto in uscita
     */
    @Test
    @DisplayName("Elimina il tag iniziale")
    public void levaTesta() {
        System.out.println("");
        System.out.println("Elimina il tag iniziale");

        sorgente = "Non Levare questo inizio ";
        tag = "Non";
        previsto = "Levare questo inizio";
        ottenuto = SERVICE.levaTesta(sorgente, tag);
        assertEquals(previsto, ottenuto);
        print("Leva tag", sorgente + SEP3 + tag, ottenuto);

        sorgente = "Non Levare questo inizio ";
        tag = "";
        previsto = "Non Levare questo inizio";
        ottenuto = SERVICE.levaTesta(sorgente, tag);
        assertEquals(previsto, ottenuto);
        print("Leva tag", sorgente + SEP3 + tag, ottenuto);

        sorgente = "Non Levare questo inizio ";
        tag = "NonEsisteQuestoTag";
        previsto = "Non Levare questo inizio";
        ottenuto = SERVICE.levaTesta(sorgente, tag);
        assertEquals(previsto, ottenuto);
        print("Leva tag", sorgente + SEP3 + tag, ottenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Elimina dal testo il tagFinale, se esiste
     * <p>
     * Esegue solo se il testo è valido
     * Se tagIniziale è vuoto, restituisce il testo
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testoIn   ingresso
     * @param tagFinale da eliminare
     *
     * @return test ridotto in uscita
     */
    @Test
    @DisplayName("Elimina il tag finale")
    public void levaCoda() {
        System.out.println("");
        System.out.println("Elimina il tag finale");

        sorgente = " Levare questa fine Non ";
        tag = "Non";
        previsto = "Levare questa fine";
        ottenuto = SERVICE.levaCoda(sorgente, tag);
        assertEquals(previsto, ottenuto);
        print("Leva tag", sorgente + SEP3 + tag, ottenuto);

        sorgente = "Non Levare questa fine ";
        tag = "";
        previsto = "Non Levare questa fine";
        ottenuto = SERVICE.levaCoda(sorgente, tag);
        assertEquals(previsto, ottenuto);
        print("Leva tag", sorgente + SEP3 + tag, ottenuto);

        sorgente = "Non Levare questa fine ";
        tag = "NonEsisteQuestoTag";
        previsto = "Non Levare questa fine";
        ottenuto = SERVICE.levaCoda(sorgente, tag);
        assertEquals(previsto, ottenuto);
        print("Leva tag", sorgente + SEP3 + tag, ottenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Elimina il testo da tagFinale in poi
     * <p>
     * Esegue solo se il testo è valido
     * Se tagInterrompi è vuoto, restituisce il testo
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testoIn   ingresso
     * @param tagInterrompi da dove inizia il testo da eliminare
     *
     * @return test ridotto in uscita
     */
    @Test
    @DisplayName("Elimina dal tag finale in poi")
    public void levaCodaDa() {
        System.out.println("");
        System.out.println("Elimina dal tag finale in poi");

        sorgente = " Levare questa fine Non ";
        tag = "N";
        previsto = "Levare questa fine";
        ottenuto = SERVICE.levaCodaDa(sorgente, tag);
        assertEquals(previsto, ottenuto);
        print("Leva da tag", sorgente + SEP3 + tag, ottenuto);

        sorgente = "Non Levare questa fine ";
        tag = "";
        previsto = "Non Levare questa fine";
        ottenuto = SERVICE.levaCodaDa(sorgente, tag);
        assertEquals(previsto, ottenuto);
        print("Leva da tag", sorgente + SEP3 + tag, ottenuto);

        sorgente = "Non Levare questa fine ";
        tag = "questa";
        previsto = "Non Levare";
        ottenuto = SERVICE.levaCodaDa(sorgente, tag);
        assertEquals(previsto, ottenuto);
        print("Leva da tag", sorgente + SEP3 + tag, ottenuto);

        sorgente = "Non Levare questa fine ";
        tag = "NonEsisteQuestoTag";
        previsto = "Non Levare questa fine";
        ottenuto = SERVICE.levaCodaDa(sorgente, tag);
        assertEquals(previsto, ottenuto);
        print("Leva da tag", sorgente + SEP3 + tag, ottenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Sostituisce nel testo tutte le occorrenze di oldTag con newTag.
     * Esegue solo se il testo è valido
     * Esegue solo se il oldTag è valido
     * newTag può essere vuoto (per cancellare le occorrenze di oldTag)
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testoIn ingresso da elaborare
     * @param oldTag  da sostituire
     * @param newTag  da inserire
     *
     * @return testo modificato
     */
    @Test
    @DisplayName("Sostituisce tutte le occorrenze")
    public void sostituisce() {
        System.out.println("");
        System.out.println("Sostituisce tutte le occorrenze");

        sorgente = "Devo sostituire tutte le t con p";
        oldTag = "t";
        newTag = "p";
        previsto = "Devo sospipuire puppe le p con p";
        ottenuto = SERVICE.sostituisce(sorgente, oldTag, newTag);
        assertEquals(previsto, ottenuto);
        print("Sostituisce", sorgente + SEP3 + oldTag + SEP3 + newTag, ottenuto);

        sorgente = "Devo sostituire oldTagtutte le oldTag con newTag";
        oldTag = "oldTag";
        newTag = "newTag";
        previsto = "Devo sostituire newTagtutte le newTag con newTag";
        ottenuto = SERVICE.sostituisce(sorgente, oldTag, newTag);
        assertEquals(previsto, ottenuto);
        print("Sostituisce", sorgente + SEP3 + oldTag + SEP3 + newTag, ottenuto);

        sorgente = "Devo oldTag cancoldTagellare tutte le oldTag";
        oldTag = "oldTag";
        newTag = "";
        previsto = "Devo cancellare tutte le";
        ottenuto = SERVICE.sostituisce(sorgente, oldTag, newTag);
        assertEquals(previsto, ottenuto);
        print("Sostituisce", sorgente + SEP3 + oldTag + SEP3 + newTag, ottenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Inserisce nel testo alla posizione indicata
     * Esegue solo se il testo è valido
     * Esegue solo se il newTag è valido
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testoIn ingresso da elaborare
     * @param newTag  da inserire
     * @param pos     di inserimento
     *
     * @return testo modificato
     */
    @Test
    @DisplayName("Inserisce il tag")
    public void inserisce() {
        System.out.println("");
        System.out.println("Inserisce il tag");

        tag = "tutte";
        newTag = "pippoz";
        sorgente = "Devo oldTag cancellare tutte le oldTag";
        previsto = "Devo oldTag cancellare " + newTag + "tutte le oldTag";

        pos = sorgente.indexOf(tag);
        ottenuto = SERVICE.inserisce(sorgente, newTag, pos);
        assertEquals(previsto, ottenuto);
        print("Inserisce tag", sorgente + SEP3 + newTag + SEP3 + tag, ottenuto);
    }// end of single test


}// end of class
