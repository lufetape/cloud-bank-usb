package co.edu.usbcali.cloudbank.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase utilitaria para gestionar operaciones que tengan que ver con
 * expresiones regulares
 *
 * @author Felipe
 */
public class UtilRegExp {

    private static final String ACENTOS = "\u00e1\u00e9\u00ed\u00f3\u00fa\u00c1\u00c9\u00cd\u00d3\u00da\u00f1\u00d1\u00FC\u00DC";
    private static final String CARACTERES_PERMITIDOS = "\\.\\,\\;\\:\\=\\-\\_\\'\\?\\¿\\/\\&\\%\\$\\#\\!\\¡\\@\\(\\)\\*\\{\\}\\[\\]\\+";
    private static final String NUMERIC_REGEXP = "^(\\-)?[0-9]+(\\.[0-9]*)?$";
    private static final String INTEGER_REGEXP = "^(\\-)?[0-9]+?$";
    private static final String INTEGER_POSITIVE_REGEXP = "^(\\d+)$";
    private static final String ACCOUNT_NUMBER_REGEXP = "^[a-zA-Z\\s\\-]+$";
    private static final String ALPHABETIC_REGEXP = "^[" + CARACTERES_PERMITIDOS + "a-zA-Z\\s" + ACENTOS + "]+$";
    private static final String ALPHANUMERIC_REGEXP = "^[" + CARACTERES_PERMITIDOS + "a-zA-Z0-9\\s" + ACENTOS + "]+$";
    private static final String EMAIL_REGEXP = "^[\\w\\-\\_\\+]+(\\.[\\w\\-\\_\\+]+)*@([\\_\\-A-Za-z0-9]+\\.)*[A-Za-z]{2,4}$";
    private static final String PATH_REGEXP = "^[\\_\\-\\/\\.\\,\\;\\:\\=\\%\\?\\&\\~a-zA-Z0-9]+$";

    /**
     * Metodo que valida una cadena contra una expresion regular
     *
     * @param string Cadena
     * @param regExp Expresion regular
     * @return true si la expresion es valida, false de lo contrario
     */
    private static boolean validate(String string, final String regExp) {
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();

    }

    /**
     * Metodo que valida si un string es un campo numerico
     *
     * @param string Cadena
     * @return true si la expresion es valida, false de lo contrario
     */
    public static boolean isNumeric(String string) {
        return validate(string, NUMERIC_REGEXP);
    }

    /**
     * Metodo que valida si un string es un campo entero (negativo o positivo)
     *
     * @param string Cadena
     * @return true si la expresion es valida, false de lo contrario
     */
    public static boolean isInteger(String string) {
        return validate(string, INTEGER_REGEXP);
    }

    /**
     * Metodo que valida si un string es un campo entero
     *
     * @param string Cadena
     * @return true si la expresion es valida, false de lo contrario
     */
    public static boolean isIntegerPositive(String string) {
        return validate(string, INTEGER_POSITIVE_REGEXP);
    }

    /**
     * Metodo que valida si un string es un campo alfabetico con una lista
     * blanca de caracteres permitidos
     *
     * @param string Cadena
     * @return true si la expresion es valida, false de lo contrario
     */
    public static boolean isAlphabetic(String string) {
        return validate(string, ALPHABETIC_REGEXP);
    }

    /**
     * Metodo que valida si un string es un campo alfanumerico con una lista
     * blanca de caracteres permitidos
     *
     * @param string Cadena
     * @return true si la expresion es valida, false de lo contrario
     */
    public static boolean isAlphanumeric(String string) {
        return validate(string, ALPHANUMERIC_REGEXP);
    }

    /**
     * Metodo que valida si un string es un campo email
     *
     * @param string Cadena
     * @return true si la expresion es valida, false de lo contrario
     */
    public static boolean isEmail(String string) {
        return validate(string, EMAIL_REGEXP);
    }

    /**
     * Metodo que valida si un string es un campo tipo numero de cuenta con una
     * lista blanca de caracteres permitidos
     *
     * @param string Cadena
     * @return true si la expresion es valida, false de lo contrario
     */
    public static boolean isAccountNumber(String string) {
        return validate(string, ACCOUNT_NUMBER_REGEXP);
    }

    /**
     * Metodo que valida si un string es una ruta valida (PATH)
     *
     * @param string Cadena
     * @return true si la expresion es valida, false de lo contrario
     */
    public static boolean isPath(String string) {
        return validate(string, PATH_REGEXP);
    }
}
