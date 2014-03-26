package co.edu.usbcali.i_cloudbank.utils;

import co.edu.usbcali.cloudbank.model.TiposDocumentos;
import co.edu.usbcali.i_cloudbank.backing.BaseBacking;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Convertidor de Tipos de Documentos para componentes gráficos, donde se
 * necesita pintar y retornar el objeto completo
 *
 * @author Felipe
 */
public class ConverterTipoDocumento extends BaseBacking implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        TiposDocumentos llave = new TiposDocumentos();
        try {
            llave.setTdocCodigo(Long.parseLong(submittedValue));
            List<TiposDocumentos> tiposDocumentos = (List<TiposDocumentos>) getValueFromSession(ObjectKeys.KEY_TIPOS_DOCUMENTOS);
            int indexOf = tiposDocumentos.indexOf(llave);
            return tiposDocumentos.get(indexOf);
        } catch (NumberFormatException e) {
            return new TiposDocumentos();
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null) {
            return "";
        } else {
            return String.valueOf(((TiposDocumentos) value).getTdocCodigo());
        }
    }
}
