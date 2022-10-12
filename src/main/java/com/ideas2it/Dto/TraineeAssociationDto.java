package com.ideas2it.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TraineeAssociationDto {

    private int traineeId;
    private List<Integer> trainersList;

}
