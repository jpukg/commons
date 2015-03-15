package com.samenea.commons.model.repository;

import com.samenea.commons.component.model.BasicRepository;
import com.samenea.commons.component.model.exceptions.QueryException;
import com.samenea.commons.component.model.query.Expression;
import com.samenea.commons.component.model.query.Paging;
import com.samenea.commons.component.model.query.enums.SortOrder;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: jalal <a href="mailto:jalal.ashrafi@gmail.com">Jalal Ashrafi</a>
 * Date: 6/13/12
 * Time: 1:45 PM
 */
@Repository
public class BasicRepositoryJpa<T, PK extends Serializable> implements BasicRepository<T, PK> {
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log log = LogFactory.getLog(getClass());

//    public static final String PERSISTENCE_UNIT_NAME = "ApplicationEntityManager";

    private BasicRepositoryJpa() {
    }

    /**
     * Entity manager, injected by Spring using @PersistenceContext annotation on setEntityManager()
     */

    private EntityManager entityManager;
    private Class<T> persistentClass;

    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing or using dependency injection.
     *
     * @param persistentClass the class type you'd like to persist
     */
    public BasicRepositoryJpa(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }


    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext(unitName = "ApplicationEntityManager")
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing or using dependency injection.
     *
     * @param persistentClass the class type you'd like to persist
     * @param entityManager   the configured EntityManager for JPA implementation.
     */
    public BasicRepositoryJpa(final Class<T> persistentClass, EntityManager entityManager) {
        this.persistentClass = persistentClass;
        this.entityManager = entityManager;
    }

    public List<T> getAll() {
        return this.getEntityManager().createQuery(
                "select obj from " + this.persistentClass.getName() + " obj")
                .getResultList();
    }

    public List<T> getAllDistinct() {
        return this.getEntityManager().createQuery(
                "select distinct obj from " + this.persistentClass.getName() + " obj")
                .getResultList();
    }

    public T get(PK id) {
        T entity = this.getEntityManager().find(this.persistentClass, id);

        if (entity == null) {
            String msg = "Uh oh, '" + this.persistentClass + "' object with id '" + id + "' not found...";
            log.warn(msg);
            throw new EntityNotFoundException(msg);
        }

        return entity;
    }

    public boolean exists(PK id) {
        T entity = this.getEntityManager().find(this.persistentClass, id);
        return entity != null;
    }

    public T store(T object) {
        return this.getEntityManager().merge(object);
    }

    public void remove(PK id) {
        getEntityManager().remove(this.get(id));
    }

