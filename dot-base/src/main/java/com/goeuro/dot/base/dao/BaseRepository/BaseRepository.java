package com.goeuro.dot.base.dao.BaseRepository;

import com.goeuro.dot.base.dao.GoeuroDAO.GoeuroDAO;
import com.goeuro.dot.base.dao.GoeuroDAO.GoeuroSQLQuery;
import com.goeuro.dot.base.exception.DotException;
import com.goeuro.dot.base.model.BaseModel;
import com.goeuro.dot.base.model.PagedResponse;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * Base repository
 *
 * @param <M>
 */
public abstract class BaseRepository<M extends BaseModel> {


    @PersistenceContext
    protected EntityManager entityManager;
    @Autowired
    private GoeuroDAO<M> goeuroDAO;

    private final Class<M> modelClass;
    private final ModelMerger<M> modelMerger;

    public BaseRepository(Class<M> modelClass, ModelMerger<M> modelMerger) {
        this.modelClass = modelClass;
        this.modelMerger = modelMerger;
    }

    /**
     * Interface to manipulate the search criteria
     */
    @FunctionalInterface
    public interface SearchCriteria {

        void addCriteria(Criteria criteria);
    }

    /**
     * Executes read only query against Goeuro Database
     *
     * @param goeuroSQLQuery SQL query to be executed
     * @return list of transformed models
     */
    @Transactional(readOnly = true)
    public List<M> executeGoeuroQuery(GoeuroSQLQuery goeuroSQLQuery) {
        return goeuroDAO.searchByQuery(goeuroSQLQuery);
    }

    @Transactional(readOnly = true)
    public Optional<M> findById(Long id) {
        M model = entityManager.find(modelClass, id);

        if (model == null)
            return Optional.empty();

        doAfterLoad(model);

        return Optional.of(model);
    }

    /**
     * Saves or updates a model
     * If the model is not existing in the database , it is directly saved
     * If the model exists , it will be loaded from database , merged from the incoming model, then updated
     *
     * @param model model
     * @return updated model
     */
    public Optional<M> saveOrUpdate(M model) {
        try {
            if (model == null)
                throw new DotException("Can't save or update a null reference !");

            //Model already exists in database and this is an update
            if (model.getId() != null) {
                Optional<M> existingModel = findById(model.getId());
                if (existingModel.isPresent()) {
                    modelMerger.merge(model, existingModel.get());

                    return Optional.of(entityManager.merge(existingModel.get()));
                } else {
                    return Optional.empty();
                }
            }

            //model doesn't pre-exist and this is a new insert
            else {
                return Optional.of(entityManager.merge(model));
            }
        } catch (PersistenceException ex) {
            throw new DotException("Trying to add duplicate data !");
        }

    }

    /**
     * Searches by any-criteria query
     *
     * @param searchCriteria search criteria
     * @return list of found models
     */
    @Transactional(readOnly = true)
    protected List<M> searchByQuery(SearchCriteria searchCriteria) {
        //create criteria
        Criteria criteria = getSession().createCriteria(modelClass);
        //add criteria params
        searchCriteria.addCriteria(criteria);
        //get result
        List<M> models = criteria.list();
        //do after load each model
        models.parallelStream().forEach(m -> doAfterLoad(m));

        return models;
    }

    /**
     * Searches by any-criteria query with pagination
     *
     * @param searchCriteria search criteria
     * @return list of found models
     */
    @Transactional(readOnly = true)
    protected PagedResponse<M> searchByQueryPaged(SearchCriteria searchCriteria, int pageIndex, int pageSize) {
        //create dataCriteria
        Criteria dataCriteria = getSession().createCriteria(modelClass);
        //add dataCriteria params
        searchCriteria.addCriteria(dataCriteria);
        dataCriteria.setFirstResult(pageIndex * pageSize).setMaxResults(pageSize);
        //get result
        List<M> models = dataCriteria.list();
        //do after load each model
        models.parallelStream().forEach(m -> doAfterLoad(m));

        //create countCriteria
        Criteria countCriteria = getSession().createCriteria(modelClass);
        //add countCriteria params
        searchCriteria.addCriteria(countCriteria);
        Long totalCount = (Long) countCriteria.setProjection(Projections.rowCount()).uniqueResult();


        return new PagedResponse().setItems(models).setPageIndex(pageIndex).setPageSize(pageSize).setTotalItems(totalCount);
    }

    public boolean delete(M model) {
        return delete(model.getId());
    }

    public boolean delete(Long id) {
        if (id != null) {
            M reference = entityManager.getReference(modelClass, id);
            entityManager.remove(reference);
            return true;
        }
        return false;
    }

    //********************
    protected void doAfterLoad(M model) {

    }

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }


}
