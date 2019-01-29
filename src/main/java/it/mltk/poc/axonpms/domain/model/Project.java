package it.mltk.poc.axonpms.domain.model;

import it.mltk.poc.axonpms.domain.command.InitializeProject;
import it.mltk.poc.axonpms.domain.event.ProjectInitialized;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Aggregate
public class Project {

    @AggregateIdentifier
    private UUID uuid;

    private String name = "New Project";

    @AggregateMember
    private List<Task> tasks = new ArrayList<>();

    @CommandHandler
    public Project(final InitializeProject command) {
        Assert.hasLength(command.getUuid().toString(), "Missing UUID");

        AggregateLifecycle.apply(new ProjectInitialized(command.getUuid()));
    }

    @EventSourcingHandler
    private void on(ProjectInitialized event) {
        this.uuid = event.getUuid();
    }
}
