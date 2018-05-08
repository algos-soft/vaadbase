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

    vaadin("vaadbase", "vaadbase", "BaseCost"),
    test("vaadbase", "vaadtest", "AppCost"),
    pippo("pippo", "pippo", "AppCost"),
    bio("vaadbio", "vaadbio", "AppCost"),
    wam("vaadwam", "vaadwam", "AppCost"),;

    private String nameProject;
    private String nameModule;
    private String nameClassCost;


    Progetto(String nameProject, String nameModule, String nameClassCost) {
        this.setNameProject(nameProject);
        this.setNameModule(nameModule);
        this.setNameClassCost(nameClassCost);
    }// fine del costruttore

    public static List<String> getNames() {
        List<String> nomi = new ArrayList<>();
        Progetto[] allProgetti = Progetto.values();

        for (Progetto progetto : Progetto.values()) {
            nomi.add(progetto.nameProject);
        }// end of for cycle

        return nomi;
    }// end of method

    public String getNameProject() {
        return nameProject;
    }// end of method

    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }// end of method

    public String getNameModule() {
        return nameModule;
    }// end of method

    public void setNameModule(String nameModule) {
        this.nameModule = nameModule;
    }// end of method

    public String getNameClassCost() {
        return nameClassCost;
    }// end of method

    public void setNameClassCost(String nameClassCost) {
        this.nameClassCost = nameClassCost;
    }// end of method

}// end of enumeration class