    public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams) {
        TypedQuery<T> namedQuery = this.getEntityManager().createNamedQuery(queryName, persistentClass);
        for (Map.Entry<String, Object> stringObjectEntry : queryParams.entrySet()) {
            namedQuery.setParameter(stringObjectEntry.getKey(), stringObjectEntry.getValue());
        }
        return namedQuery.getResultList();

    }

    public List<T> findByNamedQuery(String queryName) {
        TypedQuery<T> namedQuery = this.getEntityManager().createNamedQuery(queryName, persistentClass);
        return namedQuery.getResultList();

    }
    public long getNumberOfModelsByExample(T example, List<Expression> expressions) {
        Criteria criteria = prepareCriteriaByExample(example, expressions);
        criteria.setProjection(Projections.rowCount());
        return Long.valueOf((Long) criteria.uniqueResult());
    }

    protected final Criteria createCriteriaForTable(T e, Paging paging) {
        Criteria criteria = prepareCriteriaByExample(e);
        if(paging==null){
            return criteria;
        }
        if (StringUtils.isNotBlank(paging.getSortField())) {
            if (SortOrder.DESCENDING.equals(paging.getSortOrder())) {
                criteria.addOrder(Order.desc(paging.getSortField()));
            } else if (SortOrder.ASCENDING.equals(paging.getSortOrder())) {
                criteria.addOrder(Order.asc(paging.getSortField()));
            }
        }
        if (paging.getPageSize() > 0) {
            criteria.setMaxResults(paging.getPageSize());
            criteria.setFetchSize(paging.getPageSize());
        }
        criteria.setFirstResult(paging.getFirst());
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria;
    }

    /**
     * @param example
     * @param expressions
     * @param paging
     * @return
     */
    protected final Criteria createCriteriaForTable(T example, List<Expression> expressions, Paging paging) {
        Criteria criteria = createCriteriaForTable(example, paging);
        if (!CollectionUtils.isEmpty(expressions)) {
            for (Expression expression : expressions) {
                restrictCriteria(criteria, expression);
            }
        }

        return criteria;
    }

    /**
     * @param expressions
     * @param paging
     * @return
     */
    protected final Criteria createCriteriaForTable(List<Expression> expressions, Paging paging) {
        return createCriteriaForTable(null, expressions, paging);
    }

    /**
     * @param exampleE
     * @return
     */

    protected final Criteria prepareCriteriaByExample(T exampleE) {
        Session delegate = (Session) entityManager.getDelegate();
        Criteria criteria = delegate.createCriteria(persistentClass);
        if (exampleE != null) {
            criteria.add(createExample(exampleE));
        }
        return criteria;
    }

    /**
     * @param exampleE
     * @param expressions
     * @return
     */
    protected final Criteria prepareCriteriaByExample(T exampleE, List<Expression> expressions) {
        Criteria criteria = prepareCriteriaByExample(exampleE);
        if (!CollectionUtils.isEmpty(expressions)) {
            for (Expression expression : expressions) {
                restrictCriteria(criteria, expression);
            }
        }

        return criteria;
    }

    /**
     * @param exampleEntity
     * @return
     */
    public Example createExample(T exampleEntity) {
        return Example.create(exampleEntity)
                .ignoreCase()
                .excludeZeroes()
                .excludeProperty("id")
                .excludeProperty("version")
                .excludeZeroes()
                .enableLike(MatchMode.ANYWHERE);
    }

    /**
     * @param e
     * @return
     */

    public long getNumberOfModelsByExample(T e) {
        Criteria criteria = prepareCriteriaByExample(e);
        criteria.setProjection(Projections.rowCount());
        return Long.valueOf((Long) criteria.uniqueResult());
    }

    /**
     * @param example
     * @param paging
     * @return
     */
    public List<T> findModelByExample(T example, Paging paging) {
        Criteria criteria = createCriteriaForTable(example, paging);
        return getFinderResult(criteria);
    }

    /**
     * @param example
     * @param expressions
     * @param paging
     * @return
     */
    public List<T> findModelByExample(T example, List<Expression> expressions, Paging paging) {
        Criteria criteria = createCriteriaForTable(example, expressions, paging);
        return getFinderResult(criteria);
    }

    @Override
    public List<T> findModelByExample(T example) {
        return findModelByExample(example, Paging.NON_PAGING);
    }

    @Override
    public List<T> findModelByExample(T example, List<Expression> ranges) {
        return findModelByExample(example, ranges, Paging.NON_PAGING);
    }

    /**
     * @param criteria
     * @return
     */
    final private List<T> getFinderResult(Criteria criteria) {
        List<T> pojos = new ArrayList<T>();
        for (Object o : criteria.list()) {
            if (persistentClass.isInstance(o) ) {
                pojos.add((T) o);
            } else if (o instanceof List) {
                for (Object anObject : (List) o) {
                    if (persistentClass.isInstance(anObject)) {
                        pojos.add((T) anObject);
                    }
                }
            }
        }
        return pojos;
    }

    @Override
    public List<T> findModelByExpression(List<Expression> ranges, Paging paging) {
        return findModelByExample(null, ranges, paging);
    }

    @Override
    public List<T> findModelByExpression(List<Expression> ranges) {
        return findModelByExample(null, ranges);
    }

    @Override
    public long getNumberOfModelsByExpression(List<Expression> ranges) {
        return getNumberOfModelsByExample(null, ranges);
    }

    protected final Criteria restrictCriteria(Criteria criteria, com.samenea.commons.component.model.query.Expression expression) {
        Criterion criterion = null;

        switch (expression.getOperator()) {
            case GT:
                criterion = Restrictions.gt(expression.getFieldName(), expression.getParams().get(0));
                break;
            case EQ:
                criterion = Restrictions.eq(expression.getFieldName(), expression.getParams().get(0));
                break;
            case NE:
                criterion = Restrictions.ne(expression.getFieldName(), expression.getParams().get(0));
                break;
            case LE:
                criterion = Restrictions.le(expression.getFieldName(), expression.getParams().get(0));
                break;
            case LT:
                criterion = Restrictions.lt(expression.getFieldName(), expression.getParams().get(0));
                break;
            case GE:
                criterion = Restrictions.ge(expression.getFieldName(), expression.getParams().get(0));
                break;
            case I_LIKE:
                criterion = Restrictions.ilike(expression.getFieldName(), expression.getParams().get(0).toString(), MatchMode.ANYWHERE);
                break;
            case LIKE:
                criterion = Restrictions.like(expression.getFieldName(), expression.getParams().get(0).toString(), MatchMode.ANYWHERE);
                break;
            case IS_NULL:
                criterion = Restrictions.isNull(expression.getFieldName());
                break;
            case IS_NOT_NULL:
                criterion = Restrictions.isNotNull(expression.getFieldName());
                break;
            case IS_NOT_EMPTY:
                criterion = Restrictions.isNotEmpty(expression.getFieldName());
                break;
            case IS_EMPTY:
                criterion = Restrictions.isEmpty(expression.getFieldName());
                break;
            case SIZE_EQ:
                criterion = Restrictions.sizeEq(expression.getFieldName(),Integer.valueOf(expression.getParams().get(0).toString()));
                break;
            case SIZE_GE:
                criterion = Restrictions.sizeGe(expression.getFieldName(),Integer.valueOf(expression.getParams().get(0).toString()));
                break;
            case SIZE_GT:
                criterion = Restrictions.sizeGt(expression.getFieldName(),Integer.valueOf(expression.getParams().get(0).toString()));
                break;
            case SIZE_LE:
                criterion = Restrictions.sizeLe(expression.getFieldName(),Integer.valueOf(expression.getParams().get(0).toString()));
                break;
            case SIZE_LT:
                criterion = Restrictions.sizeLt(expression.getFieldName(),Integer.valueOf(expression.getParams().get(0).toString()));
                break;
            case SIZE_NE:
                criterion = Restrictions.sizeNe(expression.getFieldName(),Integer.valueOf(expression.getParams().get(0).toString()));
                break;
            case BETWEEN:
                criterion = Restrictions.between(expression.getFieldName(), expression.getParams().get(0), expression.getParams().get(1));
                break;
            default:
                throw new QueryException(expression.getOperator() + " is not allowed");
        }
        return criteria.add(criterion);
    }
}
