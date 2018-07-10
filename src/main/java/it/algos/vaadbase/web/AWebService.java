package it.algos.vaadbase.web;

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
 * Time: 13:31
 */
@Slf4j
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AWebService {


    /**
     * Request principale (GET)
     * Legge una pagina internet (qualsiasi)
     * Accetta SOLO un domain (indirizzo) completo
     *
     * @param webPage titolo completo della pagina web generica
     */
    public String urlRequest(String webPage) {
        String testoRisposta = "";
        URLConnection urlConn = null;


        //--crea la connessione
        try { // prova ad eseguire il codice
            urlConn = this.creaUrlConnection(webPage);
        } catch (Exception unErrore) { // intercetta l'errore
            log.error(unErrore.toString());
        }// fine del blocco try-catch


        //--Invia la request (GET oppure POST)
        try { // prova ad eseguire il codice
            testoRisposta = sendConnection(urlConn);
        } catch (Exception unErrore) { // intercetta l'errore
            log.error(unErrore.toString());
        }// fine del blocco try-catch

        return testoRisposta;
    } // fine del metodo


    /**
     * Crea la connessione
     * <p>
     * Regola i parametri della connessione
     *
     * @param urlDomain stringa della request
     *
     * @return connessione con la request
     */
    private URLConnection creaUrlConnection(String urlDomain) throws Exception {
        URLConnection urlConn = null;

        if (urlDomain != null && !urlDomain.equals("")) {
            urlConn = new URL(urlDomain).openConnection();
            urlConn.setDoOutput(true);
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            urlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; PPC Mac OS X; it-it) AppleWebKit/418.9 (KHTML, like Gecko) Safari/419.3");
        }// end of if cycle

        return urlConn;
    } // fine del metodo


    /**
     * Invia la request (GET)
     *
     * @param urlConn connessione con la request
     *
     * @return valore di ritorno della request
     */
    private String sendConnection(URLConnection urlConn) throws Exception {
        InputStream input;
        InputStreamReader inputReader;
        BufferedReader readBuffer;
        StringBuilder textBuffer = new StringBuilder();
        String stringa;

        input = urlConn.getInputStream();
        inputReader = new InputStreamReader(input, "UTF8");

        // read the response
        readBuffer = new BufferedReader(inputReader);
        while ((stringa = readBuffer.readLine()) != null) {
            textBuffer.append(stringa);
        }// fine del blocco while

        //--close all
        readBuffer.close();
        inputReader.close();
        input.close();

        return textBuffer.toString();
    } // fine del metodo


}// end of class
