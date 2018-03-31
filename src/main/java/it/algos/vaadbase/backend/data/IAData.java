package it.algos.vaadbase.backend.data;

import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: gio, 22-mar-2018
 * Time: 21:20
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public interface IAData {

    /**
     * Controlla se la collezione esiste già
     *
     * @return true se la collection è inesistente
     */
    public boolean nessunRecordEsistente();


    /**
     * Creazione di una collezione
     * Solo se non ci sono records
     * Controlla se la collezione esiste già
     * Creazione della collezione
     */
    public void loadData();


    public <T> T getRandom(T[] array);

}// end of interface
