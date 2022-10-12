package com.ideas2it.mapper;

import com.ideas2it.Dto.TraineeDto;
import com.ideas2it.Dto.TraineeRequestDto;
import com.ideas2it.Dto.TraineeResponseDto;
import com.ideas2it.Dto.TrainerDto;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class TraineeMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    /*public TraineeRequestDto TraineeToTraineeDto(Trainee trainee) {
        return TraineeRequestDto.builder()
                .traineeId(trainee.getTraineeId())
                .build();
    }*/

    public Trainee traineeRequestDtoToTrainee(TraineeRequestDto traineeRequestDto) {
        Trainee trainee = new Trainee();
        trainee.setUuid(traineeRequestDto.getUuid());
        trainee.setEmployeeName(traineeRequestDto.getEmployeeName());
        trainee.setEmployeeDateOfBirth(LocalDate.parse(traineeRequestDto.getEmployeeDateOfBirth()));
        trainee.setEmployeeDesignation(traineeRequestDto.getEmployeeDesignation());
        trainee.setEmployeeMail(traineeRequestDto.getEmployeeMail());
        trainee.setEmployeeMobileNumber(traineeRequestDto.getEmployeeMobileNumber());
        trainee.setCurrentAddress(traineeRequestDto.getCurrentAddress());
        trainee.setAadhaarCardNumber(traineeRequestDto.getAadhaarCardNumber());
        trainee.setPanCardNumber(traineeRequestDto.getPanCardNumber());
        trainee.setTraineeId(traineeRequestDto.getTraineeId());
        return trainee;
    }

    public TraineeResponseDto traineeToTraineeResponseDto(Trainee trainee) {
        TraineeResponseDto traineeResponseDto = new TraineeResponseDto();
        traineeResponseDto.setEmployeeName(trainee.getEmployeeName());
        traineeResponseDto.setEmployeeDesignation(trainee.getEmployeeDesignation());
        traineeResponseDto.setEmployeeMail(trainee.getEmployeeMail());
        traineeResponseDto.setEmployeeMobileNumber(trainee.getEmployeeMobileNumber());
        traineeResponseDto.setCurrentAddress(trainee.getCurrentAddress());
        traineeResponseDto.setTraineeId(trainee.getTraineeId());
        traineeResponseDto.setTrainers(trainerToTrainerDto(trainee.getTrainers()));
        return traineeResponseDto;
    }

    public Set<TrainerDto> trainerToTrainerDto(Set<Trainer> trainer) {
        Set<TrainerDto> trainerDtoList = new HashSet<>();
        if (null != trainer) {
            for (Trainer trainer1 : trainer) {
                TrainerDto trainerDto = new TrainerDto();
                trainerDto.setEmployeeName(trainer1.getEmployeeName());
                trainerDto.setTrainerId(trainer1.getTrainerId());
                trainerDtoList.add(trainerDto);
            }
            return trainerDtoList;
        } else {
            return null;
        }
    }

    public TraineeDto traineeToTraineeDto(Trainee trainee) {
        return modelMapper.map(trainee,TraineeDto.class);
    }

}
