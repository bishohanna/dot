package com.goeuro.dot.positioneditor.position.dao.query.positionsByName;

import com.goeuro.dot.base.dao.GoeuroDAO.GoeuroRowMapper;
import com.goeuro.dot.base.dao.GoeuroDAO.GoeuroSQLQuery;
import com.goeuro.dot.positioneditor.position.model.Position;
import org.apache.commons.lang3.StringUtils;

public class FindPositionsByName implements GoeuroSQLQuery<Position> {

    private String positionName;
    private int index = 0;
    private int size = 1500;//default to 5000 positions


    @Override
    public String getQueryString() {
        return String.format("SELECT position_id id,X(latlon) lat, Y(latlon) lon, name positionName FROM position WHERE name like %s",
                "'".concat(getPositionName()).concat("%'"));
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public int getSize() {
        return size;
    }


    @Override
    public Class<? extends GoeuroRowMapper<Position>> getMapperClass() {
        return PositionRawMapper.class;
    }

    public FindPositionsByName setPositionName(String positionName) {
        this.positionName = positionName;

        return this;
    }

    public FindPositionsByName setIndex(int index) {
        this.index = index;

        return this;
    }

    public FindPositionsByName setSize(int size) {
        this.size = size;

        return this;
    }

    private String getPositionName() {
        return StringUtils.isBlank(positionName) ? "" : positionName;
    }
}
