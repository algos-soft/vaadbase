package it.algos.vaadbase;

import com.vaadin.flow.router.Route;
import it.algos.vaadbase.modules.role.Role;
import it.algos.vaadbase.service.AAnnotationService;
import it.algos.vaadbase.service.AArrayService;
import it.algos.vaadbase.service.AReflectionService;
import it.algos.vaadbase.service.ATextService;
import it.algos.vaadbase.ui.annotation.*;
import it.algos.vaadbase.ui.enumeration.EAFieldType;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static it.algos.vaadbase.service.AAnnotationService.INT_NULL;
import static it.algos.vaadbase.service.AAnnotationService.TESTO_NULL;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Project springvaadin
 * Created by Algos
 * User: gac
 * Date: sab, 09-dic-2017
 * Time: 14:01
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("annotation")
@DisplayName("Test sulle annotazioni Algos")
public class AAnnotationServiceTest extends ATest {


    @InjectMocks
    public AArrayService array;
    @InjectMocks
    public ATextService text;
    @InjectMocks
    private AAnnotationService service;
    @InjectMocks
    private AReflectionService reflection;

//    @InjectMocks
//    public ASessionService session;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(service);
        MockitoAnnotations.initMocks(reflection);
        MockitoAnnotations.initMocks(array);
        MockitoAnnotations.initMocks(text);
//        MockitoAnnotations.initMocks(session);
        service.reflection = reflection;
//        service.session = session;
        service.text = text;
        array.text = text;
        service.array = array;
        reflection.array = array;
        FIELD_ORDINE = reflection.getField(ROLE_ENTITY_CLASS, NAME_ORDINE);
        FIELD_CODE = reflection.getField(ROLE_ENTITY_CLASS, NAME_CODE);
//        FIELD_ROLE = reflection.getField(USER_ENTITY_CLASS, NAME_ROLE);
    }// end of method


