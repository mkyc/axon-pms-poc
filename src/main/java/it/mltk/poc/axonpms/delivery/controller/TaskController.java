package it.mltk.poc.axonpms.delivery.controller;

import it.mltk.poc.axonpms.delivery.command.InitializeTaskCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/projects/{projectUuid}/tasks")
public class TaskController {

    private final CommandGateway commandGateway;

    public TaskController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public ResponseEntity createTask(
            @PathVariable("projectUuid") String projectUuid,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        UUID taskUuid = UUID.randomUUID();
        commandGateway.sendAndWait(new InitializeTaskCommand(UUID.fromString(projectUuid), taskUuid));
        return ResponseEntity
                .created(uriComponentsBuilder
                        .path("/projects/{projectUuid}/tasks/{taskUuid}")
                        .buildAndExpand(projectUuid, taskUuid)
                        .toUri())
                .build();
    }
}
