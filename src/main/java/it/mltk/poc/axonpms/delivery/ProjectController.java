package it.mltk.poc.axonpms.delivery;

import it.mltk.poc.axonpms.domain.command.InitializeProject;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final CommandGateway commandGateway;

    public ProjectController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public ResponseEntity createProject(UriComponentsBuilder uriComponentsBuilder) {

        UUID projectUuid = commandGateway.sendAndWait(new InitializeProject(UUID.randomUUID()));

        return ResponseEntity
                .created(uriComponentsBuilder
                        .path("/projects/{projectUuid}")
                        .buildAndExpand(projectUuid)
                        .toUri())
                .build();
    }
}
