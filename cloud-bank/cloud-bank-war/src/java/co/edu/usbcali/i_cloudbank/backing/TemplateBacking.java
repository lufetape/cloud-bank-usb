/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbcali.i_cloudbank.backing;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author rexis
 */
@ManagedBean
@RequestScoped
public class TemplateBacking extends BaseBacking implements Serializable {
    private Date fechaActual;
    private String fechaActualFormat;
    
    @PostConstruct
    public void init(){
        fechaActual = Calendar.getInstance().getTime();
        FacesContext context = FacesContext.getCurrentInstance();
        Locale locale = context.getViewRoot().getLocale();
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL,locale);
        fechaActualFormat = df.format(fechaActual);
    }

    /**
     * @return the fechaActual
     */
    public Date getFechaActual() {
        return fechaActual;
    }

    /**
     * @param fechaActual the fechaActual to set
     */
    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    /**
     * @return the fechaActualFormat
     */
    public String getFechaActualFormat() {
        return fechaActualFormat;
    }

    /**
     * @param fechaActualFormat the fechaActualFormat to set
     */
    public void setFechaActualFormat(String fechaActualFormat) {
        this.fechaActualFormat = fechaActualFormat;
    }
}
