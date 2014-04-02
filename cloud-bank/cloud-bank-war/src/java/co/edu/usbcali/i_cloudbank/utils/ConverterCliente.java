package co.edu.usbcali.i_cloudbank.utils;

import co.edu.usbcali.cloudbank.model.Clientes;
import co.edu.usbcali.cloudbank.model.TiposUsuarios;
import co.edu.usbcali.i_cloudbank.backing.BaseBacking;
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
public class ConverterCliente extends BaseBacking implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        Clientes llave = new Clientes();
        try {
            llave.setCliId(Long.parseLong(submittedValue));
            List<Clientes> clientes = (List<Clientes>) getValueFromSession(ObjectKeys.KEY_CLIENTES);
            int indexOf = clientes.indexOf(llave);
            return clientes.get(indexOf);
        } catch (NumberFormatException e) {
            return new Clientes();
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null) {
            return "";
        } else {
            return String.valueOf(((Clientes) value).getCliId());
        }
    }
}
