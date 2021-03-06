package it.algos.vaadbase.application;

import com.vaadin.flow.router.RouterLayout;
import it.algos.vaadbase.ui.MainLayout;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class BaseCost {
    public final static String TAG_UTE = "utente";
    public final static String TAG_VER = "versione";
    public final static boolean USA_BAKERY_PAGES = false;
    public static final Locale APP_LOCALE = Locale.US;
    public final static boolean DEBUG = false;

    public final static Class<? extends RouterLayout> LAYOUT = MainLayout.class;
    public final static String LAYOUT_NAME = "MainLayout";
    public static final String VIEWPORT = "width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes";

    public final static int FLASH = 2000;

    public final static String TAG_HOM = "home";
    public final static String TAG_WIZ = "wizard";
    public final static String TAG_DEV = "developer";
    public final static String TAG_ADD = "address";
    public final static String TAG_PER = "person";
    public final static String TAG_COM = "company";
    public final static String TAG_ROL = "role";
    public final static String TAG_PRE = "preferenza";


    public static final String PAGE_ROOT = "";
    public static final String PAGE_WIZARD = "wizard";
    public static final String PAGE_ADDRESS = "address";
    public static final String PAGE_ADDRESS_EDIT = "address/edit";
    public static final String PAGE_STOREFRONT = "storefront";
    public static final String PAGE_STOREFRONT_EDIT = "storefront/edit";
    public static final String PAGE_COMPANY = "company";
    public static final String PAGE_USERS = "users";
    public static final String PAGE_PRODUCTS = "products";
    public static final String PAGE_LOGOUT = "logout";
    public static final String PAGE_NOTFOUND = "404";
    public static final String PAGE_DEFAULT = PAGE_COMPANY;
    public static final String PAGE_ACCESS_DENIED = "access-denied";

    public static final String ICON_WIZARD = "magic";
    public static final String ICON_STOREFRONT = "edit";
    public static final String ICON_DASHBOARD = "clock";
    public static final String ICON_USERS = "user";
    public static final String ICON_PRODUCTS = "calendar";
    public static final String ICON_LOGOUT = "exit";

    public static final String TITLE_WIZARD = "Wizard";
    public static final String TITLE_STOREFRONT = "Storefront";
    public static final String TITLE_DASHBOARD = "Dashboard";
    public static final String TITLE_USERS = "Users";
    public static final String TITLE_PRODUCTS = "Products";
    public static final String TITLE_LOGOUT = "Logout";
    public static final String TITLE_NOT_FOUND = "Page was not found";
    public static final String TITLE_ACCESS_DENIED = "Access denied";

    public static final String[] ORDER_SORT_FIELDS = {"dueDate", "dueTime", "id"};
    public static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.ASC;

    public static final String DASHBOARD_ORDER_CARD_STYLE = "dashboard-order-card";
    public static final String STOREFRONT_ORDER_CARD_STYLE = "storefront-order-card";

    public final static String BOT_ACCETTA = "accetta";
    public final static String BOT_CONFERMA = "conferma";
    public final static String BOT_ANNULLA = "annulla";
    public final static String BOT_BACK = "back";
    public final static String BOT_CREATE = "nuovo";
    public final static String BOT_DELETE = "elimina";
    public final static String BOT_EDIT = "edit";
    public final static String BOT_SHOW = "show";
    public final static String BOT_IMAGE = "immagine";
    public final static String BOT_IMPORT = "import";
    public final static String BOT_LINK_ACCETTA = "linkaccetta";
    public final static String BOT_LINK_REGISTRA = "linkregistra";
    public final static String BOT_REGISTRA = "registra";
    public final static String BOT_REVERT = "revert";
    public final static String BOT_SEARCH = "ricerca";
    public final static String BOT_SHOW_ALL = "tutto";
    public final static String BOT_LINK = "botlink";
    public final static String BOT_CHOOSER = "apri";

    public final static String BUTTON_NORMAL_WIDTH = "8em";
    public final static String BUTTON_ICON_WIDTH = "3em";

    public final static String PROPERTY_ID = "id";
    public final static String PROPERTY_COMPANY = "company2";
    public final static String PROPERTY_SERIAL = "serialVersionUID";
    public final static String PROPERTY_ORDINE = "ordine";
    public final static String PROPERTY_NOTE = "note";
    public final static String PROPERTY_CREAZIONE = "creazione";
    public final static String PROPERTY_MODIFICA = "modifica";
    public final static String COMPANY_CODE = "code";
    public final static String COMPANY_UNICO = "codeCompanyUnico";
    public final static String VIEW_ROL = "roleview";
    // generali
    public final static String USA_DEBUG = "usaDebug";
    public final static String USA_LOG_DEBUG = "usaLogDebug";
    public final static String USA_COMPANY = "usaCompany";
    // moduli visibili
    public final static String SHOW_COMPANY = "showCompany";
    public final static String SHOW_PREFERENZA = "showPreferenza";
    public final static String SHOW_WIZARD = "showWizard";
    public final static String SHOW_DEVELOPER = "showDeveloper";
    public final static String SHOW_ADDRESS = "showAddress";
    public final static String SHOW_PERSON = "showPerson";
    public final static String SHOW_ROLE = "showRole";
    public final static String SHOW_VERSION = "showVersion";
    public final static String SHOW_USER = "showUser";
    public final static String SHOW_LOGGER = "showLogger";
    private final static String[] esclusiAll = {PROPERTY_SERIAL, PROPERTY_CREAZIONE, PROPERTY_MODIFICA};
    public final static List<String> ESCLUSI_ALL = Arrays.asList(esclusiAll);
    private final static String[] esclusiList = {PROPERTY_ID, PROPERTY_SERIAL, PROPERTY_COMPANY, PROPERTY_NOTE, PROPERTY_CREAZIONE, PROPERTY_MODIFICA};
    public final static List<String> ESCLUSI_LIST = Arrays.asList(esclusiList);
    private final static String[] esclusiForm = {PROPERTY_ID, PROPERTY_SERIAL, PROPERTY_COMPANY, PROPERTY_CREAZIONE, PROPERTY_MODIFICA};
    public final static List<String> ESCLUSI_FORM = Arrays.asList(esclusiForm);
    private final static String[] esclusiMatrice = {PROPERTY_SERIAL, PROPERTY_ID, PROPERTY_COMPANY};
    public final static List<String> ESCLUSI = Arrays.asList(esclusiMatrice);
    private final static String[] companyMatrice = {COMPANY_CODE, COMPANY_UNICO};
    public final static List<String> COMPANY_OPTIONAL = Arrays.asList(companyMatrice);
    public static List<Class> MENU_CLAZZ_LIST = new ArrayList<>();

}// end of static class