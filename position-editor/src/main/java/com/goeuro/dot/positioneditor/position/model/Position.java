package com.goeuro.dot.positioneditor.position.model;

import com.goeuro.dot.base.model.BaseEditableModel;
import com.goeuro.dot.base.model.BaseModel;
import com.goeuro.dot.positioneditor.branch.model.Branch;

import javax.persistence.*;

@Entity
@Table(name = "position", uniqueConstraints = @UniqueConstraint(columnNames = {"branch_id", "goEuroId"}))
public class Position implements BaseEditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long goEuroId;
    private String name;
    private Double latitude;
    private Double longitude;
    private State state = State.ORIGINAL;
    private boolean markedDeleted;

    @ManyToOne(targetEntity = Branch.class)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    //Transient fields

    @Transient
    private String iconUrl;
    @Transient
    private boolean selected;

    @Override
    public Long getId() {
        return id;
    }

    public Position setId(Long id) {
        this.id = id;

        return this;
    }

    public Long getGoEuroId() {
        return goEuroId;
    }

    public Position setGoEuroId(Long goEuroId) {
        this.goEuroId = goEuroId;

        return this;
    }

    public String getName() {
        return name;
    }

    public Position setName(String name) {
        this.name = name;

        return this;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Position setLatitude(Double latitude) {
        this.latitude = latitude;

        return this;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Position setLongitude(Double longitude) {
        this.longitude = longitude;

        return this;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public Position setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;

        return this;
    }

    public Branch getBranch() {
        return branch;
    }

    public Position setBranch(Branch branch) {
        this.branch = branch;

        return this;
    }

    public boolean isSelected() {
        return selected;
    }

    public Position setSelected(boolean selected) {
        this.selected = selected;

        return this;
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public boolean isMarkedDeleted() {
        return markedDeleted;
    }

    public Position setState(State state) {
        this.state = state;

        return this;
    }

    public Position setMarkedDeleted(boolean markedDeleted) {
        this.markedDeleted = markedDeleted;

        return this;
    }
}
