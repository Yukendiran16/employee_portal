package com.ideas2it.service.impl;

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
     * @param {@link }  
     * @return {@link void} returns nothing
     */           
    @Override
    public String addTrainer(Trainer trainer) throws SQLException, HibernateException {        
         return employeeDao.insertTrainer(trainer);
    }

    /**
     * method used to get trainee details from controller to pass the details to dao
     * @param {@link String} employeeId
     * @param {@link Trainee} trainee object
     * @param {@link }  
     * @return {@link String} returns nothing
     */           
    @Override        
    public String addTrainee(Trainee trainee) throws SQLException, HibernateException {
        return employeeDao.insertTrainee(trainee);
    }
 
    /**
     * method used to get employeeId from controller to pass the employeeId to dao
     * @param {@link }  
     * @return {@link List<Trainer>} returns trainers Data
     */           
    @Override
    public List<Trainer> getTrainersData() throws SQLException, HibernateException {
        List<Trainer> trainer = employeeDao.retrieveTrainers();
        return trainer;
    }

    /**
     * method used to get employeeId from controller to pass the employeeId to dao
     * @param {@link String} employeeId
     * @param {@link }  
     * @return {@link Trainer} returns trainer Data
     */           
    @Override
    public Trainer searchTrainerData(int id) throws SQLException, HibernateException {
        Trainer trainer = employeeDao.retrieveTrainer(id);
        return trainer;
    }

    /**
     * method used to get employeeId from controller to pass the employeeId to dao     
     * @param {@link }  
     * @return {@link List<Trainee>} returns trainees Data
     */           
    @Override   
    public List<Trainee> getTraineesData() throws SQLException, HibernateException {
        List<Trainee> trainee = employeeDao.retrieveTrainees();
        return trainee;
    }

    /**
     * method used to get employeeId from controller to pass the employeeId to dao
     * @param {@link String} employeeId
     * @param {@link }  
     * @return {@link Trainee} returns trainee Data
     */           
    @Override   
    public Trainee searchTraineeData(int id) throws SQLException, HibernateException {
        Trainee trainee = employeeDao.retrieveTrainee(id);
        return trainee;
    }

    /**
     * method used to get updated trainer details from controller to pass the details to dao
     * @param {@link String} employeeId
     * @param {@link Trainer} trainer object
     * @param {@link }  
     * @return {@link String} returns nothing
     */           
    @Override 
    public String updateTrainerData(Trainer trainer) throws SQLException, HibernateException {
        return employeeDao.updateTrainer(trainer);
    }

    /**
     * method used to get updated trainee details from controller to pass the details to dao
     * @param {@link String} employeeId
     * @param {@link Trainee} trainee object
     * @param {@link }  
     * @return {@link String} returns nothing
     */           
    @Override
    public String updateTraineeData(Trainee trainee) throws SQLException, HibernateException {
        return employeeDao.updateTrainee(trainee);
    }                         

    /**
     * method used to get employeeId from controller to pass the employeeId to dao
     * @param {@link String} employeeId
     * @param {@link }  
     * @return {@link String} returns nothing
     */           
    @Override
    public String deleteTrainerData(int id) throws SQLException, HibernateException {
        return employeeDao.removeTrainer(id);
    }     

    /**
     * method used to get employeeId from controller to pass the employeeId to dao for deleting trainer details
     * @param {@link String} employeeId
     * @param {@link }  
     * @return {@link String} returns nothing
     */           
    @Override
    public String deleteTraineeData(int id) throws SQLException, HibernateException {
        return employeeDao.removeTrainee(id);
    }                    
}  


      
 
   