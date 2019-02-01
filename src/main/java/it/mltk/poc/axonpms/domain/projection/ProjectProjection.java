package it.mltk.poc.axonpms.domain.projection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity(name = "project")
@DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProjectProjection {
    @Id
    @NonNull
    private UUID uuid;
    @NonNull
    private String name;
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "project")
    private List<TaskProjection> tasks = new LinkedList<>();

    public void addTask(TaskProjection taskProjection) {
        tasks.add(taskProjection);
    }
}
