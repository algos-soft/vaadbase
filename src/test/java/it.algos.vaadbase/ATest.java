package it.algos.vaadbase;

import it.algos.vaadbase.backend.entity.AEntity;
import it.algos.vaadbase.modules.role.Role;
import it.algos.vaadbase.modules.role.RoleViewList;
import it.algos.vaadbase.ui.IAView;
import it.algos.vaadbase.ui.enumeration.EAFieldType;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Project springvaadin
 * Created by Algos
 * User: gac
 * Date: lun, 25-dic-2017
 * Time: 09:54
 */
public class ATest {

    protected final static String FIELD_NAME_KEY = "id";
    protected final static String FIELD_NAME_ORDINE = "ordine";
    protected final static String FIELD_NAME_CODE = "code";
    protected final static String FIELD_NAME_COMPANY = "company2";
    protected final static String FIELD_NAME_NICKNAME = "nickname";
    protected final static String FIELD_NAME_PASSWORD = "password";
    protected final static String FIELD_NAME_ROLE = "role";
    protected final static String FIELD_NAME_ENABLED = "enabled";
    protected final static String FIELD_NAME_NOTE = "note";
    protected final static String FIELD_NAME_CREAZIONE = "creazione";
    protected final static String FIELD_NAME_MODIFICA = "modifica";

    protected final static String NAME_ORDINE = "ordine";
    protected final static String NAME_CODE = "code";
    protected final static String NAME_ROLE = "role";
    protected final static String HEADER_ORDINE = "#";
    protected final static String HEADER_CODE = "Code";
    protected static Field FIELD_ORDINE;
    protected static Field FIELD_CODE;
    protected static Field FIELD_ROLE;
    protected static Class<? extends IAView> ROLE_VIEW_CLASS = RoleViewList.class;
    protected static Class<? extends AEntity> ROLE_ENTITY_CLASS = Role.class;

    protected Field reflectionJavaField;
    protected String sorgente = "";
    protected String previsto = "";
    protected String ottenuto = "";
    protected boolean previstoBooleano;
    protected boolean ottenutoBooleano;
    protected int previstoIntero = 0;
    protected int ottenutoIntero = 0;
    protected List<String> previstoList;
    protected List<String> ottenutoList;
    protected List<Field> previstoFieldList;
    protected List<Field> ottenutoFieldList;
    protected EAFieldType previstoType;
    protected EAFieldType ottenutoType;
//    protected EAFieldAccessibility previstaAccessibilità;
//    protected EAFieldAccessibility ottenutaAccessibilità;
//    protected EACompanyRequired previstoCompany;
//    protected EACompanyRequired ottenutoCompany;

    protected static String TITOLO_WEB = "http://www.algos.it/estudio";
    protected static String TITOLO_WEB_ERRATO = "http://www.pippozbelloz.it/";

    private static String SEP1 = ": ";
    private static String SEP2 = " -> ";
    protected static String SEP3 = "/";


    // alcune date di riferimento
    protected final static Date DATE_UNO = new Date(1413868320000L); // 21 ottobre 2014, 7 e 12
    protected final static Date DATE_DUE = new Date(1398057120000L); // 21 aprile 2014, 7 e 12
    protected final static Date DATE_TRE = new Date(1412485920000L); // 5 ottobre 2014, 7 e 12
    protected final static Date DATE_QUATTRO = new Date(1394259124000L); // 8 marzo 2014, 7 e 12 e 4
    protected final static LocalDate LOCAL_DATE_UNO = LocalDate.of(2014, 10, 21);
    protected final static LocalDate LOCAL_DATE_DUE = LocalDate.of(2014, 4, 21);
    protected final static LocalDate LOCAL_DATE_TRE = LocalDate.of(2014, 10, 5);
    protected final static LocalDate LOCAL_DATE_QUATTRO = LocalDate.of(2014, 3, 8);
    protected final static LocalDateTime LOCAL_DATE_TIME_UNO = LocalDateTime.of(2014, 10, 21, 7, 12);
    protected final static LocalDateTime LOCAL_DATE_TIME_DUE = LocalDateTime.of(2014, 4, 21, 7, 12);

    protected void print(String message, String sorgente, Object ottenuto) {
        System.out.println(message + SEP1 + sorgente + SEP2 + ottenuto);
    }// end of single test

}// end of class
