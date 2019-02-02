package it.mltk.poc.axonpms.domain.model;

import it.mltk.poc.axonpms.delivery.command.DeleteProjectCommand;
import it.mltk.poc.axonpms.delivery.command.InitializeProjectCommand;
import it.mltk.poc.axonpms.delivery.command.InitializeTaskCommand;
import it.mltk.poc.axonpms.delivery.command.RenameProjectCommand;
import it.mltk.poc.axonpms.domain.event.ProjectDeletedEvent;
import it.mltk.poc.axonpms.domain.event.ProjectInitializedEvent;
import it.mltk.poc.axonpms.domain.event.ProjectRenamedEvent;
import it.mltk.poc.axonpms.domain.event.TaskInitializedEvent;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import static org.axonframework.modelling.command.AggregateLifecycle.markDeleted;

@Data
@NoArgsConstructor
@Aggregate
public class Project {

    @AggregateIdentifier
    private UUID uuid;
    private String name;

    @AggregateMember
    private List<Task> tasks = new ArrayList<>();

    @CommandHandler
    public Project(final InitializeProjectCommand command) {
        Assert.hasLength(command.getProjectUuid().toString(), "Missing project UUID");
        apply(new ProjectInitializedEvent(command.getProjectUuid()));
    }

    @CommandHandler
    public Project handle(final RenameProjectCommand command) {
        Assert.hasLength(command.getProjectUuid().toString(), "Missing project UUID");
        Assert.hasLength(command.getNewName(), "Missing newName");
        apply(new ProjectRenamedEvent(command.getProjectUuid(), command.getNewName()));
        return this;
    }

    @CommandHandler
    public Project handle(final InitializeTaskCommand command) {
        Assert.hasLength(command.getProjectUuid().toString(), "Missing project UUID");
        Assert.hasLength(command.getTaskUuid().toString(), "Missing task UUID");
        apply(new TaskInitializedEvent(command.getProjectUuid(), command.getTaskUuid()));
        return this;
    }

    @CommandHandler
    public void handle(final DeleteProjectCommand command) {
        Assert.hasLength(command.getProjectUuid().toString(), "Missing project UUID");
        apply(new ProjectDeletedEvent(command.getProjectUuid()));
    }

    @EventSourcingHandler
    public void on(ProjectInitializedEvent event) {
        this.uuid = event.getProjectUuid();
        this.name = event.getName();
    }

    @EventSourcingHandler
    public void on(ProjectRenamedEvent event) {
        this.name = event.getName();
    }

    @EventSourcingHandler
    public void on(TaskInitializedEvent event) {
        this.tasks.add(new Task(event.getTaskUuid(), event.getName()));
    }

    @EventSourcingHandler
    public void on(ProjectDeletedEvent event) {
        markDeleted();
    }
}
