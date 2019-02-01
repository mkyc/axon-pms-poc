package it.mltk.poc.axonpms.infrastructure.persistence.denormalizer;

import it.mltk.poc.axonpms.domain.event.ProjectInitializedEvent;
import it.mltk.poc.axonpms.domain.event.ProjectRenamedEvent;
import it.mltk.poc.axonpms.domain.event.TaskInitializedEvent;
import it.mltk.poc.axonpms.domain.projection.ProjectProjection;
import it.mltk.poc.axonpms.domain.projection.TaskProjection;
import it.mltk.poc.axonpms.infrastructure.persistence.repository.ProjectProjectionRepository;
import it.mltk.poc.axonpms.infrastructure.persistence.repository.TaskProjectionRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectProjectionDenormalizer {

    private final ProjectProjectionRepository projectProjectionRepository;
    private final TaskProjectionRepository taskProjectionRepository;

    @Autowired
    public ProjectProjectionDenormalizer(
            ProjectProjectionRepository projectProjectionRepository,
            TaskProjectionRepository taskProjectionRepository
    ) {
        this.projectProjectionRepository = projectProjectionRepository;
        this.taskProjectionRepository = taskProjectionRepository;
    }

    @EventHandler
    public void on(ProjectInitializedEvent event) {
        projectProjectionRepository.save(new ProjectProjection(event.getProjectUuid(), event.getName()));
    }

    @EventHandler
    public void on(ProjectRenamedEvent event) {
        ProjectProjection projection = projectProjectionRepository.getOne(event.getProjectUuid());
        projection.setName(event.getName());
        projectProjectionRepository.save(projection);
    }

    @EventHandler
    public void on(TaskInitializedEvent event) {
        ProjectProjection projectProjection = projectProjectionRepository.getOne(event.getProjectUuid());
        TaskProjection taskProjection = taskProjectionRepository.save(new TaskProjection(event.getTaskUuid(), event.getName(), projectProjection));
        projectProjection.addTask(taskProjection);
        projectProjectionRepository.save(projectProjection);
    }
}
