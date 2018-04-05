package it.algos.vaadbase;

import it.algos.vaadbase.service.ADateService;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Project springvaadin
 * Created by Algos
 * User: gac
 * Date: sab, 10-feb-2018
 * Time: 15:04
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("data")
@DisplayName("Test sul service di elaborazione stringhe")
public class ADateServiceTest extends ATest {


    @InjectMocks
    public ADateService service;


    // alcune date di riferimento
    private final static Date DATE_UNO = new Date(1413868320000L); // 21 ottobre 2014, 7 e 12
    private final static Date DATE_DUE = new Date(1398057120000L); // 21 aprile 2014, 7 e 12
    private final static Date DATE_TRE = new Date(1412485920000L); // 5 ottobre 2014, 7 e 12
    private final static Date DATE_QUATTRO = new Date(1394259124000L); // 8 marzo 2014, 7 e 12 e 4

    private final static LocalDate LOCAL_DATE_UNO = LocalDate.of(2014, 10, 21);
    private final static LocalDate LOCAL_DATE_DUE = LocalDate.of(2014, 4, 21);
    private final static LocalDate LOCAL_DATE_TRE = LocalDate.of(2014, 10, 5);
    private final static LocalDate LOCAL_DATE_QUATTRO = LocalDate.of(2014, 3, 8);

    private final static LocalDateTime LOCAL_DATE_TIME_UNO = LocalDateTime.of(2014, 10, 21, 7, 12);
    private final static LocalDateTime LOCAL_DATE_TIME_DUE = LocalDateTime.of(2014, 4, 21, 0, 0);

    private static int GIORNO = 12;
    private static int MESE = 7;
    private static int ANNO = 2004;

