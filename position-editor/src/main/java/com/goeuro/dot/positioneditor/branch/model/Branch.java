package com.goeuro.dot.positioneditor.branch.model;

import com.goeuro.dot.base.model.BaseModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "branch")
public class Branch implements BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;
    private String createdBy;
    private String description;
    private Date createdDate;


    public Long getId() {
        return id;
    }

    public Branch setId(Long id) {
        this.id = id;

        return this;
    }

    public String getName() {
        return name;
    }

    public Branch setName(String name) {
        this.name = name;

        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Branch setCreatedBy(String createdBy) {
        this.createdBy = createdBy;

        return this;
    }

    public String getDescription() {
        return description;
    }

    public Branch setDescription(String description) {
        this.description = description;

        return this;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Branch setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;

        return this;
    }
}
