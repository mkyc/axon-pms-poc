package it.mltk.poc.axonpms.delivery.command;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Value
public class InitializeTaskCommand {
    @TargetAggregateIdentifier
    UUID projectUuid;
    UUID taskUuid;
}
