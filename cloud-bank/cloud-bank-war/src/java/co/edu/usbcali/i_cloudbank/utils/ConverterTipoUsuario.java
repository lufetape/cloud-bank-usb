package co.edu.usbcali.i_cloudbank.utils;

import co.edu.usbcali.cloudbank.model.TiposUsuarios;
import co.edu.usbcali.i_cloudbank.backing.BaseBacking;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Convertidor de Tipos de Usuarios para componentes gráficos, donde se necesita pintar y
 * retornar el objeto completo
 *
 * @author Felipe
 */
public class ConverterTipoUsuario extends BaseBacking implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        TiposUsuarios llave = new TiposUsuarios();
        try {
            llave.setTusuCodigo(Long.parseLong(submittedValue));
            List<TiposUsuarios> tiposUsuarios = (List<TiposUsuarios>) getValueFromSession(ObjectKeys.KEY_TIPOS_USUARIOS);
            int indexOf = tiposUsuarios.indexOf(llave);
            return tiposUsuarios.get(indexOf);
        } catch (NumberFormatException e) {
            return new TiposUsuarios();
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null) {
            return "";
        } else {
            return String.valueOf(((TiposUsuarios) value).getTusuCodigo());
        }
    }
}