//    @SuppressWarnings("javadoc")
//    /**
//     * Get the specific annotation of the class.
//     * Spring view class
//     *
//     * @param viewClazz the view class
//     *
//     * @return the Annotation for the specific class
//     */
//    @Test
//    public void getAIView() {
//        AIView ottenuto = service.getAIView(ROLE_VIEW_CLASS_LIST);
//        assertNotNull(ottenuto);
//    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the specific annotation of the class.
     * Entity class
     *
     * @param entityClazz the entity class
     *
     * @return the Annotation for the specific class
     */
    @Test
    public void getAIEntity() {
        AIEntity ottenuto = service.getAIEntity(ROLE_ENTITY_CLASS);
        assertNotNull(ottenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the specific annotation of the class.
     * Entity class
     *
     * @param entityClazz the entity class
     *
     * @return the Annotation for the specific class
     */
    @Test
    public void getAIList() {
        AIList ottenuto;

        ottenuto = service.getAIList(ROLE_ENTITY_CLASS);
        assertNotNull(ottenuto);

//        ottenuto = service.getAIList(USER_ENTITY_CLASS);
//        assertNotNull(ottenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the specific annotation of the class.
     * Entity class
     *
     * @param entityClazz the entity class
     *
     * @return the Annotation for the specific class
     */
    @Test
    public void getAIForm() {
        AIForm ottenuto = service.getAIForm(ROLE_ENTITY_CLASS);
        assertNotNull(ottenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the specific annotation of the class.
     * Entity class
     *
     * @param entityClazz the entity class
     *
     * @return the Annotation for the specific class
     */
    @Test
    public void getAIView() {
//        AIView ottenutoList = service.getAIView(ROLE_VIEW_CLASS_LIST);
//        assertNotNull(ottenutoList);

        AIView ottenutoForm = service.getAIView(ROLE_VIEW_CLASS);
//        assertNull(ottenutoForm);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the specific annotation of the class.
     * View class
     *
     * @param entityClazz the entity class
     *
     * @return the Annotation for the specific class
     */
    @Test
    public void getRoute() {
        Route ottenuto = service.getRoute(ROLE_VIEW_CLASS);
        assertNotNull(ottenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the specific annotation of the field.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the Annotation for the specific field
     */
    @Test
    public void getAIColumn() {
        AIColumn ottenuto;

        ottenuto = service.getAIColumn(FIELD_CODE);
        assertNotNull(ottenuto);

        ottenuto = service.getAIColumn(ROLE_ENTITY_CLASS, FIELD_NAME_NOTE);
        assertNotNull(ottenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the specific annotation of the field.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the Annotation for the specific field
     */
    @Test
    public void getAIField() {
        AIField ottenuto = service.getAIField(FIELD_CODE);
        assertNotNull(ottenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the specific annotation of the field.
     *
     * @param entityClazz the entity class
     * @param fieldName   the property name
     *
     * @return the Annotation for the specific field
     */
    @Test
    public void getAIField2() {
        AIField ottenuto;
        ottenuto = service.getAIField(ROLE_ENTITY_CLASS, FIELD_NAME_CODE);
        assertNotNull(ottenuto);
    }// end of method

    @SuppressWarnings("javadoc")
    /**
     * Get the name of the spring-view.
     *
     * @param clazz the entity class
     *
     * @return the name of the spring-view
     */
    @Test
    public void getViewName() {
        previsto = "role";
        ottenuto = service.getViewName(ROLE_VIEW_CLASS);
        assertEquals(previsto, ottenuto);
    }// end of single test


//    @SuppressWarnings("javadoc")
//    /**
//     * Get the status listShowsID of the class.
//     *
//     * @param clazz the entity class
//     *
//     * @return status of class - default false
//     */
//    @Test
//    public void isListShowsID() {
//        previstoBooleano = false;
//        ottenutoBooleano = service.isListShowsID(ROLE_ENTITY_CLASS);
//        assertEquals(previstoBooleano, ottenutoBooleano);
//    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Colonne visibili (e ordinate) nella Grid
     * Nomi dei fields da considerare per estrarre i Java Reflected Field dalle @Annotation della Entity
     * Se la classe AEntity->@AIList prevede una lista specifica, usa quella lista (con o senza ID)
     * Sovrascrivibile
     *
     * @param clazz the entity class
     *
     * @return nomi dei fields, oppure null se non esiste l'Annotation specifica @AIList() nella Entity
     */
    @Test
    public void getGridPropertiesName() {
        String[] stringArray = {"ordine", "code"};
        previstoList = Arrays.asList(stringArray);

        ottenutoList = service.getGridPropertiesName(ROLE_ENTITY_CLASS);
        assertEquals(previstoList, ottenutoList);
    }// end of single test

    @SuppressWarnings("javadoc")
    /**
     * Nomi delle properties del, estratti dalle @Annotation della Entity
     * Se la classe AEntity->@AIForm prevede una lista specifica, usa quella lista (con o senza ID)
     * Se l'annotation @AIForm non esiste od è vuota,
     * restituisce tutti i campi (properties della classe e superclasse)
     * Sovrascrivibile
     *
     * @return lista di nomi di property, oppure null se non esiste l'Annotation specifica @AIForm() nella Entity
     */
    @Test
    public void getFormPropertiesName() {
        String[] stringArray = {"ordine", "code"};
        previstoList = Arrays.asList(stringArray);

        ottenutoList = service.getFormPropertiesName(ROLE_ENTITY_CLASS);
        assertEquals(previstoList, ottenutoList);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Nomi dei fields da considerare per estrarre i Java Reflected Field dalle @Annotation della Entity
     * Se la classe AEntity->@AIForm prevede una lista specifica, usa quella lista (con o senza ID)
     * Sovrascrivibile
     *
     * @return nomi dei fields, oppure null se non esiste l'Annotation specifica @AIForm() nella Entity
     */
    @Test
    public void getFormFieldsName() {
        String[] stringArray = {"ordine", "code"};
        previstoList = Arrays.asList(stringArray);

        ottenutoList = service.getFormFieldsName(ROLE_ENTITY_CLASS);
        assertEquals(previstoList, ottenutoList);

//        ottenutoList = service.getFormFieldsName(USER_ENTITY_CLASS);
//        assertNull(ottenutoList);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the name (column) of the property.
     * Se manca, usa il nome del Field
     * Se manca, usa il nome della property
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the name (column) of the field
     */
    @Test
    public void getColumnName() {
        previsto = HEADER_ORDINE;
        ottenuto = service.getColumnName(FIELD_ORDINE);
        assertEquals(previsto, ottenuto);

        previsto = HEADER_CODE;
        ottenuto = service.getColumnName(FIELD_CODE);
        assertEquals(previsto, ottenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the type (column) of the property.
     * Se manca, usa il type del Field
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the type for the specific column
     */
    @Test
    public void getColumnType() {
//        previstoType = EAFieldType.integer;
//        ottenutoType = service.getColumnType(FIELD_ORDINE);
//        assertEquals(previstoType, ottenutoType);
//
//        previstoType = EAFieldType.text;
//        ottenutoType = service.getColumnType(FIELD_CODE);
//        assertEquals(previstoType, ottenutoType);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the visibility of the column.
     * Di default true
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the visibility of the column
     */
    @Test
    public void isColumnVisibile() {
        //@todo RIMETTERE
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the width of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the width of the column expressed in int
     */
    @Test
    public void getColumnWith() {
        previstoIntero = 55;
        ottenutoIntero = service.getColumnWith(FIELD_ORDINE);
        assertEquals(previstoIntero, ottenutoIntero);

        previstoIntero = 210;
        ottenutoIntero = service.getColumnWith(FIELD_CODE);
        assertEquals(previstoIntero, ottenutoIntero);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the type (field) of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the type for the specific column
     */
    @Test
    public void getFormType() {
        previstoType = EAFieldType.integer;
        ottenutoType = service.getFormType(ROLE_ENTITY_CLASS, FIELD_NAME_ORDINE);
        assertEquals(previstoType, ottenutoType);

        previstoType = EAFieldType.text;
        ottenutoType = service.getFormType(ROLE_ENTITY_CLASS, FIELD_NAME_CODE);
        assertEquals(previstoType, ottenutoType);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the name (field) of the property.
     * Se manca, usa il nome della property
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the name (rows) of the field
     */
    @Test
    public void getFormFieldName() {
        previsto = text.primaMaiuscola(NAME_ORDINE);
        ottenuto = service.getFormFieldNameCapital(FIELD_ORDINE);
        assertEquals(previsto, ottenuto);

        previsto = text.primaMaiuscola(NAME_CODE);
        ottenuto = service.getFormFieldNameCapital(FIELD_CODE);
        assertEquals(previsto, ottenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the alert message from @NotNull or from @Size
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the alert message
     */
    @Test
    public void getMessage() {
        previsto = FIELD_NAME_ORDINE + INT_NULL;
        previsto = text.primaMaiuscola(previsto);

        ottenuto = service.getMessage(FIELD_ORDINE);
        assertEquals(previsto, ottenuto);

        ottenuto = service.getMessageNull(FIELD_ORDINE);
        assertEquals(previsto, ottenuto);

        previsto = "";
        ottenuto = service.getMessageSize(FIELD_ORDINE);
        assertEquals(previsto, ottenuto);

        previsto = FIELD_NAME_CODE + TESTO_NULL;
        previsto = text.primaMaiuscola(previsto);
        ottenuto = service.getMessage(FIELD_CODE);
        assertEquals(previsto, ottenuto);
        ottenuto = service.getMessageNull(FIELD_CODE);
        assertEquals(previsto, ottenuto);

        previsto = FIELD_NAME_CODE + " deve contenere almeno 3 caratteri";
        previsto = text.primaMaiuscola(previsto);
        ottenuto = service.getMessage(FIELD_CODE);
        assertNotEquals(previsto, ottenuto);
        ottenuto = service.getMessageSize(FIELD_CODE);
        assertEquals(previsto, ottenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the status of visibility for the field of ACompanyEntity.
     * <p>
     * Controlla se l'applicazione usa le company2 - flag  AlgosApp.USE_MULTI_COMPANY=true
     * Controlla se la collection (table) usa la company2
     * Controlla se l'buttonUser collegato è un developer
     *
     * @param clazz the entity class
     *
     * @return status - default true
     */
    @Test
    public void isCompanyFieldVisible() {
        //@todo RIMETTERE
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Tipo di lista (EAListButton) indicata nella AEntity class per la view AList
     *
     * @return valore della enumeration
     */
    @Test
    public void getListBotton() {
//        EAListButton ottenutoListButton;
//        EAListButton previstoListButton = EAListButton.standard;
//
//        ottenutoListButton = service.getListBotton(ROLE_ENTITY_CLASS);
//        assertEquals(previstoListButton, ottenutoListButton);
        //@todo RIMETTERE
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Tipo di lista (EAFormButton) indicata nella AEntity class per la view AForm
     *
     * @return valore della enumeration
     */
    @Test
    public void getFormBotton() {
//        EAFormButton ottenutoFormButton;
//        EAFormButton previstoFormButton = EAFormButton.standard;
//
//        ottenutoFormButton = service.getFormBotton(ROLE_ENTITY_CLASS);
//        assertEquals(previstoFormButton, ottenutoFormButton);
        //@todo RIMETTERE
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the status focus of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return status of field
     */
    @Test
    public void isFocus() {
        previstoBooleano = false;
        ottenutoBooleano = service.isFocus(FIELD_ORDINE);
        assertEquals(previstoBooleano, ottenutoBooleano);

        previstoBooleano = true;
        ottenutoBooleano = service.isFocus(FIELD_CODE);
        assertEquals(previstoBooleano, ottenutoBooleano);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the class of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the class for the specific column
     */
    public void getComboClass() {
        Class previstoClass = Role.class;
        Class ottenutoClass = service.getComboClass(FIELD_CODE);
        assertEquals(previstoClass, ottenutoClass);
    }// end of method


    @SuppressWarnings("javadoc")
    /**
     * Get the width of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the width of the field expressed in em
     */
    @Test
    public void getWidth() {
        previstoIntero = 3;
        ottenutoIntero = service.getWidth(FIELD_ORDINE);
        assertEquals(previstoIntero, ottenutoIntero);

        previstoIntero = 12;
        ottenutoIntero = service.getWidth(FIELD_CODE);
        assertEquals(previstoIntero, ottenutoIntero);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the widthEM of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the width of the field expressed in em
     */
    @Test
    public void getWidthEM() {
        previsto = "3em";
        ottenuto = service.getWidthEM(FIELD_ORDINE);
        assertEquals(previsto, ottenuto);

        previsto = "12em";
        ottenuto = service.getWidthEM(FIELD_CODE);
        assertEquals(previsto, ottenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the status required of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return status of field
     */
    @Test
    public void isRequired() {
        previstoBooleano = false;
        ottenutoBooleano = service.isRequired(FIELD_ORDINE);
        assertEquals(previstoBooleano, ottenutoBooleano);

        previstoBooleano = true;
        ottenutoBooleano = service.isRequired(FIELD_CODE);
        assertEquals(previstoBooleano, ottenutoBooleano);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the status 'nonUsata, facoltativa, obbligatoria' of the class.
     *
     * @param clazz the entity class
     */
    @Test
    public void getCompanyRequired() {
//        previstoCompany = EACompanyRequired.nonUsata;
//        ottenutoCompany = service.getCompanyRequired(ROLE_ENTITY_CLASS);
//        assertEquals(previstoCompany, ottenutoCompany);
//
//        previstoCompany = EACompanyRequired.obbligatoria;
//        ottenutoCompany = service.getCompanyRequired(USER_ENTITY_CLASS);
//        assertEquals(previstoCompany, ottenutoCompany);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the roleTypeVisibility of the class.
     * Viene usata come default, se manca il valore specifico del singolo field
     * La Annotation @AIEntity ha un suo valore di default per la property @AIEntity.roleTypeVisibility()
     * Se manca completamente l'annotation, inserisco qui un valore di default (per evitare comunque un nullo)
     *
     * @param clazz the entity class
     *
     * @return the roleTypeVisibility of the class
     */
    @Test
    public void getEntityRoleType() {
//        EARoleType roleTypeVisibilityOttenuta = null;
//        EARoleType roleTypeVisibilityPrevista = EARoleType.developer;
//        roleTypeVisibilityOttenuta = service.getEntityRoleType(ROLE_ENTITY_CLASS);
//        assertEquals(roleTypeVisibilityPrevista, roleTypeVisibilityOttenuta);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the roleTypeVisibility of the View class.
     * La Annotation @AIView ha un suo valore di default per la property @AIView.roleTypeVisibility()
     * Se manca completamente l'annotation, inserisco qui un valore di default (per evitare comunque un nullo)
     *
     * @param clazz the entity class
     *
     * @return the roleTypeVisibility of the class
     */
    @Test
    public void getViewRoleType() {
//        EARoleType roleTypeVisibilityOttenuta = null;
//        EARoleType roleTypeVisibilityPrevista = EARoleType.user;
//        roleTypeVisibilityOttenuta = service.getViewRoleType(ROLE_VIEW_CLASS_LIST);
//        assertEquals(roleTypeVisibilityPrevista, roleTypeVisibilityOttenuta);
//
//        roleTypeVisibilityPrevista = EARoleType.user;
//        roleTypeVisibilityOttenuta = service.getViewRoleType(ROLE_VIEW_CLASS_FORM);
//        assertEquals(roleTypeVisibilityPrevista, roleTypeVisibilityOttenuta);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the accessibility status of the class for the developer login.
     * Viene usata come default, se manca il valore specifico del singolo field
     * La Annotation @AIForm ha un suo valore di default per la property @AIForm.fieldsDev()
     * Se manca completamente l'annotation, inserisco qui un valore di default (per evitare comunque un nullo)
     *
     * @param clazz the entity class
     *
     * @return accessibilità del Form
     */
    @Test
    public void getFormAccessibilityDev() {
//        previstaAccessibilità = EAFieldAccessibility.allways;
//        ottenutaAccessibilità = service.getFormAccessibilityDev(ROLE_ENTITY_CLASS);
//        assertEquals(previstaAccessibilità, ottenutaAccessibilità);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the accessibility status of the class for the admin login.
     * Viene usata come default, se manca il valore specifico del singolo field
     * La Annotation @AIForm ha un suo valore di default per la property @AIForm.fieldsAdmin()
     * Se manca completamente l'annotation, inserisco qui un valore di default (per evitare comunque un nullo)
     *
     * @param clazz the entity class
     *
     * @return accessibilità del Form
     */
    @Test
    public void getFormAccessibilityAdmin() {
//        previstaAccessibilità = EAFieldAccessibility.never;
//        ottenutaAccessibilità = service.getFormAccessibilityAdmin(ROLE_ENTITY_CLASS);
//        assertEquals(previstaAccessibilità, ottenutaAccessibilità);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the accessibility status of the class for the user login.
     * Viene usata come default, se manca il valore specifico del singolo field
     * La Annotation @AIForm ha un suo valore di default per la property @AIForm.fieldsUser()
     * Se manca completamente l'annotation, inserisco qui un valore di default (per evitare comunque un nullo)
     *
     * @param clazz the entity class
     *
     * @return accessibilità del Form
     */
    @Test
    public void getFormAccessibilityUser() {
//        previstaAccessibilità = EAFieldAccessibility.never;
//        ottenutaAccessibilità = service.getFormAccessibilityUser(ROLE_ENTITY_CLASS);
//        assertEquals(previstaAccessibilità, ottenutaAccessibilità);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the accessibility status of the field for the developer login.
     * La Annotation @AIField ha un suo valore di default per la property @AIField.dev()
     * Se il field lo prevede (valore di default) ci si rifà al valore generico del Form
     * Se manca completamente l'annotation, inserisco qui un valore di default (per evitare comunque un nullo)
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return accessibilità del field
     */
    @Test
    public void getFieldAccessibilityDev() {
//        previstaAccessibilità = EAFieldAccessibility.showOnly;
//        ottenutaAccessibilità = service.getFieldAccessibilityDev(FIELD_ORDINE);
//        assertEquals(previstaAccessibilità, ottenutaAccessibilità);
//
//        previstaAccessibilità = EAFieldAccessibility.allways;
//        ottenutaAccessibilità = service.getFieldAccessibilityDev(FIELD_CODE);
//        assertEquals(previstaAccessibilità, ottenutaAccessibilità);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the accessibility status of the field for the admin login.
     * La Annotation @AIField ha un suo valore di default per la property @AIField.admin()
     * Se il field lo prevede (valore di default) ci si rifà al valore generico del Form
     * Se manca completamente l'annotation, inserisco qui un valore di default (per evitare comunque un nullo)
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return accessibilità del field
     */
    @Test
    public void getFieldAccessibilityAdmin() {
//        previstaAccessibilità = EAFieldAccessibility.never;
//        ottenutaAccessibilità = service.getFieldAccessibilityAdmin(FIELD_ORDINE);
//        assertEquals(previstaAccessibilità, ottenutaAccessibilità);
//
//        previstaAccessibilità = EAFieldAccessibility.showOnly;
//        ottenutaAccessibilità = service.getFieldAccessibilityAdmin(FIELD_CODE);
//        assertEquals(previstaAccessibilità, ottenutaAccessibilità);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the accessibility status of the field for the user login.
     * La Annotation @AIField ha un suo valore di default per la property @AIField.user()
     * Se il field lo prevede (valore di default) ci si rifà al valore generico del Form
     * Se manca completamente l'annotation, inserisco qui un valore di default (per evitare comunque un nullo)
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return accessibilità del field
     */
    @Test
    public void getFieldAccessibilityUser() {
//        previstaAccessibilità = EAFieldAccessibility.never;
//        ottenutaAccessibilità = service.getFieldAccessibilityUser(FIELD_ORDINE);
//        assertEquals(previstaAccessibilità, ottenutaAccessibilità);
//
//        previstaAccessibilità = EAFieldAccessibility.never;
//        ottenutaAccessibilità = service.getFieldAccessibilityUser(FIELD_CODE);
//        assertEquals(previstaAccessibilità, ottenutaAccessibilità);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the accessibility status of the field for the current login.
     * Se manca completamente l'annotation, inserisco qui un valore di default (per evitare comunque un nullo)
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return accessibilità del field
     */
    @Test
    public void getFieldAccessibility() {
//        //--developer
//        session.setDeveloper(true);
//        session.setAdmin(false);
//        session.setUser(false);
//        previstaAccessibilità = EAFieldAccessibility.showOnly;
//        ottenutaAccessibilità = service.getFieldAccessibility(FIELD_ORDINE);
//        assertEquals(previstaAccessibilità, ottenutaAccessibilità);
//        previstaAccessibilità = EAFieldAccessibility.allways;
//        ottenutaAccessibilità = service.getFieldAccessibility(FIELD_CODE);
//        assertEquals(previstaAccessibilità, ottenutaAccessibilità);
//
//        //--admin Non funziona, perché ASessionService usa VaadinSession.getCurrent();
//        session.setDeveloper(false);
//        session.setAdmin(true);
//        session.setUser(false);
//        previstaAccessibilità = EAFieldAccessibility.never;
//        ottenutaAccessibilità = service.getFieldAccessibility(FIELD_ORDINE);
////        assertEquals(previstaAccessibilità, ottenutaAccessibilità);
//        previstaAccessibilità = EAFieldAccessibility.showOnly;
//        ottenutaAccessibilità = service.getFieldAccessibility(FIELD_CODE);
////        assertEquals(previstaAccessibilità, ottenutaAccessibilità);
//
//        //--user Non funziona, perché ASessionService usa VaadinSession.getCurrent();
//        session.setDeveloper(false);
//        session.setAdmin(false);
//        session.setUser(true);
//        previstaAccessibilità = EAFieldAccessibility.never;
//        ottenutaAccessibilità = service.getFieldAccessibility(FIELD_ORDINE);
////        assertEquals(previstaAccessibilità, ottenutaAccessibilità);
//        previstaAccessibilità = EAFieldAccessibility.never;
//        ottenutaAccessibilità = service.getFieldAccessibility(FIELD_CODE);
////        assertEquals(previstaAccessibilità, ottenutaAccessibilità);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the roleTypeVisibility of the field.
     * La Annotation @AIField ha un suo valore di default per la property @AIField.roleTypeVisibility()
     * Se il field lo prevede (valore di default) ci si rifà al valore generico del Form
     * Se manca completamente l'annotation, inserisco qui un valore di default (per evitare comunque un nullo)
     *
     * @param reflectionJavaField di riferimento per estrarre le Annotation
     *
     * @return the ARoleType of the field
     */
    @Test
    public void getFieldRoleType() {
//        EARoleType roleTypeVisibilityOttenuta = null;
//        EARoleType roleTypeVisibilityPrevista = EARoleType.developer;
//        roleTypeVisibilityOttenuta = service.getFieldRoleType(FIELD_ORDINE);
//        assertEquals(roleTypeVisibilityPrevista, roleTypeVisibilityOttenuta);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Get the visibility of the field.
     * Controlla il ruolo del login connesso
     *
     * @param reflectionJavaField di riferimento per estrarre le Annotation
     *
     * @return the visibility of the field
     */
    @Test
    public void isFieldVisibileRole() {
//        //--developer
//        session.setDeveloper(true);
//        session.setAdmin(false);
//        session.setUser(false);
//        previstoBooleano = true;
//        ottenutoBooleano = service.isFieldVisibileRole(FIELD_ORDINE);
//        assertEquals(previstoBooleano, ottenutoBooleano);
//
//        //--admin
//        session.setDeveloper(false);
//        session.setAdmin(true);
//        session.setUser(false);
//        previstoBooleano = true;
//        ottenutoBooleano = service.isFieldVisibileRole(FIELD_ORDINE);
//        assertEquals(previstoBooleano, ottenutoBooleano);
//
//        //--user Non funziona, perché ASessionService usa VaadinSession.getCurrent();
//        session.setDeveloper(false);
//        session.setAdmin(false);
//        session.setUser(true);
//        previstoBooleano = false;
//        ottenutoBooleano = service.isFieldVisibileRole(FIELD_ORDINE);
////        assertEquals(previstoBooleano, ottenutoBooleano);
    }// end of single test

}// end of class
