package com.goeuro.dot.positioneditor.branch.service;


import com.goeuro.dot.positioneditor.base.rest.ServiceResponse;
import com.goeuro.dot.positioneditor.branch.dao.BranchRepository;
import com.goeuro.dot.positioneditor.branch.model.Branch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@Transactional(readOnly = true)
public class BranchRestService {

    @Autowired
    BranchRepository branchRepository;

    @RequestMapping(value = "/branch/getAll")
    public ServiceResponse getAllBranches() {
        try {
            return ServiceResponse.success(branchRepository.getAll());
        } catch (Exception ex) {
            return ServiceResponse.faild(ex);
        }
    }


    @RequestMapping(value = "/branch/create", method = RequestMethod.POST)
    @Transactional(readOnly = false, noRollbackFor = Exception.class)
    public ServiceResponse<Branch> createBranch(@RequestBody Branch branch) {
        try {
            return ServiceResponse.success(branchRepository.saveOrUpdate(branch.setCreatedDate(new Date())).get());
        } catch (Exception ex) {
            return ServiceResponse.faild(ex);
        }
    }

    @RequestMapping(value = "/branch/delete", method = RequestMethod.POST)
    @Transactional(readOnly = false)
    public ServiceResponse deleteBranch(@RequestBody Branch branch) {
        try {
            return ServiceResponse.success(branchRepository.delete(branch));
        } catch (Exception ex) {
            return ServiceResponse.faild(ex);
        }
    }

}
