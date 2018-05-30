package it.algos.vaadbase.modules.preferenza;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.enumeration.EAPrefType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: mer, 30-mag-2018
 * Time: 07:27
 */
public enum EAPreferenza {

    debug("debug", "Modalità di controllo del programma", EAPrefType.bool, true),
    usaCompany("useCompany", "L'applicazione è multiCompany ", EAPrefType.bool, true),
    showCompany("showCompany", show(), EAPrefType.bool, false),
    showPreferenza("showPreferenza", show(), EAPrefType.bool, false),
    showWizard("showWizard", show(), EAPrefType.bool, false),
    showDeveloper("showDeveloper", show(), EAPrefType.bool, false),
    showAddress("showAddress", show(), EAPrefType.bool, false),
    showPerson("showPerson", show(), EAPrefType.bool, false),
    showRole("showRole", show(), EAPrefType.bool, false),
    ;

    private String code;
    private String descrizione;
    private EAPrefType type;
    private Object value;


    EAPreferenza(String code, String descrizione, EAPrefType type, Object value) {
        this.setCode(code);
        this.setDescrizione(descrizione);
        this.setType(type);
        this.setValue(value);
    }// fine del costruttore

    public static String show() {
        return "Flag per costruire la UI con il modulo visibile nel menu";
    }// end of method

    public String getCode() {
        return code;
    }// end of method

    public void setCode(String code) {
        this.code = code;
    }// end of method

    public String getDescrizione() {
        return descrizione;
    }// end of method

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }// end of method

    public EAPrefType getType() {
        return type;
    }// end of method

    public void setType(EAPrefType type) {
        this.type = type;
    }// end of method

    public Object getValue() {
        return value;
    }// end of method

    public void setValue(Object value) {
        this.value = value;
    }// end of method

} // end of enumeration
