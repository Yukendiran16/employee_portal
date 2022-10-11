package com.ideas2it.mapper;

import com.ideas2it.Dto.TraineeDto;
import com.ideas2it.Dto.TraineeRequestDto;
import com.ideas2it.Dto.TraineeResponseDto;
import com.ideas2it.Dto.TrainerDto;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

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

    public Trainee TraineeRequestDtoToTrainee(TraineeRequestDto traineeRequestDto) {
        Trainee trainee = new Trainee();
        trainee.setUuid(traineeRequestDto.getUuid());
        trainee.setEmployeeName(traineeRequestDto.getEmployeeName());
        trainee.setEmployeeDateOfBirth(traineeRequestDto.getEmployeeDateOfBirth());
        trainee.setEmployeeDesignation(traineeRequestDto.getEmployeeDesignation());
        trainee.setEmployeeMail(traineeRequestDto.getEmployeeMail());
        trainee.setEmployeeMobileNumber(traineeRequestDto.getEmployeeMobileNumber());
        trainee.setCurrentAddress(traineeRequestDto.getCurrentAddress());
        trainee.setAadhaarCardNumber(traineeRequestDto.getAadhaarCardNumber());
        trainee.setPanCardNumber(traineeRequestDto.getPanCardNumber());
        trainee.setIsActive(traineeRequestDto.isActive());
        trainee.setTraineeId(traineeRequestDto.getTraineeId());
        trainee.setTrainers(traineeRequestDto.getTrainers());
        return trainee;
    }

    public TraineeResponseDto TraineeToTraineeResponseDto(Trainee trainee) {
        TraineeResponseDto traineeResponseDto = new TraineeResponseDto();
        traineeResponseDto.setEmployeeName(trainee.getEmployeeName());
        traineeResponseDto.setEmployeeDesignation(trainee.getEmployeeDesignation());
        traineeResponseDto.setEmployeeMail(trainee.getEmployeeMail());
        traineeResponseDto.setEmployeeMobileNumber(trainee.getEmployeeMobileNumber());
        traineeResponseDto.setCurrentAddress(trainee.getCurrentAddress());
        traineeResponseDto.setTraineeId(trainee.getTraineeId());
        traineeResponseDto.setTrainers(TrainerToTrainerDto(trainee.getTrainers()));
        return traineeResponseDto;
    }

    public Set<TrainerDto> TrainerToTrainerDto(Set<Trainer> trainer) {
        Set<TrainerDto> trainerDtoList = new HashSet<>();
        for(Trainer trainer1 : trainer) {
            TrainerDto trainerDto = new TrainerDto();
            trainerDto.setEmployeeName(trainer1.getEmployeeName());
            trainerDto.setTrainerId(trainer1.getTrainerId());
        }
        return trainerDtoList;
    }

    /*public TraineeDto TraineeToTraineeDto(Trainee trainee) {
        return modelMapper.map(trainee,TraineeDto.class);
    }

    public Trainee TraineeDtoToTrainee(TraineeRequestDto traineeDto) {
        return modelMapper.map(traineeDto,Trainee.class);
    }*/

}
