package com.samenea.commons.model.repository;

import com.samenea.commons.component.model.BasicRepository;
import com.samenea.commons.component.model.exceptions.*;
import com.samenea.commons.component.model.query.*;
import com.samenea.commons.component.model.query.enums.SortOrder;
import com.samenea.commons.component.utils.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.*;

/**
 * @author: jalal <a href="mailto:jalal.ashrafi@gmail.com">Jalal Ashrafi</a>
 * Date: 6/13/12
 * Time: 1:45 PM
 */
@Repository
public class BasicRepositoryHibernate<T, PK extends Serializable> implements BasicRepository<T, PK> {
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log log = LogFactory.getLog(getClass());
    private Class<T> persistentClass;
   // @Resource
    private SessionFactory sessionFactory;

    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     *
     * @param persistentClass the class type you'd like to persist
     */
    public BasicRepositoryHibernate(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    /**
     * Constructor that takes in a class and sessionFactory for easy creation of DAO.
     *
     * @param persistentClass the class type you'd like to persist
     * @param sessionFactory  the pre-configured Hibernate SessionFactory
     */
    public BasicRepositoryHibernate(final Class<T> persistentClass, SessionFactory sessionFactory) {
        this.persistentClass = persistentClass;
        this.sessionFactory = sessionFactory;
    }

    private BasicRepositoryHibernate() {
    }

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    @Autowired
//    @Required
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        Session sess = getSession();
        return sess.createCriteria(persistentClass).list();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> getAllDistinct() {
        Collection result = new LinkedHashSet(getAll());
        return new ArrayList(result);
    }


    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T get(PK id) {
        Object entity = getSession().get(persistentClass, id);

        if (entity == null) {
            log.warn("Uh oh, '" + this.persistentClass + "' object with id '" + id + "' not found...");
            throw new ObjectRetrievalFailureException(this.persistentClass, id);
        }

        return (T) entity;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public boolean exists(PK id) {
        Object entity = getSession().get(persistentClass, id);
        return entity != null;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T store(T object) {
        Session sess = getSession();
        return (T) sess.merge(object);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(T object) {
        Session sess = getSession();
        sess.delete(object);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(PK id) {
        Session sess = getSession();
        Object entity = sess.get(persistentClass, id);
        sess.delete(entity);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams) {
        Session sess = getSession();
        Query namedQuery = sess.getNamedQuery(queryName);

        for (String s : queryParams.keySet()) {
            namedQuery.setParameter(s, queryParams.get(s));
        }

        return namedQuery.list();
    }

    @Override
    public List<T> findByNamedQuery(String queryName) {
        return findByNamedQuery(queryName,new HashMap<String, Object>());
    }


    public long getNumberOfModelsByExample(T example, List<com.samenea.commons.component.model.query.Expression> expressions) {
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
    protected final Criteria createCriteriaForTable(T example, List<com.samenea.commons.component.model.query.Expression> expressions, Paging paging) {
        Criteria criteria = createCriteriaForTable(example, paging);
        if (!CollectionUtils.isEmpty(expressions)) {
            for (com.samenea.commons.component.model.query.Expression expression : expressions) {
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
    protected final Criteria createCriteriaForTable(List<com.samenea.commons.component.model.query.Expression> expressions, Paging paging) {
        return createCriteriaForTable(null, expressions, paging);
    }

    /**
     * @param exampleE
     * @return
     */

    protected final Criteria prepareCriteriaByExample(T exampleE) {
        Session delegate = getSession();
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
    protected final Criteria prepareCriteriaByExample(T exampleE, List<com.samenea.commons.component.model.query.Expression> expressions) {
        Criteria criteria = prepareCriteriaByExample(exampleE);
        if (!CollectionUtils.isEmpty(expressions)) {
            for (com.samenea.commons.component.model.query.Expression expression : expressions) {
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
    public List<T> findModelByExample(T example, List<com.samenea.commons.component.model.query.Expression> expressions, Paging paging) {
        Criteria criteria = createCriteriaForTable(example, expressions, paging);
        return getFinderResult(criteria);
    }

    @Override
    public List<T> findModelByExample(T example) {
        return findModelByExample(example, Paging.NON_PAGING);
    }

    @Override
    public List<T> findModelByExample(T example, List<com.samenea.commons.component.model.query.Expression> ranges) {
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
    public List<T> findModelByExpression(List<com.samenea.commons.component.model.query.Expression> ranges, Paging paging) {
        return findModelByExample(null, ranges, paging);
    }

    @Override
    public List<T> findModelByExpression(List<com.samenea.commons.component.model.query.Expression> ranges) {
        return findModelByExample(null, ranges);
    }

    @Override
    public long getNumberOfModelsByExpression(List<com.samenea.commons.component.model.query.Expression> ranges) {
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
                throw new com.samenea.commons.component.model.exceptions.QueryException(expression.getOperator() + " is not allowed");
        }
        return criteria.add(criterion);
    }
    protected Session getSession() throws HibernateException {
        Session sess = getSessionFactory().getCurrentSession();
        if (sess == null) {
            sess = getSessionFactory().openSession();
        }
        return sess;
    }

}
