package com.ideas2it.mapper;

import com.ideas2it.Dto.TraineeDto;
import com.ideas2it.model.Trainee;

public class TraineeMapper {

    public static TraineeDto TraineeToTraineeDto(Trainee trainee) {
        return TraineeDto.builder()
                .traineeId(trainee.getTraineeId())
                .build();
    }
}
