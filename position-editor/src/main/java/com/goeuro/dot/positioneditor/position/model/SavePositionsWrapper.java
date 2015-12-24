package com.goeuro.dot.positioneditor.position.model;

import com.goeuro.dot.positioneditor.branch.model.Branch;

import java.io.Serializable;
import java.util.List;

public class SavePositionsWrapper implements Serializable {

    private List<Position> positions;
    private Branch branch;


    public List<Position> getPositions() {
        return positions;
    }

    public SavePositionsWrapper setPositions(List<Position> positions) {
        this.positions = positions;

        return this;
    }

    public Branch getBranch() {
        return branch;
    }

    public SavePositionsWrapper setBranch(Branch branch) {
        this.branch = branch;

        return this;
    }
}
