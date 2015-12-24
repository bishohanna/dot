package com.goeuro.dot.positioneditor.position.dao.query.positionsByName;

import com.goeuro.dot.base.dao.GoeuroDAO.GoeuroRowMapper;
import com.goeuro.dot.positioneditor.position.model.Position;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PositionRawMapper implements GoeuroRowMapper<Position> {
    @Override
    public Position mapRow(ResultSet resultSet, int i) throws SQLException {

        return new Position()
                .setGoEuroId(resultSet.getLong("id"))
                .setLatitude(resultSet.getDouble("lat"))
                .setLongitude(resultSet.getDouble("lon"))
                .setName(resultSet.getString("positionName"))
                .setIconUrl("/resources/images/rmarker.png");

    }
}
