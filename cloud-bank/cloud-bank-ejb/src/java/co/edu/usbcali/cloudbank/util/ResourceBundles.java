package co.edu.usbcali.cloudbank.util;

/**
 * Clase que maneja los Resources Bundles del ejb
 *
 * @author Felipe
 */
public class ResourceBundles {

    public static enum RB_MENSAJES {

        COMUN("co.edu.usbcali.cloudbank.bundles.ComunBundle"),
        CLIENTE("co.edu.usbcali.cloudbank.bundles.ClienteBundle"),
        USUARIO("co.edu.usbcali.cloudbank.bundles.UsuarioBundle"),
        CUENTA("co.edu.usbcali.cloudbank.bundles.CuentaBundle"),
        TIPO_DOCUMENTO("co.edu.usbcali.cloudbank.bundles.TipoDocumentoBundle"),
        TIPO_USUARIO("co.edu.usbcali.cloudbank.bundles.TipoUsuarioBundle");
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
