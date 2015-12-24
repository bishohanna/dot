package com.goeuro.dot.positioneditor.position.dao;

import com.goeuro.dot.base.dao.BaseRepository.BaseRepository;
import com.goeuro.dot.base.model.BaseEditableModel;
import com.goeuro.dot.base.model.PagedResponse;
import com.goeuro.dot.positioneditor.position.dao.query.positionsByName.FindPositionsByName;
import com.goeuro.dot.positioneditor.position.model.Position;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
@Transactional(readOnly = true)
public class PositionRepository extends BaseRepository<Position> {


    public PositionRepository() {
        super(Position.class, (n, o) -> o
                        .setName(n.getName())
                        .setLatitude(n.getLatitude())
                        .setLongitude(n.getLongitude())
                        .setMarkedDeleted(n.isMarkedDeleted())
                        .setState(n.getState() != BaseEditableModel.State.NEW ? BaseEditableModel.State.UPDATE : BaseEditableModel.State.NEW)
                        .setGoEuroId(n.getGoEuroId())
        );
    }


    @Override
    protected void doAfterLoad(Position model) {
        super.doAfterLoad(model);

        model.setIconUrl("/resources/images/rmarker.png");
    }

    public List<Position> findGoeuroPositionsByName(String name) {
        return executeGoeuroQuery(new FindPositionsByName().setPositionName(name));
    }

    public Optional<Position> findByPositionIdInBranch(Long positionId, Long branchId) {
        return searchByQuery(q -> q.add(Restrictions.eq("id", positionId)).add(Restrictions.eq("branch.id", branchId))).stream().findFirst();
    }

    public boolean isPositionInBranch(Long goeuroId, Long branchId) {
        return searchByQuery(q -> q
                .add(Restrictions.eq("goEuroId", goeuroId))
                .add(Restrictions.eq("branch.id", branchId)))
                .stream().findAny().isPresent();
    }

    public PagedResponse<Position> searchInBranchByName(Long branchId, String name, int startIndex, int pageSize) {
        return searchByQueryPaged(q -> q.add(Restrictions.eq("branch.id", branchId))
                .add(StringUtils.isNotBlank(name) ? Restrictions.like("name", name, MatchMode.START) : Restrictions.isNotNull("name")), startIndex, pageSize);
    }
}
