package com.ideas2it.service;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException; 

import java.util.List;
import java.util.ArrayList;

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
     * @param {@link }  
     * @return {@link void} returns nothing
     */ 
    String addTrainer(Trainer trainer) throws SQLException, HibernateException;

    /**
     * method used to get trainee details from controller to pass the details to dao
     * @param {@link String} employeeId
     * @param {@link Trainee} trainee object
     * @param {@link }  
     * @return {@link String} returns nothing
     */  
    String addTrainee(Trainee trainee) throws SQLException, HibernateException;s

    /**
     * method used to get employeeId from controller to pass the employeeId to dao
     * @param {@link }  
     * @return {@link List<Trainee>} returns trainees Data
     */              
    List<Trainer> getTrainersData() throws SQLException, HibernateException;

    /**
     * method used to get employeeId from controller to pass the employeeId to dao
     * @param {@link String} employeeId
     * @param {@link }  
     * @return {@link Trainer} returns trainer Data
     */
    Trainer searchTrainerData(String employeeId) throws SQLException, HibernateException;

    /**
     * method used to get employeeId from controller to pass the employeeId to dao
     * @param {@link }  
     * @return {@link List<Trainee>} returns trainees Data
     */             
    List<Trainee> getTraineesData() throws SQLException, HibernateException;

    /**
     * method used to get employeeId from controller to pass the employeeId to dao
     * @param {@link String} employeeId
     * @param {@link }  
     * @return {@link Trainee} returns trainee Data
     */
    Trainee searchTraineeData(String employeeId) throws SQLException, HibernateException;

    /**
     * method used to get updated trainer details from controller to pass the details to dao
     * @param {@link String} employeeId
     * @param {@link Trainer} trainer object
     * @param {@link }  
     * @return {@link String} returns nothing
     */ 
    String updateTrainerData(String employeeId, Trainer trainer) throws SQLException, HibernateException;

    /**
     * method used to get updated trainee details from controller to pass the details to dao
     * @param {@link String} employeeId
     * @param {@link Trainee} trainee object
     * @param {@link }  
     * @return {@link String} returns nothing
     */
    String updateTraineeData(String employeeId, Trainee trainee) throws SQLException, HibernateException;

    /**
     * method used to get employeeId from controller to pass the employeeId to dao
     * @param {@link String} employeeId
     * @param {@link }  
     * @return {@link String} returns nothing
     */
    String deleteTrainerData(String employeeId) throws SQLException, HibernateException;

    /**
     * method used to get employeeId from controller to pass the employeeId to dao for deleting trainer details
     * @param {@link String} employeeId
     * @param {@link }  
     * @return {@link String} returns nothing
     */ 
    String deleteTraineeData(String employeeId) throws SQLException, HibernateException;

    void assignTrainerForTrainees(String trainerId, List<String> traineeId) throws SQLException;

    void assignTraineeForTrainers(String traineeId, List<String> trainerId) throws SQLException;

    List<Trainer> readTrainersOfGivenTrainee(String employeeId) throws SQLException;

    List<Trainee> readTraineesOfGivenTrainer(String employeeId) throws SQLException;

    void changeAndAssignTrainerForTrainees( String trainerId, List<String> traineeId) throws SQLException;
 
    void changeAndAssignTraineeForTrainers( String traineeId, List<String> trainerId) throws SQLException;

    void deleteAssociationTrainerToTrainee( String trainerId, String traineeId) throws SQLException;

    void deleteAssociationTraineeToTrainer( String traineeId, String trainerId) throws SQLException;
    
    int getLastTrainerId() throws SQLException, HibernateException;

    int getLastTraineeId() throws SQLException, HibernateException;

}