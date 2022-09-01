package com.ideas2it.controller;

import java.lang.NullPointerException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
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

public class TraineeController {

    private EmployeeServiceImpl employeeService = new EmployeeServiceImpl();

    private Logger logger = LoggerFactory.getLogger(TraineeController.class);

    private EmployeeUtil util = new EmployeeUtil();

    /**
     *
     * <h1> Trainee Menu </h1>
     *
     * method used to presents the main menu for trainee related operation
     * 
     * operations are......
     * ----> create user profile and store in database
     * ----> display the trainees in our database  
     * ----> update details or change smething in database
     * ----> delete the trainee in our database   
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
                stringBuilder.append("\nEnter 1 for Create trainee details in database\n");
	        stringBuilder.append("Enter 2 for Read   trainee details in database\n"); 
		stringBuilder.append("Enter 3 for Update trainee details in database\n"); 
	        stringBuilder.append("Enter 4 for Delete trainee details in database\n");
	        stringBuilder.append("Enter 5 for assign trainers for trainee\n");
                stringBuilder.append("Enter 6 for Exit  \n\n\n");
                stringBuilder.append("----------------------------------------------");
                stringBuilder.append("\n----------------------------------------------");
                stringBuilder.append("\n***************** THANK YOU ******************");
                stringBuilder.append("\n----------------------------------------------");
                logger.info("" + stringBuilder.substring(1,249));    
                String userOption = scanner.next();
                isContinue = perform(userOption, isContinue);
                logger.info("" + stringBuilder.substring(250));

            } catch (InputMismatchException e) {
                logger.error("\ninvalid data : " + e);

            } catch (HibernateException e) {
                logger.error("\ninvalid data : " + e);

            } catch (SQLException e) {
                logger.error("\n" + e);

            } catch (NullPointerException e) {
                logger.error("\nno data found" + e);
            }
        }
    } 

    /**
     *
     * <h1> PerformUserOperation </h1>
     *
     * method used to select the which operation will be happens 
     * 
     * operations are......
     * ----> create user profile and store in database
     * ----> display the trainers in our database  
     * ----> update details or change smething in database
     * ----> delete the trainer in our database   
     *
     * @param {@link String} userOption for perform one operation 
     * @param {@link boolean} isContinue for continue operations 
     * @return {@link boolean} return boolean 
     *
     */
    public boolean perform(String userOption, boolean isContinue) throws InputMismatchException, SQLException, HibernateException, NullPointerException {

        switch (userOption) {
        case "1":	   
     	    createTraineeData();                
            break;
        case "2":
            readTraineeData();                
            break;
        case "3":
            updateTraineeData();
            break; 
        case "4":
            removeTraineeData();                     
            break;
        case "5":
            assignTrainersForTrainee();                     
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
     * method used to create Trainee profile  
     *
     * @return {@link void} returns nothing
     */
    public void createTraineeData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {
        
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;

        while (isContinue) {
            getAndSetData();
            logger.info(" \nIf you want to continue\n1.yes\t2.no");
            String userOption = scanner.next();
            logger.info("----------------------------------------------");
            isContinue = !(userOption.equals("2"));                   
        }
    }

    /**
     *
     * <h1> getAndSetData </h1>
     *
     * method used to get and set details and store in database
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
     * method used to select display choise of trainee details 
     *
     * @return {@link void} returns nothing
     *
     */
    public void readTraineeData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {
        
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;

        while (isContinue) { 
	    logger.info("\n1.whole data\n2.particular data");
            String userOption = scanner.next();
            logger.info("\n----------------------------------------------"); 

            if (userOption.equals("1")) {
                readWholeTraineeData();
            } else {
                readParticularTraineeData();
            }        
            logger.info("\nif you want to continue\n1.yes\t2.no"); 
            userOption = scanner.next();          
            isContinue = !(userOption.equals("2")); 
        }
    }

    /**
     *
     * <h1> displayWholeTraineeData </h1>
     *
     * method used to display all trainee details 
     *
     * @return {@link void} returns nothing
     *
     */
    public void readWholeTraineeData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {

        List<Trainee> trainees = employeeService.getTraineesData();

        if (trainees == null) {
            logger.info("\nNo data found");
        } else {
            trainees.forEach(trainee -> { StringBuilder stringBuilder = new StringBuilder();
                                          stringBuilder.append("\nTrainee Details : ") 
                                          .append("\nTraineeId          : "+trainee.getTraineeId())
                                          .append("\nTraineeName        : " + trainee.getEmployeeName())
                                          .append("\nTraineeDesignation : " + trainee.getEmployeeDesignation())
                                          .append("\nTraineeMail        : " + trainee.getEmployeeMail())
                                       	  .append("\nTraineeMobileNumber: " + trainee.getEmployeeMobileNumber())
                                          .append("\nCurrentAddress     : " + trainee.getCurrentAddress());
                                          logger.info(stringBuilder + "\n----------------------------------------------");});                             
        }
    } 

    /**
     *
     * <h1> displayParticularTrainerData </h1>
     *
     * method used to particular trainer details 
     *
     * @return {@link void} returns nothing
     *
     */
    public void readParticularTraineeData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {

        Scanner scanner = new Scanner(System.in);
        logger.info("\nEnter TraineeId :");
        boolean isValidId = true;

        while (isValidId) {
            int traineeId = scanner.nextInt();
            Trainee trainee = employeeService.searchTraineeData(traineeId);

            if (trainee == null) {
                logger.info("\nNo data found\nEnter valid Id");
                isValidId = true; 
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("\nTrainee Detail : ")
                .append("\nTrainee Id          : " + trainee.getTraineeId())
                .append("\nTrainee Name        : " + trainee.getEmployeeName())
                .append("\nTrainee Designation : " + trainee.getEmployeeDesignation())
                .append("\nTrainee Mail        : " + trainee.getEmployeeMail())
                .append("\nTrainee MobileNumber: " + trainee.getEmployeeMobileNumber())
                .append("\nCurrent Address     : " + trainee.getCurrentAddress());
                logger.info(stringBuilder + "\n----------------------------------------------");
                trainee.getTrainers().forEach(trainer -> { StringBuilder stringBuilder1 = new StringBuilder();
                                                      stringBuilder1.append("\nAssociated trainer")
                                                                    .append("\nTrainer Id          : " + trainer.getTrainerId()) 
                                                      .append("\nTrainer Name        : " + trainer.getEmployeeName());
                                                      logger.info(stringBuilder + "\n----------------------------------------------");});     
                
                isValidId = false;
            }
        }
    }

    /**
     *
     * <h1> updateTraineeData </h1>
     *
     * method used to update the changes for trainee details 
     *
     * @return {@link void} returns nothing
     *
     */
    public void updateTraineeData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {

        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;

        while (isContinue) {          
            logger.info("\nEnter TraineeId :");
            int traineeId = scanner.nextInt();        
            boolean isValidId = true;

            while (isValidId) {                       
                Trainee trainee = employeeService.searchTraineeData(traineeId);

                if (trainee == null) {                           
                    logger.info("\nno data found\nEnter valid Id");
                    isValidId = true; 
                } else {
                    Trainee updateTrainee = getInformationForUpdateTrainee(trainee);
                    String message = employeeService.updateTraineeData(traineeId, updateTrainee);
                    logger.info("" + message);
                    isValidId = false;
                }
            }
            logger.info("\nif you want to continue\n1.yes\t2.no"); 
            String userOption = scanner.next();
            isContinue = !(userOption.equals("2"));
        }
    }

    /**
     *
     * <h1> removeTraineeData </h1>
     *
     * method used to remove trainee details  
     *
     * @return {@link void} returns nothing
     *
     */
    public void removeTraineeData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {
        
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;

        while (isContinue) {
            int employeeId; 
            boolean isValidId = true;      

            while (isValidId) {
                logger.info("\nEnter TraineeId :");
                int traineeId = scanner.nextInt();
                String message = employeeService.deleteTraineeData(traineeId);
                logger.info("" + message);
                isValidId = false;
            }
            logger.info("\nIf you want to continue\n1.yes\t2.no" );
            String userOption = scanner.next();
            logger.info("\n----------------------------------------------");
            isContinue = !(userOption.equals("2")); 
        }
    }

    /**
     *
     * <h1> assignTrainersForTrainee </h1>
     *
     * method used to assign trainee for multiple trainers 
     *
     * @return {@link void} returns nothing
     *
     */
    public void assignTrainersForTrainee() throws InputMismatchException, SQLException, HibernateException, NullPointerException {

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
            isContinue = !(userOption.equals("2"));
        }
    }

    /**
     *
     * <h1> getTraineesForAssignTrainee </h1>
     *
     * method used to get trainers for assign trainee 
     *
     * @param {@link trainee} trainee
     * @return {@link List<Trainers>} returns list of trainers 
     *
     */
    public Trainee getTrainersForAssignTrainee(Trainee trainee) throws InputMismatchException, SQLException, HibernateException, NullPointerException {

        Scanner scanner = new Scanner(System.in);
        List<Trainer> trainers = employeeService.getTrainersData();

        if (trainers == null) {
            logger.info("\nNo data found");
        } else {
            logger.info("Enter 0 for stop assigning.....\nEnter trainer Id :");
            int trainerId;

            do {  
                trainerId = scanner.nextInt();               

                if (trainerId != 0) {

                    for (Trainer trainerById : trainers) {

                        if (trainerId == trainerById.getTrainerId()) {
                            trainee.getTrainers().add(trainerById);
                        }
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
     * method used to get trainer details from user for create profile
     *
     * @param {@link String} uuid
     * @return {@link Trainer} returns trainer
     *
     */ 
    public Trainee getInformationFromTrainee(String uuid) throws SQLException {

       
        Trainee trainee = new Trainee();
        boolean isValid = true;

        trainee.setUuid(uuid); 
        getTraineeName(trainee, isValid);
        getTraineeDateofBirth(trainee, isValid);
        getTraineeDesignation(trainee, isValid);
        getTraineeMailId(trainee, isValid);
        getTraineeMobileNumber(trainee, isValid);
        getTraineeAddress(trainee, isValid);
        getTraineeAadharNumber(trainee, isValid);
        getTraineePanNumber(trainee, isValid);
        getTraineeCurrentTask(trainee, isValid);
        getTraineeCurrentTechknowledge(trainee, isValid);
        return trainee;
    }

    /**
     *
     * <h1> getInformationFofUpdateTrainee </h1>
     *
     * method used to get trainee details from user for update profile
     *
     * @param {@link Trainee} trainee
     * @return {@link Trainee} returns trainee
     *
     */
    public Trainee getInformationForUpdateTrainee(Trainee trainee) throws SQLException {
 
        boolean isValid = true;
        getName(trainee, isValid);
        getDateofBirth(trainee, isValid);
        getDesignation(trainee, isValid);
        getMailId(trainee, isValid);
        getMobileNumber(trainee, isValid);
        getAddress(trainee, isValid);
        getAadharNumber(trainee, isValid);
        getPanNumber(trainee, isValid);
        getCurrentTask(trainee, isValid);
        getCurrentTechknowledge(trainee, isValid);
        return trainee;
    }

    /**
     *
     * <h1> getTraineeName </h1>
     *
     * method used to get trainee name from user
     *
     * @param {@link Trainee} trainee
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */ 
    public void getTraineeName(Trainee trainee, boolean isValid) {

        Scanner scanner = new Scanner(System.in); 
	logger.info("Enter Trainee Name : ");
        String name = scanner.nextLine();

        if (!name.isEmpty()) {
            boolean isValidName = true;

            while (isValidName) {
                isValid = util.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",name);

                if (isValid) {
                    trainee.setEmployeeName(name);
                    isValidName = false;
                } else {
                   logger.info("not valid");
                   name = scanner.nextLine();
                }
            }            	    
        }
    }

    /**
     *
     * <h1> getTraineeDateofBirth </h1>
     *
     * method used to get trainee date of birth from user
     *
     * @param {@link Trainee} trainee
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */ 
    public void getTraineeDateofBirth(Trainee trainee, boolean isValid) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainee DateOfBirth MM/DD/YYYY:");
        String employeeDateOfBirth = scanner.nextLine();

        if (!employeeDateOfBirth.isEmpty()) {
            boolean isValidDOB = true;

            while (isValidDOB) {
                isValid = util.validationOfDateOfBirth(employeeDateOfBirth);

                if (isValid) {
                    String[] date = employeeDateOfBirth.split("/");
                    employeeDateOfBirth = date[2]+"-"+date[0]+"-"+date[1];
                    trainee.setEmployeeDateOfBirth(employeeDateOfBirth);
                    isValidDOB = false;
                } else {
                   logger.info("not valid");
                   employeeDateOfBirth = scanner.nextLine();
                }
            }
        }
    }

    /**
     *
     * <h1> getTraineeDesignation </h1>
     *
     * method used to get trainee designation from user
     *
     * @param {@link Trainee} trainee
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */ 
    public void getTraineeDesignation(Trainee trainee, boolean isValid) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainee Designation : ");
        String designation = scanner.nextLine();

        if (!designation.isEmpty()) {
            boolean isValidDesignation = true;

            while (isValidDesignation) {
                isValid = util.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",designation);

                if (isValid) {
                    trainee.setEmployeeDesignation(designation);
                    isValidDesignation = false;
                } else {
                   logger.info("not valid");
                   designation = scanner.nextLine();
                }
            }	    
        }  
    }

    /**
     *
     * <h1> getTraineeMailId </h1>
     *
     * method used to get trainee mail Id from user
     *
     * @param {@link Trainee} trainee
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */
    public void getTraineeMailId(Trainee trainee, boolean isValid) {

        Scanner scanner = new Scanner(System.in);
        logger.info("Enter Trainee Mail : ");
        boolean isValidMail = true;

        while (isValidMail) {
            String mail = scanner.nextLine();

            if (!mail.isEmpty()) {
                try {

                    isValid = util.validationOfMail(mail);
                    trainee.setEmployeeMail(mail);
                    isValidMail = false;                 

                } catch (EmailMismatchException e) {
                    logger.error("Exception occured :" + e);
                }
            }	             
        }
    }

    /**
     *
     * <h1> getTraineeMobileNumber </h1>
     *
     * method used to get trainee mobile number from user
     *
     * @param {@link Trainee} trainee
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */ 
    public void getTraineeMobileNumber(Trainee trainee, boolean isValid) {

        Scanner scanner = new Scanner(System.in);     
	logger.info("Enter Trainee MobileNumber : ");
        String mobileNumber = scanner.nextLine();

        if (!mobileNumber.isEmpty()) {
            boolean isValidMobileNumber = true;

            while (isValidMobileNumber) {
                isValid = util.matchRegex("^(([6-9]{1}[0-9]{9})*)$",mobileNumber);

                if (isValid) {
                    trainee.setEmployeeMobileNumber(mobileNumber);
                    isValidMobileNumber = false;
                } else {
                   logger.info("not valid");
                   mobileNumber = scanner.nextLine();
                }
            }	    
        }
    }

    /**
     *
     * <h1> getTraineeAddress </h1>
     *
     * method used to get trainee address from user
     *
     * @param {@link Trainee} trainee
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */        
    public void getTraineeAddress(Trainee trainee, boolean isValid) {

        Scanner scanner = new Scanner(System.in);
        logger.info("Enter Trainee CurrentAddress : ");
        String currentAddress = scanner.nextLine();

        if (!currentAddress.isEmpty()) {
            boolean isValidAddress = true;

            while (isValidAddress) {
                isValid = util.matchRegex("^(([0-9\\sa-zA-Z,.-]{3,150})*)$",currentAddress);

                if (isValid) {
                    trainee.setCurrentAddress(currentAddress);
                    isValidAddress = false;
                } else {
                   logger.info("not valid");
                   currentAddress = scanner.nextLine();
                }
            }	    
        }
    }

    /**
     *
     * <h1> getTraineeAadharNumber </h1>
     *
     * method used to get trainee aadhar number from user
     *
     * @param {@link Trainee} trainee
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */
    public void getTraineeAadharNumber(Trainee trainee, boolean isValid) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainee AadharCardNumber : ");            
        String aadharNumber = scanner.nextLine();

        if (!aadharNumber.isEmpty()) {
            boolean isValidAadharCardNumber = true;

            while (isValidAadharCardNumber) {
                isValid = util.matchRegex("^(([1-9]{1}[0-9]{11})*)$",aadharNumber);

                if (isValid) {
                    trainee.setAadharCardNumber(aadharNumber);
                    isValidAadharCardNumber = false;
                } else {
                   logger.info("not valid");
                   aadharNumber = scanner.nextLine();
                }
            }	    
        }
    }

    /**
     *
     * <h1> getTraineePanNumber </h1>
     *
     * method used to get trainee pan number from user
     *
     * @param {@link Trainee} trainee
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */
    public void getTraineePanNumber(Trainee trainee, boolean isValid) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainee PanCardNumber : ");            
        String panNumber = scanner.nextLine();

        if (!panNumber.isEmpty()) {
            boolean isValidPanCardNumber = true;

            while (isValidPanCardNumber) {
                isValid = util.matchRegex("^(([A-Z0-9]{10})*)$",panNumber);

                if (isValid) {
                    trainee.setPanCardNumber(panNumber);
                    isValidPanCardNumber = false;
                } else {
                   logger.info("not valid");
                   panNumber = scanner.nextLine();
                }
            }	    
        }
    }

    /**
     *
     * <h1> getTraineeCurrentTask </h1>
     *
     * method used to get trainee current project from user
     *
     * @param {@link Trainee} trainee
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */ 
    public void getTraineeCurrentTask(Trainee trainee, boolean isValid) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter your currentTask : ");            
        String currentTask = scanner.nextLine();

        if (!currentTask.isEmpty()) {
            boolean isValidCurrentTask = true;

            while (isValidCurrentTask) {
                isValid = util.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",currentTask);

                if (isValid) {
                    trainee.setCurrentTask(currentTask);
                    isValidCurrentTask = false;
                } else {
                   logger.info("not valid");
                   currentTask = scanner.nextLine();
                }
            }	    
        }
    }

    /**
     *
     * <h1> getTraineeCurrentTechknowledge </h1>
     *
     * method used to get trainee currentTechknowledge from user
     *
     * @param {@link Trainee} trainee
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */
    public void getTraineeCurrentTechknowledge(Trainee trainee, boolean isValid) {

        Scanner scanner = new Scanner(System.in);	
	logger.info("Enter your currentTechknowledge : ");            
        String currentTechknowledge = scanner.nextLine();

        if (!currentTechknowledge.isEmpty()) {
            boolean isValidCurrentTechknowledge = true;

            while (isValidCurrentTechknowledge) {
                isValid = util.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",currentTechknowledge);

                if (isValid) {
                    trainee.setCurrentTechknowledge(currentTechknowledge);
                    isValidCurrentTechknowledge = false;
                } else {
                   logger.info("not valid");
                   currentTechknowledge = scanner.nextLine();
                }
            }	    
        }
    }

    /**
     *
     * <h1> getName </h1>
     *
     * method used to get trainee name from user
     *
     * @param {@link Trainee} trainee
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */ 
    public void getName(Trainee trainee, boolean isValid) {

        Scanner scanner = new Scanner(System.in); 
	logger.info("Enter Trainee Name : ");
        String name = scanner.nextLine();

        if (!name.isEmpty()) {
            boolean isValidName = true;

            while (isValidName) {
                isValid = util.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",name);

                if (isValid) {
                    trainee.setEmployeeName(name);
                    isValidName = false;
                } else {
                   logger.info("not valid");
                   name = scanner.nextLine();
                }
            }            	    
        }
    }

    /**
     *
     * <h1> getDateofBirth </h1>
     *
     * method used to get trainee date of birth from user
     *
     * @param {@link Trainee} trainee
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */ 
    public void getDateofBirth(Trainee trainee, boolean isValid) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainee DateOfBirth MM/DD/YYYY:");
        String employeeDateOfBirth = scanner.nextLine();

        if (!employeeDateOfBirth.isEmpty()) {
            boolean isValidDOB = true;

            while (isValidDOB) {
                isValid = util.validationOfDateOfBirth(employeeDateOfBirth);

                if (isValid) {
                    String[] date = employeeDateOfBirth.split("/");

                    employeeDateOfBirth = date[2]+"-"+date[0]+"-"+date[1];
                    trainee.setEmployeeDateOfBirth(employeeDateOfBirth);
                    isValidDOB = false;
                } else {
                   logger.info("not valid");
                   employeeDateOfBirth = scanner.nextLine();
                }
            }
        }
    }

