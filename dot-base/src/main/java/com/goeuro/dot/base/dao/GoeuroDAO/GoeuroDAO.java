package com.goeuro.dot.base.dao.GoeuroDAO;


import com.goeuro.dot.base.exception.DotException;
import com.goeuro.dot.base.model.BaseModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GoeuroDAO<M extends BaseModel> {


    @Autowired
    @Qualifier(value = "goeuroTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Executes Read only SQL query and converts the result set to
     *
     * @param goeuroSQLQuery
     * @return
     */
    public <Q extends GoeuroSQLQuery> List<M> searchByQuery(Q goeuroSQLQuery) {

        if (StringUtils.isBlank(goeuroSQLQuery.getQueryString()) || goeuroSQLQuery.getMapperClass() == null) {
            throw new DotException("SQL query in class [" + goeuroSQLQuery.getClass() + "] should have a valid SQL query and a mapper class");
        }


        GoeuroRowMapper<M> rowMapper = (GoeuroRowMapper<M>) applicationContext.getBean(goeuroSQLQuery.getMapperClass());

        String queryString = goeuroSQLQuery.getQueryString().concat(String.format(" limit %d,%d", goeuroSQLQuery.getIndex(), goeuroSQLQuery.getSize()));

        return jdbcTemplate.query(queryString, rowMapper);
    }

}
