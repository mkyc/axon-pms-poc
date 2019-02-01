package it.mltk.poc.axonpms.infrastructure.persistence.service;

import it.mltk.poc.axonpms.delivery.query.GetOneProjectQuery;
import it.mltk.poc.axonpms.domain.projection.ProjectProjection;
import it.mltk.poc.axonpms.infrastructure.persistence.repository.ProjectProjectionRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class ProjectProjectionService {

    private final ProjectProjectionRepository projectProjectionRepository;

    @Autowired
    public ProjectProjectionService(ProjectProjectionRepository projectProjectionRepository) {
        this.projectProjectionRepository = projectProjectionRepository;
    }

    @QueryHandler
    @Transactional
    public ProjectProjection handle(GetOneProjectQuery query) {
        ProjectProjection projection = projectProjectionRepository.getOne(query.getUuid());
        log.debug("got ProjectProjection = " + projection);
        return projection;
    }
}
