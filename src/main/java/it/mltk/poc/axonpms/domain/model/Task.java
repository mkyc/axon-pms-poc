package it.mltk.poc.axonpms.domain.model;

import org.axonframework.modelling.command.EntityId;

import java.util.UUID;

public class Task {

    @EntityId
    private UUID uuid;

    private String name;
}
