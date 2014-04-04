package co.edu.usbcali.i_cloudbank.utils;

/**
 * Clase que maneja los Resources Bundles de la aplicación web
 *
 * @author Felipe
 */
public class ResourceBundles {

    public static enum RB_MENSAJES {

        COMUN("co.edu.usbcali.i_cloudbank.bundles.ComunBundle"),
        LOGIN("co.edu.usbcali.i_cloudbank.bundles.LoginBundle"),
        MENU("co.edu.usbcali.i_cloudbank.bundles.MenuBundle"),
        TIPO_DOCUMENTO("co.edu.usbcali.i_cloudbank.bundles.TipoDocumentoBundle"),
        TIPO_USUARIO("co.edu.usbcali.i_cloudbank.bundles.TipoUsuarioBundle"),
        USUARIO("co.edu.usbcali.i_cloudbank.bundles.UsuarioBundle"),
        CLIENTE("co.edu.usbcali.i_cloudbank.bundles.ClienteBundle"),
        CUENTA("co.edu.usbcali.i_cloudbank.bundles.CuentaBundle"),
        CONSGNACION("co.edu.usbcali.i_cloudbank.bundles.ConsignacionBundle"),
        RETIRO("co.edu.usbcali.i_cloudbank.bundles.RetiroBundle"),
        TRANSFERENCIA("co.edu.usbcali.i_cloudbank.bundles.TransferenciaBundle");
        
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
