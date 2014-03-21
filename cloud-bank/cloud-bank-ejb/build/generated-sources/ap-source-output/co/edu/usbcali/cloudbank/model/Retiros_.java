package co.edu.usbcali.cloudbank.model;

import co.edu.usbcali.cloudbank.model.Cuentas;
import co.edu.usbcali.cloudbank.model.RetirosPK;
import co.edu.usbcali.cloudbank.model.Usuarios;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-03-21T09:23:23")
@StaticMetamodel(Retiros.class)
public class Retiros_ { 

    public static volatile SingularAttribute<Retiros, Usuarios> usuCedula;
    public static volatile SingularAttribute<Retiros, BigDecimal> retValor;
    public static volatile SingularAttribute<Retiros, Cuentas> cuentas;
    public static volatile SingularAttribute<Retiros, String> retDescripcion;
    public static volatile SingularAttribute<Retiros, Date> retFecha;
    public static volatile SingularAttribute<Retiros, RetirosPK> retirosPK;

}