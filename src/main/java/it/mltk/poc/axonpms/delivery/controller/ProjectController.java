package it.mltk.poc.axonpms.delivery.controller;

import it.mltk.poc.axonpms.delivery.command.InitializeProjectCommand;
import it.mltk.poc.axonpms.delivery.command.RenameProjectCommand;
import it.mltk.poc.axonpms.delivery.query.GetOneProjectQuery;
import it.mltk.poc.axonpms.domain.projection.ProjectProjection;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public ProjectController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping
    public ResponseEntity createProject(
            UriComponentsBuilder uriComponentsBuilder
    ) {
        UUID projectUuid = commandGateway.sendAndWait(new InitializeProjectCommand(UUID.randomUUID()));
        return ResponseEntity
                .created(uriComponentsBuilder
                        .path("/projects/{projectUuid}")
                        .buildAndExpand(projectUuid)
                        .toUri())
                .build();
    }

    @GetMapping("/{projectUuid}")
    public ResponseEntity getProject(
            @PathVariable("projectUuid") String projectUuid
    ) {
        return ResponseEntity
                .ok(queryGateway
                        .query(
                                new GetOneProjectQuery(UUID.fromString(projectUuid)),
                                ResponseTypes.instanceOf(ProjectProjection.class)
                        ).join()
                );
    }

    @PatchMapping("/{projectUuid}")
    public ResponseEntity renameProject(
            @PathVariable("projectUuid") String projectUuid,
            @RequestParam("newName") final String newName,
            UriComponentsBuilder uriComponentsBuilder) {
        commandGateway.sendAndWait(new RenameProjectCommand(UUID.fromString(projectUuid), newName));
        return ResponseEntity
                .created(uriComponentsBuilder
                        .path("/projects/{projectUuid}")
                        .buildAndExpand(projectUuid)
                        .toUri())
                .build();
    }
}
