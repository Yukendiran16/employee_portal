package com.ideas2it.dao.impl;

import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.ideas2it.model.Employee;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.dao.EmployeeDao;

/**
 *
 * <h2>EmployeDaoImpl</h2>
 *
 * The EmployeeDaoImpl class is implements Employeedao and 
 * The class implements an application that
 * defines all methods used in EmployeeController class
 * the medhods perform insert, retrieve, modify, and remove object in map interface 
 * and return data's to EmployeeService 
 *
 * @author  Yukendiran K
 * @version 1.0
 * @since   2022-08-04 
 *
 */

public class EmployeeDaoImpl implements EmployeeDao{

    private static Map<String,Trainer> trainersData = new HashMap<>();
    private static Map<String,Trainee> traineesData = new HashMap<>();

    /**
     *method used to get trainer details from trainer map
     *@return {@link Map<String,Trainer>} returns trainersData 
     */ 
    @Override 
    public Map<String,Trainer> getTrainersData() {
        return trainersData;
    } 

    /**
     *method used to get trainer details from trainee map
     *@return {@link Map<String,Trainee>} returns traineesData 
     */
    @Override  
    public Map<String,Trainee> getTraineesData() {
        return traineesData;
    } 

    /**
     *method used to get trainer details from EmployeeService and insert details to trainermap
     *@param {@link String} uuidIsKey
     *@param {@link Trainer} trainer object
     *@return {@link void} returns nothing
     */           
    @Override
    public void insertTrainer(String uuidIsKey,Trainer trainer) {
        trainersData.put(uuidIsKey,trainer);
    }

    /**
     *method used to get trainee details from EmployeeService and insert details to trainee map
     *@param {@link String} uuidIsKey
     *@param {@link Trainee} trainee object
     *@return {@link void} returns nothing
     */           
    @Override
    public void insertTrainee(String uuidIsKey,Trainee trainee) {
        traineesData.put(uuidIsKey,trainee);
    }
 
    /**
     *method used to get trainer details by using uuidIsKey
     *@param {@link String} uuidIsKey
     *@param {@link Trainer} trainee object
     *@return {@link Trainer} returns trainersData
     */           
    @Override
   public Trainer retrieveTrainerData(String uuidIsKey) {
        if (trainersData.containsKey(uuidIsKey)) {

            return trainersData.get(uuidIsKey);
        }
        return null;
    }    

    /**
     *method used to retrieve trainee details by using uuidIsKey
     *@param {@link String} uuidIsKey
     *@param {@link Trainee} trainee object
     *@return {@link Trainee} returns traineesData
     */           
    @Override
    public Trainee retrieveTraineeData(String uuidIsKey) {
        if (traineesData.containsKey(uuidIsKey)) {
            
            return traineesData.get(uuidIsKey);
            
        }
        return null;
    }

    /**
     *method used to update trainer details by using uuidIsKey
     *@param {@link String} uuidIsKey
     *@param {@link Trainer} trainer object
     *@return {@link void} returns nothing
     */           
    @Override
    public void updateTrainerData(String uuidIsKey, Trainer trainer) {
         trainersData.put(uuidIsKey,trainer);
    }

    /**
     *method used to update trainee details by using emplpoyeeId
     *@param {@link String} uuidIsKey
     *@param {@link Trainee} trainee object
     *@return {@link void} returns nothing
     */           
    @Override
    public void updateTraineeData(String uuidIsKey, Trainee trainee) {
         traineesData.put(uuidIsKey,trainee);
    }                          

    /**
     *method used to remove trainer details by using uuidIsKey
     *@param {@link String} uuidIsKey
     *@return {@link void} returns nothing
     */           
    @Override
    public void removeSingleTrainerData(String uuidIsKey)  {
        trainersData.remove(uuidIsKey);
    }     

    /**
     *method used to remove trainee details by using uuidIsKey
     *@param {@link String} uuidIsKey
     *@return {@link void} returns nothing
     */           
    @Override
    public void removeSingleTraineeData(String uuidIsKey) {               
        traineesData.remove(uuidIsKey);
    }
}  


            