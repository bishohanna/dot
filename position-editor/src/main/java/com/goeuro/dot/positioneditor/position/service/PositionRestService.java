package com.goeuro.dot.positioneditor.position.service;

import com.goeuro.dot.base.exception.DotException;
import com.goeuro.dot.base.model.PagedResponse;
import com.goeuro.dot.positioneditor.base.rest.ServiceResponse;
import com.goeuro.dot.positioneditor.position.dao.PositionRepository;
import com.goeuro.dot.positioneditor.position.model.Position;
import com.goeuro.dot.positioneditor.position.model.SavePositionsWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Transactional(readOnly = true)
public class PositionRestService {

    @Value("${positionPageDefaultSize}")
    private Integer pageSize;

    @Autowired
    PositionRepository positionRepository;


    @RequestMapping(value = "/goeuroPosition/findByName")
    public ServiceResponse findGoeuroPositionsByName(@RequestParam("name") String name, @RequestParam("branchId") Long branchId) {
        try {
            List<Position> positions = positionRepository.findGoeuroPositionsByName(name);

            //remove positions that i already have in my branch
            positions = positions.parallelStream()
                    //just in case
                    .filter(p -> p != null)

                            //get positions that are not present in my branch
                    .filter(position -> !positionRepository.isPositionInBranch(position.getGoEuroId(), branchId))
                    .collect(Collectors.toList());

            return ServiceResponse.success(positions);
        } catch (Exception ex) {
            return ServiceResponse.faild(ex);
        }
    }

    @RequestMapping(value = "/position/loadByBranchPaged")
    public ServiceResponse loadPositionsByBranchPaged(@RequestParam("branchId") Long branchId,
                                                      @RequestParam(value = "name", required = false) String positionName,
                                                      @RequestParam(value = "startIndex", defaultValue = "1") int startIndex) {
        try {

            PagedResponse<Position> searchPage = positionRepository.searchInBranchByName(branchId, positionName, startIndex - 1, pageSize);

            return ServiceResponse.success(searchPage.getItems(), startIndex, pageSize, searchPage.getTotalItems());

        } catch (Exception ex) {
            return ServiceResponse.faild(ex);
        }
    }

    @RequestMapping(value = "/position/update", method = RequestMethod.POST)
    @Transactional(readOnly = false)
    public ServiceResponse updatePosition(@RequestBody Position position) {
        try {
            return ServiceResponse.success(positionRepository.saveOrUpdate(position));
        } catch (Exception ex) {
            return ServiceResponse.faild(ex);
        }
    }

    @RequestMapping(value = "/position/remove")
    @Transactional(readOnly = false)
    public ServiceResponse removePositionById(@RequestParam("positionId") Long positionId, @RequestParam("branchId") Long branchId) {
        try {
            Optional<Position> loadedPosition = positionRepository.findByPositionIdInBranch(positionId, branchId);

            if (loadedPosition.isPresent()) {
                boolean result = positionRepository.delete(loadedPosition.get());
                return ServiceResponse.success(result);
            } else {
                return ServiceResponse.faild(new DotException("Position not found !"));
            }
        } catch (Exception ex) {
            return ServiceResponse.faild(ex);
        }
    }

    @RequestMapping(value = "/position/saveNewPositions", method = RequestMethod.POST)
    @Transactional(readOnly = false)
    public ServiceResponse saveNewPositions(@RequestBody SavePositionsWrapper positionsWrapper) {
        try {

            if (!CollectionUtils.isEmpty(positionsWrapper.getPositions()) || positionsWrapper.getBranch() == null) {

                List savedPositions = positionsWrapper.getPositions()
                        .stream()
                                //on not null positions (to make sure)
                        .filter(position -> position != null)
                                //add branch to positions
                        .map(position -> position.setBranch(positionsWrapper.getBranch()))
                                //remove any duplicates for this position on DB
                        .filter(position ->
                                !positionRepository.isPositionInBranch(position.getGoEuroId(), position.getBranch().getId()))
                                //save each position
                        .map(position -> positionRepository.saveOrUpdate(position))
                                //get only saved positions without error
                        .filter(savedPosition -> savedPosition.isPresent())
                                //convert to real positions
                        .map(savedPosition -> savedPosition.get())
                        .collect(Collectors.toList());


                return ServiceResponse.success(savedPositions);
            } else {
                return ServiceResponse.faild(new DotException("Empty positions list"));
            }

        } catch (Exception ex) {
            return ServiceResponse.faild(ex);
        }
    }
}
