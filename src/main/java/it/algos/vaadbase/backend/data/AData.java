package it.algos.vaadbase.backend.data;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.backend.service.IAService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.Random;

/**
 * Project vbase
 * Created by Algos
 * User: gac
 * Date: lun, 19-mar-2018
 * Time: 21:10
 */
@Slf4j
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public abstract class AData implements IAData{

    /**
     * Il service viene iniettato dal costruttore della sottoclasse concreta
     */
    protected IAService service;


    private final Random random = new Random(1L);


    /**
     * Costruttore @Autowired <br>
     */
    public AData(IAService service) {
        this.service = service;
    }// end of Spring constructor


    /**
     * Controlla se la collezione esiste già
     *
     * @return true se la collection è inesistente
     */
    @Override
    public boolean nessunRecordEsistente() {
        return service.count() == 0;
    }// end of method


    /**
     * Creazione di una collezione
     * Solo se non ci sono records
     * Controlla se la collezione esiste già
     * Creazione della collezione
     */
    @Override
    public void loadData() {
    }// end of method


    @Override
    public <T> T getRandom(T[] array) {
        return array[random.nextInt(array.length)];
    }// end of method

}// end of class
