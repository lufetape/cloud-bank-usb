package co.edu.usbcali.i_cloudbank.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * Clase para almacenar datos básicos de temas para la GUI
 * @author rexis
 */
public class TemaDTO implements Serializable {

    private String id;
    private String nombre;
    private String imagen;

    public TemaDTO() {
    }
    
    public TemaDTO(String id) {
        this.id = id;
    }

    public TemaDTO(String id, String nombre, String imagen) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final TemaDTO other = (TemaDTO) obj;
        return Objects.equals(this.id, other.id);
    }    
}