    // alcuni parametri utilizzati
    private Date dataPrevista = null;
    private Date dataOttenuta = null;
    private LocalDate localDataPrevista = null;
    private LocalDate localDataOttenuta = null;
    private LocalDateTime localDateTimePrevista = null;
    private LocalDateTime localDateTimeOttenuta = null;


    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(service);
    }// end of method

    @BeforeAll
    public void tearDown() {
        System.out.println("");
        System.out.println("");
        System.out.println("Data settimanale lunga: "+service.getWeekLong(LOCAL_DATE_UNO));
        System.out.println("Data settimanale breve: "+service.getDayWeekShort(LOCAL_DATE_UNO));
        System.out.println("");
        System.out.println("");
    }// end of method


    @SuppressWarnings("javadoc")
    /**
     * Convert java.util.Date to java.time.LocalDate
     * Date HA ore, minuti e secondi
     * LocalDate NON ha ore, minuti e secondi
     * Si perdono quindi le ore i minuti ed i secondi di Date
     *
     * @param data da convertire
     *
     * @return data locale (deprecated)
     */
    @Test
    public void dateToLocalDate() {
        localDataPrevista = LOCAL_DATE_UNO;
        localDataOttenuta = service.dateToLocalDate(DATE_UNO);
        assertEquals(localDataOttenuta, localDataPrevista);
        System.out.println("");
        System.out.println("dateToLocalDate: " + DATE_UNO + " -> " + localDataOttenuta);
        System.out.println("");
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Convert java.time.LocalDate to java.util.Date
     * LocalDate NON ha ore, minuti e secondi
     * Date HA ore, minuti e secondi
     * La Date ottenuta ha il tempo regolato a mezzanotte
     *
     * @param localDate da convertire
     *
     * @return data (deprecated)
     */
    @Test
    public void localDateToDate() {
        dataPrevista = DATE_UNO;
        dataOttenuta = service.localDateToDate(LOCAL_DATE_UNO);
//        assertEquals(dataOttenuta, dataPrevista);
        System.out.println("");
        System.out.println("localDateToDate: " + LOCAL_DATE_UNO + " -> " + dataOttenuta);
        System.out.println("");
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Convert java.util.Date to java.time.LocalDateTime
     * Date HA ore, minuti e secondi
     * LocalDateTime HA ore, minuti e secondi
     * Non si perde nulla
     *
     * @param data da convertire
     *
     * @return data e ora locale
     */
    @Test
    public void dateToLocalDateTime() {
        localDateTimePrevista = LOCAL_DATE_TIME_UNO;
        localDateTimeOttenuta = service.dateToLocalDateTime(DATE_UNO);
        assertEquals(localDataOttenuta, localDataPrevista);
        System.out.println("");
        System.out.println("dateToLocalDateTime: " + DATE_UNO + " -> " + localDateTimeOttenuta);
        System.out.println("");
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Convert java.time.LocalDateTime to java.util.Date
     * LocalDateTime HA ore, minuti e secondi
     * Date HA ore, minuti e secondi
     * Non si perde nulla
     *
     * @param localDateTime da convertire
     *
     * @return data (deprecated)
     */
    @Test
    public void localDateTimeToDate() {
        dataPrevista = DATE_UNO;
        dataOttenuta = service.localDateTimeToDate(LOCAL_DATE_TIME_UNO);
        assertEquals(dataOttenuta, dataPrevista);
        System.out.println("");
        System.out.println("localDateTimeToDate: " + LOCAL_DATE_TIME_UNO + " -> " + dataOttenuta);
        System.out.println("");
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Convert java.time.LocalDate to java.time.LocalDateTime
     * LocalDate NON ha ore, minuti e secondi
     * LocalDateTime HA ore, minuti e secondi
     * La LocalDateTime ottenuta ha il tempo regolato a mezzanotte
     *
     * @param localDate da convertire
     *
     * @return data con ore e minuti alla mezzanotte
     */
    @Test
    public void localDateToLocalDateTime() {
        localDateTimePrevista = LOCAL_DATE_TIME_UNO;
        localDateTimeOttenuta = service.localDateToLocalDateTime(LOCAL_DATE_UNO);
//        assertEquals(localDateTimeOttenuta, localDateTimePrevista);
        System.out.println("");
        System.out.println("localDateToLocalDateTime: " + LOCAL_DATE_UNO + " -> " + localDateTimeOttenuta);
        System.out.println("");
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Convert java.time.LocalDateTime to java.time.LocalDate
     * LocalDateTime HA ore, minuti e secondi
     * LocalDate NON ha ore, minuti e secondi
     * Si perdono quindi le ore i minuti ed i secondi di Date
     *
     * @param localDateTime da convertire
     *
     * @return data con ore e minuti alla mezzanotte
     */
    @Test
    public void localDateTimeToLocalDate() {
        localDataPrevista = LOCAL_DATE_UNO;
        localDataOttenuta = service.localDateTimeToLocalDate(LOCAL_DATE_TIME_UNO);
        assertEquals(localDataOttenuta, localDataPrevista);
        System.out.println("");
        System.out.println("localDateTimeToLocalDate: " + LOCAL_DATE_TIME_UNO + " -> " + localDataOttenuta);
        System.out.println("");
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Restituisce una stringa nel formato d-M-yy
     * <p>
     * Returns a string representation of the date <br>
     * Not using leading zeroes in day <br>
     * Two numbers for year <b>
     *
     * @param localDate da rappresentare
     *
     * @return la data sotto forma di stringa
     */
    @Test
    public void getWeekLong() {
        previsto = "martedì 21";

        ottenuto = service.getWeekLong(LOCAL_DATE_UNO);
        assertEquals(ottenuto, previsto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Ritorna il numero della settimana dell'anno di una data fornita.
     * Usa Calendar
     *
     * @param data fornita
     *
     * @return il numero della settimana dell'anno
     */
    @Test
    public void getWeekYear() {
        previstoIntero = 43;

        ottenutoIntero = service.getWeekYear(DATE_UNO);
        assertEquals(ottenutoIntero, previstoIntero);
    }// end of method


    @SuppressWarnings("javadoc")
    /**
     * Ritorna il numero della settimana del mese di una data fornita.
     * Usa Calendar
     *
     * @param data fornita
     *
     * @return il numero della settimana del mese
     */
    @Test
    public void getWeekMonth() {
        previstoIntero = 4;

        ottenutoIntero = service.getWeekMonth(DATE_UNO);
        assertEquals(ottenutoIntero, previstoIntero);
    }// end of method


    @SuppressWarnings("javadoc")
    /**
     * Ritorna il numero del giorno dell'anno di una data fornita.
     * Usa LocalDate internamente, perché Date è deprecato
     *
     * @param data fornita
     *
     * @return il numero del giorno dell'anno
     */
    @Test
    public void getDayYear() {
        previstoIntero = 294;

        ottenutoIntero = service.getDayYear(DATE_UNO);
        assertEquals(ottenutoIntero, previstoIntero);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Ritorna il numero del giorno del mese di una data fornita.
     * Usa LocalDate internamente, perché Date è deprecato
     *
     * @param data fornita
     *
     * @return il numero del giorno del mese
     */
    @Test
    public void getDayOfMonth() {
        previstoIntero = 21;

        ottenutoIntero = service.getDayOfMonth(DATE_UNO);
        assertEquals(ottenutoIntero, previstoIntero);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Ritorna il numero del giorno della settimana di una data fornita.
     * Usa Calendar
     *
     * @param data fornita
     *
     * @return il numero del giorno della settimana (1=dom, 7=sab)
     */
    @Test
    public void getDayWeek() {
        previstoIntero = 3;

        ottenutoIntero = service.getDayWeek(DATE_UNO);
        assertEquals(ottenutoIntero, previstoIntero);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Ritorna il giorno (testo) della settimana di una data fornita.
     *
     * @param localDate fornita
     *
     * @return il giorno della settimana in forma breve
     */
    @Test
    public void getDayWeekShort() {
        previsto = "mar";

        ottenuto = service.getDayWeekShort(LOCAL_DATE_UNO);
        assertEquals(ottenuto, previsto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Ritorna il giorno (testo) della settimana di una data fornita.
     * Usa LocalDate internamente, perché Date è deprecato
     *
     * @param data fornita
     *
     * @return il giorno della settimana in forma breve
     */
    @Test
    public void getDayWeekShortDate() {
        previsto = "mar";

        ottenuto = service.getDayWeekShort(DATE_UNO);
        assertEquals(ottenuto, previsto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Ritorna il giorno (testo) della settimana di una data fornita.
     * Usa LocalDate internamente, perché Date è deprecato
     *
     * @param data fornita
     *
     * @return il giorno della settimana in forma estesa
     */
    @Test
    public void getDayWeekFull() {
        previsto = "martedì";

        ottenuto = service.getDayWeekFull(LOCAL_DATE_UNO);
        assertEquals(ottenuto, previsto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Ritorna il giorno (testo) della settimana di una data fornita.
     * Usa LocalDate internamente, perché Date è deprecato
     *
     * @param data fornita
     *
     * @return il giorno della settimana in forma estesa
     */
    @Test
    public void getDayWeekFullDate() {
        previsto = "martedì";

        ottenuto = service.getDayWeekFull(DATE_UNO);
        assertEquals(ottenuto, previsto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Ritorna il numero delle ore di una data fornita.
     * Usa LocalDateTime internamente, perché Date è deprecato
     *
     * @param data fornita
     *
     * @return il numero delle ore
     */
    @Test
    public void getOre() {
        previstoIntero = 7;

        ottenutoIntero = service.getOre(DATE_UNO);
        assertEquals(ottenutoIntero, previstoIntero);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Ritorna il numero dei minuti di una data fornita.
     * Usa LocalDateTime internamente, perché Date è deprecato
     *
     * @param data fornita
     *
     * @return il numero dei minuti
     */
    @Test
    public void getMinuti() {
        previstoIntero = 12;

        ottenutoIntero = service.getMinuti(DATE_UNO);
        assertEquals(ottenutoIntero, previstoIntero);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Ritorna il numero dei secondi di una data fornita.
     * Usa LocalDateTime internamente, perché Date è deprecato
     *
     * @param data fornita
     *
     * @return il numero dei secondi
     */
    @Test
    public void getSecondi() {
        previstoIntero = 0;

        ottenutoIntero = service.getSecondi(DATE_UNO);
        assertEquals(ottenutoIntero, previstoIntero);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Ritorna il numero dell'anno di una data fornita.
     * Usa LocalDate internamente, perché Date è deprecato
     *
     * @return il numero dell'anno
     */
    @Test
    public void getYear() {
        previstoIntero = 2014;

        ottenutoIntero = service.getYear(DATE_UNO);
        assertEquals(ottenutoIntero, previstoIntero);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Costruisce la data per il 1° gennaio dell'anno corrente.
     *
     * @return primo gennaio dell'anno
     */
    @Test
    public void getPrimoGennaio() {
        localDataPrevista = LocalDate.of(2018, 1, 1);
        localDataOttenuta = service.getPrimoGennaio();
        assertEquals(localDataOttenuta, localDataPrevista);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Costruisce la data per il 1° gennaio dell'anno indicato.
     *
     * @param anno di riferimento 1985
     *
     * @return primo gennaio dell'anno
     */
    @Test
    public void getPrimoGennaioAnno() {
        localDataPrevista = LocalDate.of(1985, 1, 1);
        localDataOttenuta = service.getPrimoGennaio(1985);
        assertEquals(localDataOttenuta, localDataPrevista);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Costruisce la localData per il giorno dell'anno indicato.
     *
     * @param giorno di riferimento (numero progressivo dell'anno)
     *
     * @return localData
     */
    @Test
    public void getLocalDateByDay() {
        localDataPrevista = LocalDate.now();
        int numGiorno=LocalDate.now().getDayOfYear();
        localDataOttenuta = service.getLocalDateByDay(numGiorno);
        assertEquals(localDataOttenuta, localDataPrevista);
    }// end of single test


    @Test
    public void formattazione() {
        System.out.println("");
        System.out.println("");
        System.out.println(service.getWeekLong(LOCAL_DATE_UNO));
    }// end of single test

    @Test
    public void formattazione2() {
        System.out.println("");
        System.out.println(LOCAL_DATE_TIME_UNO.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
    }// end of single test

}// end of class
