package com.goeuro.dot.base.dao.GoeuroDAO;

import com.goeuro.dot.base.model.BaseModel;
import org.springframework.jdbc.core.RowMapper;

public interface GoeuroRowMapper<M extends BaseModel> extends RowMapper<M> {
}
