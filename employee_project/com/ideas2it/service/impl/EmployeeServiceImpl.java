package com.ideas2it.service.impl;

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
import com.ideas2it.service.EmployeeService;
import com.ideas2it.dao.impl.EmployeeDaoImpl;
import com.ideas2it.dao.EmployeeDao;

/**
*
*<h2>EmployeeServiceImpl</h2>
*
* The EmployeeServiceImpl class is implements EmployeeService and 
* The class implements an application that
* defines all methods used in EmployeeController class
* the medhods perform pass the parameters to DAO class 
* and return data's to controller from Dao
* creates method generate employeeId
*
* @author  Yukendiran K
* @version 1.0
* @since   2022-08-04 
*
*/

public class EmployeeServiceImpl implements EmployeeService {

    private static EmployeeDao employeeDao = new EmployeeDaoImpl();

    /**
     * method used to get trainer details from controller to pass the details to dao
     * @param {@link String} employeeId
     * @param {@link Trainer} trainer object
     * @param {@link Logger} logger 
     * @return {@link void} returns nothing
     */           
    @Override
    public void addTrainer(String employeeId, Trainer trainer, Logger logger) throws SQLException {        
         employeeDao.insertTrainer(employeeId,trainer,logger);
    }

    /**
     * method used to get trainee details from controller to pass the details to dao
     * @param {@link String} employeeId
     * @param {@link Trainee} trainee object
     * @param {@link Logger} logger 
     * @return {@link void} returns nothing
     */           
    @Override        
    public void addTrainee(String employeeId, Trainee trainee, Logger logger) throws SQLException {
        employeeDao.insertTrainee(employeeId,trainee,logger);
    }
 
    /**
     * method used to get employeeId from controller to pass the employeeId to dao
     * @param {@link Logger} logger 
     * @return {@link List<Trainer>} returns trainers Data
     */           
    @Override
    public List<Trainer> getTrainersData(Logger logger) throws SQLException {
        List<Trainer> trainer = employeeDao.retrieveTrainers(logger);
        return trainer;
    }

    /**
     * method used to get employeeId from controller to pass the employeeId to dao
     * @param {@link String} employeeId
     * @param {@link Logger} logger 
     * @return {@link Trainer} returns trainer Data
     */           
    @Override
    public Trainer searchTrainerData(String employeeId, Logger logger) throws SQLException {
        Trainer trainer = employeeDao.retrieveTrainer(employeeId,logger);
        return trainer;
    }

    /**
     * method used to get employeeId from controller to pass the employeeId to dao     
     * @param {@link Logger} logger 
     * @return {@link List<Trainee>} returns trainees Data
     */           
    @Override   
    public List<Trainee> getTraineesData(Logger logger) throws SQLException {
        List<Trainee> trainee = employeeDao.retrieveTrainees(logger);
        return trainee;
    }

    /**
     * method used to get employeeId from controller to pass the employeeId to dao
     * @param {@link String} employeeId
     * @param {@link Logger} logger 
     * @return {@link Trainee} returns trainee Data
     */           
    @Override   
    public Trainee searchTraineeData(String employeeId, Logger logger) throws SQLException {
        Trainee trainee = employeeDao.retrieveTrainee(employeeId,logger);
        return trainee;
    }

    /**
     * method used to get updated trainer details from controller to pass the details to dao
     * @param {@link String} employeeId
     * @param {@link Trainer} trainer object
     * @param {@link Logger} logger 
     * @return {@link void} returns nothing
     */           
    @Override 
    public void updateTrainerData(String employeeId, Trainer trainer, Logger logger) throws SQLException {
        employeeDao.updateTrainer(employeeId,trainer,logger);
    }

    /**
     * method used to get updated trainee details from controller to pass the details to dao
     * @param {@link String} employeeId
     * @param {@link Trainee} trainee object
     * @param {@link Logger} logger 
     * @return {@link void} returns nothing
     */           
    @Override
    public void updateTraineeData(String employeeId, Trainee trainee, Logger logger) throws SQLException {
        employeeDao.updateTrainee(employeeId,trainee,logger);
    }                         

    /**
     * method used to get employeeId from controller to pass the employeeId to dao
     * @param {@link String} employeeId
     * @param {@link Logger} logger 
     * @return {@link void} returns nothing
     */           
    @Override
    public void deleteTrainerData(String employeeId, Logger logger) throws SQLException {
        employeeDao.removeTrainer(employeeId, logger);
    }     

    /**
     * method used to get employeeId from controller to pass the employeeId to dao for deleting trainer details
     * @param {@link String} employeeId
     * @param {@link Logger} logger 
     * @return {@link void} returns nothing
     */           
    @Override
    public void deleteTraineeData(String employeeId, Logger logger) throws SQLException {
        employeeDao.removeTrainee(employeeId, logger);
    }

    @Override
    public void createAssociation( List<String> trainerId, List<String> traineeId) throws SQLException {
        employeeDao.createAssociation(trainerId, traineeId);
    }

    @Override
    public List<Trainer> associateTrainer(String employeeId) throws SQLException {
        List<Trainer> trainers = employeeDao.retrieveAssociatedTrainers(employeeId);   
        return trainers; 
    } 

    @Override
    public List<Trainee> associateTrainee(String employeeId) throws SQLException {
        List<Trainee> trainees = employeeDao.retrieveAssociatedTrainees(employeeId);
        return trainees;
    } 

    @Override 
    public int getLastTrainerId() throws SQLException {
         return employeeDao.getLastTrainerId();
    } 

    @Override 
    public int getLastTraineeId() throws SQLException {
         return employeeDao.getLastTraineeId();
    }                          
}  


      
 
   