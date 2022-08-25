package com.ideas2it.dao.impl;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;

import com.ideas2it.model.Employee;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.dao.EmployeeDao;
import com.ideas2it.config.DataBaseConnection;
import com.ideas2it.ManageTrainer;


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
    public String insertTrainer(Trainer trainer) throws HibernateException {

      Transaction tx = null;
      String message = "couldn't insert data";
      
      try (Session session = ManageTrainer.getFactory().openSession();) {
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
    public String insertTrainee(Trainee trainee) throws HibernateException {

      Transaction tx = null;
      String message = "couldn't insert data";
      
      try (Session session = ManageTrainer.getFactory().openSession();) {
         tx = session.beginTransaction();
         session.save(trainee);
         tx.commit();
         message = "insert successfully";
      } catch (HibernateException e) {
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
    public List<Trainer> retrieveTrainers() throws HibernateException {

      List<Trainer> trainers = new ArrayList<>();

      Transaction tx = null;
      
      try  
        ( Session session = ManageTrainer.getFactory().openSession();) {
         tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Trainer.class);
            criteria.add(Restrictions.eq("isDeleted", false));
            List<Trainer> results = criteria.list();
            trainers = results;
            tx.commit();
      } catch (HibernateException e) {
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
    public Trainer retrieveTrainer(String employeeId) throws HibernateException {
    
        Transaction tx = null;
        Trainer trainer = null;
        try(Session session = ManageTrainer.getFactory().openSession();) {

            tx = session.beginTransaction();
            int id = Integer.valueOf(employeeId.substring(7));
            trainer = (Trainer) session.get(Trainer.class, id);
            tx.commit();
            return (trainer.getIs_Active() == false) ? trainer : null;
        } catch(HibernateException e) {
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
    public List<Trainee> retrieveTrainees() throws HibernateException {
  
      List<Trainee> trainees = new ArrayList<>();

      Transaction tx = null;
      
      try ( Session session = ManageTrainer.getFactory().openSession();) {
          tx = session.beginTransaction();
          Criteria criteria = session.createCriteria(Trainee.class);
          criteria.add(Restrictions.eq("is_Active", false));
          List<Trainee> results = criteria.list();
          trainees = results;
          tx.commit();
      } catch (HibernateException e) {
          if (tx!=null) tx.rollback();
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
    public Trainee retrieveTrainee(String employeeId) throws HibernateException {

        Transaction tx = null;
        Trainee trainee = null;
        try(Session session = ManageTrainer.getFactory().openSession();) {

            tx = session.beginTransaction();
            int id = Integer.valueOf(employeeId.substring(7));
            trainee = (Trainee) session.get(Trainee.class, id);
            tx.commit();
            return (trainee.getIs_Active() == false) ? trainee : null;
        } catch(HibernateException e) {
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
    public String updateTrainer(String employeeId, Trainer trainer) throws HibernateException {

        Transaction tx = null;
        String message = "";
        try(Session session = ManageTrainer.getFactory().openSession();) {

            tx = session.beginTransaction();
            int id = Integer.valueOf(employeeId.substring(7));
            Trainer updateTrainer = (Trainer) session.get(Trainer.class, id);
            if (updateTrainer.getIs_Active() == false) {

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

            tx.commit();
            message = "updated successfully";
            } else {
               message = "employee is not active";
            }
        } catch(HibernateException e) {
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
    public String updateTrainee(String employeeId, Trainee trainee) throws HibernateException {
 
        Transaction tx = null;
        String message = "";
        try(Session session = ManageTrainer.getFactory().openSession();) {

            tx = session.beginTransaction();
            int id = Integer.valueOf(employeeId.substring(7));
            Trainee updateTrainee = (Trainee) session.get(Trainee.class, id);
            if (updateTrainee.getIs_Active() == false) {
                
            updateTrainee.setEmployeeName(trainee.getEmployeeName());
            updateTrainee.setEmployeeDateOfBirth(trainee.getEmployeeDateOfBirth());
            updateTrainee.setEmployeeDesignation(trainee.getEmployeeDesignation());
            updateTrainee.setEmployeeMail(trainee.getEmployeeMail());
            updateTrainee.setEmployeeMobileNumber(trainee.getEmployeeMobileNumber());
            updateTrainee.setCurrentAddress(trainee.getCurrentAddress());
            updateTrainee.setAadharCardNumber(trainee.getAadharCardNumber());
            updateTrainee.setPanCardNumber(trainee.getPanCardNumber());
            updateTrainee.setCurrentTask(trainee.getCurrentTask());
            updateTrainee.setCurrentTechknowledge(trainee.getCurrentTechknowledge());

            tx.commit();
            message = "updated successfully";
            } else {
               message = "employee is not active"; 
            }
        } catch(HibernateException e) {
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
    public String removeTrainer(String employeeId) throws HibernateException {

        Transaction tx = null;
        String message = "no operation ahead";

        try (Session session = ManageTrainer.getFactory().openSession();) {

            tx = session.beginTransaction();
            int id = Integer.valueOf(employeeId.substring(7));
            Trainer trainer = (Trainer) session.get(Trainer.class, id);
            trainer.setIs_Active(true);
            session.update(trainer);
            System.out.println("update");
            tx.commit();
            message = "successfully deleted";
        } catch (HibernateException e) {
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
    public String removeTrainee(String employeeId) throws HibernateException {

        Transaction tx = null;
        String message = "no operation ahead";

        try (Session session = ManageTrainer.getFactory().openSession();) {

            tx = session.beginTransaction();
            int id = Integer.valueOf(employeeId.substring(7));
            Trainee trainee = (Trainee) session.get(Trainee.class, id);
            trainee.setIs_Active(true);
            session.update(trainee);
            tx.commit();
            message = "successfully deleted";
        } catch (HibernateException e) {
            if(tx != null) tx.rollback();
            throw e;
        }
        return message;
    }

    @Override
    public void assignTrainerForTrainees(String trainerId, List<String> traineeId) throws SQLException {

        String sql = "insert into association(trainerId, traineeId, trId, teId) value(?, ?, ?, ?)";

        try (Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);) {
 
            for (String trainee : traineeId) {

	        statement.setString(1,trainerId);
                statement.setString(2,trainee);
	        statement.setInt(3,Integer.valueOf(trainerId.substring(7)));
                statement.setInt(4,Integer.valueOf(trainee.substring(7)));
                statement.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void assignTraineeForTrainers(String traineeId, List<String> trainerId) throws SQLException {

        String sql = "insert into association(trainerId, traineeId, trId, teId) value(?, ?, ?, ?)";

        try (Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);) {
 
            for (String trainer : trainerId) {
	        statement.setString(1,trainer);
                statement.setString(2,traineeId);
	        statement.setInt(3,Integer.valueOf(trainer.substring(7)));
                statement.setInt(4,Integer.valueOf(traineeId.substring(7)));
                statement.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * method used to get trainer details by using employeeId
     * @param {@link String} employeeId
     * @param {@link Logger} logger  
     * @return {@link Trainer} returns trainer Data
     */           
    @Override
    public List<Trainee> retrieveAssociatedTrainees(String employeeId) throws SQLException {

        List<Trainee> trainees = new ArrayList<Trainee>();

        String sql = "select trainee.employeeId, trainee.name from association inner join trainee on trainee.employeeId = association.traineeid inner join trainer on association.trainerId = trainer.employeeId where association.trainerId = (?)";

        try (Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, employeeId);

            try (ResultSet result=statement.executeQuery();) {

                while (result.next()) {
                    Trainee trainee = new Trainee();
                    trainee.setEmployeeId(result.getString("employeeId"));
                    trainee.setEmployeeName(result.getString("name"));
                    trainees.add(trainee);
                }
            } 
            return trainees; 
          
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * method used to get trainer details by using employeeId
     * @param {@link String} employeeId
     * @param {@link Logger} logger  
     * @return {@link Trainer} returns trainer Data
     */           
    @Override
    public List<Trainer> retrieveAssociatedTrainers(String employeeId) throws SQLException {

        List<Trainer> trainers = new ArrayList<Trainer>();

        String sql = "select trainer.employeeId, trainer.name from association inner join trainee on trainee.employeeId = association.traineeId inner join trainer on association.trainerId = trainer.employeeId where association.traineeId = (?)";
        System.out.println("jfrhvhvjvjv");

        try (Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, employeeId);

            try (ResultSet result=statement.executeQuery();) {

                while (result.next()) {
                    Trainer trainer = new Trainer();
                    trainer.setEmployeeId(result.getString("employeeId"));
                    trainer.setEmployeeName(result.getString("name"));
                    trainers.add(trainer);
                }
            }          
            return trainers;

        } catch (SQLException e) {
            throw e;
        }
    }



    @Override
    public void updateAndAssignTraineeForTrainers(String traineeId, List<String> trainerId) throws SQLException {

        String sql = "update association set traineeId = (?), teId = (?) where = traineeId = (?)";

        try (Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setString(2,traineeId);
            statement.setInt(4,Integer.valueOf(traineeId.substring(7)));
            statement.setString(2,traineeId);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void updateAndAssignTrainerForTrainees(String trainerId, List<String> traineeId) throws SQLException {

        String sql = "update association set trainerId = (?), trId = (?) where trainerId = (?)";

        try (Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);) {
 
	    statement.setString(1,trainerId);
	    statement.setInt(3,Integer.valueOf(trainerId.substring(7)));
	    statement.setString(1,trainerId);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * method used to remove trainer details by using employeeId
     * @param {@link String} employeeId
     * @param {@link Logger} logger 
     * @return {@link String} returns nothing
     */           
    @Override
    public void deleteAssociationTrainerToTrainee( String traineeId, String trainerId) throws SQLException {

        String sql = "delete from association where trId = (?) and teId = (?)";

        try (Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);) { 
           
	    statement.setInt(3,Integer.valueOf(trainerId.substring(7)));
            statement.setInt(4,Integer.valueOf(traineeId.substring(7)));
            statement.executeUpdate();  

        } catch (SQLException e) {
            throw e;
        }
    }     

    /**
     * method used to remove trainee details by using employeeId
     * @param {@link String} employeeId
     * @param {@link Logger} logger 
     * @return {@link String} returns nothing
     */           
    @Override
    public void deleteAssociationTraineeToTrainer( String trainerId, String traineeId) throws SQLException {

        String sql = "delete from association where trId = (?) and teId = (?)";

        try (Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);) { 
           
	    statement.setInt(3,Integer.valueOf(trainerId.substring(7)));
            statement.setInt(4,Integer.valueOf(traineeId.substring(7)));
            statement.executeUpdate();  

        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * method used to get trainer details by using employeeId
     * @param {@link String} employeeId
     * @param {@link Logger} logger  
     * @return {@link Trainer} returns trainer Data
     */

    @Override
    public int getLastTrainerId() throws HibernateException {

        Transaction tx = null;
        try (Session session = ManageTrainer.getFactory().openSession();) {

            tx = session.beginTransaction();
            Trainer trainer = (Trainer) session.get(Trainer.class, Projections.max("id"));
            int value = trainer.getId();
            return value;

        } catch (HibernateException e) {
            throw e;
        }
    }       

    @Override
    public int getLastTraineeId() throws HibernateException {

        Transaction tx = null;
        try (Session session = ManageTrainer.getFactory().openSession();) {

            tx = session.beginTransaction();
            Trainee trainee = (Trainee) session.get(Trainee.class, Projections.max("id"));
            int value = trainee.getId();
            return value;
        } catch (HibernateException e) {
            throw e;
        }
    }       
}


            