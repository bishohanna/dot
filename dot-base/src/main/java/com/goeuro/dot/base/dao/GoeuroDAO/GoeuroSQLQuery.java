package com.goeuro.dot.base.dao.GoeuroDAO;

import com.goeuro.dot.base.model.BaseModel;

public interface GoeuroSQLQuery<M extends BaseModel> {

    int getIndex();

    int getSize();

    String getQueryString();

    Class<? extends GoeuroRowMapper<M>> getMapperClass();
}
