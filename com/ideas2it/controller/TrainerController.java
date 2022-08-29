package com.ideas2it.controller;

import java.util.Scanner;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.util.InputMismatchException;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.lang.NullPointerException; 

import org.hibernate.HibernateException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.service.EmployeeService;
import com.ideas2it.service.impl.EmployeeServiceImpl;
import com.ideas2it.util.EmployeeUtil;
import com.ideas2it.exception.EmailMismatchException;


public class TrainerController {

    private static EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
 
    private static EmployeeUtil util = new EmployeeUtil();

    private static Logger logger = LoggerFactory.getLogger(TrainerController.class);

    public static void trainerMenu()  {
       
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;
        while (isContinue) {
           
            try {

                logger.info("\nEnter 1 for Create trainer details in database\n"+ 
			    "Enter 2 for Read   trainer details in database\n"+ 
			    "Enter 3 for Update trainer details in database\n"+ 
	                    "Enter 4 for Delete trainer details in database\n"+
                            "Enter 5 for Assign trainee for trainers\n"+
                            "Enter 6 for Exit  \n\n\n"+"----------------------------------------------");
                String userOption = scanner.next();
                isContinue = Perform(userOption, isContinue);                
                logger.info("\n----------------------------------------------");
                logger.info("***************** THANK YOU ******************");
                logger.info("----------------------------------------------");    

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

    public static boolean Perform(String userOption, boolean isContinue) throws InputMismatchException, SQLException, HibernateException, NullPointerException {
 
        switch (userOption) {
            case "1":	   
     	        createTrainerData();                
                break;
            case "2":
                readTrainerData();                
                break;
            case "3":
                updateTrainerData();
                break; 
            case "4":
                removeTrainerData();                
                break;
            case "5":
                assignTrainerForTrainees();
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
     * method used to create employee profile  
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @return {@link void} returns nothing
     */
    public static void createTrainerData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {
        
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;
        String position = "";
        while (isContinue) {

            getAndSetData();
            logger.info(" \nIf you want to continue\n" + "1.yes\n" + "2.no");
            String addAnotherEmployeeData = scanner.next();
            logger.info("----------------------------------------------");
            isContinue = !(addAnotherEmployeeData.equals("2"));                   
        }
    }



    public static void getAndSetData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {

         UUID uuId = UUID.randomUUID();
         String temp = uuId.toString();
         String[] arr = temp.split("-");
         String uuid = arr[0]+arr[3];
       	 logger.info("\nEnter Trainer Details :");
         Trainer trainer = getInformationFromTrainer(uuid);
         String message = employeeService.addTrainer(trainer);                     
         logger.info("" + message);
         logger.info("\n----------------------------------------------");
    }

    /**
     * method used to read employee profile  
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @return {@link void} returns nothing
     */
    public static void readTrainerData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {
        
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;
        while (isContinue) { 

            
	    logger.info("\n1.whole data\n" + "2.particular data");
            String readTrainerData = scanner.next();
            logger.info("\n----------------------------------------------"); 

            if (readTrainerData.equals("1")) {
                readWholeTrainerData(readTrainerData);
            } else {
                readParticularTrainerData(readTrainerData);
            }        
                    
            logger.info("\nif you want to continue\n" + "1.yes\n" + "2.no"); 
            String choiceForReadAnotherInformation = scanner.next();          
            isContinue = !(choiceForReadAnotherInformation.equals("2")); 
        }
    }


    public static void readWholeTrainerData(String readTrainerData) throws InputMismatchException, SQLException, HibernateException, NullPointerException {

        List<Trainer> trainers = employeeService.getTrainersData();
        if (trainers == null) {
            logger.info("\nNo data found");
        } else {
            trainers.forEach(trainer -> logger.info("Trainer Detail : " +"\n"+ "Employee Id          : "+trainer.getTrainerId() +"\n"+
                                                    "Trainer Name        : "+trainer.getEmployeeName() +"\n"+
                                                    "Trainer Designation : "+trainer.getEmployeeDesignation() +"\n"+
                                                    "Trainer Mail        : "+trainer.getEmployeeMail() +"\n"+
                                       	            "Trainer MobileNumber: "+trainer.getEmployeeMobileNumber() +"\n"+
                                                    "Current Address     : "+trainer.getCurrentAddress() +"\n"+
                                                    "----------------------------------------------"));                             
        }
    } 
    public static void readParticularTrainerData(String readTrainerData) throws InputMismatchException, SQLException, HibernateException, NullPointerException {

        Scanner scanner = new Scanner(System.in);
        logger.info("\nEnter TrainerId :");
        boolean isValidId = true;
        while (isValidId) {
            int trainerId = scanner.nextInt();
            Trainer trainer = employeeService.searchTrainerData(trainerId);
            if (trainer == null) {
                logger.info("\nNo data found\n" + "Enter valid Id");
                isValidId = true; 
            } else {
                logger.info("Trainer Detail : " +"\n"+
                            "Trainer Id          : " + trainer.getTrainerId()+"\n"+
                            "Trainer Name        : " + trainer.getEmployeeName()+"\n"+
                   	    "Trainer Designation : " + trainer.getEmployeeDesignation()+"\n"+
                            "Trainer Mail        : " + trainer.getEmployeeMail()+"\n"+
                            "Trainer MobileNumber: " + trainer.getEmployeeMobileNumber()+"\n"+
                            "Current Address     : " + trainer.getCurrentAddress());
                trainer.getTrainees().forEach(trainee -> logger.info("Assigned Trainees :" +"\n"+
                                              "Trainee Id          : " + trainee.getTraineeId()+"\n"+
                                              "Trainee Name        : " + trainee.getEmployeeName()));
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
    public static void updateTrainerData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {

        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;
        while (isContinue) {
           
            boolean isValidId = true;
            while (isValidId) {

                logger.info("\nEnter EmployeeId :");
                int trainerId = scanner.nextInt();                                 
                Trainer trainer = employeeService.searchTrainerData(trainerId);
                if (trainer == null) {                           
                    logger.info("\nno data found\n" + "Enter valid Id");
                    isValidId = true; 
                } else {
                    Trainer updateTrainer = getInformationForUpdateTrainer(trainer);
                    String message = employeeService.updateTrainerData(trainerId, updateTrainer);
                    logger.info("" + message);
                    isValidId = false;
                }
            }
        
            logger.info("\nif you want to continue\n" + "1.yes\n" + "2.no"); 
            String updateForAnotherEmployeeData = scanner.next();
            isContinue = !(updateForAnotherEmployeeData.equals("2"));
        }
    }

    /**
     * method used to delete employee profile  
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @return {@link void} returns nothing
     */
    public static void removeTrainerData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {
        
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;
        while (isContinue) {

            boolean isValidId = true;      
            while (isValidId) {

                logger.info("\nEnter EmployeeId :");
                int trainerId = scanner.nextInt();
                String message = employeeService.deleteTrainerData(trainerId);
                logger.info("" + message);
                isValidId = false;
            }
         
            logger.info("\nIf you want to continue : \n" + "1.yes\n" + "2.no" );
            String removeMoreDataTrainer = scanner.next();
            logger.info("\n----------------------------------------------");
            isContinue = !(removeMoreDataTrainer.equals("2")); 
        }
    }

    /**
     * method used to read employee profile  
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @return {@link void} returns nothing
     */
    public static void assignTrainerForTrainees() throws InputMismatchException, SQLException, HibernateException, NullPointerException {

        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;  
        while (isContinue) { 
            
            logger.info("\nEnter Trainer Id :");
            int trainerId = scanner.nextInt();         
            Trainer trainer = employeeService.searchTrainerData(trainerId);
            trainer = getTraineesForAssignTrainer(trainer);
            String message = employeeService.updateTrainerData(trainerId, trainer);
            logger.info("{}" + message);
            logger.info("\nIf you want to continue : \n" + "1.yes\n" + "2.no" );
            String assignNextAssociation = scanner.next();
            logger.info("\n----------------------------------------------");
            isContinue = !(assignNextAssociation.equals("2"));
        }
    }

    public static Trainer getTraineesForAssignTrainer(Trainer trainer) throws InputMismatchException, SQLException, HibernateException, NullPointerException {

        Scanner scanner = new Scanner(System.in);
        List<Trainee> trainees = employeeService.getTraineesData();
        if (trainees == null) {
            logger.info("\nNo data found");
        } else {
            logger.info("Enter 0 for stop assigning.....\nEnter trainee Id :");
            int traineeId;
            do {  
                traineeId = scanner.nextInt();               
                if (traineeId != 0) {
                    for (Trainee traineeById : trainees) {
                        if (traineeId == traineeById.getTraineeId()) {
                            trainer.getTrainees().add(traineeById);
                            traineeById.setTrainer(trainer);
                            String message = employeeService.updateTraineeData(traineeId, traineeById);
                            logger.info("{}" + message);
                        }
                    }
                }
            }while (traineeId != 0); 
        }  
        return trainer;
    }

    /**
     * method used to get trainer details from user for create profile
     * @param {@link Scanner} scanner 
     * @return {@link Trainer} returns trainer
     */ 
    public static Trainer getInformationFromTrainer(String uuid) throws SQLException {

        Trainer trainer = new Trainer();
        boolean isValid = true;

        trainer.setUuid(uuid); 
        getTrainerName(trainer, isValid);
        getTrainerDateofBirth(trainer, isValid);
        getTrainerDesignation(trainer, isValid);
        getTrainerMailId(trainer, isValid);
        getTrainerMobileNumber(trainer, isValid);
        getTrainerAddress(trainer, isValid);
        getTrainerAadharNumber(trainer, isValid);
        getTrainerPanNumber(trainer, isValid);
        getTrainerCurrentProject(trainer, isValid);
        getTrainerAchievement(trainer, isValid);
        return trainer;
    }

    /**
     * method used to get trainer details from user for update
     * @param {@link Scanner} scanner
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     */ 
    public static Trainer getInformationForUpdateTrainer(Trainer trainer) throws SQLException {
 
        boolean isValid = true;
        getName(trainer, isValid);
        getDateofBirth(trainer, isValid);
        getDesignation(trainer, isValid);
        getMailId(trainer, isValid);
        getMobileNumber(trainer, isValid);
        getAddress(trainer, isValid);
        getAadharNumber(trainer, isValid);
        getPanNumber(trainer, isValid);
        getCurrentProject(trainer, isValid);
        getAchievement(trainer, isValid);
        return trainer;
    }

    public static void getTrainerName(Trainer trainer,boolean  isValid) {

        Scanner scanner = new Scanner(System.in); 
	logger.info("Enter Trainer Name : ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            boolean isValidName = true;
            while (isValidName) {
                isValid = util.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",name);
                if (isValid) {
                    trainer.setEmployeeName(name);
                    isValidName = false;
                } else {
                   logger.info("not valid");
                   name = scanner.nextLine();
                }
            }            	    
        }
    }

    public static void getTrainerDateofBirth(Trainer trainer,boolean  isValid) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainer DateOfBirth MM/DD/YYYY:");
        String employeeDateOfBirth = scanner.nextLine();
        if (!employeeDateOfBirth.isEmpty()) {
            boolean isValidDOB = true;
            while (isValidDOB) {
                isValid = util.validationOfDateOfBirth(employeeDateOfBirth);
                if (isValid) {
                    String[] date = employeeDateOfBirth.split("/");
                    employeeDateOfBirth = date[2]+"-"+date[0]+"-"+date[1];
                    trainer.setEmployeeDateOfBirth(employeeDateOfBirth);
                    isValidDOB = false;
                } else {
                   logger.info("not valid");
                   employeeDateOfBirth = scanner.nextLine();
                }
            }
        }
    }

    public static void getTrainerDesignation(Trainer trainer,boolean  isValid) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainer Designation : ");
        String designation = scanner.nextLine();
        if (!designation.isEmpty()) {
            boolean isValidDesignation = true;
            while (isValidDesignation) {
                isValid = util.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",designation);
                if (isValid) {
                    trainer.setEmployeeDesignation(designation);
                    isValidDesignation = false;
                } else {
                   logger.info("not valid");
                   designation = scanner.nextLine();
                }
            }	    
        }  
    }

    public static void getTrainerMailId(Trainer trainer,boolean  isValid) {

        Scanner scanner = new Scanner(System.in);
        logger.info("Enter Trainer Mail : ");
        boolean isValidMail = true;
        while (isValidMail) {
            String mail = scanner.nextLine();
            if (!mail.isEmpty()) {
                try {
                    isValid = util.validationOfMail(mail);
                    trainer.setEmployeeMail(mail);
                    isValidMail = false;                 
                } catch (EmailMismatchException e) {
                    logger.error("Exception occured :" + e);
                }
            }	             
        }
    }
 
    public static void getTrainerMobileNumber(Trainer trainer,boolean  isValid) {

        Scanner scanner = new Scanner(System.in);     
	logger.info("Enter Trainer MobileNumber : ");
        String mobileNumber = scanner.nextLine();
        if (!mobileNumber.isEmpty()) {
            boolean isValidMobileNumber = true;
            while (isValidMobileNumber) {
                isValid = util.matchRegex("^(([6-9]{1}[0-9]{9})*)$",mobileNumber);
                if (isValid) {
                    trainer.setEmployeeMobileNumber(mobileNumber);
                    isValidMobileNumber = false;
                } else {
                   logger.info("not valid");
                   mobileNumber = scanner.nextLine();
                }
            }	    
        }
    }
        
    public static void getTrainerAddress(Trainer trainer,boolean  isValid) {

        Scanner scanner = new Scanner(System.in);
        logger.info("Enter Trainer CurrentAddress : ");
        String currentAddress = scanner.nextLine();
        if (!currentAddress.isEmpty()) {
            boolean isValidAddress = true;
            while (isValidAddress) {
                isValid = util.matchRegex("^(([0-9\\sa-zA-Z,.-]{3,150})*)$",currentAddress);
                if (isValid) {
                    trainer.setCurrentAddress(currentAddress);
                    isValidAddress = false;
                } else {
                   logger.info("not valid");
                   currentAddress = scanner.nextLine();
                }
            }	    
        }
    }

    public static void getTrainerAadharNumber(Trainer trainer,boolean  isValid) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainer AadharCardNumber : ");            
        String aadharNumber = scanner.nextLine();
        if (!aadharNumber.isEmpty()) {
            boolean isValidAadharCardNumber = true;
            while (isValidAadharCardNumber) {
                isValid = util.matchRegex("^(([1-9]{1}[0-9]{11})*)$",aadharNumber);
                if (isValid) {
                    trainer.setAadharCardNumber(aadharNumber);
                    isValidAadharCardNumber = false;
                } else {
                   logger.info("not valid");
                   aadharNumber = scanner.nextLine();
                }
            }	    
        }
    }

    public static void getTrainerPanNumber(Trainer trainer,boolean  isValid) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainer PanCardNumber : ");            
        String panNumber = scanner.nextLine();
        if (!panNumber.isEmpty()) {
            boolean isValidPanCardNumber = true;
            while (isValidPanCardNumber) {
                isValid = util.matchRegex("^(([A-Z0-9]{10})*)$",panNumber);
                if (isValid) {
                    trainer.setPanCardNumber(panNumber);
                    isValidPanCardNumber = false;
                } else {
                   logger.info("not valid");
                   panNumber = scanner.nextLine();
                }
            }	    
        }
    }

    public static void getTrainerCurrentProject(Trainer trainer,boolean  isValid) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter your currentProject : ");            
        String currentProject = scanner.nextLine();
        if (!currentProject.isEmpty()) {
            boolean isValidCurrentProject = true;
            while (isValidCurrentProject) {
                isValid = util.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",currentProject);
                if (isValid) {
                    trainer.setCurrentProject(currentProject);
                    isValidCurrentProject = false;
                } else {
                   logger.info("not valid");
                   currentProject = scanner.nextLine();
                }
            }	    
        }
    }

    public static void getTrainerAchievement(Trainer trainer,boolean  isValid) {

        Scanner scanner = new Scanner(System.in);	
	logger.info("Enter your achievements : ");            
        String achievement = scanner.nextLine();
        if (!achievement.isEmpty()) {
            boolean isValidAchievement = true;
            while (isValidAchievement) {
                isValid = util.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",achievement);
                if (isValid) {
                    trainer.setAchievement(achievement);
                    isValidAchievement = false;
                } else {
                   logger.info("not valid");
                   achievement = scanner.nextLine();
                }
            }	    
        }
    }

    public static void getName(Trainer trainer,boolean  isValid) {

        Scanner scanner = new Scanner(System.in); 
	logger.info("Enter Trainer Name : ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            boolean isValidName = true;
            while (isValidName) {
                isValid = util.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",name);
                if (isValid) {
                    trainer.setEmployeeName(name);
                    isValidName = false;
                } else {
                   logger.info("not valid");
                   name = scanner.nextLine();
                }
            }            	    
        }
    }

    public static void getDateofBirth(Trainer trainer,boolean  isValid) {


        Scanner scanner = new Scanner(System.in);	logger.info("Enter Trainer DateOfBirth MM/DD/YYYY:");
        String employeeDateOfBirth = scanner.nextLine();
        if (!employeeDateOfBirth.isEmpty()) {
            boolean isValidDOB = true;
            while (isValidDOB) {
                isValid = util.validationOfDateOfBirth(employeeDateOfBirth);
                if (isValid) {
                    String[] date = employeeDateOfBirth.split("/");
                    employeeDateOfBirth = date[2]+"-"+date[0]+"-"+date[1];
                    trainer.setEmployeeDateOfBirth(employeeDateOfBirth);
                    isValidDOB = false;
                } else {
                   logger.info("not valid");
                   employeeDateOfBirth = scanner.nextLine();
                }
            }
        }
    }

    public static void getDesignation(Trainer trainer,boolean  isValid) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainer Designation : ");
        String designation = scanner.nextLine();
        if (!designation.isEmpty()) {
            boolean isValidDesignation = true;
            while (isValidDesignation) {
                isValid = util.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",designation);
                if (isValid) {
                    trainer.setEmployeeDesignation(designation);
                    isValidDesignation = false;
                } else {
                   logger.info("not valid");
                   designation = scanner.nextLine();
                }
            }	    
        }  
    }

    public static void getMailId(Trainer trainer,boolean  isValid) {

        Scanner scanner = new Scanner(System.in);
        logger.info("Enter Trainer Mail : ");
        boolean isValidMail = true;
        while (isValidMail) {
            String mail = scanner.nextLine();
            if (!mail.isEmpty()) {
                try {
                    isValid = util.validationOfMail(mail);
                    trainer.setEmployeeMail(mail);
                    isValidMail = false;                 
                } catch (EmailMismatchException e) {
                    logger.error("Exception occured :" + e);
                }
            }	             
        }
    }
 
    public static void getMobileNumber(Trainer trainer,boolean  isValid) {

        Scanner scanner = new Scanner(System.in);     
	logger.info("Enter Trainer MobileNumber : ");
        String mobileNumber = scanner.nextLine();
        if (!mobileNumber.isEmpty()) {
            boolean isValidMobileNumber = true;
            while (isValidMobileNumber) {
                isValid = util.matchRegex("^(([6-9]{1}[0-9]{9})*)$",mobileNumber);
                if (isValid) {
                    trainer.setEmployeeMobileNumber(mobileNumber);
                    isValidMobileNumber = false;
                } else {
                   logger.info("not valid");
                   mobileNumber = scanner.nextLine();
                }
            }	    
        }
    }
        
    public static void getAddress(Trainer trainer,boolean  isValid) {

        Scanner scanner = new Scanner(System.in);
        logger.info("Enter Trainer CurrentAddress : ");
        String currentAddress = scanner.nextLine();
        if (!currentAddress.isEmpty()) {
            boolean isValidAddress = true;
            while (isValidAddress) {
                isValid = util.matchRegex("^(([0-9\\sa-zA-Z,.-]{3,150})*)$",currentAddress);
                if (isValid) {
                    trainer.setCurrentAddress(currentAddress);
                    isValidAddress = false;
                } else {
                   logger.info("not valid");
                   currentAddress = scanner.nextLine();
                }
            }	    
        }
    }

    public static void getAadharNumber(Trainer trainer,boolean  isValid) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainer AadharCardNumber : ");            
        String aadharNumber = scanner.nextLine();
        if (!aadharNumber.isEmpty()) {
            boolean isValidAadharCardNumber = true;
            while (isValidAadharCardNumber) {
                isValid = util.matchRegex("^(([1-9]{1}[0-9]{11})*)$",aadharNumber);
                if (isValid) {
                    trainer.setAadharCardNumber(aadharNumber);
                    isValidAadharCardNumber = false;
                } else {
                   logger.info("not valid");
                   aadharNumber = scanner.nextLine();
                }
            }	    
        }
    }

    public static void getPanNumber(Trainer trainer,boolean  isValid) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainer PanCardNumber : ");            
        String panNumber = scanner.nextLine();
        if (!panNumber.isEmpty()) {
            boolean isValidPanCardNumber = true;
            while (isValidPanCardNumber) {
                isValid = util.matchRegex("^(([A-Z0-9]{10})*)$",panNumber);
                if (isValid) {
                    trainer.setPanCardNumber(panNumber);
                    isValidPanCardNumber = false;
                } else {
                   logger.info("not valid");
                   panNumber = scanner.nextLine();
                }
            }	    
        }
    }

    public static void getCurrentProject(Trainer trainer,boolean  isValid) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter your currentProject : ");            
        String currentProject = scanner.nextLine();
        if (!currentProject.isEmpty()) {
            boolean isValidCurrentProject = true;
            while (isValidCurrentProject) {
                isValid = util.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",currentProject);
                if (isValid) {
                    trainer.setCurrentProject(currentProject);
                    isValidCurrentProject = false;
                } else {
                   logger.info("not valid");
                   currentProject = scanner.nextLine();
                }
            }	    
        }
    }

    public static void getAchievement(Trainer trainer,boolean  isValid) {

        Scanner scanner = new Scanner(System.in);	
	logger.info("Enter your achievements : ");            
        String achievement = scanner.nextLine();
        if (!achievement.isEmpty()) {
            boolean isValidAchievement = true;
            while (isValidAchievement) {
                isValid = util.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",achievement);
                if (isValid) {
                    trainer.setAchievement(achievement);
                    isValidAchievement = false;
                } else {
                   logger.info("not valid");
                   achievement = scanner.nextLine();
                }
            }	    
        }
    }
}