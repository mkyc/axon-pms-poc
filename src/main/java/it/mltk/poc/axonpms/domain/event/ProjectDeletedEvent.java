package it.mltk.poc.axonpms.domain.event;

import lombok.Value;

import java.util.UUID;

@Value
public class ProjectDeletedEvent {
    UUID projectUuid;
}
