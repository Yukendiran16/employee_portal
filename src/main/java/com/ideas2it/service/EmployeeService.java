package com.ideas2it.service;

import com.ideas2it.Dto.TraineeRequestDto;
import com.ideas2it.Dto.TrainerRequestDto;
import com.ideas2it.Dto.TrainerResponseDto;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

/**
 * <h2>EmployeeService</h2>
 * <p>
 * The EmployeeService class is an Interface and
 * The class implements an application that
 * defines signature of all methods used in EmployeeServiceImpl class
 * EmployeeServiceImpl is an implementation of this Interface
 * </p>
 *
 * @author Yukendiran K
 * @version 1.0
 * @since 2022-08-04
 */
public interface EmployeeService {

     /**
     * <h1> addTrainer </h1>
     * <p>
     * method used to get trainer details from controller to pass the details to dao
     *
     * @param trainerDto object
     * @return status of operation
     */
    TrainerResponseDto addTrainer(TrainerRequestDto trainerDto);

    /**
     * <h1> addTrainee </h1>
     * <p>
     * method used to get trainee details from controller to pass the details to dao
     *
     * @param traineeDto object
     * @return status of operation
     */
    Trainee addTrainee(TraineeRequestDto traineeDto);

    /**
     * <h1> getTrainersData </h1>
     * <p>
     * method used to call the dao for get all trainer details
     *
     * @return {@link List<Trainer>} returns trainers Data
     */
    List<Trainer> getTrainersData() ;

    /**
     * <h1> searchTrainerData </h1>
     * <p>
     * method used to get employeeId from controller to pass the employeeId to dao
     *
     * @param trainerId for
     * @return trainer Data
     */
    Trainer searchTrainerData(int trainerId) ;

    /**
     * <h1> getTraineesData </h1>
     * <p>
     * method used to call the dao for get all trainee details
     *
     * @return trainees Data
     */
    List<Trainee> getTraineesData() ;

    /**
     * <h1> getTraineesData </h1>
     * <p>
     * method used to call the dao for get all trainee details
     *
     * @return trainees Data
     */
    Trainee searchTraineeData(int traineeId) ;

    /**
     * <h1> updateTrainerData </h1>
     * <p>
     * method used to get updated trainer details from controller to pass the details to dao
     *
     * @param trainer   object
     * @return status of operation
     */
    Trainer updateTrainerData(Trainer trainer);

    /**
     * <h1> updateTraineeData </h1>
     * <p>
     * method used to get updated trainee details from controller to pass the details to dao
     *
     * @param trainee   object
     * @return status of operation
     */
    Trainee updateTraineeData(Trainee trainee);

    /**
     * <h1> deleteTrainerData </h1>
     * <p>
     * method used to get employeeId from controller to pass the employeeId to dao
     *
     * @param trainerId for
     */

    void deleteTrainerData(Trainer trainer, int trainerId) ;

    /**
     * <h1> deleteTraineeData </h1>
     * <p>
     * method used to get employeeId from controller to pass the employeeId to dao for deleting trainer details
     *
     * @param traineeId for
     */

    void deleteTraineeData(Trainee trainee, int traineeId) ;

}