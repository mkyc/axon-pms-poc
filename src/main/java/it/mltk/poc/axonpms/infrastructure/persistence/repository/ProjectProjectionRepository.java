package it.mltk.poc.axonpms.infrastructure.persistence.repository;

import it.mltk.poc.axonpms.domain.projection.ProjectProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface ProjectProjectionRepository extends JpaRepository<ProjectProjection, UUID> {

}
