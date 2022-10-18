package com.ideas2it.Dto;

import com.ideas2it.model.Trainee;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link Trainee} entity
 */
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TraineeResponseDto implements Serializable {
    private  String employeeName;
    private  String employeeDesignation;
    private  String employeeMail;
    private  String employeeMobileNumber;
    private  String currentAddress;
    private  int traineeId;
    private  Set<TrainerDto> trainers;

}