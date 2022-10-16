package com.ideas2it.service.impl;

import java.util.Map;
import java.util.Scanner;

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
    private static int sequence;

    /**
     *method used to get trainer details from dao
     *@return {@link Map<String,Trainer>} returns trainersData 
     */ 
    @Override
    public Map<String, Trainer> getTrainersData() {
        Map<String, Trainer> trainersData = employeeDao.getTrainersData();
        return trainersData;
    } 

    /**
     *method used to get trainer details from dao
     *@return {@link Map<String,Trainer>} returns trainersData 
     */
    @Override 
    public Map<String, Trainee> getTraineesData() {
        Map<String, Trainee> traineesData = employeeDao.getTraineesData();
        return traineesData;
    } 

    /**
     *method used to get trainer details from controller to pass the details to dao
     *@param {@link String} uuidIsKey
     *@param {@link Trainer} trainer object
     *@return {@link void} returns nothing
     */           
    @Override
    public void addTrainer(String uuidIsKey, Trainer trainer) {
        employeeDao.insertTrainer(uuidIsKey, trainer);
    }

    /**
     *method used to get trainee details from controller to pass the details to dao
     *@param {@link String} uuidIsKey
     *@param {@link Trainee} trainee object
     *@return {@link void} returns nothing
     */           
    @Override        
    public void addTrainee(String uuidIsKey, Trainee trainee) {
        employeeDao.insertTrainee(uuidIsKey,trainee);
    }
 
    /**
     *method used to get uuidIsKey from controller to pass the uuidIsKey to dao
     *@param {@link String} uuidIsKey
     *@return {@link Trainer} returns trainersData
     */           
    @Override
    public Trainer searchTrainerData(String uuidIsKey) {
        Trainer trainersData = employeeDao.retrieveTrainerData(uuidIsKey);
        return trainersData;
    } 

    /**
     *method used to get uuidIsKey from controller to pass the uuidIsKey to dao
     *@param {@link String} uuidIsKey
     *@return {@link Trainee} returns traineesData
     */           
    @Override   
    public Trainee searchTraineeData(String uuidIsKey) {
        Trainee traineesData = employeeDao.retrieveTraineeData(uuidIsKey);
        return traineesData;
    } 

    /**
     *method used to get updated trainer details from controller to pass the details to dao
     *@param {@link String} uuidIsKey
     *@param {@link Trainer} trainer object
     *@return {@link void} returns nothing
     */           
    @Override 
    public void updateTrainerData(String uuidIsKey, Trainer trainer) {
        employeeDao.updateTrainerData(uuidIsKey, trainer);

    }

    /**
     *method used to get updated trainee details from controller to pass the details to dao
     *@param {@link String} uuidIsKey
     *@param {@link Trainee} trainee object
     *@return {@link void} returns nothing
     */           
    @Override
    public void updateTraineeData(String uuidIsKey, Trainee trainee) {
        employeeDao.updateTraineeData(uuidIsKey, trainee);

    }                          

    /**
     *method used to get uuidIsKey from controller to pass the uuidIsKey to dao
     *@param {@link String} uuidIsKey
     *@return {@link void} returns nothing
     */           
    @Override
    public void deleteSingleTrainerData(String uuidIsKey)  {
        employeeDao.removeSingleTrainerData(uuidIsKey);
    }     

    /**
     *method used to get uuidIsKey from controller to pass the uuidIsKey to dao for deleting trainer details
     *@param {@link String} uuidIsKey
     *@return {@link void} returns nothing
     */           
    @Override
    public void deleteSingleTraineeData(String uuidIsKey) {               
        employeeDao.removeSingleTraineeData(uuidIsKey);
    }                                
}  


      
 
   