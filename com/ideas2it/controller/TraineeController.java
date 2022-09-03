package com.ideas2it.controller;

import java.lang.NullPointerException; 
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.*;
import java.util.UUID;

import org.hibernate.HibernateException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

import com.ideas2it.exception.EmailMismatchException;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.service.EmployeeService;
import com.ideas2it.service.impl.EmployeeServiceImpl;
import com.ideas2it.util.EmployeeUtil;

/**
 *
 * <h2>TraineeController</h2>
 * <p>
 * The TraineeController class is an application that
 * do the main operation create, read, update, delete 
 * if one will be choosen the respect operation performs 
 * until user exit the operation is same for all operation 
 * </p>
 * @author  Yukendiran K
 * @version 1.0
 * @since   2022-08-04 
 *
 */
public class TraineeController {

    private EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
    private EmployeeUtil util = new EmployeeUtil();
    private Logger logger = LoggerFactory.getLogger(TraineeController.class);

    /**
     *
     * <h1> Trainee Menu </h1>
     *
     * Method used to presents the main menu for trainee related operation
     * 
     * operations are......
     * ----> create user profile and store in database
     * ----> display the trainees in our database  
     * ----> update details or change smething in database
     * ----> delete the trainee in our database   
     * ----> create association between trainee and trainees 
     * 
     * @return {@link void} returns nothing
     *
     */  
    public void viewTraineeMenu()  {
       
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;

        while (isContinue) {

            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("\nEnter 1 for Create trainee details in database\n")
	                     .append("Enter 2 for display   trainee details in database\n")
		             .append("Enter 3 for Update trainee details in database\n") 
	                     .append("Enter 4 for Delete trainee details in database\n")
                             .append("Enter 5 for assign trainee for trainers\n")
                             .append("Enter 6 for Exit  \n\n\n")
                             .append("----------------------------------------------")
                             .append("\n----------------------------------------------")
                             .append("\n***************** THANK YOU ******************")
                             .append("\n----------------------------------------------");
                logger.info("" + stringBuilder.substring(1,249));    
                String userOption = scanner.next();
                isContinue = Perform(userOption, isContinue);
                logger.info("" + stringBuilder.substring(250));

            } catch (InputMismatchException e) {
                logger.error("\ninvalid data : " + e);

            } catch (HibernateException e) {
                logger.error("\nException occured" + e);

            } catch (SQLException e) {
                logger.error("",e);

            } catch (NullPointerException e) {
                logger.error("\nno data found" + e);
            }
        }
    }

    /**
     *
     * <h1> PerformUserOperation </h1>
     *
     * Method used to select the which operation will be happens 
     * 
     * operations are......
     * ----> create user profile and store in database
     * ----> display the trainees in our database  
     * ----> update details or change smething in database
     * ----> delete the trainee in our database   
     * ----> create association between trainee and trainees 
     *
     * @param {@link String} userOption for perform one operation 
     * @param {@link boolean} isContinue for continue operations 
     * @return {@link boolean} return boolean 
     *
     */  
    public boolean Perform(String userOption, boolean isContinue) throws InputMismatchException, SQLException, HibernateException, NullPointerException {
 
        switch (userOption) {
        case "1":	   
            createTraineeData();                
            break;
        case "2":
            displayTraineeData();                
            break;
        case "3":
            updateTraineeData();
            break; 
        case "4":
            removeTraineeData();                
            break;
        case "5":
            assignTraineeForTrainers();
            break;
        case "6":
            isContinue = false;
            break;
        default:
            break;
        }
        return isContinue;
    }

    /**
     *
     * <h1> createTraineeProfile </h1>
     *
     * Method used to create Trainee profile  
     *
     * @return {@link void} returns nothing
     */
    public void createTraineeData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {
        
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;
        String position = "";

        while (isContinue) {
            getAndSetData();
            logger.info(" \nIf you want to continue\n" + "1.yes\n" + "2.no");
            String userOption = scanner.next();
            logger.info("----------------------------------------------");
            isContinue = (userOption.equals("1"));                   
        }
    }

    /**
     *
     * <h1> getAndSetData </h1>
     *
     * Method used to get and set details and store in database
     *
     * @return {@link void} returns nothing
     *
     */
    public void getAndSetData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {

         UUID uuId = UUID.randomUUID();
         String temp = uuId.toString();
         String[] arr = temp.split("-");
         String uuid = arr[0]+arr[3];
       	 logger.info("\nEnter Trainee Details :");
         Trainee trainee = getInformationFromTrainee(uuid);
         String message = employeeService.addTrainee(trainee);                     
         logger.info("" + message);
         logger.info("\n----------------------------------------------");
    }

    /**
     *
     * <h1> displayTraineeData </h1>
     *
     * Method used to select display choise of trainee details 
     *
     * @return {@link void} returns nothing
     *
     */
    public void displayTraineeData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {
        
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;

        while (isContinue) { 
	    logger.info("\n1.whole data\n" + "2.particular data");
            String userOption = scanner.next();
            logger.info("\n----------------------------------------------"); 

            if (userOption.equals("1")) {
                displayWholeTraineeData();
            } else {
                displayParticularTraineeData();
            }        
            logger.info("\nif you want to continue\n" + "1.yes\n" + "2.no"); 
            userOption = scanner.next();          
            isContinue = (userOption.equals("1")); 
        }
    }

    /**
     *
     * <h1> displayWholeTraineeData </h1>
     *
     * Method used to display all trainee details 
     *
     * @return {@link void} returns nothing
     *
     */
    public void displayWholeTraineeData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {

        List<Trainee> trainees = employeeService.getTraineesData();

        if (trainees == null) {
            logger.info("\nNo data found");
        } else {
            trainees.forEach(trainee -> { 
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("\nTrainee Detail : ")
                                 .append("\nEmployee Id         : ").append(trainee.getTraineeId())
                                 .append("\nTrainee Name        : ").append(trainee.getEmployeeName())
                                 .append("\nTrainee Designation : ").append(trainee.getEmployeeDesignation())
                                 .append("\nTrainee Mail        : ").append(trainee.getEmployeeMail())
                                 .append("\nTrainee MobileNumber: ").append(trainee.getEmployeeMobileNumber())
                                 .append("\nCurrent Address     : ").append(trainee.getCurrentAddress());
            logger.info("----------------------------------------------" + stringBuilder);});                             
        }
    } 

    /**
     *
     * <h1> displayParticularTraineeData </h1>
     *
     * Method used to particular trainee details 
     *
     * @return {@link void} returns nothing
     *
     */ 
    public void displayParticularTraineeData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {

        Scanner scanner = new Scanner(System.in);
        logger.info("\nEnter TraineeId :");
        int traineeId = scanner.nextInt();
        Trainee trainee = employeeService.searchTraineeData(traineeId);

        if (trainee != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\nTrainee Detail : ")
                         .append("\nTrainee Id          : ").append(trainee.getTraineeId())
                         .append("\nTrainee Name        : ").append(trainee.getEmployeeName())
                         .append("\nTrainee Designation : ").append(trainee.getEmployeeDesignation())
                         .append("\nTrainee Mail        : ").append(trainee.getEmployeeMail())
                         .append("\nTrainee MobileNumber: ").append(trainee.getEmployeeMobileNumber())
                         .append("\nCurrent Address     : ").append(trainee.getCurrentAddress())
                         .append("\n\nAssigned Trainees........ :");
            logger.info("" + stringBuilder);
            trainee.getTrainers().forEach(trainer -> { 
                    StringBuilder stringBuilder1 = new StringBuilder();
                    stringBuilder1.append("\n\nTrainee Id          : ").append(trainer.getTrainerId())
                                  .append("\tTrainee Name        : ").append(trainer.getEmployeeName()); 
                    logger.info("" + stringBuilder1); });
            
        } else {
            logger.info("no data found");
        }
    }

    /**
     *
     * <h1> updateTraineeData </h1>
     *
     * Method used to update the changes for trainee details 
     *
     * @return {@link void} returns nothing
     *
     */
    public void updateTraineeData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {

        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;

        while (isContinue) {
            logger.info("\nEnter EmployeeId :");                
            int traineeId = scanner.nextInt();                                 
            Trainee trainee = employeeService.searchTraineeData(traineeId);
 
            if (trainee != null) {
                Trainee updateTrainee = getInformationForUpdateTrainee(trainee);
                String message = employeeService.updateTraineeData(traineeId, updateTrainee);
                logger.info("" + message);
            } else {               
                logger.info("\nno data found\n");
            }
            logger.info("\nif you want to continue\n" + "1.yes\n" + "2.no"); 
            String userOption = scanner.next();
            isContinue = (userOption.equals("1"));
        }
    }

    /**
     *
     * <h1> removeTraineeData </h1>
     *
     * Method used to remove trainee details  
     *
     * @return {@link void} returns nothing
     *
     */
    public void removeTraineeData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {
        
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;

        while (isContinue) {
            logger.info("\nEnter EmployeeId :");                
            int traineeId = scanner.nextInt();                                 
            Trainee trainee = employeeService.searchTraineeData(traineeId);
 
            if (trainee != null) {
                String message = employeeService.deleteTraineeData(traineeId);
                logger.info("" + message);
            } else {               
                logger.info("\nno data found\n");
            }
            logger.info("\nif you want to continue\n" + "1.yes\n" + "2.no"); 
            String userOption = scanner.next();
            isContinue = (userOption.equals("1"));
        }
    }

    /**
     *
     * <h1> assignTraineeForTrainees </h1>
     *
     * Method used to assign trainee for multiple trainees 
     *
     * @return {@link void} returns nothing
     *
     */
    public void assignTraineeForTrainers() throws InputMismatchException, SQLException, HibernateException, NullPointerException {

        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;  

        while (isContinue) { 
            logger.info("\nEnter Trainee Id :");
            int traineeId = scanner.nextInt();         
            Trainee trainee = employeeService.searchTraineeData(traineeId);
            trainee = getTrainersForAssignTrainee(trainee);
            String message = employeeService.updateTraineeData(traineeId, trainee);
            logger.info("{}" + message);
            logger.info("\nIf you want to continue : \n" + "1.yes\n" + "2.no" );
            String userOption = scanner.next();
            logger.info("\n----------------------------------------------");
            isContinue = (userOption.equals("1"));
        }
    } 

    /**
     *
     * <h1> getTraineesForAssignTrainee </h1>
     *
     * Method used to get trainees for assign trainee 
     *
     * @param {@link Trainee} trainee
     * @return {@link List<Trainees>} returns list of trainees 
     *
     */
    public Trainee getTrainersForAssignTrainee(Trainee trainee) throws InputMismatchException, SQLException, HibernateException, NullPointerException {

        Scanner scanner = new Scanner(System.in);
        List<Trainer> trainers = employeeService.getTrainersData();
        trainers.forEach(trainer -> { 
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("\nTrainer Id          : ").append(trainer.getTrainerId())
                                 .append("\tTrainer Name        : ").append(trainer.getEmployeeName());
                    logger.info("" + stringBuilder);  });
            
        if (trainers == null) {
            logger.info("\nNo data found");
        } else {
            logger.info("Enter 0 for stop assigning.....\nEnter trainer Id :");
            int trainerId;

            do {  
                trainerId = scanner.nextInt();
                int temp = trainerId;               
                if (trainerId != 0) {
                    List<Trainer> trainer = trainers.stream().
                            filter(filteredTrainer -> filteredTrainer.getTrainerId() == temp).
                            collect(Collectors.toList());
                    trainee.getTrainers().add(trainer.get(0));
                } else {
                    logger.info("couldn't found entered trainer");
                }
            } while (trainerId != 0); 
        }  
        return trainee;
    }

    /**
     *
     * <h1> getInformationFromTrainee </h1>
     *
     * Method used to get trainee details from user for create profile
     *
     * @param {@link String} uuid
     * @return {@link Trainee} returns trainee
     *
     */ 
    public Trainee getInformationFromTrainee(String uuid) throws SQLException {

        Trainee trainee = new Trainee();
        trainee.setUuid(uuid); 
        getTraineeName(trainee);
        getTraineeDateofBirth(trainee);
        getTraineeDesignation(trainee);
        getTraineeMailId(trainee);
        getTraineeMobileNumber(trainee);
        getTraineeAddress(trainee);
        getTraineeAadharNumber(trainee);
        getTraineePanNumber(trainee);
        getTraineeCurrentTask(trainee);
        getTraineeCurrentTechknowledge(trainee);
        return trainee;
    }

    /**
     *
     * <h1> getInformationFofUpdateTrainee </h1>
     *
     * Method used to get trainee details from user for update profile
     *
     * @param {@link Trainee} trainee
     * @return {@link Trainee} returns trainee
     *
     */ 
    public Trainee getInformationForUpdateTrainee(Trainee trainee) throws SQLException {
 
        getName(trainee);
        getDateofBirth(trainee);
        getDesignation(trainee);
        getMailId(trainee);
        getMobileNumber(trainee);
        getAddress(trainee);
        getAadharNumber(trainee);
        getPanNumber(trainee);
        getCurrentTask(trainee);
        getCurrentTechknowledge(trainee);
        return trainee;
    }

    /**
     *
     * <h1> getTraineeName </h1>
     *
     * Method used to get trainee name from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */ 
    public void getTraineeName(Trainee trainee) {

        Scanner scanner = new Scanner(System.in); 
	logger.info("Enter Trainee Name : ");
        String name = scanner.nextLine();

        if (!name.isEmpty()) {
            boolean isValid = util.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",name);

            if (isValid) {
                trainee.setEmployeeName(name);
            } else {
                logger.info("not valid");
                getTraineeName(trainee);
            }       	    
        }
    }

    /**
     *
     * <h1> getTraineeDateofBirth </h1>
     *
     * Method used to get trainee date of birth from user
     *
     * @param {@link Trainee} trainee

     * @return {@link void} returns nothing
     *
     */ 
    public void getTraineeDateofBirth(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainee DateOfBirth MM/DD/YYYY:");
        String employeeDateOfBirth = scanner.nextLine();

        if (!employeeDateOfBirth.isEmpty()) {
            boolean isValid = util.validationOfDateOfBirth(employeeDateOfBirth);

            if (isValid) {
                String[] date = employeeDateOfBirth.split("/");
                employeeDateOfBirth = date[2]+"-"+date[0]+"-"+date[1];
                trainee.setEmployeeDateOfBirth(employeeDateOfBirth);
            } else {
                logger.info("not valid");
                getTraineeDateofBirth(trainee);
            }
        }
    }

    /**
     *
     * <h1> getTraineeDesignation </h1>
     *
     * Method used to get trainee designation from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */ 
    public void getTraineeDesignation(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainee Designation : ");
        String designation = scanner.nextLine();

        if (!designation.isEmpty()) {
            boolean isValid = util.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",designation);

            if (isValid) {
                trainee.setEmployeeDesignation(designation);
            } else {
                logger.info("not valid");
                getTraineeDesignation(trainee);
            }	    
        }  
    }

    /**
     *
     * <h1> getTraineeMailId </h1>
     *
     * Method used to get trainee mail Id from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */ 
    public void getTraineeMailId(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);
        logger.info("Enter Trainee Mail : ");
        String mail = scanner.nextLine();

        if (!mail.isEmpty()) {
                
            try {
                util.validationOfMail(mail);
                trainee.setEmployeeMail(mail);            
            } catch (EmailMismatchException e) {
                logger.error("Exception occured :" + e);
                getTraineeMailId(trainee);
            }             
        }
    }

    /**
     *
     * <h1> getTraineeMobileNumber </h1>
     *
     * Method used to get trainee mobile number from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */  
    public void getTraineeMobileNumber(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);     
	logger.info("Enter Trainee MobileNumber : ");
        String mobileNumber = scanner.nextLine();

        if (!mobileNumber.isEmpty()) {
            boolean isValid = util.matchRegex("^(([6-9]{1}[0-9]{9})*)$",mobileNumber);

            if (isValid) {
                trainee.setEmployeeMobileNumber(mobileNumber);
            } else {
                logger.info("not valid");
                getTraineeMobileNumber(trainee);
            }	    
        }
    }

    /**
     *
     * <h1> getTraineeAddress </h1>
     *
     * Method used to get trainee address from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */         
    public void getTraineeAddress(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);
        logger.info("Enter Trainee CurrentAddress : ");
        String currentAddress = scanner.nextLine();

        if (!currentAddress.isEmpty()) {
            boolean isValid = util.matchRegex("^(([0-9\\sa-zA-Z,.-]{3,150})*)$",currentAddress);

            if (isValid) {
                trainee.setCurrentAddress(currentAddress);
            } else {
                logger.info("not valid");
                getTraineeAddress(trainee);
            }	    
        }
    }

    /**
     *
     * <h1> getTraineeAadharNumber </h1>
     *
     * Method used to get trainee aadhar number from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */ 
    public void getTraineeAadharNumber(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainee AadharCardNumber : ");            
        String aadharNumber = scanner.nextLine();

        if (!aadharNumber.isEmpty()) {
            boolean isValid = util.matchRegex("^(([1-9]{1}[0-9]{11})*)$",aadharNumber);

            if (isValid) {
                trainee.setAadharCardNumber(aadharNumber);
            } else {
                logger.info("not valid");
                getTraineeAadharNumber(trainee);
            }	    
        }
    }

    /**
     *
     * <h1> getTraineePanNumber </h1>
     *
     * Method used to get trainee pan number from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */ 
    public void getTraineePanNumber(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainee PanCardNumber : ");            
        String panNumber = scanner.nextLine();

        if (!panNumber.isEmpty()) {
            boolean isValid = util.matchRegex("^(([A-Z0-9]{10})*)$",panNumber);

            if (isValid) {
                trainee.setPanCardNumber(panNumber);
            } else {
                logger.info("not valid");
                getTraineePanNumber(trainee);
            }	    
        }
    }

    /**
     *
     * <h1> getTraineeCurrentTask </h1>
     *
     * Method used to get trainee current project from user
     *
     * @param {@link Trainee} trainee

     * @return {@link void} returns nothing
     *
     */ 
    public void getTraineeCurrentTask(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter your currentTask : ");            
        String currentTask = scanner.nextLine();

        if (!currentTask.isEmpty()) {
            boolean isValid = util.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",currentTask);

            if (isValid) {
                trainee.setCurrentTask(currentTask);
            } else {
                logger.info("not valid");
                getTraineeCurrentTask(trainee);
            }
        }
    }

    /**
     *
     * <h1> getTraineeCurrentTechknowledge </h1>
     *
     * Method used to get trainee currentTechknowledge from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */ 
    public void getTraineeCurrentTechknowledge(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);	
	logger.info("Enter your currentTechknowledges : ");            
        String currentTechknowledge = scanner.nextLine();

        if (!currentTechknowledge.isEmpty()) {
            boolean isValid = util.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",currentTechknowledge);

            if (isValid) {
                trainee.setCurrentTechknowledge(currentTechknowledge);
            } else {
                logger.info("not valid");
                getTraineeCurrentTechknowledge(trainee);
            }
        }
    }

    /**
     *
     * <h1> getName </h1>
     *
     * Method used to get trainee name from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */ 
    public void getName(Trainee trainee) {

        Scanner scanner = new Scanner(System.in); 
	logger.info("Enter Trainee Name : ");
        String name = scanner.nextLine();

        if (!name.isEmpty()) {
            boolean isValid = util.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",name);

            if (isValid) {
                trainee.setEmployeeName(name);
            } else {
                logger.info("not valid");
                getName(trainee);
            }           	    
        }
    }

    /**
     *
     * <h1> getDateofBirth </h1>
     *
     * Method used to get trainee date of birth from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */ 
    public void getDateofBirth(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);	
        logger.info("Enter Trainee DateOfBirth MM/DD/YYYY:");
        String employeeDateOfBirth = scanner.nextLine();

        if (!employeeDateOfBirth.isEmpty()) {
            boolean isValid = util.validationOfDateOfBirth(employeeDateOfBirth);

            if (isValid) {
                String[] date = employeeDateOfBirth.split("/");
                employeeDateOfBirth = date[2]+"-"+date[0]+"-"+date[1];
                trainee.setEmployeeDateOfBirth(employeeDateOfBirth);
            } else {
                logger.info("not valid");
                getDateofBirth(trainee);
            }
        }
    }

    /**
     *
     * <h1> getDesignation </h1>
     *
     * Method used to get trainee designation from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */ 
    public void getDesignation(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainee Designation : ");
        String designation = scanner.nextLine();

        if (!designation.isEmpty()) {
            boolean isValid = util.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",designation);

            if (isValid) {
                trainee.setEmployeeDesignation(designation);
            } else {
                logger.info("not valid");
                getDesignation(trainee);
            }  
        }  
    }

    /**
     *
     * <h1> getMailId </h1>
     *
     * Method used to get trainee mail Id  from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */ 
    public void getMailId(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);
        logger.info("Enter Trainee Mail : ");
        String mail = scanner.nextLine();

        if (!mail.isEmpty()) {
           
            try {
                util.validationOfMail(mail);
                trainee.setEmployeeMail(mail);                 
            } catch (EmailMismatchException e) {
                logger.error("Exception occured :" + e);
                getMailId(trainee);
            }             
        }
    }

    /**
     *
     * <h1> getMobileNumber </h1>
     *
     * Method used to get trainee mobile number from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */  
    public void getMobileNumber(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);     
	logger.info("Enter Trainee MobileNumber : ");
        String mobileNumber = scanner.nextLine();

        if (!mobileNumber.isEmpty()) {
            boolean isValid = util.matchRegex("^(([6-9]{1}[0-9]{9})*)$",mobileNumber);

            if (isValid) {
                trainee.setEmployeeMobileNumber(mobileNumber);
            } else {
                logger.info("not valid");
                getMobileNumber(trainee);
            }	    
        }
    }

    /**
     *
     * <h1> getAddress </h1>
     *
     * Method used to get trainee address from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */         
    public void getAddress(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);
        logger.info("Enter Trainee CurrentAddress : ");
        String currentAddress = scanner.nextLine();

        if (!currentAddress.isEmpty()) {
            boolean isValid = util.matchRegex("^(([0-9\\sa-zA-Z,.-]{3,150})*)$",currentAddress);

            if (isValid) {
                trainee.setCurrentAddress(currentAddress);
            } else {
                logger.info("not valid");
                getAddress(trainee);
            }	    
        }
    }

    /**
     *
     * <h1> getAadharNumber </h1>
     *
     * Method used to get trainee aadhar number from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */ 
    public void getAadharNumber(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainee AadharCardNumber : ");            
        String aadharNumber = scanner.nextLine();

        if (!aadharNumber.isEmpty()) {
            boolean isValid = util.matchRegex("^(([1-9]{1}[0-9]{11})*)$",aadharNumber);

            if (isValid) {
                trainee.setAadharCardNumber(aadharNumber);
            } else {
                logger.info("not valid");
                getAadharNumber(trainee);
            }	    
        }
    }

    /**
     *
     * <h1> getPanNumber </h1>
     *
     * Method used to get trainee pan number from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */ 
    public void getPanNumber(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainee PanCardNumber : ");            
        String panNumber = scanner.nextLine();

        if (!panNumber.isEmpty()) {
            boolean isValid = util.matchRegex("^(([A-Z0-9]{10})*)$",panNumber);

            if (isValid) {
                trainee.setPanCardNumber(panNumber);
            } else {
                logger.info("not valid");
                getPanNumber(trainee);
            }	    
        }
    }

    /**
     *
     * <h1> getCurrentTask </h1>
     *
     * Method used to get trainee current project name from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */ 
    public void getCurrentTask(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter your currentTask : ");            
        String currentTask = scanner.nextLine();

        if (!currentTask.isEmpty()) {
            boolean isValid = util.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",currentTask);

            if (isValid) {
                trainee.setCurrentTask(currentTask);
            } else {
                logger.info("not valid");
                getCurrentTask(trainee);
            }	    
        }
    }

    /**
     *
     * <h1> getCurrentTechknowledge </h1>
     *
     * Method used to get trainee currentTechknowledge from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */ 
    public void getCurrentTechknowledge(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);	
	logger.info("Enter your currentTechknowledge : ");            
        String currentTechknowledge = scanner.nextLine();

        if (!currentTechknowledge.isEmpty()) {
            boolean isValid = util.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",currentTechknowledge);

            if (isValid) {
                trainee.setCurrentTechknowledge(currentTechknowledge);
            } else {
                logger.info("not valid");
                getCurrentTechknowledge(trainee);
            }	    
        }
    }
}