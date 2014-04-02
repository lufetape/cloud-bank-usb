/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.usbcali.cloudbank.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utilidades de fechas
 * @author Felipe
 */
public class UtilDate implements Serializable {
    
    public static String getFormattedDate(Date date, String format){
        SimpleDateFormat sdf=new java.text.SimpleDateFormat(format);
        return sdf.format(date);
    }
    
    public static Date getDate(String dateString, String format) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.parse(dateString);
    }
}
