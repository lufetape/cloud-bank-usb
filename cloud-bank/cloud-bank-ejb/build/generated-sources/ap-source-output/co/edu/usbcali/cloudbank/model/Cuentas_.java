package co.edu.usbcali.cloudbank.model;

import co.edu.usbcali.cloudbank.model.Clientes;
import co.edu.usbcali.cloudbank.model.Consignaciones;
import co.edu.usbcali.cloudbank.model.Retiros;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-03-21T09:23:23")
@StaticMetamodel(Cuentas.class)
public class Cuentas_ { 

    public static volatile SingularAttribute<Cuentas, String> cueNumero;
    public static volatile CollectionAttribute<Cuentas, Consignaciones> consignacionesCollection;
    public static volatile CollectionAttribute<Cuentas, Retiros> retirosCollection;
    public static volatile SingularAttribute<Cuentas, Clientes> cliId;
    public static volatile SingularAttribute<Cuentas, BigDecimal> cueSaldo;
    public static volatile SingularAttribute<Cuentas, String> cueActiva;
    public static volatile SingularAttribute<Cuentas, String> cueClave;

}