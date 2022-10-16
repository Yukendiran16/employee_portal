package com.ideas2it.mapper;

import com.ideas2it.Dto.TraineeDto;
import com.ideas2it.Dto.TrainerRequestDto;
import com.ideas2it.Dto.TrainerResponseDto;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class TrainerMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    /*public static TrainerDto TrainerToTrainerDto(Trainer trainer) {
        return TrainerDto.builder()
                .trainerId(trainer.getTrainerId())
                .build();
    }*/

    public Trainer trainerRequestDtoToTrainer(TrainerRequestDto trainerRequestDto) {
        Trainer trainer = new Trainer();
        trainer.setUuid(trainerRequestDto.getUuid());
        trainer.setEmployeeName(trainerRequestDto.getEmployeeName());
        trainer.setEmployeeDateOfBirth(LocalDate.parse(trainerRequestDto.getEmployeeDateOfBirth()));
        trainer.setEmployeeDesignation(trainerRequestDto.getEmployeeDesignation());
        trainer.setEmployeeMail(trainerRequestDto.getEmployeeMail());
        trainer.setEmployeeMobileNumber(trainerRequestDto.getEmployeeMobileNumber());
        trainer.setCurrentAddress(trainerRequestDto.getCurrentAddress());
        trainer.setAadhaarCardNumber(trainerRequestDto.getAadhaarCardNumber());
        trainer.setPanCardNumber(trainerRequestDto.getPanCardNumber());
        trainer.setTrainerId(trainerRequestDto.getTrainerId());
        return trainer;
    }

    public TrainerResponseDto trainerToTrainerResponseDto(Trainer trainer) {
        TrainerResponseDto trainerResponseDto = new TrainerResponseDto();
        trainerResponseDto.setEmployeeName(trainer.getEmployeeName());
        trainerResponseDto.setEmployeeDesignation(trainer.getEmployeeDesignation());
        trainerResponseDto.setEmployeeMail(trainer.getEmployeeMail());
        trainerResponseDto.setEmployeeMobileNumber(trainer.getEmployeeMobileNumber());
        trainerResponseDto.setCurrentAddress(trainer.getCurrentAddress());
        trainerResponseDto.setTrainerId(trainer.getTrainerId());
        trainerResponseDto.setTrainees(traineeToTraineeDto(trainer.getTrainees()));
        return trainerResponseDto;
    }

    public Set<TraineeDto> traineeToTraineeDto(Set<Trainee> trainee) {
        Set<TraineeDto> traineeDtoList = new HashSet<>();
        if (null != trainee) {
            for (Trainee trainee1 : trainee) {
                TraineeDto traineeDto = new TraineeDto();
                traineeDto.setEmployeeName(trainee1.getEmployeeName());
                traineeDto.setTraineeId(trainee1.getTraineeId());
                traineeDtoList.add(traineeDto);
            }
            return traineeDtoList;
        } else {
            return null;
        }
    }

    public TrainerRequestDto trainerToTrainerRequestDto(Trainer trainer) {
        return modelMapper.map(trainer, TrainerRequestDto.class);
    }
}
