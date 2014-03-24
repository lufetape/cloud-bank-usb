package co.edu.usbcali.i_cloudbank.dto;

import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;

/**
 * Clase para manejar los datos básicos de los idiomas de la GUI
 * @author rexis
 */
public class IdiomaDTO implements Serializable {

    private String id;
    private Locale locale;
    private String nombre;
    private String imagen;

    public IdiomaDTO() {
    }

    public IdiomaDTO(String id, Locale locale, String nombre, String imagen) {
        this.id = id;
        this.locale = locale;
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return id;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * @param locale the locale to set
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IdiomaDTO other = (IdiomaDTO) obj;
        return Objects.equals(this.id, other.id);
    }
}
