package it.algos.vaadbase.daemons;

import it.sauronsoftware.cron4j.Scheduler;
import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import javax.servlet.ServletContext;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: gio, 12-lug-2018
 * Time: 11:55
 */
@Slf4j
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class Daemon extends Scheduler {


    @Override
    public void start() throws IllegalStateException {
        if (!isStarted()) {
            super.start();

            // save daemons status flag into servlet context
//            ServletContext svc = ABootStrap.getServletContext();
//            svc.setAttribute(DAEMON_NAME, true);

            // Schedule task
            // Ogni giorno
//            schedule("5 0 * * *", new TaskCicloBio()); //dal 11 dic 2015
//            if (Pref.getBool(CostBio.USA_LOG_DAEMONS, false)) {
//                Log.debug("daemonCicloCrono", "Attivato ciclo daemonCicloCrono; flag in preferenze per confermare esecuzione alle 0:01");
//            }// fine del blocco if
        }// fine del blocco if
    }// end of method

    @Override
    public void stop() throws IllegalStateException {
        if (isStarted()) {
            super.stop();

            // save daemons status flag into servlet context
//            ServletContext svc = ABootStrap.getServletContext();
//            svc.setAttribute(DAEMON_NAME, false);

//            if (Pref.getBool(CostBio.USA_LOG_DAEMONS, false)) {
//                Log.debug("daemonCicloCrono", "Spento");
//            }// fine del blocco if

        }// fine del blocco if
    }// end of method

}// end of class
