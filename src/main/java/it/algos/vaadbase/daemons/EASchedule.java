package it.algos.vaadbase.daemons;

import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: gio, 12-lug-2018
 * Time: 17:38
 *
 * http://www.sauronsoftware.it/projects/cron4j/manual.php
 */
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public enum EASchedule {

    giornaliero("0 0 * * *","ogni giorno a mezzanotte"),
    quintoMinuto("5 * * * *","ogni ora, al minuto 5"),
    ogniMinuto("* * * * *","ogni minuto"),
    settimanale("0 0 * * 1","ogni settimana nella notte tra domenica e lunedi");


    private String tag;
    private String nota;

    EASchedule(String tag,String nota) {
        this.setTag(tag);
        this.setNota(nota);
    }// fine del costruttore

    public String getTag() {
        return tag;
    }// end of method

    public void setTag(String tag) {
        this.tag = tag;
    }// end of method

    public String getNota() {
        return nota;
    }// end of method

    public void setNota(String nota) {
        this.nota = nota;
    }// end of method

}// end of enum
