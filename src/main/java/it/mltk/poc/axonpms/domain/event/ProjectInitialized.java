package it.mltk.poc.axonpms.domain.event;

import lombok.Data;

import java.util.UUID;

@Data
public class ProjectInitialized {
    private final UUID uuid;
}
