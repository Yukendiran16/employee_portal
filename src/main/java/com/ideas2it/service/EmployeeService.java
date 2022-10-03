package com.ideas2it.service;

import com.ideas2it.exception.EmployeeNotFoundException;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;

import java.util.List;
import java.util.Map;

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
     * @param trainer object
     * @return status of operation
     */
    String addTrainer(Trainer trainer);

    /**
     * <h1> addTrainee </h1>
     * <p>
     * method used to get trainee details from controller to pass the details to dao
     *
     * @param trainee object
     * @return status of operation
     */
    String addTrainee(Trainee trainee);

    /**
     * <h1> getTrainersData </h1>
     * <p>
     * method used to call the dao for get all trainer details
     *
     * @return {@link List<Trainer>} returns trainers Data
     */
    List<Trainer> getTrainersData() throws EmployeeNotFoundException;

    /**
     * <h1> searchTrainerData </h1>
     * <p>
     * method used to get employeeId from controller to pass the employeeId to dao
     *
     * @param trainerId for
     * @return trainer Data
     */
    Trainer searchTrainerData(int trainerId) throws EmployeeNotFoundException;

    /**
     * <h1> getTraineesData </h1>
     * <p>
     * method used to call the dao for get all trainee details
     *
     * @return trainees Data
     */
    List<Trainee> getTraineesData() throws EmployeeNotFoundException;

    /**
     * <h1> getTraineesData </h1>
     * <p>
     * method used to call the dao for get all trainee details
     *
     * @return trainees Data
     */
    Trainee searchTraineeData(int traineeId) throws EmployeeNotFoundException;

    /**
     * <h1> updateTrainerData </h1>
     * <p>
     * method used to get updated trainer details from controller to pass the details to dao
     *
     * @param trainerId for
     * @param trainer object
     * @return status of operation
     */
    String updateTrainerData(int trainerId, Trainer trainer);

    /**
     * <h1> updateTraineeData </h1>
     * <p>
     * method used to get updated trainee details from controller to pass the details to dao
     *
     * @param traineeId for
     * @param trainee object
     * @return status of operation
     */
    String updateTraineeData(int traineeId, Trainee trainee);

    /**
     * <h1> deleteTrainerData </h1>
     * <p>
     * method used to get employeeId from controller to pass the employeeId to dao
     *
     * @param trainerId for
     * @return status of operation
     */

    String deleteTrainerData(Trainer trainer, int trainerId) throws EmployeeNotFoundException;

    /**
     * <h1> deleteTraineeData </h1>
     * <p>
     * method used to get employeeId from controller to pass the employeeId to dao for deleting trainer details
     *
     * @param traineeId for
     * @return status of operation
     */

    String deleteTraineeData(Trainee trainee, int traineeId) throws EmployeeNotFoundException;

    Trainer updateTraineeListInTrainer(Trainer trainer, String[] idList) throws Exception;

    Trainee updateTrainerListInTrainee(Trainee trainee, String[] idList) throws EmployeeNotFoundException;

    Trainer deleteTraineeInTrainer(Trainer trainer, int traineeId) throws EmployeeNotFoundException;

    Trainee deleteTrainerInTrainee(Trainee trainer, int traineeId) throws EmployeeNotFoundException;

    Map<String, Object> getTrainer(Trainer trainer);

    Map<String, Object> getTrainee(Trainee trainee);

}