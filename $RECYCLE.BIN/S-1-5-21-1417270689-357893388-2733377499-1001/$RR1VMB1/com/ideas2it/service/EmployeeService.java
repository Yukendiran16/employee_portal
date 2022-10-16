package com.ideas2it.service;

import java.util.Map;
import java.util.Scanner;

import com.ideas2it.model.Employee;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.dao.EmployeeDao;

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
     *method used to get trainer details from dao
     *@return {@link Map<String,Trainer>} returns trainersData 
     */ 
     Map<String, Trainer> getTrainersData();        

    /**
     *method used to get trainer details from dao
     *@return {@link Map<String,Trainer>} returns trainersData 
     */ 
     Map<String, Trainee> getTraineesData();

    /**
     *method used to get trainer details from controller to pass the details to dao
     *@param {@link String} uuidIsKey
     *@param {@link Trainer} trainer object
     *@return {@link void} returns nothing
     */ 
     void addTrainer(String uuidIsKey, Trainer trainer);

    /**
     *method used to get trainee details from controller to pass the details to dao
     *@param {@link String} uuidIsKey
     *@param {@link Trainee} trainee object
     *@return {@link void} returns nothing
     */  
     void addTrainee(String uuidIsKey, Trainee trainee);

    /**
     *method used to get uuidIsKey from controller to pass the uuidIsKey to dao
     *@param {@link String} uuidIsKey
     *@return {@link Trainer} returns trainersData
     */
     Trainer searchTrainerData(String uuidIsKey);

    /**
     *method used to get uuidIsKey from controller to pass the uuidIsKey to dao
     *@param {@link String} uuidIsKey
     *@return {@link Trainee} returns traineesData
     */
     Trainee searchTraineeData(String uuidIsKey);

    /**
     *method used to get updated trainer details from controller to pass the details to dao
     *@param {@link String} uuidIsKey
     *@param {@link Trainer} trainer object
     *@return {@link void} returns nothing
     */ 
     void updateTrainerData(String uuidIsKey, Trainer trainer);

    /**
     *method used to get updated trainee details from controller to pass the details to dao
     *@param {@link String} uuidIsKey
     *@param {@link Trainee} trainee object
     *@return {@link void} returns nothing
     */
     void updateTraineeData(String uuidIsKey, Trainee trainee);

    /**
     *method used to get uuidIsKey from controller to pass the uuidIsKey to dao
     *@param {@link String} uuidIsKey
     *@return {@link void} returns nothing
     */
     void deleteSingleTrainerData(String uuidIsKey);

    /**
     *method used to get uuidIsKey from controller to pass the uuidIsKey to dao for deleting trainer details
     *@param {@link String} uuidIsKey
     *@return {@link void} returns nothing
     */ 
     void deleteSingleTraineeData(String uuidIsKey);
}