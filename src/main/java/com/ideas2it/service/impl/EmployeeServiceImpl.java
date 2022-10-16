package com.ideas2it.service.impl;

import com.ideas2it.Dto.TraineeRequestDto;
import com.ideas2it.Dto.TrainerRequestDto;
import com.ideas2it.Dto.TrainerResponseDto;
import com.ideas2it.dao.TraineeRepository;
import com.ideas2it.dao.TrainerRepository;
import com.ideas2it.exception.AlreadyAssignedException;
import com.ideas2it.exception.EmployeeNotFoundException;
import com.ideas2it.mapper.TraineeMapper;
import com.ideas2it.mapper.TrainerMapper;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ideas2it.util.Constant.IS_ACTIVE;

/**
 * <h2>EmployeeServiceImpl</h2>
 * <p>
 * The EmployeeServiceImpl class is implements EmployeeService and
 * The class implements an application that
 * defines all methods used in EmployeeController class
 * method perform pass the parameters to DAO class
 * and return data's to controller from Dao
 * </p>
 *
 * @author Yukendiran K
 * @version 1.0
 * @since 2022-08-04
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private TrainerRepository trainerRepository;
    @Autowired
    private TraineeRepository traineeRepository;

    @Autowired(required = false)
    private TraineeMapper traineeMapper;

    @Autowired(required = false)
    private TrainerMapper trainerMapper;

    /**
     * <h1> addTrainer </h1>
     * <p>
     * method used to get trainer details from controller to pass the details to dao
     *
     * @param trainerDto object
     * @return status of operation
     */
    @Override
    public TrainerResponseDto addTrainer(TrainerRequestDto trainerDto) {
        Trainer trainer;
        trainer = trainerMapper.trainerRequestDtoToTrainer(trainerDto);
        return trainerMapper.trainerToTrainerResponseDto(trainerRepository.save(trainer));
    }

    /**
     * <h1> addTrainee </h1>
     * <p>
     * method used to get trainee details from controller to pass the details to dao
     *
     * @param traineeDto object
     * @return status of operation
     */
    @Override
    public Trainee addTrainee(TraineeRequestDto traineeDto) {
        Trainee trainee;
        trainee = traineeMapper.traineeRequestDtoToTrainee(traineeDto);
        return traineeRepository.save(trainee);
    }

    /**
     * <h1> getTrainersData </h1>
     * <p>
     * method used to call the dao for get all trainer details
     *
     * @return list of trainers Data
     */
    @Override
    public List<Trainer> getTrainersData() {
        return trainerRepository.findByIsActive(IS_ACTIVE);
        //return trainerRepository.findTrainerByIsActive(IS_ACTIVE);
    }

    /**
     * <h1> searchTrainerData </h1>
     * <p>
     * method used to get employeeId from controller to pass the employeeId to dao
     *
     * @param trainerId for
     * @return searched trainer Data
     */
    @Override
    public Trainer searchTrainerData(int trainerId) {
        return trainerRepository.findById(trainerId).orElseThrow(()
                -> new EmployeeNotFoundException("Trainer not found "));
    }

    /**
     * <h1> getTraineesData </h1>
     * <p>
     * method used to call the dao for get all trainee details
     *
     * @return {@link List<Trainee>} returns trainees Data
     */
    @Override
    public List<Trainee> getTraineesData() {
        return traineeRepository.findByIsActive(IS_ACTIVE);
        //return traineeRepository.findTraineeByIsActive(IS_ACTIVE);
    }

    /**
     * <h1> searchTraineeData </h1>
     * <p>
     * method used to get employeeId from controller to pass the employeeId to dao
     *
     * @param traineeId for
     * @return searched trainee Data
     */
    @Override
    public Trainee searchTraineeData(int traineeId) {
        return traineeRepository.findById(traineeId).orElseThrow(()
                -> new EmployeeNotFoundException("Trainee not found "));
    }

    /**
     * <h1> updateTrainerData </h1>
     * <p>
     * method used to get updated trainer details from controller to pass the details to dao
     *
     * @param trainer   object
     * @return status of operation
     */
    @Override
    public Trainer updateTrainerData(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    /**
     * <h1> updateTraineeData </h1>
     * <p>
     * method used to get updated trainee details from controller to pass the details to dao
     *
     * @param trainee   object
     * @return status of operation
     */
    @Override
    public Trainee updateTraineeData(Trainee trainee) {
        return traineeRepository.save(trainee);
    }

    /**
     * <h1> deleteTrainerData </h1>
     * <p>
     * method used to get employeeId from controller to pass the employeeId to dao
     *
     * @param trainerId for
     */
    @Override
    public void deleteTrainerData(Trainer trainer, int trainerId) {
        Trainer trainer1 = trainerRepository.findById(trainerId).orElseThrow(()
                -> new EmployeeNotFoundException("trainer not found "));
        Set<Trainee> trainee = trainer.getTrainees();
        trainee.removeAll(trainee);
        this.updateTrainerData(trainer);
        trainer1.setIsActive(true);
        trainerRepository.save(trainer1);
    }

    /**
     * <h1> deleteTraineeData </h1>
     * <p>
     * method used to get employeeId from controller to pass the employeeId to dao for deleting trainer details
     *
     * @param traineeId for
     */
    @Override
    public void deleteTraineeData(Trainee trainee, int traineeId) {
        Trainee trainee1 = traineeRepository.findById(traineeId).orElseThrow(()
                -> new EmployeeNotFoundException("Trainee not found "));
        Set<Trainer> trainer = trainee.getTrainers();
        trainer.removeAll(trainer);
        this.updateTraineeData(trainee);
        trainee1.setIsActive(true);
        traineeRepository.save(trainee1);
    }

}  


      
 
   