package com.ideas2it.mapper;

import com.ideas2it.Dto.TrainerDto;
import com.ideas2it.model.Trainer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TrainerMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    /*public static TrainerDto TrainerToTrainerDto(Trainer trainer) {
        return TrainerDto.builder()
                .trainerId(trainer.getTrainerId())
                .build();
    }

    public Trainer TrainerDtoToTrainer(TrainerDto trainerDto) {
        Trainer trainer = new Trainer();
        trainer.setUuid(trainerDto.getUuid());
        trainer.setEmployeeName(trainerDto.getEmployeeName());
        trainer.setEmployeeDateOfBirth(trainerDto.getEmployeeDateOfBirth());
        trainer.setEmployeeDesignation(trainerDto.getEmployeeDesignation());
        trainer.setEmployeeMail(trainerDto.getEmployeeMail());
        trainer.setEmployeeMobileNumber(trainerDto.getEmployeeMobileNumber());
        trainer.setCurrentAddress(trainerDto.getCurrentAddress());
        trainer.setAadhaarCardNumber(trainerDto.getAadhaarCardNumber());
        trainer.setPanCardNumber(trainerDto.getPanCardNumber());
        trainer.setIsActive(trainerDto.isActive());
        trainer.setTrainerId(trainerDto.getTrainerId());
        trainer.setTrainees(trainerDto.getTrainees());
        return trainer;
    }

    public TrainerDto TrainerToTrainerDto(Trainer trainer) {
        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setUuid(trainer.getUuid());
        trainerDto.setEmployeeName(trainer.getEmployeeName());
        trainerDto.setEmployeeDateOfBirth(trainer.getEmployeeDateOfBirth());
        trainerDto.setEmployeeDesignation(trainer.getEmployeeDesignation());
        trainerDto.setEmployeeMail(trainer.getEmployeeMail());
        trainerDto.setEmployeeMobileNumber(trainer.getEmployeeMobileNumber());
        trainerDto.setCurrentAddress(trainer.getCurrentAddress());
        trainerDto.setAadhaarCardNumber(trainer.getAadhaarCardNumber());
        trainerDto.setPanCardNumber(trainer.getPanCardNumber());
        trainerDto.setIsActive(trainer.getIsActive());
        trainerDto.setTrainerId(trainer.getTrainerId());
        trainerDto.setTrainees(trainer.getTrainees());
        return trainerDto;
    }*/

    public TrainerDto TrainerToTrainerDto(Trainer trainer) {
        return modelMapper.map(trainer,TrainerDto.class);
    }

    public Trainer TrainerDtoToTrainer(TrainerDto trainerDto) {
        return modelMapper.map(trainerDto,Trainer.class);
    }
}
