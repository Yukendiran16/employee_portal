package com.ideas2it.dao.impl;

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
import com.ideas2it.dao.EmployeeDao;
import com.ideas2it.config.DataBaseConnection;

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

    //private static DataBaseConnection dbConnection = new DataBaseConnection(); 

    /**
     * method used to get trainer details from EmployeeService and insert details to traier table
     * @param {@link String} uuidIsKey
     * @param {@link Trainer} trainer object
     * @param {@link Logger} logger 
     * @return {@link void} returns nothing
     */           
    @Override
    public void insertTrainer(String uuidIsKey, Trainer trainer, Logger logger) throws SQLException {

        String sql = "insert into trainer(uuidIsKey, companyName, name, dateOfBirth, Designation, mail, mobileNumber, address, aadharCardNumber, panCardNumber, currentProject, achievement, employeeId) value(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setString(1,uuidIsKey);
            statement.setString(2,trainer.companyName);
            statement.setString(3,trainer.getEmployeeName());
            statement.setString(4,trainer.getEmployeeDateOfBirth());
            statement.setString(5,trainer.getEmployeeDesignation());
            statement.setString(6,trainer.getEmployeeMail());
            statement.setString(7,trainer.getEmployeeMobileNumber());
            statement.setString(8,trainer.getCurrentAddress());
            statement.setString(9,trainer.getAadharCardNumber());
            statement.setString(10,trainer.getPanCardNumber());
            statement.setString(11,trainer.getCurrentProject());
            statement.setString(12,trainer.getAchievement());
            statement.setString(13,trainer.getEmployeeId());

            statement.executeUpdate();
 
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     *method used to get trainee details from EmployeeService and insert details to trainee table
     *@param {@link String} uuidIsKey
     *@param {@link Trainee} trainee object
     * @param {@link Logger} logger 
     *@return {@link void} returns nothing
     */           
    @Override
    public void insertTrainee(String uuidIsKey, Trainee trainee, Logger logger) throws SQLException {

        String sql = "insert into trainee(uuidIsKey, companyName, name, dateOfBirth, Designation, mail, mobileNumber, address, aadharCardNumber, panCardNumber, currentTask, currentTechknowledge, employeeId) value(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);) { 

            statement.setString(1,uuidIsKey);          
            statement.setString(2,trainee.companyName);
            statement.setString(3,trainee.getEmployeeName());
            statement.setString(4,trainee.getEmployeeDateOfBirth());
            statement.setString(5,trainee.getEmployeeDesignation());
            statement.setString(6,trainee.getEmployeeMail());
            statement.setString(7,trainee.getEmployeeMobileNumber());
            statement.setString(8,trainee.getCurrentAddress());
            statement.setString(9,trainee.getAadharCardNumber());
            statement.setString(10,trainee.getPanCardNumber());
            statement.setString(11,trainee.getCurrentTask());
            statement.setString(12,trainee.getCurrentTechknowledge());
            statement.setString(13,trainee.getEmployeeId());
            
            statement.executeUpdate();
 
        } catch (SQLException e) {
            throw e;
        }
    }
 
    /**
     * method used to get all trainer details in trainer table
     * @param {@link Logger} logger 
     * @return {@link List<Trainer>} returns list of trainers Data
     */           
    @Override
    public List<Trainer> retrieveTrainers(Logger logger) throws SQLException {

        List<Trainer> trainers = new ArrayList<>();
        
        String sql = "select * from Trainer";

        try (Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql); 
            ResultSet result=statement.executeQuery();) {

            while (result.next()) {
                Trainer trainer = new Trainer();
                trainer.setEmployeeId(result.getString(3));
                trainer.setEmployeeName(result.getString(4));
                trainer.setEmployeeDesignation(result.getString(6));
                trainer.setEmployeeMail(result.getString(7));
                trainer.setEmployeeMobileNumber(result.getString(8));
                trainer.setCurrentAddress(result.getString(9));
                trainers.add(trainer);
            }
            return trainers;
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
    public Trainer retrieveTrainer(String employeeId, Logger logger) throws SQLException {
    
        Trainer tr = null;
        String sql = "select * from Trainer where employeeId = ? ";

        try (Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, employeeId);

            try (ResultSet result=statement.executeQuery();) {

            while (result.next()) {
                Trainer trainer = new Trainer();
                trainer.setEmployeeId(result.getString(3));
                trainer.setEmployeeName(result.getString(4));
                trainer.setEmployeeDesignation(result.getString(6));
                trainer.setEmployeeMail(result.getString(7));
                trainer.setEmployeeMobileNumber(result.getString(8));
                trainer.setCurrentAddress(result.getString(9));
                tr = trainer;
            }          
            return tr;
        }

        } catch (SQLException e) {
            throw e;
        } 
    }       

    /**
     * method used to get all trainee details in trainee table
     * @param {@link Logger} logger 
     * @return {@link List<Trainee>} returns list of trainees Data
     */  
    @Override
    public List<Trainee> retrieveTrainees(Logger logger) throws SQLException {
  
        List<Trainee> trainees = new ArrayList<Trainee>(); 
        
        String sql = "select * from Trainee";

        try (Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql); 
            ResultSet result=statement.executeQuery();) {

            while (result.next()) {
                Trainee trainee = new Trainee();
                trainee.setEmployeeId(result.getString(3));
                trainee.setEmployeeName(result.getString(4));
                trainee.setEmployeeDesignation(result.getString(6));
                trainee.setEmployeeMail(result.getString(7));
                trainee.setEmployeeMobileNumber(result.getString(8));
                trainee.setCurrentAddress(result.getString(9)); 
                trainees.add(trainee);
            }
            return trainees;
      
        } catch (SQLException e) {
            throw e;
        } 
    }

    /**
     * method used to retrieve trainee details by using employeeId
     * @param {@link String} employeeId
     * @param {@link Logger} logger 
     * @return {@link Trainee} returns trainee Data
     */           
    @Override
    public Trainee retrieveTrainee(String employeeId, Logger logger) throws SQLException {

        Trainee te = null;
        String sql = "select * from Trainee where employeeId = ?";

        try (Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, employeeId);
   
         try (ResultSet result=statement.executeQuery();) {

            while (result.next()) {
                Trainee trainee = new Trainee();
                trainee.setEmployeeId(result.getString(3));
                trainee.setEmployeeName(result.getString(4));
                trainee.setEmployeeDesignation(result.getString(6));
                trainee.setEmployeeMail(result.getString(7));
                trainee.setEmployeeMobileNumber(result.getString(8));
                trainee.setCurrentAddress(result.getString(9));
                te = trainee; 
            }
            return te; 
            }
          
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * method used to update trainer details by using employeeId
     * @param {@link String} employeeId
     * @param {@link Trainer} trainer object
     * @param {@link Logger} logger 
     * @return {@link void} returns nothing
     */           
    @Override
    public void updateTrainer(String employeeId, Trainer trainer, Logger logger) throws SQLException {

        String sql = "update Trainer set name = (?), dateOfBirth = (?), Designation = (?), mail = (?), mobileNumber = (?), address = (?), aadharCardNumber = (?), panCardNumber = (?), currentProject = (?), achievement = (?) where employeeId = (?)";

        try (Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);) { 

            statement.setString(1,trainer.getEmployeeName());
            statement.setString(2,trainer.getEmployeeDateOfBirth());
            statement.setString(3,trainer.getEmployeeDesignation());
            statement.setString(4,trainer.getEmployeeMail());
            statement.setString(5,trainer.getEmployeeMobileNumber());
            statement.setString(6,trainer.getCurrentAddress());
            statement.setString(7,trainer.getAadharCardNumber());
            statement.setString(8,trainer.getPanCardNumber());
            statement.setString(9,trainer.getCurrentProject());
            statement.setString(10,trainer.getAchievement());
            statement.setString(11,employeeId);
            int updateStatus = statement.executeUpdate();  

        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * method used to update trainee details by using emplpoyeeId
     * @param {@link String} employeeId
     * @param {@link Trainee} trainee object
     * @param {@link Logger} logger 
     * @return {@link void} returns nothing
     */           
    @Override
    public void updateTrainee(String employeeId, Trainee trainee, Logger logger) throws SQLException {
 
        String sql = "update Trainee set name = (?), dateOfBirth = (?), Designation = (?), mail = (?), mobileNumber = (?), address = (?), aadharCardNumber = (?), panCardNumber = (?), currentTask = (?), currentTechknowledge = (?) where employeeId = (?)";

        try (Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);) { 

            statement.setString(1,trainee.getEmployeeName());
            statement.setString(2,trainee.getEmployeeDateOfBirth());
            statement.setString(3,trainee.getEmployeeDesignation());
            statement.setString(4,trainee.getEmployeeMail());
            statement.setString(5,trainee.getEmployeeMobileNumber());
            statement.setString(6,trainee.getCurrentAddress());
            statement.setString(7,trainee.getAadharCardNumber());
            statement.setString(8,trainee.getPanCardNumber());
            statement.setString(9,trainee.getCurrentTask());
            statement.setString(10,trainee.getCurrentTechknowledge());
            statement.setString(11,employeeId);
            statement.executeUpdate();  

        } catch (SQLException e) {
            throw e;
        }
    }                         

    /**
     * method used to remove trainer details by using employeeId
     * @param {@link String} employeeId
     * @param {@link Logger} logger 
     * @return {@link void} returns nothing
     */           
    @Override
    public void removeTrainer(String employeeId, Logger logger) throws SQLException {

        String sql = "delete from Trainer where employeeId = (?)";

        try (Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);) { 
           
            statement.setString(1,employeeId);
            statement.executeUpdate();  

        } catch (SQLException e) {
            throw e;
        }
    }     

    /**
     * method used to remove trainee details by using employeeId
     * @param {@link String} employeeId
     * @param {@link Logger} logger 
     * @return {@link void} returns nothing
     */           
    @Override
    public void removeTrainee(String employeeId, Logger logger) throws SQLException {

        String sql = "delete from Trainee where employeeId = (?)";

        try (Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);) { 
            
            statement.setString(1,employeeId);
            statement.executeUpdate();  

        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void createAssociation(List<String> trainerId, List<String> traineeId) throws SQLException {

        String sql = "insert into association(trainerId, traineeId, trId, teId) value(?, ?, ?, ?)";

        try (Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);) {
      
            if (trainerId.size() == 1) {
                System.out.println(trainerId +"\n"+ traineeId);
                for (String trainer : trainerId) {
                    for (String trainee : traineeId) {
	                statement.setString(1,trainer);
                        statement.setString(2,trainee);
	                statement.setInt(3,Integer.valueOf(trainer.substring(7)));
                        statement.setInt(4,Integer.valueOf(trainee.substring(7)));
                        statement.executeUpdate();
                    }
                }
            } else {
                for (String trainee : traineeId) {
                    for (String trainer : trainerId) {
	                statement.setString(1,trainer);
                        statement.setString(2,trainee);
	                statement.setInt(3,Integer.valueOf(trainer.substring(7)));
                        statement.setInt(4,Integer.valueOf(trainee.substring(7)));
                        statement.executeUpdate();
                    }
                }
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

        String sql = "select trainer.employeeId, trainer.name from association inner join trainer on trainer.employeeId = association.trainerId inner join trainer on association.traineeId = trainee.employeeId where association.traineeId = (?)";


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
    public int getLastTrainerId() throws SQLException {

        String sql = "select id from trainer order by id DESC limit 1";
        int id = 0;

        try (Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql); 
            ResultSet result=statement.executeQuery();) { 

            while(result.next()){
                id = result.getInt("id");
            }
            return id;
        } catch (SQLException e) {
            throw e;
        }
    }       

    @Override
    public int getLastTraineeId() throws SQLException {

        String sql = "select id from trainee order by id DESC limit 1";
        int id = 0;

        try (Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql); 
            ResultSet result=statement.executeQuery();) { 

            while(result.next()){
                id = result.getInt("id");
            }
            return id;
        } catch (SQLException e) {
            throw e;
        }
    }       
}


            