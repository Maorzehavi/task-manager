package com.maorzehavi.taskmanager.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link Task}
 */

@Data
@Builder
public class TaskDto implements Serializable {
    Long id;
    @NotBlank(message = "Title is mandatory")
    String title;
    Boolean completed;
}