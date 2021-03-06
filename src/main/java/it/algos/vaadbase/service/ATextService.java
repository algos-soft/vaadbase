package it.algos.vaadbase.service;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.enumeration.EAFirstChar;
import it.algos.vaadbase.enumeration.EAPrefType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;

/**
 * Project springvaadin
 * Created by Algos
 * User: gac
 * Date: gio, 07-dic-2017
 * Time: 13:45
 * Classe di Libreria
 * Gestione e formattazione di stringhe di testo
 */
@Slf4j
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ATextService {

    /**
     * tag per il carattere punto
     */
    public static final String PUNTO = ".";
    /**
     * tag per il carattere barra
     */
    public static final String BARRA = "/";
    /**
     * tag per la stringa vuota
     */
    public static final String VUOTA = "";
    public static final String VIRGOLA = ",";
    private static final ATextService INSTANCE = new ATextService();
    /**
     * Service iniettato da Spring (@Scope = 'singleton'). Unica per tutta l'applicazione. Usata come libreria.
     */
    @Autowired
    public AArrayService array;

    /**
     * Gets the unique instance of this Singleton.
     *
     * @return the unique instance of this Singleton
     */
    public static ATextService getInstance() {
        return INSTANCE;
    }// end of method

    private static boolean isNumber(char ch) {
        return ch >= '0' && ch <= '9';
    }// end of method

    /**
     * Null-safe, short-circuit evaluation.
     *
     * @param stringa in ingresso da controllare
     *
     * @return vero se la stringa è vuota o nulla
     */
    public boolean isEmpty(final String stringa) {
        return stringa == null || stringa.trim().isEmpty();
    }// end of method

    /**
     * Null-safe, short-circuit evaluation.
     *
     * @param stringa in ingresso da controllare
     *
     * @return vero se la stringa esiste è non è vuota
     */
    public boolean isValid(final String stringa) {
        return !isEmpty(stringa);
    }// end of method

    /**
     * Controlla che sia una stringa e che sia valida.
     *
     * @param obj in ingresso da controllare
     *
     * @return vero se la stringa esiste è non è vuota
     */
    public boolean isValid(final Object obj) {
        if (obj instanceof String) {
            return !isEmpty((String) obj);
        } else {
            return false;
        }// end of if/else cycle
    }// end of method

    /**
     * Forza il primo carattere della stringa secondo il flag
     * <p>
     * Se la stringa è nulla, ritorna un nullo
     * Se la stringa è vuota, ritorna una stringa vuota
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testoIn ingresso
     * @param flag    maiuscolo o minuscolo
     *
     * @return uscita string in uscita
     */
    private String primoCarattere(String testoIn, EAFirstChar flag) {
        String testoOut = "";
        String primo;
        String rimanente;

        if (this.isValid(testoIn)) {
            testoIn = testoIn.trim();
            primo = testoIn.substring(0, 1);
            switch (flag) {
                case maiuscolo:
                    primo = primo.toUpperCase();
                    break;
                case minuscolo:
                    primo = primo.toLowerCase();
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch
            rimanente = testoIn.substring(1);
            testoOut = primo + rimanente;
        }// fine del blocco if

        return testoOut.trim();
    }// end of method

    /**
     * Forza il primo carattere della stringa al carattere maiuscolo
     * <p>
     * Se la stringa è nulla, ritorna un nullo
     * Se la stringa è vuota, ritorna una stringa vuota
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testoIn ingresso
     *
     * @return test formattato in uscita
     */
    public String primaMaiuscola(final String testoIn) {
        return primoCarattere(testoIn, EAFirstChar.maiuscolo);
    }// end of method

    /**
     * Forza il primo carattere della stringa al carattere minuscolo
     * <p>
     * Se la stringa è nulla, ritorna un nullo
     * Se la stringa è vuota, ritorna una stringa vuota
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testoIn ingresso
     *
     * @return test formattato in uscita
     */
    public String primaMinuscola(final String testoIn) {
        return primoCarattere(testoIn, EAFirstChar.minuscolo);
    }// end of method

    /**
     * Elimina dal testo il tagIniziale, se esiste
     * <p>
     * Esegue solo se il testo è valido
     * Se tagIniziale è vuoto, restituisce il testo
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testoIn     ingresso
     * @param tagIniziale da eliminare
     *
     * @return test ridotto in uscita
     */
    public String levaTesta(final String testoIn, String tagIniziale) {
        String testoOut = testoIn.trim();

        if (this.isValid(testoOut) && this.isValid(tagIniziale)) {
            tagIniziale = tagIniziale.trim();
            if (testoOut.startsWith(tagIniziale)) {
                testoOut = testoOut.substring(tagIniziale.length());
            }// fine del blocco if
        }// fine del blocco if

        return testoOut.trim();
    }// end of method

    /**
     * Elimina dal testo il tagFinale, se esiste
     * <p>
     * Esegue solo se il testo è valido
     * Se tagFinale è vuoto, restituisce il testo
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testoIn   ingresso
     * @param tagFinale da eliminare
     *
     * @return test ridotto in uscita
     */
    public String levaCoda(final String testoIn, String tagFinale) {
        String testoOut = testoIn.trim();

        if (this.isValid(testoOut) && this.isValid(tagFinale)) {
            tagFinale = tagFinale.trim();
            if (testoOut.endsWith(tagFinale)) {
                testoOut = testoOut.substring(0, testoOut.length() - tagFinale.length());
            }// fine del blocco if
        }// fine del blocco if

        return testoOut.trim();
    }// end of method

    /**
     * Elimina il testo da tagFinale in poi
     * <p>
     * Esegue solo se il testo è valido
     * Se tagInterrompi è vuoto, restituisce il testo
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testoIn       ingresso
     * @param tagInterrompi da dove inizia il testo da eliminare
     *
     * @return test ridotto in uscita
     */
    public String levaCodaDa(final String testoIn, String tagInterrompi) {
        String testoOut = testoIn.trim();

        if (this.isValid(testoOut) && this.isValid(tagInterrompi)) {
            tagInterrompi = tagInterrompi.trim();
            if (testoOut.contains(tagInterrompi)) {
                testoOut = testoOut.substring(0, testoOut.lastIndexOf(tagInterrompi));
            }// fine del blocco if
        }// fine del blocco if

        return testoOut.trim();
    }// end of method

    /**
     * Controlla se il testo contiene uno elemento di una lista di tag
     *
     * @param testoIn   ingresso
     * @param listaTags lista di tag da controllare
     *
     * @return vero se ne contiene uno o più di uno
     */
    public boolean isContiene(final String testoIn, List<String> listaTags) {
        boolean neContieneAlmenoUno = false;

        if (array.isValid(listaTags)) {
            for (String singleTag : listaTags) {
                if (testoIn.contains(singleTag)) {
                    neContieneAlmenoUno = true;
                }// end of if cycle
            }// end of for cycle
        }// end of if cycle

        return neContieneAlmenoUno;
    }// end of method

    /**
     * Controlla che il testo non contenga nessun elemento di una lista di tag
     *
     * @param testoIn   ingresso
     * @param listaTags lista di tag da controllare
     *
     * @return vero se ne contiene nessuno
     */
    public boolean nonContiene(final String testoIn, List<String> listaTags) {
        return !isContiene(testoIn, listaTags);
    }// end of method


    /**
     * Sostituisce nel testo tutte le occorrenze di oldTag con newTag.
     * Esegue solo se il testo è valido
     * Esegue solo se il oldTag è valido
     * newTag può essere vuoto (per cancellare le occorrenze di oldTag)
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testoIn ingresso da elaborare
     * @param oldTag  da sostituire
     * @param newTag  da inserire
     *
     * @return testo modificato
     */
    public String sostituisce(final String testoIn, String oldTag, String newTag) {
        String testoOut = "";
        String prima = "";
        String rimane = testoIn;
        int pos = 0;
        String charVuoto = " ";

        if (this.isValid(testoIn) && this.isValid(oldTag)) {
            if (rimane.contains(oldTag)) {
                pos = rimane.indexOf(oldTag);

                while (pos != -1) {
                    pos = rimane.indexOf(oldTag);
                    if (pos != -1) {
                        prima += rimane.substring(0, pos);
                        prima += newTag;
                        pos += oldTag.length();
                        rimane = rimane.substring(pos);
                        if (prima.endsWith(charVuoto) && rimane.startsWith(charVuoto)) {
                            rimane = rimane.substring(1);
                        }// end of if cycle
                    }// fine del blocco if
                }// fine di while

                testoOut = prima + rimane;
            }// fine del blocco if
        }// fine del blocco if

        return testoOut.trim();
    }// end of  method

    /**
     * Inserisce nel testo alla posizione indicata
     * Esegue solo se il testo è valido
     * Esegue solo se il newTag è valido
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testoIn ingresso da elaborare
     * @param newTag  da inserire
     * @param pos     di inserimento
     *
     * @return testo modificato
     */
    public String inserisce(String testoIn, String newTag, int pos) {
        String testoOut = testoIn;
        String prima = "";
        String dopo = "";

        if (this.isValid(testoIn) && this.isValid(newTag)) {
            prima = testoIn.substring(0, pos);
            dopo = testoIn.substring(pos);

            testoOut = prima + newTag + dopo;
        }// fine del blocco if

        return testoOut.trim();
    }// end of  method

    public boolean isNumber(String value) {
        boolean status = true;
        char[] caratteri = value.toCharArray();

        for (char car : caratteri) {
            if (isNotNumber(car)) {
                status = false;
            }// end of if cycle
        }// end of for cycle

        return status;
    }// end of method

    private boolean isNotNumber(char ch) {
        return !isNumber(ch);
    }// end of method

    public String getModifiche(Object oldValue, Object newValue) {
        return getModifiche(oldValue, newValue, EAPrefType.string);
    } // fine del metodo


    public String getModifiche(Object oldValue, Object newValue, EAPrefType type) {
        String tatNew = "Aggiunto: ";
        String tatEdit = "Modificato: ";
        String tatDel = "Cancellato: ";
        String tagSep = " -> ";

        if (oldValue == null && newValue == null) {
            return "";
        }// end of if cycle

        if (oldValue instanceof String && newValue instanceof String) {
            if (this.isEmpty((String) oldValue) && isEmpty((String) newValue)) {
                return "";
            }// end of if cycle

            if (isValid((String) oldValue) && isEmpty((String) newValue)) {
                return tatDel + oldValue.toString();
            }// end of if cycle

            if (isEmpty((String) oldValue) && isValid((String) newValue)) {
                return tatNew + newValue.toString();
            }// end of if cycle

            if (!oldValue.equals(newValue)) {
                return tatEdit + oldValue.toString() + tagSep + newValue.toString();
            }// end of if cycle
        } else {
            if (oldValue != null && newValue != null && oldValue.getClass() == newValue.getClass()) {
                if (!oldValue.equals(newValue)) {
                    if (oldValue instanceof byte[]) {
                        try { // prova ad eseguire il codice
                            return tatEdit + type.bytesToObject((byte[]) oldValue) + tagSep + type.bytesToObject((byte[]) newValue);
                        } catch (Exception unErrore) { // intercetta l'errore
                            log.error(unErrore.toString() + " LibText.getDescrizione() - Sembra che il PrefType non sia del tipo corretto");
                            return "";
                        }// fine del blocco try-catch
                    } else {
                        return tatEdit + oldValue.toString() + tagSep + newValue.toString();
                    }// end of if/else cycle
                }// end of if cycle
            } else {
                if (oldValue != null && newValue == null) {
                    if (oldValue instanceof byte[]) {
                        return "";
                    } else {
                        return tatDel + oldValue.toString();
                    }// end of if/else cycle
                }// end of if cycle

                if (newValue != null && oldValue == null) {
                    if (newValue instanceof byte[]) {
                        return "";
                    } else {
                        return tatNew + newValue.toString();
                    }// end of if/else cycle
                }// end of if cycle
            }// end of if/else cycle
        }// end of if/else cycle

        return "";
    }// end of method

    public String estrae(String valueIn, String tagIni) {
        return estrae(valueIn, tagIni, tagIni);
    }// end of method

    public String estrae(String valueIn, String tagIni, String tagEnd) {
        String valueOut = valueIn;
        int length = 0;
        int posIni = 0;
        int posEnd = 0;

        if (isValid(valueIn) && valueIn.contains(tagIni) && valueIn.contains(tagEnd)) {
            length = tagIni.length();
            posIni = valueIn.indexOf(tagIni);
            posEnd = valueIn.indexOf(tagEnd, posIni + length);
            valueOut = valueIn.substring(posIni + length, posEnd);
        }// end of if cycle

        return valueOut.trim();
    }// end of method


    /**
     * Elimina tutti i caratteri contenuti nella stringa.
     * Esegue solo se il testo è valido
     *
     * @param testoIn    in ingresso
     * @param subStringa da eliminare
     *
     * @return testoOut stringa convertita
     */
    public String levaTesto(String testoIn, String subStringa) {
        String testoOut = testoIn;

        if (testoIn != null && subStringa != null) {
            testoOut = testoIn.trim();
            if (testoOut.contains(subStringa)) {
                testoOut = sostituisce(testoOut, subStringa, VUOTA);
            }// fine del blocco if
        }// fine del blocco if

        return testoOut;
    }// end of method


    /**
     * Elimina tutte le virgole contenute nella stringa.
     * Esegue solo se la stringa è valida
     * Se arriva un oggetto non stringa, restituisce l'oggetto
     *
     * @param entrata stringa in ingresso
     *
     * @return uscita stringa convertita
     */
    public String levaVirgole(String entrata) {
        return levaTesto(entrata, VIRGOLA);
    }// end of method


    /**
     * Elimina tutti i punti contenuti nella stringa.
     * Esegue solo se la stringa è valida
     * Se arriva un oggetto non stringa, restituisce l'oggetto
     *
     * @param entrata stringa in ingresso
     *
     * @return uscita stringa convertita
     */
    public String levaPunti(String entrata) {
        return levaTesto(entrata, PUNTO);
    }// end of method


    /**
     * Formattazione di un numero.
     * <p>
     * Il numero può arrivare come stringa, intero o double
     * Se la stringa contiene punti e virgole, viene pulita
     * Se la stringa non è convertibile in numero, viene restituita uguale
     * Inserisce il punto separatore ogni 3 cifre
     * Se arriva un oggetto non previsto, restituisce null
     *
     * @param numObj da formattare (stringa, intero, long o double)
     *
     * @return stringa formattata
     */
    public String format(Object numObj) {
        String formattato = VUOTA;
        String numText = VUOTA;
        String sep = PUNTO;
        int numTmp = 0;
        int len;
        String num3;
        String num6;
        String num9;
        String num12;

        if (numObj instanceof String || numObj instanceof Integer || numObj instanceof Long || numObj instanceof Double) {
            if (numObj instanceof String) {
                numText = (String) numObj;
                numText = levaVirgole(numText);
                numText = levaPunti(numText);
                try { // prova ad eseguire il codice
                    numTmp = Integer.decode(numText);
                } catch (Exception unErrore) { // intercetta l'errore
                    return (String) numObj;
                }// fine del blocco try-catch
            } else {
                if (numObj instanceof Integer) {
                    numText = Integer.toString((int) numObj);
                }// fine del blocco if
                if (numObj instanceof Long) {
                    numText = Long.toString((long) numObj);
                }// fine del blocco if
                if (numObj instanceof Double) {
                    numText = Double.toString((double) numObj);
                }// fine del blocco if
            }// fine del blocco if-else
        } else {
            return null;
        }// fine del blocco if-else

        formattato = numText;
        len = numText.length();
        if (len > 3) {
            num3 = numText.substring(0, len - 3);
            num3 += sep;
            num3 += numText.substring(len - 3);
            formattato = num3;
            if (len > 6) {
                num6 = num3.substring(0, len - 6);
                num6 += sep;
                num6 += num3.substring(len - 6);
                formattato = num6;
                if (len > 9) {
                    num9 = num6.substring(0, len - 9);
                    num9 += sep;
                    num9 += num6.substring(len - 9);
                    formattato = num9;
                    if (len > 12) {
                        num12 = num9.substring(0, len - 12);
                        num12 += sep;
                        num12 += num9.substring(len - 12);
                        formattato = num12;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if
        }// fine del blocco if

        // valore di ritorno
        return formattato;
    }// end of method


    /**
     * Formattazione di un numero giustificato a due cifre.
     * <p>
     * Il numero può arrivare come stringa, intero o double
     * Se la stringa contiene punti e virgole, viene pulita
     * Se la stringa non è convertibile in numero, viene restituita uguale
     * Se arriva un oggetto non previsto, restituisce null
     *
     * @param numObj da formattare (stringa, intero, long o double)
     *
     * @return stringa formattata
     */
    public String format2(Object numObj) {
        String numText = VUOTA;
        String sep = PUNTO;
        int num = 0;
        int len;
        String num3;
        String num6;
        String num9;
        String num12;

        if (numObj instanceof String || numObj instanceof Integer || numObj instanceof Long || numObj instanceof Double) {
            if (numObj instanceof String) {
                numText = (String) numObj;
                numText = levaVirgole(numText);
                numText = levaPunti(numText);
                try { // prova ad eseguire il codice
                    num = Integer.decode(numText);
                } catch (Exception unErrore) { // intercetta l'errore
                    return (String) numObj;
                }// fine del blocco try-catch
            } else {
                if (numObj instanceof Integer) {
                    num = (int) numObj;
                }// fine del blocco if
                if (numObj instanceof Long) {
                    num = ((Long) numObj).intValue();
                }// fine del blocco if
                if (numObj instanceof Double) {
                    num = ((Double) numObj).intValue();
                }// fine del blocco if
            }// fine del blocco if-else
        } else {
            return null;
        }// fine del blocco if-else

        numText = "" + num;
        if (num < 10) {
            return numText = "0" + numText;
        }// end of if cycle

        // valore di ritorno
        return numText;
    }// end of method


    /**
     * Formattazione di un numero giustificato a tre cifre.
     * <p>
     * Il numero può arrivare come stringa, intero o double
     * Se la stringa contiene punti e virgole, viene pulita
     * Se la stringa non è convertibile in numero, viene restituita uguale
     * Se arriva un oggetto non previsto, restituisce null
     *
     * @param numObj da formattare (stringa, intero, long o double)
     *
     * @return stringa formattata
     */
    public String format3(Object numObj) {
        String numText = VUOTA;
        String sep = PUNTO;
        int num = 0;
        int len;
        String num3;
        String num6;
        String num9;
        String num12;

        if (numObj instanceof String || numObj instanceof Integer || numObj instanceof Long || numObj instanceof Double) {
            if (numObj instanceof String) {
                numText = (String) numObj;
                numText = levaVirgole(numText);
                numText = levaPunti(numText);
                try { // prova ad eseguire il codice
                    num = Integer.decode(numText);
                } catch (Exception unErrore) { // intercetta l'errore
                    return (String) numObj;
                }// fine del blocco try-catch
            } else {
                if (numObj instanceof Integer) {
                    num = (int) numObj;
                }// fine del blocco if
                if (numObj instanceof Long) {
                    num = ((Long) numObj).intValue();
                }// fine del blocco if
                if (numObj instanceof Double) {
                    num = ((Double) numObj).intValue();
                }// fine del blocco if
            }// fine del blocco if-else
        } else {
            return null;
        }// fine del blocco if-else

        numText = "" + num;
        if (num < 100) {
            if (num < 10) {
                return numText = "00" + numText;
            } else {
                return numText = "0" + numText;
            }// end of if/else cycle
        }// end of if cycle

        // valore di ritorno
        return numText;
    }// end of method

}// end of class
