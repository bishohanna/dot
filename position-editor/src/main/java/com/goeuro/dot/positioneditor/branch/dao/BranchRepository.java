package com.goeuro.dot.positioneditor.branch.dao;

import com.goeuro.dot.base.dao.BaseRepository.BaseRepository;
import com.goeuro.dot.positioneditor.branch.model.Branch;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class BranchRepository extends BaseRepository<Branch> {


    public BranchRepository() {
        super(Branch.class, (n, o) -> {
        });
    }

    public List<Branch> getAll() {
        return searchByQuery(q -> {
            //empty criteria
        });
    }
}
