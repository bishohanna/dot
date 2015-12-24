package com.goeuro.dot.base.model;

public interface BaseEditableModel extends BaseModel {

    State getState();

    boolean isMarkedDeleted();

    public enum State {
        ORIGINAL, UPDATE, NEW
    }
}
