package com.ideas2it.mapper;

import com.ideas2it.Dto.TraineeDto;
import com.ideas2it.model.Trainee;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TraineeMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    /*public static TraineeDto TraineeToTraineeDto(Trainee trainee) {
        return TraineeDto.builder()
                .traineeId(trainee.getTraineeId())
                .build();
    }

    public Trainee TraineeDtoToTrainee(TraineeDto traineeDto) {
        Trainee trainee = new Trainee();
        trainee.setUuid(traineeDto.getUuid());
        trainee.setEmployeeName(traineeDto.getEmployeeName());
        trainee.setEmployeeDateOfBirth(traineeDto.getEmployeeDateOfBirth());
        trainee.setEmployeeDesignation(traineeDto.getEmployeeDesignation());
        trainee.setEmployeeMail(traineeDto.getEmployeeMail());
        trainee.setEmployeeMobileNumber(traineeDto.getEmployeeMobileNumber());
        trainee.setCurrentAddress(traineeDto.getCurrentAddress());
        trainee.setAadhaarCardNumber(traineeDto.getAadhaarCardNumber());
        trainee.setPanCardNumber(traineeDto.getPanCardNumber());
        trainee.setIsActive(traineeDto.isActive());
        trainee.setTraineeId(traineeDto.getTraineeId());
        trainee.setTrainers(traineeDto.getTrainers());
        return trainee;
    }

    public TraineeDto TraineeToTraineeDto(Trainee trainee) {
        TraineeDto traineeDto = new TraineeDto();
        traineeDto.setUuid(trainee.getUuid());
        traineeDto.setEmployeeName(trainee.getEmployeeName());
        traineeDto.setEmployeeDateOfBirth(trainee.getEmployeeDateOfBirth());
        traineeDto.setEmployeeDesignation(trainee.getEmployeeDesignation());
        traineeDto.setEmployeeMail(trainee.getEmployeeMail());
        traineeDto.setEmployeeMobileNumber(trainee.getEmployeeMobileNumber());
        traineeDto.setCurrentAddress(trainee.getCurrentAddress());
        traineeDto.setAadhaarCardNumber(trainee.getAadhaarCardNumber());
        traineeDto.setPanCardNumber(trainee.getPanCardNumber());
        traineeDto.setIsActive(trainee.getIsActive());
        traineeDto.setTraineeId(trainee.getTraineeId());
        traineeDto.setTrainers(trainee.getTrainers());
        return traineeDto;
    }*/

    public TraineeDto TraineeToTraineeDto(Trainee trainee) {
        return modelMapper.map(trainee,TraineeDto.class);
    }

    public Trainee TraineeDtoToTrainee(TraineeDto traineeDto) {
        return modelMapper.map(traineeDto,Trainee.class);
    }

}
