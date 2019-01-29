package it.mltk.poc.axonpms.domain.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
public class InitializeProject {
    @TargetAggregateIdentifier
    private final UUID uuid;
}
