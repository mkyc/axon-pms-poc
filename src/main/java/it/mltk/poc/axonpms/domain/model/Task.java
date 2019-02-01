package it.mltk.poc.axonpms.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.EntityId;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
class Task {

    @EntityId
    private UUID uuid;
    private String name;
}