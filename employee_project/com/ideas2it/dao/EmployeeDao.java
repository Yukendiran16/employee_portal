package com.ideas2it.dao;

import java.util.Map;
import java.util.HashMap;

import com.ideas2it.model.Employee;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;

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
     * method used to get trainer details from trainer map
     * @return {@link Map<String,Trainer>} returns trainersData 
     */  
    public Map<String, Trainer> getTrainersData(); 

    /**
     * method used to get trainer details from trainee map
     * @return {@link Map<String,Trainee>} returns traineesData 
     */ 
    public Map<String, Trainee> getTraineesData();

    /**
     * method used to get trainer details from EmployeeService and insert details to trainermap
     * @param {@link String} uuidIsKey
     * @param {@link Trainer} trainer object
     * @return {@link void} returns nothing
     */ 
    public void insertTrainer(String uuidIsKey, Trainer trainer);

    /**
     * method used to get trainee details from EmployeeService and insert details to trainee map
     * @param {@link String} uuidIsKey
     * @param {@link Trainee} trainee object
     * @return {@link void} returns nothing
     */ 
    public void insertTrainee(String uuidIsKey, Trainee trainee);

    /**
     * method used to get trainer details by using uuidIsKey
     * @param {@link String} uuidIsKey
     * @return {@link Trainer} returns trainersData
     */ 
    public Trainer retrieveTrainerData(String uuidIsKey);

    /**
     * method used to retrieve trainee details by using uuidIsKey
     * @param {@link String} uuidIsKey
     * @return {@link Trainee} returns traineesData
     */
    public Trainee retrieveTraineeData(String uuidIsKey);

    /**
     * method used to update trainer details by using uuidIsKey
     * @param {@link String} uuidIsKey
     * @param {@link Trainer} trainer object
     * @return {@link void} returns nothing
     */ 
    public void updateTrainerData(String uuidIsKey, Trainer trainer);

    /**
     * method used to update trainee details by using emplpoyeeId
     * @param {@link String} uuidIsKey
     * @param {@link Trainee} trainee object
     * @return {@link void} returns nothing
     */ 
    public void updateTraineeData(String uuidIsKey, Trainee trainee);

    /**
     * method used to remove trainer details by using uuidIsKey
     * @param {@link String} uuidIsKey
     * @return {@link void} returns nothing
     */
    public void removeSingleTrainerData(String uuidIsKey);

    /**
     * method used to remove trainee details by using uuidIsKey
     * @param {@link String} uuidIsKey
     * @return {@link void} returns nothing
     */
    public void removeSingleTraineeData(String uuidIsKey);
}