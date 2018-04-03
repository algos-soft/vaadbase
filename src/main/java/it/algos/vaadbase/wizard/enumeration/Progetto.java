package it.algos.vaadbase.wizard.enumeration;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Project springvaadin
 * Created by Algos
 * User: gac
 * Date: gio, 08-mar-2018
 * Time: 08:23
 */
@Slf4j
public enum Progetto {

    vaadin("vaadbase"),
    wam("vaadwam"),
    bio("vaadbio");

    private String nameProject;


    Progetto(String nameProject) {
        this.setNameProject(nameProject);
    }// fine del costruttore

    public String getNameProject() {
        return nameProject;
    }// end of method

    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }// end of method


    public static List<String> getNames() {
        List<String> nomi = new ArrayList<>();
        Progetto[] allProgetti = Progetto.values();

        for (Progetto progetto : Progetto.values()) {
            nomi.add(progetto.nameProject);
        }// end of for cycle

        return nomi;
    }// end of method

}// end of enumeration class
