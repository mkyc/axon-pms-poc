package it.mltk.poc.axonpms.infrastructure.persistence.denormalizer;

import it.mltk.poc.axonpms.domain.event.ProjectInitializedEvent;
import it.mltk.poc.axonpms.domain.event.ProjectRenamedEvent;
import it.mltk.poc.axonpms.domain.projection.ProjectProjection;
import it.mltk.poc.axonpms.infrastructure.persistence.repository.ProjectProjectionRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectProjectionDenormalizer {

    private final ProjectProjectionRepository projectProjectionRepository;

    @Autowired
    public ProjectProjectionDenormalizer(ProjectProjectionRepository projectProjectionRepository) {
        this.projectProjectionRepository = projectProjectionRepository;
    }

    @EventHandler
    public void on(ProjectInitializedEvent event) {
        projectProjectionRepository.save(new ProjectProjection(event.getUuid(), event.getName()));
    }

    @EventHandler
    public void on(ProjectRenamedEvent event) {
        ProjectProjection projection = projectProjectionRepository.getOne(event.getUuid());
        projection.setName(event.getName());
        projectProjectionRepository.save(projection);
    }
}
