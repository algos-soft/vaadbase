package it.algos.vaadbase.wizard.scripts;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.service.AFileService;
import it.algos.vaadbase.service.ATextService;
import it.algos.vaadbase.wizard.enumeration.Chiave;
import it.algos.vaadbase.wizard.enumeration.Progetto;
import it.algos.vaadbase.wizard.enumeration.Task;
import it.algos.vaadbase.wizard.enumeration.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.Map;

/**
 * Project springvaadin
 * Created by Algos
 * User: gac
 * Date: mar, 06-mar-2018
 * Time: 08:35
 */
@Slf4j
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TElabora {


    private static final String A_CAPO = "\n";
    private static final String TAB = "\t";
    private static final String SEP = "/";
    private static final String JAVA_SUFFIX = ".java";
    private static final String SOURCE_SUFFIX = ".txt";
    private static final String BOOT_SUFFIX = "Boot";
    private static final String TAG = "TAG_";
    private static final String IMPORT = "import it.algos.";
    private static final String DIR_MAIN = "/src/main";
    private static final String DIR_JAVA = DIR_MAIN + "/java/it/algos";
    private static final String PROJECT_BASE_NAME = "vaadbase";
    private static final String DIR_PROJECT_BASE = DIR_JAVA + "/" + PROJECT_BASE_NAME;
    private static final String SOURCES_NAME = "wizard/sources";
    private static final String APP_NAME = "application";
    private static final String UI_NAME = "ui";
    private static final String ENTITIES_NAME = "modules";
    private static final String LIB_NAME = "lib";
    private static final String DIR_SOURCES = DIR_PROJECT_BASE + SEP + SOURCES_NAME;
    private static final String SUPERCLASS_ENTITY = "AEntity";
    private static final String SUPERCLASS_ENTITY_COMPANY = "ACEntity";
    private static final String PROPERTY = "Property";
    private static final String METHOD = "Method";
    private static final String PROPERTY_ORDINE_NAME = "Ordine";
    private static final String PROPERTY_CODE_NAME = "Code";
    private static final String PROPERTY_DESCRIZIONE_NAME = "Descrizione";
    private static final String PROPERTY_ORDINE_SOURCE_NAME = PROPERTY + PROPERTY_ORDINE_NAME + SOURCE_SUFFIX;
    private static final String PROPERTY_CODE_SOURCE_NAME = PROPERTY + PROPERTY_CODE_NAME + SOURCE_SUFFIX;
    private static final String PROPERTY_DESCRIZIONE_SOURCE_NAME = PROPERTY + PROPERTY_DESCRIZIONE_NAME + SOURCE_SUFFIX;
    private static final String METHOD_FIND = METHOD + "Find" + SOURCE_SUFFIX;
    private static final String METHOD_NEW_ENTITY = METHOD + "NewEntity" + SOURCE_SUFFIX;
    private static final String METHOD_NEW_ORDINE = METHOD + "NewOrdine" + SOURCE_SUFFIX;
    private static final String METHOD_ID_KEY_SPECIFICA = METHOD + "IdKeySpecifica" + SOURCE_SUFFIX;
    private static final String VIEW_SUFFIX = "ViewList";
    private static final String POM = "pom";
    private static final String COST_NAME = "AppCost";
    private static final String BOOT_NAME = "Boot";
    private static final String HOME_NAME = "HomeView";

    /**
     * Libreria di servizio. Inietta da Spring come 'singleton'
     */
    @Autowired
    public AFileService file;

    /**
     * Libreria di servizio. Inietta da Spring come 'singleton'
     */
    @Autowired
    public ATextService text;

    //--regolate indipendentemente dai risultati del dialogo
    private String userDir;                 //--di sistema
    private String ideaProjectRootPath;     //--userDir meno PROJECT_BASE_NAME
    private String projectBasePath;         //--ideaProjectRootPath più PROJECT_BASE_NAME
    private String sourcePath;              //--projectBasePath più DIR_SOURCES


    //--risultati del dialogo
    private String targetProjectName;       //--dal dialogo di input
    private String targetModuleName;        //--dal dialogo di input
    private String newProjectName;          //--dal dialogo di input
    private String newPackageName;          //--dal dialogo di input
    private String newEntityName;           //--dal dialogo di input
    private String newEntityTag;            //--dal dialogo di input
    private boolean flagOrdine;             //--dal dialogo di input
    private boolean flagCode;               //--dal dialogo di input
    private boolean flagDescrizione;        //--dal dialogo di input
    private boolean flagKeyCode;            //--dal dialogo di input
    private boolean flagCompany;            //--dal dialogo di input
    private boolean flagSovrascrive;        //--dal dialogo di input


    //--regolate elaborando i risultati del dialogo
    private String projectPath;         //--ideaProjectRootPath più targetProjectName (usato come radice per pom.xml e README.text)
    private String targetModuleCapitalName;   //--targetModuleName con la prima maiuscola
    private String projectJavaPath;     //--projectPath più DIR_JAVA più targetProjectName
    //    private String pathMain;        //--pathProject più PATH_MAIN (usato come radice per resources e webapp)
//    private String pathModulo;    //--pathMain più PATH_JAVA più nameProject (usato come radice per i files java)
    private String applicationPath;     //--projectJavaPath più APP_NAME
    private String bootPath;          //--applicationPath più LAYOUT_SUFFIX più JAVA_SUFFIX
    private String uiPath;              //--projectJavaPath più UI_NAME
    private String entityPath;          //--projectJavaPath più newPackageName
    private String nameClassCost;

    private String packagePath;         //--entityPath più newPackageName
    private String nameCost;        //--NAME_COST (springvaadin) o NAME_APP_COST (altri progetti)
    private String dirCost;         //--DIR_LIB (springvaadin) o DIR_APP (altri progetti)
    private String pathFileCost;    //--pathModulo più lib (springvaadin) o application (altri progetti)
    private String qualifier;       //--NAME_COST (springvaadin) o NAME_APP_COST (altri progetti) più TAG più tagBreveTreChar
    private String qualifierView;   //--NAME_COST (springvaadin) o NAME_APP_COST (altri progetti) più VIEW più tagBreveTreChar
    private String queryText;
    private String propertiesText;
    private String parametersEntityText;
    private String parametersDocText;
    private String parametersText;
    private String methodFindText;
    private String methodNewEntityText;
    private String methodNewOrdineText;
    private String methodIdKeySpecificaText;
    private String methodKeyUnicaText;
    private String methodBuilderText;
    private String superClassEntity;
    private String importCost;


    public TElabora() {
    }// end of constructor


    /**
     * Creazione di un nuovo project
     */
    public void newProject(Map<Chiave, Object> mappaInput) {
        updateProject(mappaInput);
    }// end of method

    /**
     * Update di un project esistente
     */
    public void updateProject(Map<Chiave, Object> mappaInput) {
        this.regola();
        this.regolaProgetto(mappaInput);
        this.copiaDirectoriesBase();
        this.copiaPom();
        this.creaProjectModule();
        this.creaApplicationMain();
        this.creaApplicationDirectory();
        this.creaApplicationFolder();
        this.creaModulesDirectory();
        this.copiaResources();
        this.copiaWebapp();
    }// end of method


    /**
     * Creazione completa del package
     * Crea una directory
     * Crea i files previsti nella enumeration
     */
    public void newPackage(Map<Chiave, Object> mappaInput) {
        this.regola();
        this.regolaProgetto(mappaInput);
        this.regolaTag(mappaInput);
        this.creaDirectory();
        this.creaTasks(mappaInput);
        this.addPackageMenu();
        this.addTagCostanti();
    }// end of method


    /**
     * Regolazioni iniziali indipendenti dal dialogo di input
     */
    private void regola() {
        this.userDir = System.getProperty("user.dir");
        this.ideaProjectRootPath = text.levaCodaDa(userDir, SEP);

        this.projectBasePath = ideaProjectRootPath + SEP + PROJECT_BASE_NAME;
        this.sourcePath = projectBasePath + DIR_SOURCES;

        log.info("");
        log.info("PROJECT_BASE: " + PROJECT_BASE_NAME);
        log.info("DIR_PROJECT_BASE: " + DIR_PROJECT_BASE);
        log.info("DIR_SOURCES: " + DIR_SOURCES);
        log.info("userDir: " + userDir);
        log.info("ideaProjectRootPath: " + ideaProjectRootPath);
        log.info("projectBasePath: " + projectBasePath);
        log.info("sourcePath: " + sourcePath);
    }// end of method


    /**
     * Regolazioni iniziali con i valori del dialogo di input
     */
    private void regolaProgetto(Map<Chiave, Object> mappaInput) {
        Object projectValue;
        Progetto progetto;
        String projectName;

        if (mappaInput.containsKey(Chiave.targetProjectName)) {
            projectValue = mappaInput.get(Chiave.targetProjectName);
            if (projectValue instanceof Progetto) {
                progetto = (Progetto) projectValue;
                this.targetProjectName = progetto.getNameProject().toLowerCase();
                this.targetModuleName = progetto.getNameModule().toLowerCase();
                this.nameClassCost = progetto.getNameClassCost();
            } else {
                projectName = (String) projectValue;
                this.targetProjectName = projectName.toLowerCase();
                this.targetModuleName = projectName.toLowerCase();
                this.nameClassCost = "AppCost";
            }// end of if/else cycle
            this.targetModuleCapitalName = text.primaMaiuscola(targetModuleName);
            this.projectPath = ideaProjectRootPath + SEP + targetProjectName;
            this.projectJavaPath = projectPath + DIR_JAVA + SEP + targetModuleName;
            this.applicationPath = projectJavaPath + SEP + APP_NAME;
            this.uiPath = projectJavaPath + SEP + UI_NAME;
            this.entityPath = projectJavaPath + SEP + ENTITIES_NAME;
            //--applicationPath più LAYOUT_SUFFIX più JAVA_SUFFIX
            this.bootPath = applicationPath + SEP + targetModuleCapitalName + BOOT_SUFFIX + JAVA_SUFFIX;
        }// end of if cycle

        if (mappaInput.containsKey(Chiave.newProjectName)) {
            this.newProjectName = (String) mappaInput.get(Chiave.newProjectName);
            this.projectPath = ideaProjectRootPath + SEP + newProjectName;
            this.projectJavaPath = projectPath + DIR_JAVA + SEP + newProjectName;
            this.applicationPath = projectJavaPath + SEP + APP_NAME;
            this.uiPath = projectJavaPath + SEP + UI_NAME;
            this.entityPath = projectJavaPath + SEP + ENTITIES_NAME;
        }// end of if cycle

        log.info("");
        log.info("newProjectName: " + newProjectName);
        log.info("targetProjectName: " + targetProjectName);
        log.info("targetModuleName: " + targetModuleName);
        log.info("projectPath: " + projectPath);
        log.info("projectJavaPath: " + projectJavaPath);
        log.info("applicationPath: " + applicationPath);
        log.info("uiPath: " + uiPath);
        log.info("entityPath: " + entityPath);
    }// end of method


    private void regolaTag(Map<Chiave, Object> mappaInput) {
        Progetto progetto;

        if (mappaInput.containsKey(Chiave.newPackageName)) {
            this.newPackageName = (String) mappaInput.get(Chiave.newPackageName);
            this.packagePath = entityPath + SEP + newPackageName;
        }// end of if cycle

        if (mappaInput.containsKey(Chiave.newEntityTag)) {
            this.newEntityTag = (String) mappaInput.get(Chiave.newEntityTag);
            this.qualifier = TAG + newEntityTag;
        }// end of if cycle

//        if (mappaInput.containsKey(Chiave.targetProjectName)) {
//            progetto = (Progetto) mappaInput.get(Chiave.targetProjectName);
//            if (text.isValid(progetto)) {
//            }// end of if cycle
//        }// end of if cycle


        if (mappaInput.containsKey(Chiave.flagOrdine)) {
            this.flagOrdine = (boolean) mappaInput.get(Chiave.flagOrdine);
        }// end of if cycle

        if (mappaInput.containsKey(Chiave.flagCode)) {
            this.flagCode = (boolean) mappaInput.get(Chiave.flagCode);
        }// end of if cycle

        if (mappaInput.containsKey(Chiave.flagDescrizione)) {
            this.flagDescrizione = (boolean) mappaInput.get(Chiave.flagDescrizione);
        }// end of if cycle

        if (mappaInput.containsKey(Chiave.flagKeyCode)) {
            this.flagKeyCode = (boolean) mappaInput.get(Chiave.flagKeyCode);
        }// end of if cycle

        if (mappaInput.containsKey(Chiave.flagCompany)) {
            this.flagCompany = (boolean) mappaInput.get(Chiave.flagCompany);
            if (flagCompany) {
                superClassEntity = SUPERCLASS_ENTITY_COMPANY;
            } else {
                superClassEntity = SUPERCLASS_ENTITY;
            }// end of if/else cycle
        }// end of if cycle

        if (mappaInput.containsKey(Chiave.flagSovrascrive)) {
            this.flagSovrascrive = (boolean) mappaInput.get(Chiave.flagSovrascrive);
        }// end of if cycle

        log.info("");
        log.info("newPackageName: " + newPackageName);
        log.info("newEntityTag: " + newEntityTag);
        log.info("packagePath: " + packagePath);
        log.info("flagOrdine: " + flagOrdine);
        log.info("flagCode: " + flagCode);
        log.info("flagDescrizione: " + flagDescrizione);
        log.info("flagKeyCode: " + flagKeyCode);
        log.info("flagCompany: " + flagCompany);
        log.info("flagSovrascrive: " + flagSovrascrive);
    }// end of method


    private void creaDirectory() {
        if (text.isValid(newPackageName)) {
            if (!file.isEsisteDirectory(entityPath)) {
                log.warn("Non esisteva la directory " + ENTITIES_NAME + " nel progetto " + targetProjectName + " e l'ho creata");
                file.creaDirectory(entityPath);
            }// end of if cycle

            file.creaDirectory(packagePath);
        }// end of if cycle
    }// end of method


    private void creaTasks(Map<Chiave, Object> mappaInput) {
        for (Task task : Task.values()) {
            creaTask(task, mappaInput);
        }// end of for cycle
    }// end of method


    private void creaTask(Task task, Map<Chiave, Object> mappaInput) {
        String nomeFileTextSorgente = "";
        String sourceTemplatesText = "";
        String newTaskText = "";

        if (mappaInput.containsKey(Chiave.newEntityName)) {
            this.newEntityName = (String) mappaInput.get(Chiave.newEntityName);
            nomeFileTextSorgente = task.getSourceName();
        } else {
            return;
        }// end of if/else cycle

        sourceTemplatesText = leggeFile(nomeFileTextSorgente);
        newTaskText = replaceText(sourceTemplatesText);

        this.checkAndWriteFile(task, mappaInput, newTaskText);
    }// end of method

    private String leggeFile(String nomeFileTextSorgente) {
        return file.leggeFile(sourcePath + SEP + nomeFileTextSorgente);
    }// end of method

    private void checkAndWriteFile(Task task, Map<Chiave, Object> mappaInput, String newTaskText) {
        String fileNameJava = "";
        String pathFileJava;
        String oldFileText = "";

        fileNameJava = newEntityName + task.getJavaClassName();
        pathFileJava = packagePath + SEP + fileNameJava;

        if (flagSovrascrive) {
            file.scriveFile(pathFileJava, newTaskText, true);
            System.out.println(fileNameJava + " esisteva già ed è stato modificato");
        } else {
            oldFileText = file.leggeFile(pathFileJava);
            if (text.isValid(oldFileText)) {
                if (checkFile(oldFileText)) {
                    file.scriveFile(pathFileJava, newTaskText, true);
                    System.out.println(fileNameJava + " esisteva già ed è stato modificato");
                } else {
                    System.out.println(fileNameJava + " esisteva già e NON è stato modificato");
                }// end of if/else cycle
            } else {
                file.scriveFile(pathFileJava, newTaskText, true);
                System.out.println(fileNameJava + " non esisteva ed è stato creato");
            }// end of if/else cycle
        }// end of if/else cycle
    }// end of method


    private boolean checkFile(String oldFileText) {
        boolean sovrascrivibile = true;
        String fileNameJava = "";
        String pathFileJava;
        String tagUno = "@AIScript(sovrascrivibile";
        String tagDue = "=";
        String tagTre = ")";
        int posIni;
        int posEnd;
        String estratto;

        if (oldFileText.contains(tagUno)) {
            sovrascrivibile = false;
            posIni = oldFileText.indexOf(tagUno);
            posIni = oldFileText.indexOf(tagDue, posIni);
            posEnd = oldFileText.indexOf(tagTre, posIni);
            estratto = oldFileText.substring(posIni, posEnd);
            estratto = text.levaTesta(estratto, tagDue);

            if (estratto.equals("true")) {
                sovrascrivibile = true;
            }// end of if cycle
        }// end of if cycle

        return sovrascrivibile;
    }// end of method


    private String replaceText(String sourceText) {
        Map<Token, String> mappa = new HashMap<>();

        mappa.put(Token.projectName, targetProjectName);
        mappa.put(Token.moduleNameMinuscolo, targetModuleName);
        mappa.put(Token.moduleNameMaiuscolo, targetModuleCapitalName);
        mappa.put(Token.packageName, newPackageName);
        mappa.put(Token.appCost, nameClassCost);
        mappa.put(Token.user, "Gac");
        mappa.put(Token.today, LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
        mappa.put(Token.qualifier, qualifier != null ? qualifier : "");
        mappa.put(Token.tagView, "");
        mappa.put(Token.entity, newEntityName);
        mappa.put(Token.superClassEntity, superClassEntity);
        mappa.put(Token.methodFind, creaFind());
        mappa.put(Token.parametersNewEntity, creaParametersNewEntity());
        mappa.put(Token.methodNewEntity, creaNewEntity());
        mappa.put(Token.methodNewOrdine, creaNewOrdine());
        mappa.put(Token.methodIdKeySpecifica, creaIdKeySpecifica());
        mappa.put(Token.query, creaQuery());
        mappa.put(Token.findAll, creaFindAll());
        mappa.put(Token.properties, creaProperties());
        mappa.put(Token.propertyOrdine, creaPropertyOrdine());
        mappa.put(Token.propertyCode, creaPropertyCode());
        mappa.put(Token.propertyDescrizione, creaPropertyDescrizione());
        mappa.put(Token.toString, creaToString());

        return Token.replaceAll(sourceText, mappa);
    }// end of method


    private String creaFind() {
        methodFindText = "";

        if (flagCode) {
            methodFindText += leggeFile(METHOD_FIND);
            methodFindText = Token.replace(Token.entity, methodFindText, newEntityName);
            methodFindText = Token.replace(Token.parametersFind, methodFindText, creaParametersFind());
            methodFindText = Token.replace(Token.parameters, methodFindText, creaParameters());
        }// end of if cycle

        return methodFindText;
    }// end of method


    private String creaNewEntity() {
        methodNewEntityText = "";

        methodNewEntityText += leggeFile(METHOD_NEW_ENTITY);
        methodNewEntityText = Token.replace(Token.entity, methodNewEntityText, newEntityName);
        methodNewEntityText = Token.replace(Token.parametersDoc, methodNewEntityText, creaParametersDoc());
        methodNewEntityText = Token.replace(Token.parameters, methodNewEntityText, creaParameters());
        methodNewEntityText = Token.replace(Token.keyUnica, methodNewEntityText, creaKeyUnica());
        methodNewEntityText = Token.replace(Token.builder, methodNewEntityText, creaBuilder());
        methodNewEntityText = Token.replace(Token.builder, methodNewEntityText, creaBuilder());

        return methodNewEntityText;
    }// end of method


    private String creaNewOrdine() {
        methodNewOrdineText = "";

        if (flagOrdine) {
            methodNewOrdineText += leggeFile(METHOD_NEW_ORDINE);
            methodNewOrdineText = Token.replace(Token.entity, methodNewOrdineText, newEntityName);
        }// end of if cycle

        return methodNewOrdineText;
    }// end of method


    private String creaIdKeySpecifica() {
        methodIdKeySpecificaText = "";

        if (flagCode && flagKeyCode) {
            methodIdKeySpecificaText += leggeFile(METHOD_ID_KEY_SPECIFICA);
            methodIdKeySpecificaText = Token.replace(Token.entity, methodIdKeySpecificaText, newEntityName);
        }// end of if cycle

        return methodIdKeySpecificaText;
    }// end of method


    private String creaQuery() {
        queryText = "";
        String aCapo = "\n\n\t";
        String entity = aCapo + "public " + newEntityName + " findBy";
        String list = aCapo + "public List<" + newEntityName + "> findAllByOrderBy";
        String listFirst = aCapo + "public List<" + newEntityName + "> findTop1AllByOrderBy";

        if (flagCode) {
            queryText += entity + PROPERTY_CODE_NAME + "(String " + PROPERTY_CODE_NAME.toLowerCase() + ");";
            queryText += list + PROPERTY_CODE_NAME + "Asc();";
        }// end of if cycle

        if (flagDescrizione) {
            queryText += entity + PROPERTY_DESCRIZIONE_NAME + "(String " + PROPERTY_DESCRIZIONE_NAME.toLowerCase() + ");";
            queryText += list + PROPERTY_DESCRIZIONE_NAME + "Asc();";
        }// end of if cycle

        if (flagOrdine) {
            queryText += entity + PROPERTY_ORDINE_NAME + "(int " + PROPERTY_ORDINE_NAME.toLowerCase() + ");";
            queryText += list + PROPERTY_ORDINE_NAME + "Asc();";
            queryText += listFirst + PROPERTY_ORDINE_NAME + "Desc();";
        }// end of if cycle

        return queryText;
    }// end of method


    private String creaFindAll() {
        String findAll = "";

        if (flagOrdine) {
            findAll += "lista = repository.findAllByOrderBy" + PROPERTY_ORDINE_NAME + "Asc();";
        } else {
            findAll += "lista = repository.findAll();";
        }// end of if/else cycle

        return findAll;
    }// end of method


    private String creaProperties() {
        propertiesText = "";
        String apici = "\"";
        String virgola = ", ";

        if (flagOrdine) {
            propertiesText += apici + PROPERTY_ORDINE_NAME.toLowerCase() + apici + virgola;
        }// end of if cycle
        if (flagCode) {
            propertiesText += apici + PROPERTY_CODE_NAME.toLowerCase() + apici + virgola;
        }// end of if cycle
        if (flagDescrizione) {
            propertiesText += apici + PROPERTY_DESCRIZIONE_NAME.toLowerCase() + apici + virgola;
        }// end of if cycle

        propertiesText = text.levaCoda(propertiesText, virgola);
        return propertiesText;
    }// end of method


    private String creaParametersFind() {
        parametersEntityText = "";
        String tagOrdine = "0";
        String tagDescrizione = "\"\"";
        String virgola = ", ";

        if (flagOrdine) {
            parametersEntityText += tagOrdine + virgola;
        }// end of if cycle
        if (flagCode) {
            parametersEntityText += PROPERTY_CODE_NAME.toLowerCase() + virgola;
        }// end of if cycle
        if (flagDescrizione) {
            parametersEntityText += tagDescrizione + virgola;
        }// end of if cycle

        parametersEntityText = text.levaCoda(parametersEntityText, virgola);
        return parametersEntityText;
    }// end of method


    private String creaParametersDoc() {
        parametersDocText = "";
        String inizio = "\n\t* @param ";
        String intero = "int";
        String spazio = " ";
        String virgola = ", ";

        if (flagOrdine) {
            parametersDocText += inizio + "ordine      di presentazione (obbligatorio con inserimento automatico se è zero)";
        }// end of if cycle
        if (flagCode) {
            parametersDocText += inizio + "code        codice di riferimento (obbligatorio)";
        }// end of if cycle
        if (flagDescrizione) {
            parametersDocText += inizio + "descrizione (facoltativa, non unica)";
        }// end of if cycle

        parametersDocText = text.levaCoda(parametersDocText, virgola);
        return parametersDocText;
    }// end of method


    private String creaParametersNewEntity() {
        String testo = "";
        String tagNumerico = "0";
        String tagTesto = "\"\"";
        String virgola = ", ";

        if (flagOrdine) {
            testo += tagNumerico + virgola;
        }// end of if cycle
        if (flagCode) {
            testo += tagTesto + virgola;
        }// end of if cycle
        if (flagDescrizione) {
            testo += tagTesto + virgola;
        }// end of if cycle

        testo = text.levaCoda(testo, virgola);
        return testo;
    }// end of method


    private String creaParameters() {
        parametersText = "";
        String stringa = "String";
        String intero = "int";
        String spazio = " ";
        String virgola = ", ";

        if (flagOrdine) {
            parametersText += intero + spazio + PROPERTY_ORDINE_NAME.toLowerCase() + virgola;
        }// end of if cycle
        if (flagCode) {
            parametersText += stringa + spazio + PROPERTY_CODE_NAME.toLowerCase() + virgola;
        }// end of if cycle
        if (flagDescrizione) {
            parametersText += stringa + spazio + PROPERTY_DESCRIZIONE_NAME.toLowerCase() + virgola;
        }// end of if cycle

        parametersText = text.levaCoda(parametersText, virgola);
        return parametersText;
    }// end of method


    private String creaKeyUnica() {
        methodKeyUnicaText = "";
        String stringa = "String";
        String intero = "int";
        String tab = "\t";
        String aCapo = "\n" + tab + tab;

        if (flagCode) {
            methodKeyUnicaText += "entity = findByKeyUnica(code);";
            methodKeyUnicaText += aCapo + "if (entity != null) {";
            methodKeyUnicaText += aCapo + tab + "return findByKeyUnica(code);";
            methodKeyUnicaText += aCapo + "}// end of if cycle";
            methodKeyUnicaText += aCapo;
        }// end of if cycle

        return methodKeyUnicaText;
    }// end of method


    private String creaBuilder() {
        methodBuilderText = "";
        String stringa = "String";
        String intero = "int";
        String tab4 = "\t\t\t\t";
        String aCapo = "\n" + tab4;

        if (flagOrdine) {
            methodBuilderText += aCapo + ".ordine(ordine != 0 ? ordine : this.getNewOrdine())";
        }// end of if cycle
        if (flagCode) {
            methodBuilderText += aCapo + ".code(code)";
        }// end of if cycle
        if (flagDescrizione) {
            methodBuilderText += aCapo + ".descrizione(descrizione)";
        }// end of if cycle

        return methodBuilderText;
    }// end of method


    private String creaPropertyOrdine() {
        String entityPropertyText = "";

        if (flagOrdine) {
            entityPropertyText = leggeFile(PROPERTY_ORDINE_SOURCE_NAME);
            if (text.isValid(entityPropertyText)) {
                entityPropertyText = A_CAPO + TAB + entityPropertyText;
            }// end of if cycle
        }// end of if cycle

        return entityPropertyText;
    }// end of method


    private String creaPropertyCode() {
        String entityPropertyText = "";

        if (flagCode) {
            entityPropertyText = leggeFile(PROPERTY_CODE_SOURCE_NAME);
            if (text.isValid(entityPropertyText)) {
                entityPropertyText = A_CAPO + TAB + entityPropertyText;
            }// end of if cycle
        }// end of if cycle

        return entityPropertyText;
    }// end of method


    private String creaPropertyDescrizione() {
        String entityPropertyText = "";

        if (flagDescrizione) {
            entityPropertyText = leggeFile(PROPERTY_DESCRIZIONE_SOURCE_NAME);
            if (text.isValid(entityPropertyText)) {
                entityPropertyText = A_CAPO + TAB + entityPropertyText;
            }// end of if cycle
        }// end of if cycle

        return entityPropertyText;
    }// end of method


    private String creaToString() {
        String entityPropertyText = "return super.toString();";

        if (flagCode) {
            entityPropertyText = "return code;";
        } else {
            if (flagDescrizione) {
                entityPropertyText = "return descrizione;";
            }// end of if/else cycle
        }// end of if/else cycle

        return entityPropertyText;
    }// end of method


    private void addPackageMenu() {
        String viewClass = text.primaMaiuscola(newPackageName) + VIEW_SUFFIX;
        //
        addRouteSpecifichePackage(bootPath, viewClass);
        addImportPackage(bootPath, viewClass);
    }// end of method


    private void addRouteSpecifichePackage(String layoutPath, String viewClass) {
        String aCapo = "\n\t\t";
        String tagPackage = "";
        String tagMethod = "private void addRouteSpecifiche() {";
        String textUIClass = file.leggeFile(layoutPath);

        if (isEsisteMetodo(targetModuleCapitalName + BOOT_SUFFIX, textUIClass, tagMethod)) {
            tagPackage = "BaseCost.MENU_CLAZZ_LIST.add(" + viewClass + ".class);";

            if (textUIClass.contains(tagPackage)) {
            } else {
                textUIClass = text.sostituisce(textUIClass, tagMethod, tagMethod + aCapo + tagPackage);
                file.scriveFile(layoutPath, textUIClass, true);

                System.out.println("Il package " + text.primaMaiuscola(newPackageName) + " è stato aggiunto al menu");
            }// end of if/else cycle
        }// end of if cycle
    }// end of method


    private boolean isEsisteMetodo(String fileNameUIClass, String textUIClass, String tagMethod) {
        boolean esiste = false;

        if (textUIClass.contains(tagMethod)) {
            esiste = true;
        } else {
            System.out.println("Nella classe iniziale " + fileNameUIClass + " manca il metodo " + tagMethod);
        }// end of if/else cycle

        return esiste;
    }// end of method


    private void addImportPackage(String layoutPath, String viewClass) {
        String aCapo = "\n";
        String tagImport = "";
        String tagInizioInserimento = "\n/**";
        int posIni = 0;
        String textUIClass = file.leggeFile(layoutPath);

        tagImport = "import it.algos." + targetModuleName + ".modules." + newPackageName + "." + viewClass + ";";

        if (textUIClass.contains(tagImport)) {
        } else {
            posIni = textUIClass.indexOf(tagInizioInserimento);
            tagImport = tagImport + aCapo;
            textUIClass = text.inserisce(textUIClass, tagImport, posIni);
            file.scriveFile(layoutPath, textUIClass, true);

            System.out.println("L'import del file " + viewClass + " è stato inserito negli import iniziali");
        }// end of if/else cycle
    }// end of method


    private void addTagCostanti() {
        String textCostClass;
        String path = applicationPath + SEP + nameClassCost + JAVA_SUFFIX;
        String tagOld = "public class " + nameClassCost + " {";
        String tagRif = "public final static String " + qualifier + " = \"" + newPackageName + "\";";
        String tagNew = tagOld + A_CAPO + TAB + tagRif;

        textCostClass = file.leggeFile(path);
        if (!textCostClass.contains(tagRif)) {
            textCostClass = text.sostituisce(textCostClass, tagOld, tagNew);
            file.scriveFile(path, textCostClass, true);
        }// end of if cycle
    }// end of method


    private void copiaDirectoriesBase() {
        boolean progettoCopiato = false;
        String tag = DIR_JAVA + "/" + PROJECT_BASE_NAME;
        String srcPath = projectBasePath;
        String destPath = ideaProjectRootPath + "/" + newProjectName;

        if (text.isValid(newProjectName)) {
            progettoCopiato = file.copyDirectory(srcPath + tag, destPath + tag);
        }// end of if cycle
    }// end of method


    private void copiaPom() {
        String destPath = ideaProjectRootPath + "/" + newProjectName + "/" + POM + ".xml";
        String testoPom = leggeFile(POM + SOURCE_SUFFIX);

        testoPom = Token.replace(Token.moduleNameMinuscolo, testoPom, newProjectName);

        file.scriveFile(destPath, testoPom, true);
    }// end of method

    private void copiaResources() {
    }// end of method

    private void copiaWebapp() {
    }// end of method

    private void creaProjectModule() {
        file.creaDirectory(projectJavaPath);
    }// end of method

    private void creaApplicationMain() {
        String mainApp = newProjectName + text.primaMaiuscola(APP_NAME);
        mainApp = text.primaMaiuscola(mainApp);
        String destPath = projectJavaPath + "/" + mainApp + JAVA_SUFFIX;
        String testoApp = leggeFile(APP_NAME + SOURCE_SUFFIX);

        testoApp = Token.replace(Token.moduleNameMinuscolo, testoApp, newProjectName);
        testoApp = Token.replace(Token.moduleNameMaiuscolo, testoApp, text.primaMaiuscola(newProjectName));

        file.scriveFile(destPath, testoApp, true);
    }// end of method

    private void creaApplicationDirectory() {
        file.creaDirectory(projectJavaPath + "/" + APP_NAME);
    }// end of method

    private void creaModulesDirectory() {
        file.creaDirectory(projectJavaPath + "/" + ENTITIES_NAME);
    }// end of method

    private void creaApplicationFolder() {
        creaCost();
        creaBoot();
        creaHome();
    }// end of method


    private void creaCost() {
        String destPath = projectJavaPath + "/" + APP_NAME + "/" + COST_NAME + JAVA_SUFFIX;
        String testoCost = leggeFile(COST_NAME + SOURCE_SUFFIX);

        testoCost = Token.replace(Token.moduleNameMinuscolo, testoCost, newProjectName);
        checkAndWrite(destPath, testoCost);
    }// end of method


    private void creaBoot() {
        String destPath = projectJavaPath + "/" + APP_NAME + "/" + text.primaMaiuscola(newProjectName) + BOOT_NAME + JAVA_SUFFIX;
        String testoBoot = leggeFile(BOOT_NAME + SOURCE_SUFFIX);

        testoBoot = Token.replace(Token.moduleNameMinuscolo, testoBoot, newProjectName);
        testoBoot = Token.replace(Token.moduleNameMaiuscolo, testoBoot, text.primaMaiuscola(newProjectName));
        checkAndWrite(destPath, testoBoot);
    }// end of method

    private void creaHome() {
        String destPath = projectJavaPath + "/" + APP_NAME + "/" + HOME_NAME + JAVA_SUFFIX;
        String testoHome = leggeFile(HOME_NAME + SOURCE_SUFFIX);

        testoHome = Token.replace(Token.moduleNameMinuscolo, testoHome, newProjectName);
        checkAndWrite(destPath, testoHome);
    }// end of method

    private void checkAndWrite(String destPath, String newText) {
        String oldFileText = file.leggeFile(destPath);
        if (checkFile(oldFileText)) {
            file.scriveFile(destPath, newText, true);
        }// end of if cycle
    }// end of method


//    private void addTagCost(String tag, String value) {
//        String aCapo = "\n\t";
//        String tagFind = "public abstract class " + nameCost + " {";
//        String tagStaticFind = "";
//        String tagStaticReplace = "";
//        String textCostClass = file.leggeFile(pathFileCost);
//        int posIni = 0;
//
//        tagStaticFind = "public final static String " + tag;
//        tagStaticReplace = tagStaticFind + " = \"" + value + "\";";
//
//        if (textCostClass.contains(tagStaticFind)) {
//        } else {
//            posIni = textCostClass.indexOf(tagFind);
//            posIni = posIni + tagFind.length();
//            tagStaticReplace = aCapo + tagStaticReplace;
//
//            textCostClass = text.inserisce(textCostClass, tagStaticReplace, posIni);
//            file.scriveFile(pathFileCost, textCostClass, true);
//
//            System.out.println("La costante statica TAG_" + tag + " è stata inserita nel file " + nameCost);
//        }// end of if/else cycle
//    }// end of method


}// end of class
