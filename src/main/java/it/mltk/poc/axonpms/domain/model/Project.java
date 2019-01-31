package it.mltk.poc.axonpms.domain.model;

import it.mltk.poc.axonpms.delivery.command.InitializeProjectCommand;
import it.mltk.poc.axonpms.delivery.command.RenameProjectCommand;
import it.mltk.poc.axonpms.domain.event.ProjectInitializedEvent;
import it.mltk.poc.axonpms.domain.event.ProjectRenamedEvent;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

import java.util.UUID;

@Data
@NoArgsConstructor
@Aggregate
public class Project {

    @AggregateIdentifier
    private UUID uuid;
    private String name;

//    @AggregateMember
//    private List<Task> tasks = new ArrayList<>();

    @CommandHandler
    public Project(final InitializeProjectCommand command) {
        Assert.hasLength(command.getUuid().toString(), "Missing UUID");
        AggregateLifecycle.apply(new ProjectInitializedEvent(command.getUuid()));
    }

    @CommandHandler
    public Project handle(final RenameProjectCommand command) {
        Assert.hasLength(command.getUuid().toString(), "Missing UUID");
        Assert.hasLength(command.getNewName(), "Missing newName");
        AggregateLifecycle.apply(new ProjectRenamedEvent(command.getUuid(), command.getNewName()));
        return this;
    }

    @EventSourcingHandler
    public void on(ProjectInitializedEvent event) {
        this.uuid = event.getUuid();
        this.name = event.getName();
    }

    @EventSourcingHandler
    public void on(ProjectRenamedEvent event) {
        this.name = event.getName();
    }
}
