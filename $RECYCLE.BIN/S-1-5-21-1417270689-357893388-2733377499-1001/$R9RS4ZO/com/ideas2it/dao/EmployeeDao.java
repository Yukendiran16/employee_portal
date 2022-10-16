package com.ideas2it.dao;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

import com.ideas2it.model.Employee;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.config.DataBaseConnection;

/**
*
* <h2>EmployeDao</h2>
*
* The EmployeeDao class is an Interface and 
* The class implements an application that
* defines signature of all methods used in EmployeeDaoImpl class
* EmployeeDaoImpl is an implementation of this Interface
*
* @author  Yukendiran K
* @version 1.0
* @since   2022-08-04 
*
*/

public interface EmployeeDao {

    /**
     * method used to get trainer details from EmployeeService and insert details to trainer table
     * @param {@link String} employeeId
     * @param {@link Trainer} trainer object
     * @param {@link Logger} logger 
     * @return {@link void} returns nothing
     */ 
    void insertTrainer(Trainer trainer) throws SQLException;

    /**
     * method used to get trainee details from EmployeeService and insert details to trainee table
     * @param {@link String} employeeId
     * @param {@link Trainee} trainee object
     * @param {@link Logger} logger 
     * @return {@link void} returns nothing
     */ 
    void insertTrainee(Trainee trainee) throws SQLException;

    /**
     * method used to get all trainer details in trainer table
     * @param {@link Logger} logger 
     * @return {@link List<Trainer>} returns trainers Data
     */ 
    List<Trainer> retrieveTrainers() throws SQLException;

    /**
     * method used to get trainer details by using employeeId
     * @param {@link String} employeeId
     * @param {@link Logger} logger 
     * @return {@link Trainer} returns trainer Data
     */ 
    Trainer retrieveTrainer(String employeeId) throws SQLException;

    /**
     * method used to retrieve trainee details by using employeeId
     * @param {@link String} employeeId
     * @return {@link List<Trainee>} returns trainees Data
     */
    List<Trainee> retrieveTrainees() throws SQLException;

    /**
     * method used to retrieve trainee details by using employeeId
     * @param {@link String} employeeId
     * @param {@link Logger} logger 
     * @return {@link Trainee} returns trainee Data
     */
    Trainee retrieveTrainee(String employeeId) throws SQLException;

    /**
     * method used to update trainer details by using employeeId
     * @param {@link String} employeeId
     * @param {@link Trainer} trainer object
     * @param {@link Logger} logger 
     * @return {@link void} returns nothing
     */ 
    void updateTrainer(String employeeId, Trainer trainer) throws SQLException;

    /**
     * method used to update trainee details by using emplpoyeeId
     * @param {@link String} employeeId
     * @param {@link Trainee} trainee object
     * @param {@link Logger} logger 
     * @return {@link void} returns nothing
     */ 
    void updateTrainee(String employeeId, Trainee trainee) throws SQLException;

    /**
     * method used to remove trainer details by using employeeId
     * @param {@link String} employeeId
     * @param {@link Logger} logger 
     * @return {@link void} returns nothing
     */
    void removeTrainer(String employeeId) throws SQLException;

    /**
     * method used to remove trainee details by using employeeId
     * @param {@link String} employeeId
     * @param {@link Logger} logger 
     * @return {@link void} returns nothing
     */
    void removeTrainee(String employeeId) throws SQLException;

    void assignTrainerForTrainees(String trainerId, List<String> traineeId) throws SQLException;

    void assignTraineeForTrainers(String traineeId, List<String> trainerId) throws SQLException;

    List<Trainee> retrieveAssociatedTrainees(String employeeId) throws SQLException;

    List<Trainer> retrieveAssociatedTrainers(String employeeId) throws SQLException;

    void updateAndAssignTraineeForTrainer(String trainerId, List<String> traineeId) throws SQLException;

    void updateAndAssignTrainerForTrainee(String traineeId, List<String> trainerId) throws SQLException;

    void updateAndAssignTraineeForTrainers(String traineeId) throws SQLException;

    void updateAndAssignTrainerForTrainees(String trainerId) throws SQLException;

    void deleteAssociationTrainerToTrainee( String traineeId, String trainerId) throws SQLException;

    void deleteAssociationTraineeToTrainer( String trainerId, String traineeId) throws SQLException;

    void deleteAssocitedTrainer( String trainerId) throws SQLException;

    void deleteAssocitedTrainee( String traineeId) throws SQLException;

    int getLastTrainerId() throws SQLException;

    int getLastTraineeId() throws SQLException;
}