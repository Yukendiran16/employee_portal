package com.ideas2it.controller;

import java.lang.NullPointerException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Set;
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
    private EmployeeUtil employeeUtil = new EmployeeUtil();
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
     * <p>
     * Method used to select the which operation will be happens 
     * 
     * operations are......
     * ----> create user profile and store in database
     * ----> display the trainees in our database  
     * ----> update details or change smething in database
     * ----> delete the trainee in our database   
     * ----> create association between trainee and trainees 
     * </p>
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

        while (isContinue) {
            getAndSetData();
            logger.info(" \nIf you want to continue\n1.yes\n2.no");
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
	    logger.info("\n1.whole data\n2.particular data");
            String userOption = scanner.next();
            logger.info("\n----------------------------------------------"); 

            if (userOption.equals("1")) {
                displayWholeTraineeData();
            } else {
                displayParticularTraineeData();
            }        
            logger.info("\nif you want to continue\n1.yes\n2.no"); 
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
            logger.info("\nif you want to continue\n1.yes\n2.no"); 
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
            logger.info("\nif you want to continue\n1.yes\n2.no"); 
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
            logger.info("\nIf you want to continue : \n1.yes\n2.no" );
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
                    Set<Trainer> trainer = trainers.stream().
                            filter(filteredTrainer -> filteredTrainer.getTrainerId() == temp).
                            collect(Collectors.toSet());
                    List<Trainer> trainerList = new ArrayList<>(trainer);
                    if (trainer.size() != 0) {
                        trainee.getTrainers().add(trainerList.get(0));
                    } else {
                        logger.info("couldn't found entered trainer");
                    }
                }
            } while (trainerId != 0); 
        }  
        return trainee;
    }

    /**
     *
     * <h1> getInformationFromTrainer </h1>
     *
     * Method used to get trainer details from user for create profile
     *
     * @param {@link String} uuid
     * @return {@link Trainer} returns trainer
     *
     */ 
    public Trainee getInformationFromTrainee(String uuid) throws SQLException {

        Trainee trainee = new Trainee();
        trainee.setUuid(uuid); 
        validateTraineeName(trainee);
        validateTraineeDateofBirth(trainee);
        validateTraineeDesignation(trainee);
        validateTraineeMailId(trainee);
        validateTraineeMobileNumber(trainee);
        validateTraineeAddress(trainee);
        validateTraineeAadharNumber(trainee);
        validateTraineePanNumber(trainee);
        validateTraineeCurrentTask(trainee);
        validateTraineeCurrentTechknowledge(trainee);
        return trainee;
    }

    /**
     *
     * <h1> validateInformationFofUpdateTrainee </h1>
     *
     * Method used to validate trainee details from user for update profile
     *
     * @param {@link Trainee} trainee
     * @return {@link Trainee} returns trainee
     *
     */ 
    public Trainee getInformationForUpdateTrainee(Trainee trainee) throws SQLException {
 
        validateName(trainee);
        validateDateofBirth(trainee);
        validateDesignation(trainee);
        validateMailId(trainee);
        validateMobileNumber(trainee);
        validateAddress(trainee);
        validateAadharNumber(trainee);
        validatePanNumber(trainee);
        validateCurrentTask(trainee);
        validateCurrentTechknowledge(trainee);
        return trainee;
    }

    /**
     *
     * <h1> validateTraineeName </h1>
     *
     * Method used to validate trainee name from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */ 
    public void validateTraineeName(Trainee trainee) {

        Scanner scanner = new Scanner(System.in); 
	logger.info("Enter Trainee Name : ");
        String name = scanner.nextLine();

        if (!name.isEmpty()) {

            if (employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",name)) {
                trainee.setEmployeeName(name);
            } else {
                logger.info("not valid");
                validateTraineeName(trainee);
            }       	    
        }
    }

    /**
     *
     * <h1> validateTraineeDateofBirth </h1>
     *
     * Method used to validate trainee date of birth from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */ 
    public void validateTraineeDateofBirth(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainee DateOfBirth YYYY-MM-DD:");
        String employeeDateOfBirth = scanner.nextLine();

        if (!employeeDateOfBirth.isEmpty()) {
            
            try {
                
                if (employeeUtil.validationOfDateOfBirth(employeeDateOfBirth)) {
                    trainee.setEmployeeDateOfBirth(employeeDateOfBirth);   
                } else {
                    logger.error("invalid date format");
                    validateTraineeDateofBirth(trainee);
                }      
            } catch (NumberFormatException e) {
                logger.error("invalid date format");
                validateTraineeDateofBirth(trainee);
            } catch ( ArrayIndexOutOfBoundsException e) {
                logger.error("invalid date format");
                validateTraineeDateofBirth(trainee);
            }
        }
    }

    /**
     *
     * <h1> validateTraineeDesignation </h1>
     *
     * Method used to validate trainee designation from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */ 
    public void validateTraineeDesignation(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainee Designation : ");
        String designation = scanner.nextLine();

        if (!designation.isEmpty()) {

            if (employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",designation)) {
                trainee.setEmployeeDesignation(designation);
            } else {
                logger.info("not valid");
                validateTraineeDesignation(trainee);
            }	    
        }  
    }

    /**
     *
     * <h1> validateTraineeMailId </h1>
     *
     * Method used to validate trainee mail Id from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */ 
    public void validateTraineeMailId(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);
        logger.info("Enter Trainee Mail : ");
        String mail = scanner.nextLine();

        if (!mail.isEmpty()) {
                
            try {
                employeeUtil.validationOfMail(mail);
                trainee.setEmployeeMail(mail);            
            } catch (EmailMismatchException e) {
                logger.error("Exception occured :" + e);
                validateTraineeMailId(trainee);
            }             
        }
    }

    /**
     *
     * <h1> validateTraineeMobileNumber </h1>
     *
     * Method used to validate trainee mobile number from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */  
    public void validateTraineeMobileNumber(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);     
	logger.info("Enter Trainee MobileNumber : ");
        String mobileNumber = scanner.nextLine();

        if (!mobileNumber.isEmpty()) {

            if (employeeUtil.matchRegex("^(([6-9]{1}[0-9]{9})*)$",mobileNumber)) {
                trainee.setEmployeeMobileNumber(mobileNumber);
            } else {
                logger.info("not valid");
                validateTraineeMobileNumber(trainee);
            }	    
        }
    }

    /**
     *
     * <h1> validateTraineeAddress </h1>
     *
     * Method used to validate trainee address from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */         
    public void validateTraineeAddress(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);
        logger.info("Enter Trainee CurrentAddress : ");
        String currentAddress = scanner.nextLine();

        if (!currentAddress.isEmpty()) {

            if (employeeUtil.matchRegex("^(([0-9\\sa-zA-Z,.-]{3,150})*)$",currentAddress)) {
                trainee.setCurrentAddress(currentAddress);
            } else {
                logger.info("not valid");
                validateTraineeAddress(trainee);
            }	    
        }
    }

    /**
     *
     * <h1> validateTraineeAadharNumber </h1>
     *
     * Method used to validate trainee aadhar number from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */ 
    public void validateTraineeAadharNumber(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainee AadharCardNumber : ");            
        String aadharNumber = scanner.nextLine();

        if (!aadharNumber.isEmpty()) {

            if (employeeUtil.matchRegex("^(([1-9]{1}[0-9]{11})*)$",aadharNumber)) {
                trainee.setAadharCardNumber(aadharNumber);
            } else {
                logger.info("not valid");
                validateTraineeAadharNumber(trainee);
            }	    
        }
    }

    /**
     *
     * <h1> validateTraineePanNumber </h1>
     *
     * Method used to validate trainee pan number from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */ 
    public void validateTraineePanNumber(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainee PanCardNumber : ");            
        String panNumber = scanner.nextLine();

        if (!panNumber.isEmpty()) {

            if (employeeUtil.matchRegex("^(([A-Z0-9]{10})*)$",panNumber)) {
                trainee.setPanCardNumber(panNumber);
            } else {
                logger.info("not valid");
                validateTraineePanNumber(trainee);
            }	    
        }
    }

    /**
     *
     * <h1> validateTraineeCurrentProject </h1>
     *
     * Method used to validate trainee current project from user
     *
     * @param {@link Trainee} trainee

     * @return {@link void} returns nothing
     *
     */ 
    public void validateTraineeCurrentTask(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter your currentTask : ");            
        String currentTask = scanner.nextLine();

        if (!currentTask.isEmpty()) {

            if (employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",currentTask)) {
                trainee.setCurrentTask(currentTask);
            } else {
                logger.info("not valid");
                validateTraineeCurrentTask(trainee);
            }
        }
    }

    /**
     *
     * <h1> validateTraineeCurrentTechknowledge </h1>
     *
     * Method used to validate trainee currentTechknowledge from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */ 
    public void validateTraineeCurrentTechknowledge(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);	
	logger.info("Enter your currentTechknowledges : ");            
        String currentTechknowledge = scanner.nextLine();

        if (!currentTechknowledge.isEmpty()) {

            if (employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",currentTechknowledge)) {
                trainee.setCurrentTechknowledge(currentTechknowledge);
            } else {
                logger.info("not valid");
                validateTraineeCurrentTechknowledge(trainee);
            }
        }
    }

    /**
     *
     * <h1> validateName </h1>
     *
     * Method used to validate trainee name from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */ 
    public void validateName(Trainee trainee) {

        Scanner scanner = new Scanner(System.in); 
	logger.info("Enter Trainee Name : ");
        String name = scanner.nextLine();

        if (!name.isEmpty()) {

            if (employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",name)) {
                trainee.setEmployeeName(name);
            } else {
                logger.info("not valid");
                validateName(trainee);
            }           	    
        }
    }

    /**
     *
     * <h1> validateDateofBirth </h1>
     *
     * Method used to validate trainee date of birth from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */ 
    public void validateDateofBirth(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);	
        logger.info("Enter Trainee DateOfBirth YYYY-MM-DD:");
        String employeeDateOfBirth = scanner.nextLine();

        if (!employeeDateOfBirth.isEmpty()) {

            try {

                if (employeeUtil.validationOfDateOfBirth(employeeDateOfBirth)) {
                    trainee.setEmployeeDateOfBirth(employeeDateOfBirth);   
                } else {
                    logger.error("invalid date format");
                    validateTraineeDateofBirth(trainee);
                }   
            } catch (NumberFormatException e) {
                logger.error("invalid date format");
                validateTraineeDateofBirth(trainee);
            } catch ( ArrayIndexOutOfBoundsException e) {
                logger.error("invalid date format");
                validateTraineeDateofBirth(trainee);
            }
        }
    }

    /**
     *
     * <h1> validateDesignation </h1>
     *
     * Method used to validate trainee designation from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */ 
    public void validateDesignation(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainee Designation : ");
        String designation = scanner.nextLine();

        if (!designation.isEmpty()) {

            if (employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",designation)) {
                trainee.setEmployeeDesignation(designation);
            } else {
                logger.info("not valid");
                validateDesignation(trainee);
            }  
        }  
    }

    /**
     *
     * <h1> validateMailId </h1>
     *
     * Method used to validate trainee mail Id  from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */ 
    public void validateMailId(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);
        logger.info("Enter Trainee Mail : ");
        String mail = scanner.nextLine();

        if (!mail.isEmpty()) {
           
            try {
                employeeUtil.validationOfMail(mail);
                trainee.setEmployeeMail(mail);                 
            } catch (EmailMismatchException e) {
                logger.error("Exception occured :" + e);
                validateMailId(trainee);
            }             
        }
    }

    /**
     *
     * <h1> validateMobileNumber </h1>
     *
     * Method used to validate trainee mobile number from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */  
    public void validateMobileNumber(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);     
	logger.info("Enter Trainee MobileNumber : ");
        String mobileNumber = scanner.nextLine();

        if (!mobileNumber.isEmpty()) {

            if (employeeUtil.matchRegex("^(([6-9]{1}[0-9]{9})*)$",mobileNumber)) {
                trainee.setEmployeeMobileNumber(mobileNumber);
            } else {
                logger.info("not valid");
                validateMobileNumber(trainee);
            }	    
        }
    }

    /**
     *
     * <h1> validateAddress </h1>
     *
     * Method used to validate trainee address from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */         
    public void validateAddress(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);
        logger.info("Enter Trainee CurrentAddress : ");
        String currentAddress = scanner.nextLine();

        if (!currentAddress.isEmpty()) {

            if (employeeUtil.matchRegex("^(([0-9\\sa-zA-Z,.-]{3,150})*)$",currentAddress)) {
                trainee.setCurrentAddress(currentAddress);
            } else {
                logger.info("not valid");
                validateAddress(trainee);
            }	    
        }
    }

    /**
     *
     * <h1> validateAadharNumber </h1>
     *
     * Method used to validate trainee aadhar number from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */ 
    public void validateAadharNumber(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainee AadharCardNumber : ");            
        String aadharNumber = scanner.nextLine();

        if (!aadharNumber.isEmpty()) {

            if (employeeUtil.matchRegex("^(([1-9]{1}[0-9]{11})*)$",aadharNumber)) {
                trainee.setAadharCardNumber(aadharNumber);
            } else {
                logger.info("not valid");
                validateAadharNumber(trainee);
            }	    
        }
    }

    /**
     *
     * <h1> validatePanNumber </h1>
     *
     * Method used to validate trainee pan number from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */ 
    public void validatePanNumber(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainee PanCardNumber : ");            
        String panNumber = scanner.nextLine();

        if (!panNumber.isEmpty()) {

            if (employeeUtil.matchRegex("^(([A-Z0-9]{10})*)$",panNumber)) {
                trainee.setPanCardNumber(panNumber);
            } else {
                logger.info("not valid");
                validatePanNumber(trainee);
            }	    
        }
    }

    /**
     *
     * <h1> validateCurrentTask </h1>
     *
     * Method used to validate trainee current project name from user
     *
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */ 
    public void validateCurrentTask(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter your currentTask : ");            
        String currentTask = scanner.nextLine();

        if (!currentTask.isEmpty()) {

            if (employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",currentTask)) {
                trainee.setCurrentTask(currentTask);
            } else {
                logger.info("not valid");
                validateCurrentTask(trainee);
            }	    
        }
    }

    /**
     *
     * <h1> validateCurrentTechknowledge </h1>
     * <p>
     * Method used to validate trainee currentTechknowledge from user
     * </p>
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     *
     */ 
    public void validateCurrentTechknowledge(Trainee trainee) {

        Scanner scanner = new Scanner(System.in);	
	    logger.info("Enter your currentTechknowledge : ");            
        String currentTechknowledge = scanner.nextLine();

        if (!currentTechknowledge.isEmpty()) {

            if (employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",currentTechknowledge)) {
                trainee.setCurrentTechknowledge(currentTechknowledge);
            } else {
                logger.info("not valid");
                validateCurrentTechknowledge(trainee);
            }	    
        }
    }
}