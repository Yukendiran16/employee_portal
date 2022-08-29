package com.ideas2it.controller;

import java.util.Scanner;
import java.util.UUID;
import java.util.InputMismatchException;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException; 

import java.lang.NullPointerException;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.service.EmployeeService;
import com.ideas2it.service.impl.EmployeeServiceImpl;
import com.ideas2it.util.EmployeeUtil;
import com.ideas2it.exception.EmailMismatchException;


public class TraineeController {

    private static EmployeeServiceImpl employeeService = new EmployeeServiceImpl();

    private static Logger logger = LoggerFactory.getLogger(TraineeController.class);

    private static EmployeeUtil util = new EmployeeUtil();
    
    public static void traineeMenu()  {
 
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;
        while (isContinue) {

            try {
                logger.info("\nEnter 1 for Create trainee details in database\n"+ 
			    "Enter 2 for Read   trainee details in database\n"+ 
			    "Enter 3 for Update trainee details in database\n"+ 
	                    "Enter 4 for Delete trainee details in database\n"+
                            "Enter 5 for Assign trainer for trainees\n"+
                            "Enter 6 for Exit  \n\n\n"+"----------------------------------------------");
                String userOption = scanner.next();
                isContinue = Perform(userOption, isContinue);
                logger.info("\n----------------------------------------------");
                logger.info("***************** THANK YOU ******************");
                logger.info("----------------------------------------------");    
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

   public static boolean Perform(String userOption, boolean isContinue) throws InputMismatchException, SQLException, HibernateException, NullPointerException {

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
                //removeTraineeData();                     
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
     * method used to create trainee profile  
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @return {@link void} returns nothing
     */
    public static void createTraineeData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {
        
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;
        while (isContinue) {
            
            getAndSetData();
            logger.info(" \nIf you want to continue\n" + "1.yes\n" + "2.no");
            String addAnotherTraineeData = scanner.next();
            logger.info("----------------------------------------------");
            isContinue = !(addAnotherTraineeData.equals("2"));                   
        }
    }

    public static void getAndSetData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {

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
     * method used to read trainee profile  
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @return {@link void} returns nothing
     */
    public static void readTraineeData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {
        
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;
        while (isContinue) { 

            
	    logger.info("\n1.whole data\n" + "2.particular data");
            String readTraineeData = scanner.next();
            logger.info("\n----------------------------------------------"); 

            if (readTraineeData.equals("1")) {
                readWholeTraineeData(readTraineeData);
            } else {
                readParticularTraineeData(readTraineeData);
            }        
                    
            logger.info("\nif you want to continue\n" + "1.yes\n" + "2.no"); 
            String choiceForReadAnotherInformation = scanner.next();          
            isContinue = !(choiceForReadAnotherInformation.equals("2")); 
        }
    }

    public static void readWholeTraineeData(String readTraineeData) throws InputMismatchException, SQLException, HibernateException, NullPointerException {

        List<Trainee> trainees = employeeService.getTraineesData();
        if (trainees == null) {
            logger.info("\nNo data found");
        } else {
            trainees.forEach(trainee -> logger.info("Trainee Details : " +"\n"+ 
                                                    "TraineeId          : "+trainee.getTraineeId() +"\n"+
                                                    "TraineeName        : " + trainee.getEmployeeName()+"\n"+
                                                    "TraineeDesignation : " + trainee.getEmployeeDesignation()+"\n"+
                                                    "TraineeMail        : " + trainee.getEmployeeMail() +"\n"+
                                       	            "TraineeMobileNumber: " + trainee.getEmployeeMobileNumber() +"\n"+
                                                    "CurrentAddress     : " + trainee.getCurrentAddress() +"\n"+
                                                    "----------------------------------------------"));                             
        }
    } 

    public static void readParticularTraineeData(String readTraineeData) throws InputMismatchException, SQLException, HibernateException, NullPointerException {

        Scanner scanner = new Scanner(System.in);
        logger.info("\nEnter TraineeId :");
        boolean isValidId = true;
        while (isValidId) {
            int traineeId = scanner.nextInt();
            Trainee trainee = employeeService.searchTraineeData(traineeId);
            if (trainee == null) {
                logger.info("\nNo data found\n" + "Enter valid Id");
                isValidId = true; 
            } else {
                logger.info("Trainee Detail : " +"\n"+
                            "Trainee Id          : " + trainee.getTraineeId() +"\n"+
                            "Trainee Name        : " + trainee.getEmployeeName() +"\n"+
                   	    "Trainee Designation : " + trainee.getEmployeeDesignation() +"\n"+
                            "Trainee Mail        : " + trainee.getEmployeeMail() +"\n"+
                            "Trainee MobileNumber: " + trainee.getEmployeeMobileNumber() +"\n"+
                            "Current Address     : " + trainee.getCurrentAddress());
                Trainer trainer = trainee.getTrainer();
                logger.info("Trainer Id          : " + trainer.getTrainerId() +"\n"+ 
                            "Trainer Name        : " + trainer.getEmployeeName());     
                logger.info("----------------------------------------------");
                isValidId = false;
            }
        }
    }

   /**
     * method used to update employee profile  
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @return {@link void} returns nothing
     */
    public static void updateTraineeData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {

        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;
        while (isContinue) {
          
            logger.info("\nEnter TraineeId :");
            int traineeId = scanner.nextInt();        
            boolean isValidId = true;
            while (isValidId) {
                       
                Trainee trainee = employeeService.searchTraineeData(traineeId);
                if (trainee == null) {                           
                    logger.info("\nno data found\n" + "Enter valid Id");
                    isValidId = true; 
                } else {
                    Trainee updateTrainee = getInformationForUpdateTrainee(trainee);
                    String message = employeeService.updateTraineeData(traineeId, updateTrainee);
                    logger.info("" + message);
                    isValidId = false;
                }
            }

            logger.info("\nif you want to continue\n" + "1.yes\n" + "2.no"); 
            String updateForAnotherTraineeData = scanner.next();
            isContinue = !(updateForAnotherTraineeData.equals("2"));
        }
    }

    /**
     * method used to delete employee profile  
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @return {@link void} returns nothing
     */
    public static void removeTraineeData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {
        
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

            logger.info("\nIf you want to continue : \n" + "1.yes\n" + "2.no" );
            String removeMoreDataTrainee = scanner.next();
            logger.info("\n----------------------------------------------");
            isContinue = !(removeMoreDataTrainee.equals("2")); 
        }
    }

    /**
     * method used to get trainee details from user for create profile
     * @param {@link Scanner} scanner
     * @return {@link Trainee} returns trainee
     */ 
    public static Trainee getInformationFromTrainee(String uuid) throws SQLException {

       
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
     * method used to get trainee details from user for update
     * @param {@link Scanner} scanner
     * @param {@link Trainer} trainee
     * @return {@link void} returns nothing
     */ 
    public static Trainee getInformationForUpdateTrainee(Trainee trainee) throws SQLException {
 
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

    public static void getTraineeName(Trainee trainee, boolean isValid) {

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

    public static void getTraineeDateofBirth(Trainee trainee, boolean isValid) {

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

    public static void getTraineeDesignation(Trainee trainee, boolean isValid) {

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

    public static void getTraineeMailId(Trainee trainee, boolean isValid) {

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
 
    public static void getTraineeMobileNumber(Trainee trainee, boolean isValid) {

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
        
    public static void getTraineeAddress(Trainee trainee, boolean isValid) {

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

    public static void getTraineeAadharNumber(Trainee trainee, boolean isValid) {

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

    public static void getTraineePanNumber(Trainee trainee, boolean isValid) {

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

    public static void getTraineeCurrentTask(Trainee trainee, boolean isValid) {

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

    public static void getTraineeCurrentTechknowledge(Trainee trainee, boolean isValid) {

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



    public static void getName(Trainee trainee, boolean isValid) {

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

    public static void getDateofBirth(Trainee trainee, boolean isValid) {

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

    public static void getDesignation(Trainee trainee, boolean isValid) {

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

    public static void getMailId(Trainee trainee, boolean isValid) {

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
 
    public static void getMobileNumber(Trainee trainee, boolean isValid) {

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
        
    public static void getAddress(Trainee trainee, boolean isValid) {

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

    public static void getAadharNumber(Trainee trainee, boolean isValid) {

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

    public static void getPanNumber(Trainee trainee, boolean isValid) {

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

    public static void getCurrentTask(Trainee trainee, boolean isValid) {

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

    public static void getCurrentTechknowledge(Trainee trainee, boolean isValid) {

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