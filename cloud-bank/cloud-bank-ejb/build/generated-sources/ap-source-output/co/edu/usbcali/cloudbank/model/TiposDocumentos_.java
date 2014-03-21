package co.edu.usbcali.cloudbank.model;

import co.edu.usbcali.cloudbank.model.Clientes;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-03-21T09:23:23")
@StaticMetamodel(TiposDocumentos.class)
public class TiposDocumentos_ { 

    public static volatile SingularAttribute<TiposDocumentos, String> tdocNombre;
    public static volatile SingularAttribute<TiposDocumentos, Long> tdocCodigo;
    public static volatile CollectionAttribute<TiposDocumentos, Clientes> clientesCollection;

}