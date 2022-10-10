package com.ideas2it.mapper;

import com.ideas2it.Dto.TraineeDto;
import com.ideas2it.Dto.TrainerDto;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;

public class TrainerMapper {

    public static TrainerDto TrainerToTrainerDto(Trainer trainer) {
        return TrainerDto.builder()
                .trainerId(trainer.getTrainerId())
                .build();
    }

}
