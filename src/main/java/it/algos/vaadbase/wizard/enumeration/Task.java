package it.algos.vaadbase.wizard.enumeration;

import lombok.extern.slf4j.Slf4j;

/**
 * Project springvaadin
 * Created by Algos
 * User: gac
 * Date: gio, 08-mar-2018
 * Time: 08:05
 */
@Slf4j
public enum Task {


    entity("Entity", ""),
    list("List", "List"),
    form("Form", "Form"),
    presenter("Presenter", "Presenter"),
    repository("Repository", "Repository"),
    service("Service", "Service");


    private String source;
    private String suffix;


    Task(String source, String suffix) {
        this.setSource(source);
        this.setSuffix(suffix);
    }// fine del costruttore


    public String getSourceName() {
        String txt = ".txt";
        return getSource() + txt;
    }// end of method


    public String getJavaClassName() {
        String java = ".java";
        return getSuffix() + java;
    }// end of method


    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }// end of method

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSuffix() {
        return suffix;
    }
}// end of enumeration
