package it.algos.vaadbase;

import it.algos.vaadbase.wizard.scripts.TElabora;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: mar, 05-giu-2018
 * Time: 11:50
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Test sulla riflessione")
@Tag("elabora")
public class ATElaboraTest extends ATest {

    @InjectMocks
    public TElabora elabora;

    private static final String PROPERTY_ORDINE_NAME = "Ordine";

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        elabora.newEntityName = "Prova";
        elabora.flagCode = false;
        elabora.flagCompany = false;
        elabora.flagDescrizione = false;
        elabora.flagKeyCode = false;
        elabora.flagOrdine = false;
    }// end of method


    @Test
    public void creaQuery() {
        elabora.flagCode = false;
        elabora.flagCompany = false;
        elabora.flagDescrizione = false;
        elabora.flagKeyCode = false;
        elabora.flagOrdine = false;
        System.out.println();
        System.out.println();
        System.out.println("==========");
        System.out.println("Crea query");
        System.out.println("==========");

        previsto = "";
        ottenuto = elabora.creaQuery();
        assertEquals(previsto, ottenuto);
        System.out.println("flags falsi:" + ottenuto);

        elabora.flagOrdine = true;
        ottenuto = elabora.creaQuery();
        System.out.println();
        System.out.println();
        System.out.println("flagOrdine=true:" + ottenuto);

        elabora.flagCode = true;
        ottenuto = elabora.creaQuery();
        System.out.println();
        System.out.println();
        System.out.println("flagOrdine=true and flagCode=true:" + ottenuto);

        elabora.flagCompany = true;
        elabora.flagOrdine = false;
        elabora.flagCode = false;
        ottenuto = elabora.creaQuery();
        System.out.println();
        System.out.println();
        System.out.println("flagCompany=true and flagOrdine=false and flagCode=false:" + ottenuto);

        elabora.flagOrdine = true;
        ottenuto = elabora.creaQuery();
        System.out.println();
        System.out.println();
        System.out.println("flagCompany=true and flagOrdine=true and flagCode=false:" + ottenuto);

        elabora.flagCode = true;
        ottenuto = elabora.creaQuery();
        System.out.println();
        System.out.println();
        System.out.println("flagCompany=true and flagOrdine=true and flagCode=true:" + ottenuto);
    }// end of single test


    @Test
    public void creaFindAll() {
        elabora.flagCode = false;
        elabora.flagCompany = false;
        elabora.flagDescrizione = false;
        elabora.flagKeyCode = false;
        elabora.flagOrdine = false;
        System.out.println();
        System.out.println();
        System.out.println("==========");
        System.out.println("Crea creaFindAll");
        System.out.println("==========");

        elabora.flagOrdine = false;
        elabora.flagCompany = false;
        ottenuto = elabora.creaFindAll();
        System.out.println();
        System.out.println();
        System.out.println("flagOrdine=false and flagCompany=false:" );
        System.out.println(ottenuto);

        elabora.flagOrdine = true;
        elabora.flagCompany = false;
        ottenuto = elabora.creaFindAll();
        System.out.println();
        System.out.println();
        System.out.println("flagOrdine=true and flagCompany=false:" );
        System.out.println(ottenuto);

        elabora.flagOrdine = false;
        elabora.flagCompany = true;
        ottenuto = elabora.creaFindAll();
        System.out.println();
        System.out.println();
        System.out.println("flagOrdine=false and flagCompany=true:" );
        System.out.println(ottenuto);

        elabora.flagOrdine = true;
        elabora.flagCompany = true;
        ottenuto = elabora.creaFindAll();
        System.out.println();
        System.out.println();
        System.out.println("flagOrdine=true and flagCompany=true:" );
        System.out.println(ottenuto);
    }// end of single test

}// end of class
