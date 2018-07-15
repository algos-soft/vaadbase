package it.algos.vaadbase.boot;

import it.algos.vaadbase.enumeration.EAPrefType;
import it.algos.vaadbase.modules.preferenza.EAPreferenza;
import it.algos.vaadbase.modules.preferenza.Preferenza;
import it.algos.vaadbase.modules.preferenza.PreferenzaService;
import it.algos.vaadbase.modules.versione.VersioneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadbio2
 * Created by Algos
 * User: gac
 * Date: sab, 14-lug-2018
 * Time: 15:08
 */
//@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public abstract class AVersBoot {

    private final static String CODE_PROJECT = "A";
    protected String codeProject;

    /**
     * La injection viene fatta da SpringBoot in automatico <br>
     */
    @Autowired
    protected VersioneService vers;
    /**
     * La injection viene fatta da SpringBoot in automatico <br>
     */
    @Autowired
    protected PreferenzaService pref;

    /**
     * Executed on container startup
     * Setup non-UI logic here
     * <p>
     * This method is called prior to the servlet context being initialized (when the Web application is deployed).
     * You can initialize servlet context related data here.
     * <p>
     * Tutte le aggiunte, modifiche e patch vengono inserite con una versione <br>
     * L'ordine di inserimento è FONDAMENTALE
     */
    public int inizia() {
        int k = 1;
        codeProject = CODE_PROJECT;

        //--prima installazione del programma
        //--non fa nulla, solo informativo
        if (installa(++k)) {
            crea("Setup", "Installazione iniziale");
        }// fine del blocco if

        //--crea le preferenze standard
        //--queste vengono aggiunte indipendentemente dall'ordine della enumeration
        //--vengono create se non esistono
        //--se esistono, viene aggiornato il valore e la descrizione previsti dalla enumeration
        for (EAPreferenza prefTemp : EAPreferenza.values()) {
            String code = prefTemp.getCode();
            String desc = prefTemp.getDesc();
            EAPrefType type = prefTemp.getType();
            Object value = prefTemp.getValue();
            Preferenza preferenza = (Preferenza) pref.findById(code);

            if (preferenza == null) {
                vers.creaPref(codeProject, code, desc, type, value);
                k++;
            } else {
                if (!preferenza.getDescrizione().equals(desc)) {
                    vers.crea("Z","#" + code, "#desc: " + preferenza.getDescrizione() + " -> " + desc);
                    preferenza.setDescrizione(desc);
                    pref.save(preferenza);
                } else {
                    if (!preferenza.getAValue().equals(value)) {
                        vers.crea("Z","#" + code, "#value: " + preferenza.getAValue() + " -> " + value);
                        preferenza.setAValue(value);
                        pref.save(preferenza);
                    }// end of if cycle
                }// end of if/else cycle
            }// end of if/else cycle
        }// end of for cycle

        return k;
    }// end of method


    /**
     * Controlla che la versione non esista già <br>
     */
    public boolean installa(int k) {
        return vers.findByCode(codeProject, k) == null;
    }// end of method


    /**
     * Crea una entity di versione e la registra <br>
     *
     * @param titolo della versione (obbligatorio, non unico) <br>
     * @param nome   descrittivo della versione (obbligatorio, unico) <br>
     */
    public void crea(String titolo, String nome) {
        vers.crea(codeProject, titolo, nome);
    }// end of method


    /**
     * Crea una entity di preferenze e la regiistra <br>
     * Crea una entity di versione e la registra <br>
     *
     * @param codePref key code della preferenza (obbligatoria per Pref)
     * @param descPref dettagliata (obbligatoria per Pref)
     */
    public void creaPrefBool(String codePref, String descPref) {
        vers.creaPrefBool(codeProject, codePref, descPref);
    }// end of method

}// end of class
