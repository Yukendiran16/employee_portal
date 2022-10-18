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
public class TrainerAssociationDto {

    private int trainerId;
    private List<Integer> traineesList;

}
