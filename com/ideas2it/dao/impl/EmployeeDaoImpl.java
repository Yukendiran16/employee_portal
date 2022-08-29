package com.ideas2it.dao.impl;

import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;

import com.ideas2it.model.Employee;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.dao.EmployeeDao;
import com.ideas2it.config.DataBaseConnection;
import com.ideas2it.hibernateUtil.GenerateFactory;


/**
 *
 * <h2>EmployeDaoImpl</h2>
 *
 * The EmployeeDaoImpl class is implements Employeedao and 
 * The class implements an application that
 * defines all methods used in EmployeeController class
 * the medhods perform insert, retrieve, modify, and remove object in mysql database
 * and return data's to EmployeeService 
 *
 * @author  Yukendiran K
 * @version 1.0
 * @since   2022-08-04 
 *
 */

public class EmployeeDaoImpl implements EmployeeDao{

    /**
     * method used to get trainer details from EmployeeService and insert details to traier table
     * @param {@link String} uuidIsKey
     * @param {@link Trainer} trainer object
     * @param {@link Logger} logger 
     * @return {@link String} returns nothing
     */           
    @Override
    public String insertTrainer(Trainer trainer) throws HibernateException, SQLException {

      Transaction tx = null;
      String message = "couldn't insert data";
      
      try (Session session = GenerateFactory.getFactory().openSession();) {
         tx = session.beginTransaction();
         session.persist(trainer);
         tx.commit();
         message = "insert successfully";
      } catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         throw e; 
      } 
      return message;
   }

    /**
     *method used to get trainee details from EmployeeService and insert details to trainee table
     *@param {@link String} uuidIsKey
     *@param {@link Trainee} trainee object
     * @param {@link Logger} logger 
     *@return {@link String} returns nothing
     */           
    @Override
    public String insertTrainee(Trainee trainee) throws HibernateException, SQLException {

      Transaction tx = null;
      String message = "couldn't insert data";
      
      try (Session session = GenerateFactory.getFactory().openSession();) {
         tx = session.beginTransaction();
         session.save(trainee);
         tx.commit();
         message = "insert successfully";
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         throw e; 
      } 
      return message;
   }
 
    /**
     * method used to get all trainer details in trainer table
     * @param {@link Logger} logger 
     * @return {@link List<Trainer>} returns list of trainers Data
     */           
    @Override
    public List<Trainer> retrieveTrainers() throws HibernateException, SQLException {

      List<Trainer> trainers = new ArrayList<>();

      Transaction tx = null;
      
      try (Session session = GenerateFactory.getFactory().openSession();) {
          tx = session.beginTransaction();
          Criteria criteria = session.createCriteria(Trainer.class);
          criteria.add(Restrictions.eq("is_Active", false));
          trainers  = criteria.list();
          tx.commit();
      } catch (Exception e) {
          if (tx!=null) tx.rollback();
          throw e; 
      } 
      return trainers;
    }

    /**
     * method used to get trainer details by using employeeId
     * @param {@link String} employeeId
     * @param {@link Logger} logger  
     * @return {@link Trainer} returns trainer Data
     */           
    @Override
    public Trainer retrieveTrainer(int trainerId) throws HibernateException, SQLException {
    
        Transaction tx = null;
        Trainer trainer = null;
        try(Session session = GenerateFactory.getFactory().openSession();) {

            tx = session.beginTransaction();
            trainer = (Trainer) session.get(Trainer.class, trainerId);
            tx.commit();
            return (trainer.getIs_Active() == false) ? trainer : null;
        } catch(Exception e) {
            if(tx!=null) tx.rollback();
            throw e;
        }
    }       

    /**
     * method used to get all trainee details in trainee table
     * @param {@link Logger} logger 
     * @return {@link List<Trainee>} returns list of trainees Data
     */  
    @Override
    public List<Trainee> retrieveTrainees() throws HibernateException, SQLException {
  
      List<Trainee> trainees = new ArrayList<>();

      Transaction tx = null;
      
      try ( Session session = GenerateFactory.getFactory().openSession();) {
          tx = session.beginTransaction();
          Criteria criteria = session.createCriteria(Trainee.class);
          criteria.add(Restrictions.eq("is_Active", false));
          trainees = criteria.list();
          tx.commit();
      } catch (Exception e) {
          if(tx!=null) tx.rollback();
          throw e; 
      } 
      return trainees;
    }

    /**
     * method used to retrieve trainee details by using employeeId
     * @param {@link String} employeeId
     * @param {@link Logger} logger 
     * @return {@link Trainee} returns trainee Data
     */           
    @Override
    public Trainee retrieveTrainee(int traineeId) throws HibernateException, SQLException {

        Transaction tx = null;
        Trainee trainee = null;
        try(Session session = GenerateFactory.getFactory().openSession();) {

            tx = session.beginTransaction();
            trainee = (Trainee) session.get(Trainee.class, traineeId);
            tx.commit();
            return (trainee.getIs_Active() == false) ? trainee : null;
        } catch(Exception e) {
            if(tx!=null) tx.rollback();
            throw e;
        } 
    }

    /**
     * method used to update trainer details by using employeeId
     * @param {@link String} employeeId
     * @param {@link Trainer} trainer object
     * @param {@link Logger} logger 
     * @return {@link String} returns nothing
     */           
    @Override
    public String updateTrainer(int trainerId, Trainer trainer) throws HibernateException, SQLException {

        Transaction tx = null;
        String message = "";
        try(Session session = GenerateFactory.getFactory().openSession();) {

            tx = session.beginTransaction();
            if (trainer.getIs_Active() == false) {
            Trainer updateTrainer = (Trainer) session.get(Trainer.class, trainerId);
            updateTrainer.setEmployeeName(trainer.getEmployeeName());
            updateTrainer.setEmployeeDateOfBirth(trainer.getEmployeeDateOfBirth());
            updateTrainer.setEmployeeDesignation(trainer.getEmployeeDesignation());
            updateTrainer.setEmployeeMail(trainer.getEmployeeMail());
            updateTrainer.setEmployeeMobileNumber(trainer.getEmployeeMobileNumber());
            updateTrainer.setCurrentAddress(trainer.getCurrentAddress());
            updateTrainer.setAadharCardNumber(trainer.getAadharCardNumber());
            updateTrainer.setPanCardNumber(trainer.getPanCardNumber());
            updateTrainer.setCurrentProject(trainer.getCurrentProject());
            updateTrainer.setAchievement(trainer.getAchievement());
            updateTrainer.setTrainees(trainer.getTrainees());

            tx.commit();
            message = "updated successfully";
            } else {
               message = "employee is not active";
            }
        } catch(Exception e) {
            if(tx!=null) tx.rollback();
            throw e;
        }
        return message;
    }

    /**
     * method used to update trainee details by using emplpoyeeId
     * @param {@link String} employeeId
     * @param {@link Trainee} trainee object
     * @param {@link Logger} logger 
     * @return {@link String} returns nothing
     */           
    @Override
    public String updateTrainee(int traineeId, Trainee trainee) throws HibernateException, SQLException {
 
        Transaction tx = null;
        String message = "";
        try(Session session = GenerateFactory.getFactory().openSession();) {

            tx = session.beginTransaction();
            if (trainee.getIs_Active() == false) {
                
            trainee.setEmployeeName(trainee.getEmployeeName());
            trainee.setEmployeeDateOfBirth(trainee.getEmployeeDateOfBirth());
            trainee.setEmployeeDesignation(trainee.getEmployeeDesignation());
            trainee.setEmployeeMail(trainee.getEmployeeMail());
            trainee.setEmployeeMobileNumber(trainee.getEmployeeMobileNumber());
            trainee.setCurrentAddress(trainee.getCurrentAddress());
            trainee.setAadharCardNumber(trainee.getAadharCardNumber());
            trainee.setPanCardNumber(trainee.getPanCardNumber());
            trainee.setCurrentTask(trainee.getCurrentTask());
            trainee.setCurrentTechknowledge(trainee.getCurrentTechknowledge());
            //trainee.setTrainer(trainee.getTrainer());

            tx.commit();
            message = "updated successfully";
            } else {
               message = "employee is not active"; 
            }
        } catch(Exception e) {
            if(tx!=null) {
                tx.rollback();
            }
            throw e;
        }
        return message;
    }             

    /**
     * method used to remove trainer details by using employeeId
     * @param {@link String} employeeId
     * @param {@link Logger} logger 
     * @return {@link String} returns nothing
     */           
    @Override
    public String removeTrainer(int trainerId) throws HibernateException, SQLException {

        Transaction tx = null;
        String message = "no operation ahead";

        try (Session session = GenerateFactory.getFactory().openSession();) {

            tx = session.beginTransaction();
            Trainer trainer = (Trainer) session.get(Trainer.class, trainerId);
            trainer.setIs_Active(true);
            session.update(trainer);
            System.out.println("update");
            tx.commit();
            message = "successfully deleted";
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            throw e;
        }
        return message;
    }     

    /**
     * method used to remove trainee details by using employeeId
     * @param {@link String} employeeId
     * @param {@link Logger} logger 
     * @return {@link String} returns nothing
     */           
    @Override
    public String removeTrainee(int traineeId) throws HibernateException, SQLException {

        Transaction tx = null;
        String message = "no operation ahead";

        try (Session session = GenerateFactory.getFactory().openSession();) {

            tx = session.beginTransaction();
            Trainee trainee = (Trainee) session.get(Trainee.class, traineeId);
            trainee.setIs_Active(true);
            session.update(trainee);
            tx.commit();
            message = "successfully deleted";
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            throw e;
        }
        return message;
    }
}


            