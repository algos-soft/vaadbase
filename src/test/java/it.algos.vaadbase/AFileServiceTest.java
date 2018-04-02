package it.algos.vaadbase;

import it.algos.vaadbase.service.AFileService;
import it.algos.vaadbase.service.ATextService;
import lombok.extern.slf4j.Slf4j;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Project springvaadin
 * Created by Algos
 * User: gac
 * Date: mar, 06-mar-2018
 * Time: 09:57
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("file")
@DisplayName("Test sul service di accesso ai files")
public class AFileServiceTest extends ATest {


    @InjectMocks
    private AFileService service;

    @InjectMocks
    private ATextService textService;

    private static String PATH = "/Users/gac/Documents/IdeaProjects/vaadbase/src/main/java/it/algos/vaadbase/wizard/sources/";
    private static String DIR = PATH + "Prova";
    private static String READ = "README.txt";
    private static String PACKAGE = "package it.algos.vaadbase;\n";
    private static String TESTO = PACKAGE + "public class Alfa {}";
    private static String NOME_FILE = PATH + "Alfa.java";

    private boolean statusPrevisto;
    private boolean statusOttenuto;

    private String nomeCompletoFile;
    private String nomeCompletoDirectory;

    private String testoPrevisto;
    private String testoOttenuto;


    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(service);
        MockitoAnnotations.initMocks(textService);
        service.text = textService;
    }// end of method


    @SuppressWarnings("javadoc")
    /**
     * Controlla l'esistenza di un file
     *
     * @param nameFileToBeChecked
     *
     * @return true se il file esiste
     * false se non è un file o se non esiste
     */
    @Test
    public void isEsisteFile() {
        System.out.println("");
        System.out.println("Controllo l'esistenza del file");
        nomeCompletoFile = "nonEsiste";
        statusPrevisto = false;

        statusOttenuto = service.isEsisteFile(new File(nomeCompletoFile));
        assertEquals(statusPrevisto, statusOttenuto);
        print("Esiste file", nomeCompletoFile, statusOttenuto);

        statusOttenuto = service.isEsisteFile(nomeCompletoFile);
        assertEquals(statusPrevisto, statusOttenuto);

        nomeCompletoFile = READ;
        statusPrevisto = true;
        statusOttenuto = service.isEsisteFile(nomeCompletoFile);
        assertEquals(statusPrevisto, statusOttenuto);

        nomeCompletoFile = "src";
        statusPrevisto = false;
        statusOttenuto = service.isEsisteFile(nomeCompletoFile);
        assertEquals(statusPrevisto, statusOttenuto);
    }// end of method


    @SuppressWarnings("javadoc")
    /**
     * Controlla l'esistenza di una directory
     *
     * @param directoryToBeChecked
     *
     * @return true se la directory
     * false se non è una directory o se non esiste
     */
    @Test
    public void isEsisteDirectory() {
        System.out.println("*isEsisteDirectory - inizio");
        nomeCompletoDirectory = "nonEsiste";
        statusPrevisto = false;

        statusOttenuto = service.isEsisteDirectory(new File(nomeCompletoDirectory));
        assertEquals(statusPrevisto, statusOttenuto);

        statusOttenuto = service.isEsisteDirectory(nomeCompletoDirectory);
        assertEquals(statusPrevisto, statusOttenuto);

        nomeCompletoDirectory = "src";
        statusPrevisto = true;

        statusOttenuto = service.isEsisteDirectory(nomeCompletoDirectory);
        assertEquals(statusPrevisto, statusOttenuto);

        nomeCompletoDirectory = READ;
        statusPrevisto = false;
        statusOttenuto = service.isEsisteDirectory(nomeCompletoDirectory);
        assertEquals(statusPrevisto, statusOttenuto);
        System.out.println("*isEsisteDirectory - fine");
    }// end of method


    @SuppressWarnings("javadoc")
    /**
     * Cancella un file
     *
     * @param fileToBeDeleted file col path completo
     *
     * @return true se il file è stata cancellata
     * false se non è stato cancellato o se non esiste
     */
    @Test
    public void deleteFile() {
        System.out.println("*deleteFile - inizio");
        statusPrevisto = false;
        statusOttenuto = service.deleteFile("pippo");
        assertEquals(statusPrevisto, statusOttenuto);

        service.creaFile("pippo");
        statusPrevisto = true;
        statusOttenuto = service.deleteFile("pippo");
        assertEquals(statusPrevisto, statusOttenuto);

        statusPrevisto = false;
        statusOttenuto = service.deleteFile(DIR);
        assertEquals(statusPrevisto, statusOttenuto);


        System.out.println("*deleteFile - fine");
    }// end of method


    @SuppressWarnings("javadoc")
    /**
     * Cancella una directory
     *
     * @param directoryToBeDeleted file col path completo
     *
     * @return true se la directory è stata cancellata
     * false se non è stata cancellata o se non esiste
     */
    @Test
    public void deleteDirectory() {
        System.out.println("*deleteDirectory - inizio");
        statusPrevisto = false;
        statusOttenuto = service.deleteDirectory(new File(DIR.toLowerCase()));
        assertEquals(statusPrevisto, statusOttenuto);

        service.creaDirectory(DIR);

        statusPrevisto = true;
        statusOttenuto = service.deleteDirectory(DIR);
        assertEquals(statusPrevisto, statusOttenuto);

        statusPrevisto = false;
        statusOttenuto = service.deleteDirectory("pippo");
        assertEquals(statusPrevisto, statusOttenuto);

        System.out.println("*deleteDirectory - fine");
    }// end of method


    @SuppressWarnings("javadoc")
    /**
     * Crea una directory
     *
     * @param nomeCompletoDirectory
     */
    @Test
    public void creaDirectory() {
        System.out.println("*creaDirectory - inizio");
        statusPrevisto = true;
        String nomeDirectory = DIR;
        statusOttenuto = service.creaDirectory(nomeDirectory);
        assertEquals(statusPrevisto, statusOttenuto);

        service.deleteDirectory(DIR);

        System.out.println("*creaDirectory - fine");
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Crea un file
     *
     * @param nameFileWithSuffix nome completo del file, compreso il suffisso
     * @param text     completo del file
     */
    @Test
    public void creaFile() {
        System.out.println("*creaFile - inizio");
        statusPrevisto = true;
        String nomeFile = "alfa.java";
        statusOttenuto = service.creaFile(nomeFile);
        assertEquals(statusPrevisto, statusOttenuto);

        service.deleteFile(nomeFile);
        System.out.println("*creaFile - fine");
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Scrive un file
     * Se non esiste, lo crea
     *
     * @param nameFileToBeWritten file col path completo
     * @param text                contenuto del file
     * @param sovrascrive         anche se esiste già
     */
    @Test
    public void scriveFile() {
        System.out.println("*scriveFile - inizio");
        statusPrevisto = true;
        String nomeFile = NOME_FILE;
        String testo = TESTO;
        statusOttenuto = service.scriveFile(nomeFile, testo);
        assertEquals(statusPrevisto, statusOttenuto);

        statusPrevisto = true;
        statusOttenuto = service.scriveFile(nomeFile, testo + "pippo", true);
        assertEquals(statusPrevisto, statusOttenuto);

        service.deleteFile(nomeFile);
        System.out.println("*scriveFile - fine");
    }// end of method


    @SuppressWarnings("javadoc")
    /**
     * Legge un file
     *
     * @param nameFileToBeRead nome completo del file
     */
    @Test
    public void leggeFile() {
        System.out.println("*leggeFile - inizio");
        service.scriveFile(NOME_FILE, TESTO);
        String nomeFile = NOME_FILE;
        String testo = TESTO;

        testoPrevisto = testo;
        testoOttenuto = service.leggeFile(nomeFile);
        assertEquals(testoPrevisto, testoOttenuto);
        System.out.println(testoOttenuto);

        service.deleteFile(nomeFile);
        System.out.println("*leggeFile - fine");

        nomeFile = "/Users/gac/Documents/IdeaProjects/vaadbase/src/main/java/it/algos/vaadbase/application/BaseCost.java";
        testoOttenuto = service.leggeFile(nomeFile);
        assertNotNull(testoOttenuto);
        assertTrue(testoOttenuto.length() > 0);
        System.out.println(testoOttenuto);

        nomeFile = "/Users/gac/Documents/IdeaProjects/vaadbase/src/main/java/it/algos/vaadbase/application/BaseCostDeleted.java";
        testoOttenuto = service.leggeFile(nomeFile);
        assertEquals("", testoOttenuto);
        assertTrue(testoOttenuto.length() == 0);
    }// end of method

}// end of class
