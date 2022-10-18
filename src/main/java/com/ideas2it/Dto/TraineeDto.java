package com.ideas2it.Dto;

import com.ideas2it.model.Trainee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * A DTO for the {@link Trainee} entity
 */
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TraineeDto implements Serializable {
    private String employeeName;
    private int traineeId;
}