package co.edu.usbcali.cloudbank.model;

import co.edu.usbcali.cloudbank.model.ConsignacionesPK;
import co.edu.usbcali.cloudbank.model.Cuentas;
import co.edu.usbcali.cloudbank.model.Usuarios;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-03-21T09:23:23")
@StaticMetamodel(Consignaciones.class)
public class Consignaciones_ { 

    public static volatile SingularAttribute<Consignaciones, String> conDescripcion;
    public static volatile SingularAttribute<Consignaciones, BigDecimal> conValor;
    public static volatile SingularAttribute<Consignaciones, Usuarios> usuCedula;
    public static volatile SingularAttribute<Consignaciones, ConsignacionesPK> consignacionesPK;
    public static volatile SingularAttribute<Consignaciones, Date> conFecha;
    public static volatile SingularAttribute<Consignaciones, Cuentas> cuentas;

}