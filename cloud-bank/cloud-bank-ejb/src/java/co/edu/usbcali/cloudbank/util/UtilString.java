package co.edu.usbcali.cloudbank.util;

/**
 * Clase utilitaria para hacer operaciones sobre strings
 *
 * @author Felipe
 */
public class UtilString {

    /**
     * Metodo que rellena una cadena con N caracteres a la izquierda
     *
     * @param cadena
     * @param posiciones Número de caracteres a rellenar
     * @param pad Caracter de relleno
     * @return Cadena transformada
     */
    public static String lpad(String cadena, int posiciones, String pad) {
        while (cadena.length() < posiciones) {
            cadena = pad + cadena;
        }
        return cadena;
    }

    /**
     * Metodo que rellena una cadena con N caracteres a la derecha
     *
     * @param cadena
     * @param posiciones Número de caracteres a rellenar
     * @param pad Caracter de relleno
     * @return Cadena transformada
     */
    public static String rpad(String cadena, int posiciones, String pad) {
        while (cadena.length() < posiciones) {
            cadena = cadena + pad;
        }
        return cadena;
    }
}
