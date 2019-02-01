package it.mltk.poc.axonpms.domain.event;

import lombok.Value;

import java.util.UUID;

@Value
public class TaskInitializedEvent {
    UUID projectUuid;
    UUID taskUuid;
    String name = "New Task";
}
