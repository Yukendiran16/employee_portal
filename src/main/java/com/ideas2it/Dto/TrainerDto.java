package com.ideas2it.Dto;

import com.ideas2it.model.Trainer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * A DTO for the {@link Trainer} entity
 */
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainerDto implements Serializable {
    private String employeeName;
    private int trainerId;
}