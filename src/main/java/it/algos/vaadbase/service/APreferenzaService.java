package it.algos.vaadbase.service;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.enumeration.EAPrefType;
import it.algos.vaadbase.modules.preferenza.Preferenza;
import it.algos.vaadbase.modules.preferenza.PreferenzaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: mer, 30-mag-2018
 * Time: 07:03
 */
@Slf4j
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class APreferenzaService {

    /**
     * Service iniettato da Spring (@Scope = 'singleton'). Unica per tutta l'applicazione. Usata come libreria.
     */
    @Autowired
    public PreferenzaService prefService;


    private Preferenza get(String code) {
        return prefService.findByKeyUnica(code);
    } // end of method


    public Object getValue(String code) {
        Object value = null;
        Preferenza pref = null;
        byte[] bytes = null;
        EAPrefType type = null;

        pref = get(code);

        if (pref != null) {
            bytes = pref.getValue();
        }// end of if cycle

        if (bytes != null) {
            type = pref.getType();
        }// end of if cycle

        if (type != null) {
            value = type.bytesToObject(bytes);
        }// end of if cycle

        return value;
    } // end of method


    public String getString(String code) {
        String value = "";
        Object genericValue = getValue(code);

        if (genericValue instanceof String) {
            value = (String) genericValue;
        }// end of if cycle

        return value;
    } // end of method


    public int getInt(String code) {
        int value = 0;
        Object genericValue = getValue(code);

        if (genericValue instanceof Integer) {
            value = (Integer) genericValue;
        }// end of if cycle

        return value;
    } // end of method


    public boolean isBool(String code) {
        boolean value = false;
        Object genericValue = getValue(code);

        if (genericValue instanceof Boolean) {
            value = (Boolean) genericValue;
        }// end of if cycle

        return value;
    } // end of static method

//    public void setValue(String code, Object value) {
//        Preferenza pref = get(code);
//        ;
//        EAPrefType type = null;
//
//        if (pref != null) {
//            type = pref.getType();
//        }// end of if cycle
//
//    } // end of method


    public void setBool(String code, boolean value) {
        Preferenza pref = get(code);
        EAPrefType type = null;

        if (pref != null) {
            type = pref.getType();
        }// end of if cycle

        if (type == EAPrefType.bool) {
            pref.setValue(type.objectToBytes(value));
            prefService.save(pref);
        }// end of if cycle

    } // end of method


//    public static String getString(String code, Object defaultValue) {
//        return getString(code, CompanySessionLib.getCompany(), defaultValue);
//    } // end of static method
//
//    public static String getString(String code, BaseCompany company) {
//        return getString(code, company, "");
//    } // end of static method
//
//    public static String getString(String code, BaseCompany company, Object defaultValue) {
//        Pref pref = Pref.findByCode(code, company);
//
//        if (pref != null) {
//            return (String) pref.getValore();
//        }// end of if cycle
//
//        if (defaultValue != null && defaultValue instanceof String) {
//            return (String) defaultValue;
//        }// end of if cycle
//
//        return null;
//    } // end of static method
//
//    public static Boolean getBool(String code) {
//        return getBool(code, "");
//    } // end of static method
//
//    public static Boolean getBool(String code, Object defaultValue) {
//        return getBool(code, CompanySessionLib.getCompany(), defaultValue);
//    } // end of static method
//
//    public static Boolean getBool(String code, BaseCompany company) {
//        return getBool(code, company, "");
//    } // end of static method
//
//    public static Boolean getBool(String code, BaseCompany company, Object defaultValue) {
//        Pref pref = Pref.findByCode(code, company);
//
//        if (pref != null) {
//            return (boolean) pref.getValore();
//        }// end of if cycle
//
//        if (defaultValue != null && defaultValue instanceof Boolean) {
//            return (boolean) defaultValue;
//        }// end of if cycle
//
//        return false;
//    } // end of static method
//
//    public static int getInt(String code) {
//        return getInt(code, "");
//    } // end of static method
//
//    public static int getInt(String code, Object defaultValue) {
//        return getInt(code, CompanySessionLib.getCompany(), defaultValue);
//    } // end of static method
//
//    public static int getInt(String code, BaseCompany company) {
//        return getInt(code, company, "");
//    } // end of static method
//
//    public static int getInt(String code, BaseCompany company, Object defaultValue) {
//        Pref pref = Pref.findByCode(code, company);
//
//        if (pref != null) {
//            return (int) pref.getValore();
//        }// end of if cycle
//
//        if (defaultValue != null && defaultValue instanceof Integer) {
//            return (int) defaultValue;
//        }// end of if cycle
//
//        return 0;
//    } // end of static method

}// end of class
