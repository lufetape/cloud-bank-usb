package co.edu.usbcali.cloudbank.dao.impl;

import co.edu.usbcali.cloudbank.dao.IBaseDAO;
import co.edu.usbcali.cloudbank.util.ResourceBundles;
import co.edu.usbcali.cloudbank.util.UtilBundle;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase que implementa el acceso a datos mediante la especificación JPA
 *
 * @author Felipe
 * @param <T> Entidad de la base de datos
 */
public class BaseJpaDAO<T> implements IBaseDAO<T> {
    
    private static final Logger logger = LogManager.getLogger(BaseJpaDAO.class);

    /**
     * Clase de la Entidad de la base de datos
     */
    private final Class<T> entityClass;

    /**
     * Manejador de persistencia global o base (que podrá ser sobre-escrito por
     * los hijos de la clase)
     */
    @PersistenceContext(unitName = "cloud-bank-ejb-PU")
    private EntityManager entityManager;

    /**
     * Mapa de parámetros que manejarán las consultas (queries y named queries)
     */
    protected HashMap<String, Object> parametros;

    /**
     * Variable que contiene el texto de una consulta (query o named query)
     */
    protected String query;

    /**
     * Inicializa los parámetros y la clase de la entidad
     *
     * @param entityClass
     */
    public BaseJpaDAO(Class<T> entityClass) {
        parametros = new HashMap<>();
        this.entityClass = entityClass;
    }

    /**
     * Método que obtiene el manejador de persistencia que se encuentre
     * instanciado
     *
     * @return the entityManager
     */
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Método que asigna un manejador de persistencia
     *
     * @param entityManager Manejador de persistencia
     */
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public T create(T entity) {
        getEntityManager().persist(entity);
        return entity;
    }

    @Override
    public void modify(T entity) {
        getEntityManager().merge(entity);
    }

    @Override
    public void remove(T entity, Object primaryKey) throws IllegalArgumentException {
        Object entityRemove = getEntityManager().find(entity.getClass(), primaryKey);
        if (entityRemove != null) {
            getEntityManager().remove(entityRemove);
        } else {
            throw new IllegalArgumentException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "entidadNoEncontrada"));
        }
    }

    @Override
    public void refresh(T entity) {
        getEntityManager().refresh(entity);
    }

    @Override
    public void flush() {
        getEntityManager().flush();
    }

    @Override
    public int count() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        Query countQuery = getEntityManager().createQuery(cq);
        return ((Long) countQuery.getSingleResult()).intValue();
    }

    @Override
    public T findById(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    @Override
    public List<T> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        Query findAllQuery = getEntityManager().createQuery(cq);
        return findAllQuery.getResultList();
    }

    @Override
    public List<T> findRange(int[] range) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        Query findRangeQuery = getEntityManager().createQuery(cq);
        findRangeQuery.setMaxResults(range[1] - range[0] + 1);
        findRangeQuery.setFirstResult(range[0]);
        return findRangeQuery.getResultList();
    }

    @Override
    public List<T> find() {
        Query findQuery = getEntityManager().createQuery(query);

        for (String parameterName : parametros.keySet()) {
            findQuery.setParameter(parameterName, parametros.get(parameterName));
        }

        parametros = new HashMap<>();
        return findQuery.getResultList();
    }

    @Override
    public List<T> findNamedQuery() {
        Query namedQueryFind = getEntityManager().createNamedQuery(query);

        for (String parameterName : parametros.keySet()) {
            namedQueryFind.setParameter(parameterName, parametros.get(parameterName));
        }

        parametros = new HashMap<>();
        return namedQueryFind.getResultList();
    }
}
