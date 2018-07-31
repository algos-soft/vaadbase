package it.algos.vaadbase.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: gio, 12-lug-2018
 * Time: 17:38
 *
 * Template di schedule preconfigurati, con nota esplicativa utilizzabile nelle info
 *
 * @see http://www.sauronsoftware.it/projects/cron4j/manual.php
 */
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public enum EASchedule {

    giorno("0 0 * * *","ogni giorno a mezzanotte"),
    giornoQuintoMinuto("5 0 * * *","ogni giorno 5 minuti dopo mezzanotte"),
    giornoSestoMinuto("6 0 * * *","ogni giorno 6 minuti dopo mezzanotte"),
    giornoSettimoMinuto("7 0 * * *","ogni giorno 7 minuti dopo mezzanotte"),
    giornoOttavoMinuto("8 0 * * *","ogni giorno 8 minuti dopo mezzanotte"),
    giornoNonoMinuto("9 0 * * *","ogni giorno 9 minuti dopo mezzanotte"),
    oraQuintoMinuto("5 * * * *","ogni ora, al minuto 5"),
    minuto("* * * * *","ogni minuto"),
    settimanaLunedi("0 0 * * 1","ogni settimana nella notte tra domenica e lunedi");


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
