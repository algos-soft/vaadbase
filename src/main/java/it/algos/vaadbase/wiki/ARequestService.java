package it.algos.vaadbase.wiki;

import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: ven, 06-lug-2018
 * Time: 13:21
 */
@Slf4j
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ARequestService {



//    /**
//     * Stringa del browser per la request
//     * Domain per l'URL dal titolo della pagina o dal pageid (a seconda del costruttore usato)
//     * PUO essere sovrascritto nelle sottoclassi specifiche
//     *
//     * @param wikiTitle titolo della pagina wiki
//     */
//    protected String elaboraDomain(String wikiTitle) {
//        String domainTmp = API_BASE + API_ACTION + API_QUERY + Cost.CONTENT_ALL;
//
//        if (wikiTitle != null && !wikiTitle.equals("")) {
//            domainTmp += TAG_TITOLO + titleEncoded();
//        } else {
//            domainTmp += TAG_PAGEID + wikiPageid;
//        }// end of if/else cycle
//
//        if (needBot) {
//            domainTmp += API_ASSERT;
//        }// end of if cycle
//
//        domain = domainTmp;
//        return domainTmp;
//    } // fine del metodo


    /**
     * Elabora la risposta
     * <p>
     * Informazioni, contenuto e validita della risposta
     * Controllo del contenuto (testo) ricevuto
     * PUO essere sovrascritto nelle sottoclassi specifiche
     */
    private String elaboraRisposta(String rispostaRequest) {
        int a = 87;
        return "";
    } // fine del metodo




}// end of class
