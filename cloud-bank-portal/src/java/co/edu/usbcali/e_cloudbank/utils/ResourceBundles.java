package co.edu.usbcali.e_cloudbank.utils;

/**
 * Clase que maneja los Resources Bundles de la aplicación web
 *
 * @author Felipe
 */
public class ResourceBundles {

    public static enum RB_MENSAJES {

        COMUN("co.edu.usbcali.e_cloudbank.bundles.ComunBundle"),
        MENU("co.edu.usbcali.e_cloudbank.bundles.MenuBundle"),
        CUENTA("co.edu.usbcali.e_cloudbank.bundles.CuentaBundle"),
        TRANSFERENCIA("co.edu.usbcali.e_cloudbank.bundles.TransferenciaBundle"),
        WS_CLIENT("co.edu.usbcali.e_cloudbank.bundles.WSClientBundle");
        
        private final String bundleName;

        RB_MENSAJES(String bundleName) {
            this.bundleName = bundleName;
        }

        @Override
        public String toString() {
            return bundleName;
        }
    }
}
