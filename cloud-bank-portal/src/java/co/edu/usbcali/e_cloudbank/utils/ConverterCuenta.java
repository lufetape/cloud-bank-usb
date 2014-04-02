package co.edu.usbcali.e_cloudbank.utils;

import co.edu.usbcali.e_cloudbank.backing.BaseBacking;
import co.edu.usbcali.e_cloudbank.wsclient.CuentaDTO;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Convertidor de Clientes para componentes gráficos, donde se necesita pintar y
 * retornar el objeto completo
 *
 * @author Felipe
 */
public class ConverterCuenta extends BaseBacking implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        CuentaDTO llave = new CuentaDTO();
        try {
            llave.setNumero(submittedValue);
            List<CuentaDTO> cuenta = (List<CuentaDTO>) getValueFromSession(ObjectKeys.KEY_CUENTAS);
            int indexOf = cuenta.indexOf(llave);
            return cuenta.get(indexOf);
        } catch (NumberFormatException e) {
            return new CuentaDTO();
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null) {
            return "";
        } else {
            return String.valueOf(((CuentaDTO) value).getNumero());
        }
    }
}
