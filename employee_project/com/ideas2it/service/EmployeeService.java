package com.ideas2it.service;

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
import com.ideas2it.dao.EmployeeDao;
import com.ideas2it.dao.impl.EmployeeDaoImpl;

/**
*
*<h2>EmployeeService</h2>
*
* The EmployeeService class is an Interface and 
* The class implements an application that
* defines signature of all methods used in EmployeeServiceImpl class
* EmployeeServiceImpl is an implementation of this Interface
*
* @author  Yukendiran K
* @version 1.0
* @since   2022-08-04
* 
*/

public interface EmployeeService {

    /**
     * method used to get trainer details from controller to pass the details to dao
     * @param {@link String} employeeId
     * @param {@link Trainer} trainer object
     * @param {@link Logger} logger 
     * @return {@link void} returns nothing
     */ 
    void addTrainer(String employeeId, Trainer trainer, Logger logger) throws SQLException;

    /**
     * method used to get trainee details from controller to pass the details to dao
     * @param {@link String} employeeId
     * @param {@link Trainee} trainee object
     * @param {@link Logger} logger 
     * @return {@link void} returns nothing
     */  
    void addTrainee(String employeeId, Trainee trainee, Logger logger) throws SQLException;

    /**
     * method used to get employeeId from controller to pass the employeeId to dao
     * @param {@link Logger} logger 
     * @return {@link List<Trainee>} returns trainees Data
     */              
    List<Trainer> getTrainersData(Logger logger) throws SQLException;

    /**
     * method used to get employeeId from controller to pass the employeeId to dao
     * @param {@link String} employeeId
     * @param {@link Logger} logger 
     * @return {@link Trainer} returns trainer Data
     */
    Trainer searchTrainerData(String employeeId, Logger logger) throws SQLException;

    /**
     * method used to get employeeId from controller to pass the employeeId to dao
     * @param {@link Logger} logger 
     * @return {@link List<Trainee>} returns trainees Data
     */             
    List<Trainee> getTraineesData(Logger logger) throws SQLException;

    /**
     * method used to get employeeId from controller to pass the employeeId to dao
     * @param {@link String} employeeId
     * @param {@link Logger} logger 
     * @return {@link Trainee} returns trainee Data
     */
    Trainee searchTraineeData(String employeeId, Logger logger) throws SQLException;

    /**
     * method used to get updated trainer details from controller to pass the details to dao
     * @param {@link String} employeeId
     * @param {@link Trainer} trainer object
     * @param {@link Logger} logger 
     * @return {@link void} returns nothing
     */ 
    void updateTrainerData(String employeeId, Trainer trainer, Logger logger) throws SQLException;

    /**
     * method used to get updated trainee details from controller to pass the details to dao
     * @param {@link String} employeeId
     * @param {@link Trainee} trainee object
     * @param {@link Logger} logger 
     * @return {@link void} returns nothing
     */
    void updateTraineeData(String employeeId, Trainee trainee, Logger logger) throws SQLException;

    /**
     * method used to get employeeId from controller to pass the employeeId to dao
     * @param {@link String} employeeId
     * @param {@link Logger} logger 
     * @return {@link void} returns nothing
     */
    void deleteTrainerData(String employeeId, Logger logger) throws SQLException;

    /**
     * method used to get employeeId from controller to pass the employeeId to dao for deleting trainer details
     * @param {@link String} employeeId
     * @param {@link Logger} logger 
     * @return {@link void} returns nothing
     */ 
    void deleteTraineeData(String employeeId, Logger logger) throws SQLException;

    public void createAssociation( List<String> trainerId, List<String> traineeId) throws SQLException;

    List<Trainer> associateTrainer(String employeeId) throws SQLException;

    List<Trainee> associateTrainee(String employeeId) throws SQLException;

    public int getLastTrainerId() throws SQLException;

    public int getLastTraineeId() throws SQLException;

}