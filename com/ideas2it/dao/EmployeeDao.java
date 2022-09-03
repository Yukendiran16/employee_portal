package com.ideas2it.dao;

import java.lang.NullPointerException;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

import com.ideas2it.model.Employee;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.hibernateUtil.HibernateFactory;

/**
 *
 * <h2>EmployeDao</h2>
 * <p>
 * The EmployeeDao class is an Interface and 
 * The class implements an application that
 * defines signature of all methods used in EmployeeDaoImpl class
 * EmployeeDaoImpl is an implementation of this Interface
 * </p>
 * @author  Yukendiran K
 * @version 1.0
 * @since   2022-08-04 
 *
 */
public interface EmployeeDao {

    /**
     *
     * <h1> insertTrainer </h1>
     *
     * method used to get trainer details from EmployeeService and insert details to traier table
     *
     * @param {@link Trainer} trainer object
     * @return {@link String} returns nothing
     *
     */   
    String insertTrainer(Trainer trainer) throws SQLException, HibernateException, NullPointerException;

    /**
     *
     * <h1> insertTrainee </h1>
     *
     * method used to get trainee details from EmployeeService and insert details to trainee table
     *
     * @param {@link Trainee} trainee object
     * @return {@link String} returns nothing
     *
     */  
    String insertTrainee(Trainee trainee) throws SQLException, HibernateException, NullPointerException;

    /**
     *
     *<h1> retrieveTrainers</h1>
     *
     * method used to get all trainer details in trainer table
     *
     * @return {@link List<Trainer>} returns list of trainers Data
     *
     */  
    List<Trainer> retrieveTrainers() throws SQLException, HibernateException, NullPointerException;

    /**
     *
     * <h1> retrieveTrainer </h1>
     *
     * method used to get trainer details by using trainer Id
     *
     * @param {@link int} trainerId  
     * @return {@link Trainer} returns trainer Data
     *
     */  
    Trainer retrieveTrainer(int trainerId) throws SQLException, HibernateException, NullPointerException;

    /**
     *
     * <h1> retrieveTrainees </h1>
     *
     * method used to get all trainee details in trainee table
     * 
     * @return {@link List<Trainee>} returns list of trainees Data
     */  
    List<Trainee> retrieveTrainees() throws SQLException, HibernateException, NullPointerException;

    /**
     *
     * <h1> retrieveTrainee </h1>
     *
     * method used to retrieve trainee details by using trainee Id
     *
     * @param {@link String} trainee Id
     * @return {@link Trainee} returns trainee Data
     *
     */ 
    Trainee retrieveTrainee(int traineeId) throws SQLException, HibernateException, NullPointerException;

    /**
     *
     * <h1> updateTrainer </h1>
     *
     * method used to update trainer details by using trainer Id
     *
     * @param {@link String} trainer Id
     * @param {@link Trainer} trainer object
     * @return {@link String} returns nothing
     *
     */  
    String updateTrainer(int trainerId, Trainer trainer) throws SQLException, HibernateException, NullPointerException;

    /**
     *
     * <h1> updateTrainee </h1>
     *
     * method used to update trainee details by using trainee Id
     *
     * @param {@link String} trainee Id
     * @param {@link Trainee} trainee object
     * @return {@link String} returns nothing
     *
     */ 
    String updateTrainee(int traineeId, Trainee trainee) throws SQLException, HibernateException, NullPointerException;

    /**
     *
     * <h1> removetrainer </h1> 
     *
     * method used to remove trainer details by using trainer Id and it is a soft delete.
     * it means details is present but trainer not active.    
     *
     * @param {@link String} trainer Id 
     * @return {@link String} returns message. message contains status of the method.
     *
     */ 
    String removeTrainer(int trainerId) throws SQLException, HibernateException, NullPointerException;

    /**
     *
     * <h1> removeTrainee </h1>
     *
     * method used to remove trainee details by using trainee Id and it is a soft delete.
     * it means details is present but trainee not active.
     *
     * @param {@link String} trainee Id 
     * @return {@link String} returns message. message contains status of the method.
     *
     */   
    String removeTrainee(int traineeId) throws SQLException, HibernateException, NullPointerException;
}