    /**
     *
     * <h1> getDesignation </h1>
     *
     * method used to get trainee designation from user
     *
     * @param {@link Trainee} trainee
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */ 
    public void getDesignation(Trainee trainee, boolean isValid) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainee Designation : ");
        String designation = scanner.nextLine();

        if (!designation.isEmpty()) {
            boolean isValidDesignation = true;

            while (isValidDesignation) {
                isValid = util.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",designation);

                if (isValid) {
                    trainee.setEmployeeDesignation(designation);
                    isValidDesignation = false;
                } else {
                   logger.info("not valid");
                   designation = scanner.nextLine();
                }
            }	    
        }  
    }

    /**
     *
     * <h1> getMailId </h1>
     *
     * method used to get trainee mail Id  from user
     *
     * @param {@link Trainee} trainee
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */ 
    public void getMailId(Trainee trainee, boolean isValid) {

        Scanner scanner = new Scanner(System.in);
        logger.info("Enter Trainee Mail : ");
        boolean isValidMail = true;

        while (isValidMail) {
            String mail = scanner.nextLine();

            if (!mail.isEmpty()) {
                try {
                    isValid = util.validationOfMail(mail);
                    trainee.setEmployeeMail(mail);
                    isValidMail = false;                 

                } catch (EmailMismatchException e) {
                    logger.error("Exception occured :" + e);
                }
            }	             
        }
    }

    /**
     *
     * <h1> getMobileNumber </h1>
     *
     * method used to get trainee mobile number from user
     *
     * @param {@link Trainee} trainee
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */ 
    public void getMobileNumber(Trainee trainee, boolean isValid) {

        Scanner scanner = new Scanner(System.in);     
	logger.info("Enter Trainee MobileNumber : ");
        String mobileNumber = scanner.nextLine();

        if (!mobileNumber.isEmpty()) {
            boolean isValidMobileNumber = true;

            while (isValidMobileNumber) {
                isValid = util.matchRegex("^(([6-9]{1}[0-9]{9})*)$",mobileNumber);

                if (isValid) {
                    trainee.setEmployeeMobileNumber(mobileNumber);
                    isValidMobileNumber = false;
                } else {
                   logger.info("not valid");
                   mobileNumber = scanner.nextLine();
                }
            }	    
        }
    }

    /**
     *
     * <h1> getAddress </h1>
     *
     * method used to get trainee address from user
     *
     * @param {@link Trainee} trainee
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */        
    public void getAddress(Trainee trainee, boolean isValid) {

        Scanner scanner = new Scanner(System.in);
        logger.info("Enter Trainee CurrentAddress : ");
        String currentAddress = scanner.nextLine();

        if (!currentAddress.isEmpty()) {
            boolean isValidAddress = true;

            while (isValidAddress) {
                isValid = util.matchRegex("^(([0-9\\sa-zA-Z,.-]{3,150})*)$",currentAddress);

                if (isValid) {
                    trainee.setCurrentAddress(currentAddress);
                    isValidAddress = false;
                } else {
                   logger.info("not valid");
                   currentAddress = scanner.nextLine();
                }
            }	    
        }
    }

    /**
     *
     * <h1> getAadharNumber </h1>
     *
     * method used to get trainee aadhar number from user
     *
     * @param {@link Trainee} trainee
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */
    public void getAadharNumber(Trainee trainee, boolean isValid) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainee AadharCardNumber : ");            
        String aadharNumber = scanner.nextLine();

        if (!aadharNumber.isEmpty()) {
            boolean isValidAadharCardNumber = true;

            while (isValidAadharCardNumber) {
                isValid = util.matchRegex("^(([1-9]{1}[0-9]{11})*)$",aadharNumber);

                if (isValid) {
                    trainee.setAadharCardNumber(aadharNumber);
                    isValidAadharCardNumber = false;
                } else {
                   logger.info("not valid");
                   aadharNumber = scanner.nextLine();
                }
            }	    
        }
    }

    /**
     *
     * <h1> getPanNumber </h1>
     *
     * method used to get trainee pan number from user
     *
     * @param {@link Trainee} trainee
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */
    public void getPanNumber(Trainee trainee, boolean isValid) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainee PanCardNumber : ");            
        String panNumber = scanner.nextLine();

        if (!panNumber.isEmpty()) {
            boolean isValidPanCardNumber = true;

            while (isValidPanCardNumber) {
                isValid = util.matchRegex("^(([A-Z0-9]{10})*)$",panNumber);

                if (isValid) {
                    trainee.setPanCardNumber(panNumber);
                    isValidPanCardNumber = false;
                } else {
                   logger.info("not valid");
                   panNumber = scanner.nextLine();
                }
            }	    
        }
    }

    /**
     *
     * <h1> getCurrentTask </h1>
     *
     * method used to get trainee current task name from user
     *
     * @param {@link Trainee} trainee
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */
    public void getCurrentTask(Trainee trainee, boolean isValid) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter your currentTask : ");            
        String currentTask = scanner.nextLine();

        if (!currentTask.isEmpty()) {
            boolean isValidCurrentTask = true;

            while (isValidCurrentTask) {
                isValid = util.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",currentTask);

                if (isValid) {
                    trainee.setCurrentTask(currentTask);
                    isValidCurrentTask = false;
                } else {
                   logger.info("not valid");
                   currentTask = scanner.nextLine();
                }
            }	    
        }
    }

    /**
     *
     * <h1> getCurrentTechknowledge </h1>
     *
     * method used to get trainee Current Techknowledge from user
     *
     * @param {@link Trainee} trainee
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */ 
    public void getCurrentTechknowledge(Trainee trainee, boolean isValid) {

        Scanner scanner = new Scanner(System.in);	
	logger.info("Enter your currentTechknowledge : ");            
        String currentTechknowledge = scanner.nextLine();

        if (!currentTechknowledge.isEmpty()) {
            boolean isValidCurrentTechknowledge = true;

            while (isValidCurrentTechknowledge) {
                isValid = util.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",currentTechknowledge);

                if (isValid) {
                    trainee.setCurrentTechknowledge(currentTechknowledge);
                    isValidCurrentTechknowledge = false;
                } else {
                   logger.info("not valid");
                   currentTechknowledge = scanner.nextLine();
                }
            }	    
        }
    }   
}