package com.ideas2it.controller;

import java.util.Scanner;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ideas2it.model.Employee;
import com.ideas2it.model.Trainer;
import com.ideas2it.model.Trainee;
import com.ideas2it.service.impl.EmployeeServiceImpl;
import com.ideas2it.service.EmployeeService;
import com.ideas2it.util.EmployeeUtil;
import com.ideas2it.exception.EmailMismatchException;

import java.sql.SQLException;

/**
*
* <h2>EmployeInformation</h2>
*
* The EmployeeInformation class is an application that
* get employee details from user and set details to  
* respect employee position trainer or trainee
* and inputs is validated and object is return to controller.
*
* @author  Yukendiran K
* @version 1.0
* @since   2022-08-04 
*
*/


class EmployeeInformation {

    private static Logger logger = LoggerFactory.getLogger(EmployeeInformation.class);
    private static EmployeeService employeeService = new EmployeeServiceImpl();
    private static Scanner scanner = new Scanner(System.in);

    /**
     * method used to get trainer details from user for create profile
     * @param {@link Scanner} scanner 
     * @return {@link Trainer} returns trainer
     */ 
    public Trainer getInformationFromTrainer(String uuid) throws SQLException {

        
        EmployeeUtil util = new EmployeeUtil();
        Trainer trainer = new Trainer();
        boolean isValid = true;
       
        trainer.setUuid(uuid);

	logger.info("Enter Employee Name : ");
        String name = scanner.nextLine();
        if (!name.isEmpty() && !name.equals("exitprogram")) {
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

	logger.info("Enter Employee DateOfBirth MM/DD/YYYY:");
        String employeeDateOfBirth = scanner.nextLine();
        if (!employeeDateOfBirth.isEmpty() && !name.equals("exitprogram")) {
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

	logger.info("Enter Employee Designation : ");
        String designation = scanner.nextLine();
        if (!designation.isEmpty() && !name.equals("exitprogram")) {
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

        logger.info("Enter Employee Mail : ");
        boolean isValidMail = true;
        while (isValidMail) {
            String mail = scanner.nextLine();
            if (!mail.isEmpty() && !name.equals("exitprogram")) {
                try {
                    isValid = util.validationOfMail(mail);
                    trainer.setEmployeeMail(mail);
                    isValidMail = false;                 
                } catch (EmailMismatchException e) {
                    logger.error("Exception occured :" + e);
                }
            }	             
        } 
     
	logger.info("Enter Employee MobileNumber : ");
        String mobileNumber = scanner.nextLine();
        if (!mobileNumber.isEmpty() && !name.equals("exitprogram")) {
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
	
        logger.info("Enter Employee CurrentAddress : ");
        String currentAddress = scanner.nextLine();
        if (!currentAddress.isEmpty() && !name.equals("exitprogram")) {
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

	logger.info("Enter Employee AadharCardNumber : ");            
        String aadharNumber = scanner.nextLine();
        if (!aadharNumber.isEmpty() && !name.equals("exitprogram")) {
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

	logger.info("Enter Employee PanCardNumber : ");            
        String panNumber = scanner.nextLine();
        if (!panNumber.isEmpty() && !name.equals("exitprogram")) {
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

	logger.info("Enter your currentProject : ");            
        String currentProject = scanner.nextLine();
        if (!currentProject.isEmpty() && !name.equals("exitprogram")) {
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

	logger.info("Enter your achievements : ");            
        String achievement = scanner.nextLine();
        if (!achievement.isEmpty() && !name.equals("exitprogram")) {
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
        return trainer;
    }

    /**
     * method used to get trainee details from user for create profile
     * @param {@link Scanner} scanner
     * @return {@link Trainee} returns trainee
     */ 
    public Trainee getInformationFromTrainee(String uuid) throws SQLException {

        
        EmployeeUtil util = new EmployeeUtil();
        Trainee trainee = new Trainee();
        boolean isValid = true;

        trainee.setUuid(uuid);
     
	logger.info("Enter Employee Name : ");
        String name = scanner.nextLine();
        if (!name.isEmpty() && !name.equals("exitprogram")) {
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

	logger.info("Enter Employee DateOfBirth MM/DD/YYYY:");
        String employeeDateOfBirth = scanner.nextLine();
        if (!employeeDateOfBirth.isEmpty() && !name.equals("exitprogram")) {
            boolean isValidDOB = true;
            while (isValidDOB) {
                isValid = util.validationOfDateOfBirth(employeeDateOfBirth);
                if (isValid) {
                    trainee.setEmployeeDateOfBirth(employeeDateOfBirth);
                    isValidDOB = false;
                } else {
                   logger.info("not valid");
                   employeeDateOfBirth = scanner.nextLine();
                }
            }
        }
       
	logger.info("Enter Employee Designation : ");
        String designation = scanner.nextLine();
        if (!designation.isEmpty() && !name.equals("exitprogram")) {
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

        logger.info("Enter Employee Mail : ");
        boolean isValidMail = true;
        while (isValidMail) {
            String mail = scanner.nextLine();
            if (!mail.isEmpty() && !name.equals("exitprogram")) {
                try {
                    isValid = util.validationOfMail(mail);
                    trainee.setEmployeeMail(mail);
                    isValidMail = false;                 
                } catch (EmailMismatchException e) {
                    logger.error("Exception occured :" + e);
                }
            }	             
        } 
      
	logger.info("Enter Employee MobileNumber : ");
        String mobileNumber = scanner.nextLine();
        if (!mobileNumber.isEmpty() && !name.equals("exitprogram")) {
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

        logger.info("Enter Employee CurrentAddress : ");
        String currentAddress = scanner.nextLine();
        if (!currentAddress.isEmpty() && !name.equals("exitprogram")) {
            boolean isValidCurrentAddress = true;
            while (isValidCurrentAddress) {
                isValid = util.matchRegex("^(([0-9\\sa-zA-Z,.-]{3,150})*)$",currentAddress);
                if (isValid) {
                    trainee.setCurrentAddress(currentAddress);
                    isValidCurrentAddress = false;
                } else {
                   logger.info("not valid");
                   currentAddress = scanner.nextLine();
                }
            }	    
        }

	logger.info("Enter Employee AadharCardNumber : ");            
        String aadharNumber = scanner.nextLine();
        if (!aadharNumber.isEmpty() && !name.equals("exitprogram")) {
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

	logger.info("Enter Employee PanCardNumber : ");            
        String panNumber = scanner.nextLine();
        if (!panNumber.isEmpty() && !name.equals("exitprogram")) {
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

	logger.info("Enter your currentTask : ");            
        String currentTask = scanner.nextLine();
        if (!currentTask.isEmpty() && !name.equals("exitprogram")) {
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

	logger.info("Enter your currentTechknowledge : ");            
        String currentTechknowledge = scanner.nextLine();
        if (!currentTechknowledge.isEmpty() && !name.equals("exitprogram")) {
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
        return trainee; 
    }
    
    /**
     * method used to get trainer details from user for update
     * @param {@link Scanner} scanner
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     */ 
    public void getInformationForUpdateTrainer(Trainer trainer) {
    
        
        EmployeeUtil util = new EmployeeUtil();
        boolean isValid = true; 

	logger.info("Enter Employee Name : ");
        String name = scanner.nextLine();
        if (!name.isEmpty() && !name.equals("exitprogram")) {
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

	logger.info("Enter Employee DateOfBirth MM/DD/YYYY:");
        String employeeDateOfBirth = scanner.nextLine();
        if (!employeeDateOfBirth.isEmpty() && !name.equals("exitprogram")) {
            boolean isValidDOB = true;
            while (isValidDOB) {
                isValid = util.validationOfDateOfBirth(employeeDateOfBirth);
                if (isValid) {
                    trainer.setEmployeeDateOfBirth(employeeDateOfBirth);
                    isValidDOB = false;
                } else {
                   logger.info("not valid");
                   employeeDateOfBirth = scanner.nextLine();
                }
            }
        }

	logger.info("Enter Employee Designation : ");
        String designation = scanner.nextLine();
        if (!designation.isEmpty() && !name.equals("exitprogram")) {
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

        logger.info("Enter Employee Mail : ");
        boolean isValidMail = true;
        while (isValidMail) {
            String mail = scanner.nextLine();
            if (!mail.isEmpty() && !name.equals("exitprogram")) {
                try {
                    isValid = util.validationOfMail(mail);
                    trainer.setEmployeeMail(mail);
                    isValidMail = false;                 
                } catch (EmailMismatchException e) {
                    logger.error("Exception occured :" + e);
                }
            } else {
            	isValidMail = false;
            }             
        }  
      	
   	logger.info("Enter Employee MobileNumber : ");
        String mobileNumber = scanner.nextLine();
        if (!mobileNumber.isEmpty() && !name.equals("exitprogram")) {
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
	
        logger.info("Enter Employee CurrentAddress : ");
        String currentAddress = scanner.nextLine();
        if (!currentAddress.isEmpty() && !name.equals("exitprogram")) {
            boolean isValidCurrentAddress = true;
            while (isValidCurrentAddress) {
                isValid = util.matchRegex("^(([0-9\\sa-zA-Z,.-]{3,150})*)$",currentAddress);
                if (isValid) {
                    trainer.setCurrentAddress(currentAddress);
                    isValidCurrentAddress = false;
                } else {
                   logger.info("not valid");
                   currentAddress = scanner.nextLine();
                }
            }	    
        }

	logger.info("Enter Employee AadharCardNumber : ");            
        String aadharNumber = scanner.nextLine();
        if (!aadharNumber.isEmpty() && !name.equals("exitprogram")) {
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

	logger.info("Enter Employee PanCardNumber : ");            
        String panNumber = scanner.nextLine();
        if (!panNumber.isEmpty() && !name.equals("exitprogram")) {
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

	logger.info("Enter your currentProject : ");            
        String currentProject = scanner.nextLine();
        if (!currentProject.isEmpty() && !name.equals("exitprogram")) {
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

	logger.info("Enter your achievements : ");            
        String achievement = scanner.nextLine();
        if (!achievement.isEmpty() && !name.equals("exitprogram")) {
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

    /**
     * method used to get trainer details from user for update
     * @param {@link Scanner} scanner
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     */ 
    public void getInformationForUpdateTrainee(Trainee trainee) {

        
        EmployeeUtil util = new EmployeeUtil(); 
        boolean isValid = true; 
   
	logger.info("Enter Employee Name : ");
        String name = scanner.nextLine();
        if (!name.isEmpty() && !name.equals("exitprogram")) {
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

	logger.info("Enter Employee DateOfBirth MM/DD/YYYY:");
        String employeeDateOfBirth = scanner.nextLine();
        if (!employeeDateOfBirth.isEmpty() && !name.equals("exitprogram")) {
            boolean isValidDOB = true;
            while (isValidDOB) {
                isValid = util.validationOfDateOfBirth(employeeDateOfBirth);
                if (isValid) {
                    trainee.setEmployeeDateOfBirth(employeeDateOfBirth);
                    isValidDOB = false;
                } else {
                   logger.info("not valid");
                   employeeDateOfBirth = scanner.nextLine();
                }
            }
        }
       
	logger.info("Enter Employee Designation : ");
        String designation = scanner.nextLine();
        if (!designation.isEmpty() && !name.equals("exitprogram")) {
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

        logger.info("Enter Employee Mail : ");
        boolean isValidMail = true;
        while (isValidMail) {
            String mail = scanner.nextLine();
            if (!mail.isEmpty() && !name.equals("exitprogram")) {
                try {
                    isValid = util.validationOfMail(mail);
                    trainee.setEmployeeMail(mail);
                    isValidMail = false;                 
                } catch (EmailMismatchException e) {
                    logger.error("Exception occured :" + e);
                }
            } else {
            	isValidMail = false;
            }             
        }  
      
	logger.info("Enter Employee MobileNumber : ");
        String mobileNumber = scanner.nextLine();
        if (!mobileNumber.isEmpty() && !name.equals("exitprogram")) {
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

        logger.info("Enter Employee CurrentAddress : ");
        String currentAddress = scanner.nextLine();
        if (!currentAddress.isEmpty() && !name.equals("exitprogram")) {
            boolean isValidCurrentAddress = true;
            while (isValidCurrentAddress) {
                isValid = util.matchRegex("^(([0-9\\sa-zA-Z,.-]{3,150})*)$",currentAddress);
                if (isValid) {
                    trainee.setCurrentAddress(currentAddress);
                    isValidCurrentAddress = false;
                } else {
                   logger.info("not valid");
                   currentAddress = scanner.nextLine();
                }
            }
        }

	logger.info("Enter Employee AadharCardNumber : ");            
        String aadharNumber = scanner.nextLine();
        if (!aadharNumber.isEmpty() && !name.equals("exitprogram")) {
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

	logger.info("Enter Employee PanCardNumber : ");            
        String panNumber = scanner.nextLine();
        if (!panNumber.isEmpty() && !name.equals("exitprogram")) {
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

	logger.info("Enter your currentTask : ");            
        String currentTask = scanner.nextLine();
        if (!currentTask.isEmpty() && !name.equals("exitprogram")) {
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

	logger.info("Enter your currentTechknowledge : ");            
        String currentTechknowledge = scanner.nextLine();
        if (!currentTechknowledge.isEmpty() && !name.equals("exitprogram")) {
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