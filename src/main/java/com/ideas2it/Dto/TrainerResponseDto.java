package com.ideas2it.Dto;

import com.ideas2it.Dto.TraineeDto;
import com.ideas2it.model.Trainer;
import lombok.*;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link Trainer} entity
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainerResponseDto implements Serializable {
    private String employeeName;
    private String employeeDesignation;
    private String employeeMail;
    private String employeeMobileNumber;
    private String currentAddress;
    private int trainerId;
    private Set<TraineeDto> trainees;
}