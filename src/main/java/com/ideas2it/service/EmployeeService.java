package com.ideas2it.service;

import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import org.hibernate.HibernateException;

import java.sql.SQLException;
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
     * @param {@link Trainer} trainer object
     * @return {@link String} returns status of operation
     */
    String addTrainer(Trainer trainer) throws SQLException, HibernateException, NullPointerException;

    ;

    /**
     * <h1> addTrainee </h1>
     * <p>
     * method used to get trainee details from controller to pass the details to dao
     *
     * @param {@link Trainee} trainee object
     * @return {@link String} returns status of operation
     */
    String addTrainee(Trainee trainee) throws SQLException, HibernateException, NullPointerException;

    /**
     * <h1> getTrainersData </h1>
     * <p>
     * method used to call the dao for get all trainer details
     *
     * @return {@link List<Trainer>} returns trainers Data
     */
    List<Trainer> getTrainersData() throws SQLException, HibernateException, NullPointerException;

    /**
     * <h1> searchTrainerData </h1>
     * <p>
     * method used to get employeeId from controller to pass the employeeId to dao
     *
     * @param {@link int} trainer Id
     * @return {@link Trainer} returns trainer Data
     */
    Trainer searchTrainerData(int trainerId) throws SQLException, HibernateException, NullPointerException;

    /**
     * <h1> getTraineesData </h1>
     * <p>
     * method used to call the dao for get all trainee details
     *
     * @return {@link List<Trainee>} returns trainees Data
     */
    List<Trainee> getTraineesData() throws SQLException, HibernateException, NullPointerException;

    /**
     * <h1> getTraineesData </h1>
     * <p>
     * method used to call the dao for get all trainee details
     *
     * @return {@link List<Trainee>} returns trainees Data
     */
    Trainee searchTraineeData(int traineeId) throws SQLException, HibernateException, NullPointerException;

    /**
     * <h1> updateTrainerData </h1>
     * <p>
     * method used to get updated trainer details from controller to pass the details to dao
     *
     * @param {@link int} trainer Id
     * @param {@link Trainer} trainer object
     * @return {@link String} returns status of operation
     */
    String updateTrainerData(int trainerId, Trainer trainer) throws SQLException, HibernateException, NullPointerException;

    /**
     * <h1> updateTraineeData </h1>
     * <p>
     * method used to get updated trainee details from controller to pass the details to dao
     *
     * @param {@link int} trainee Id
     * @param {@link Trainee} trainee object
     * @return {@link String} returns status of operation
     */
    String updateTraineeData(int traineeId, Trainee trainee) throws SQLException, HibernateException, NullPointerException;

    /**
     * <h1> deleteTrainerData </h1>
     * <p>
     * method used to get employeeId from controller to pass the employeeId to dao
     *
     * @param {@link String} employeeId
     * @return {@link String} returns status of operation
     */
    String deleteTrainerData(int trainerId) throws SQLException, HibernateException, NullPointerException;

    /**
     * <h1> deleteTraineeData </h1>
     * <p>
     * method used to get employeeId from controller to pass the employeeId to dao for deleting trainer details
     *
     * @param {@link int} trainee Id
     * @return {@link String} returns status of operation
     */
    String deleteTraineeData(int traineeId) throws SQLException, HibernateException, NullPointerException;

}