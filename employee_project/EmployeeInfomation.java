import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ideas2it.model.Employee;
import com.ideas2it.model.Trainer;
import com.ideas2it.model.Trainee;
import com.ideas2it.service.impl.EmployeeServiceImpl;
import com.ideas2it.service.EmployeeService;
import com.ideas2it.util.EmployeeUtil;

import com.ideas2it.exception.EmailMismatchException;

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

    /**
     * method used to get trainer details from user for create profile
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @param {@link boolean} isValid 
     * @return {@link Trainer} returns trainer
     */ 
    public Trainer getInformationFromTrainer(Scanner scanner, boolean isContinue, boolean isValid) {

        EmployeeUtil util = new EmployeeUtil();
        Trainer trainer = new Trainer();

     	String employeeId = util.createAndGetEmployeeId();
        trainer.setEmployeeId(employeeId);  

        scanner.nextLine();
	logger.info("Enter Employee Name : ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            while (isContinue) {
                isValid = util.matchRegex("^(([a-z\sA-Z_$]{3,50})*)$",name);
                if (isValid) {
                    name = name;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   name = scanner.nextLine();
                }
            }            
	    trainer.setEmployeeName(name);
        }

	logger.info("Enter Employee DateOfBirth MM/DD/YYYY:");
        String employeeDateOfBirth = scanner.nextLine();
        if (!employeeDateOfBirth.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.validationOfDateOfBirth(employeeDateOfBirth);
                if (isValid) {
                    employeeDateOfBirth = employeeDateOfBirth;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   employeeDateOfBirth = scanner.nextLine();
                }
            }
            trainer.setEmployeeDateOfBirth(employeeDateOfBirth);
        }

	logger.info("Enter Employee Designation : ");
        String designation = scanner.nextLine();
        if (!designation.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.matchRegex("^(([a-z\sA-Z_$]{3,50})*)$",designation);
                if (isValid) {
                    designation = designation;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   designation = scanner.nextLine();
                }
            }
	    trainer.setEmployeeDesignation(designation);
        }

        logger.info("Enter Employee Mail : ");
        isContinue = true;
        while (isContinue) {
            String mail = scanner.nextLine();
            try {
                isValid = util.validationOfMail(mail);
                trainer.setEmployeeMail(mail);
                logger.info("coming");
                isContinue = false;                 
            } catch (EmailMismatchException e) {
                logger.error("Exception occured :" + e);

            }	             
        } 
     
	logger.info("Enter Employee MobileNumber : ");
        String mobileNumber = scanner.nextLine();
        if (!mobileNumber.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.matchRegex("^(([6-9]{1}[0-9]{9})*)$",mobileNumber);
                if (isValid) {
                    mobileNumber = mobileNumber;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   mobileNumber = scanner.nextLine();
                }
            }
	    trainer.setEmployeeMobileNumber(mobileNumber);
        }
	
        logger.info("Enter Employee CurrentAddress : ");
        String currentAddress = scanner.nextLine();
        if (!currentAddress.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.matchRegex("^(([0-9\sa-zA-Z,.-]{3,150})*)$",currentAddress);
                if (isValid) {
                    currentAddress = currentAddress;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   currentAddress = scanner.nextLine();
                }
            }
	    trainer.setCurrentAddress(currentAddress);
        }

	logger.info("Enter Employee AadharCardNumber : ");            
        String aadharNumber = scanner.nextLine();
        if (!aadharNumber.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.matchRegex("^(([1-9]{1}[0-9]{11})*)$",aadharNumber);
                if (isValid) {
                    aadharNumber = aadharNumber;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   aadharNumber = scanner.nextLine();
                }
            }
	    trainer.setAadharCardNumber(aadharNumber);
        }

	logger.info("Enter Employee PanCardNumber : ");            
        String panNumber = scanner.nextLine();
        if (!panNumber.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.matchRegex("^(([A-Z0-9]{10})*)$",panNumber);
                if (isValid) {
                    panNumber = panNumber;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   panNumber = scanner.nextLine();
                }
            }
	    trainer.setPanCardNumber(panNumber);
        }

	logger.info("Enter your currentProject : ");            
        String currentProject = scanner.nextLine();
        if (!currentProject.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.matchRegex("^(([a-z\sA-Z_$]{3,50})*)$",currentProject);
                if (isValid) {
                    currentProject = currentProject;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   currentProject = scanner.nextLine();
                }
            }
	    trainer.setCurrentProject(currentProject);
        }

	logger.info("Enter your achievements : ");            
        String achievement = scanner.nextLine();
        if (!achievement.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.matchRegex("^(([a-z\sA-Z_$]{3,50})*)$",achievement);
                if (isValid) {
                    achievement = achievement;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   achievement = scanner.nextLine();
                }
            }
	    trainer.setAchievement(achievement);
        } 
        return trainer;
    }

    /**
     * method used to get trainee details from user for create profile
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @param {@link boolean} isValid 
     * @return {@link Trainee} returns trainee
     */ 
    public Trainee getInformationFromTrainee(Scanner scanner, boolean isContinue, boolean isValid) {

        EmployeeUtil util = new EmployeeUtil();
        Trainee trainee = new Trainee();

     	String employeeId = util.createAndGetEmployeeId();
        trainee.setEmployeeId(employeeId);

        scanner.nextLine();     
	logger.info("Enter Employee Name : ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.matchRegex("^(([a-z\sA-Z_$]{3,50})*)$",name);
                if (isValid) {
                    name = name;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   name = scanner.nextLine();
                }
            }
 	    trainee.setEmployeeName(name);
        }

	logger.info("Enter Employee DateOfBirth MM/DD/YYYY:");
        String employeeDateOfBirth = scanner.nextLine();
        if (!employeeDateOfBirth.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.validationOfDateOfBirth(employeeDateOfBirth);
                if (isValid) {
                    employeeDateOfBirth = employeeDateOfBirth;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   employeeDateOfBirth = scanner.nextLine();
                }
            }
            trainee.setEmployeeDateOfBirth(employeeDateOfBirth);
        }
       
	logger.info("Enter Employee Designation : ");
        String designation = scanner.nextLine();
        if (!designation.isEmpty()) {
            while (isContinue) {
                isValid = util.matchRegex("^(([a-z\sA-Z_$]{3,50})*)$",designation);
                if (isValid) {
                    designation = designation;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   designation = scanner.nextLine();
                }
            }
	    trainee.setEmployeeDesignation(designation);
        }

        logger.info("Enter Employee Mail : ");
        isContinue = true;
        while (isContinue) {
            String mail = scanner.nextLine();
            try {                
                isValid = util.validationOfMail(mail);
            } catch (EmailMismatchException e) {
                logger.error("Exception occured :" + e);
            }
            if (isValid) {
                trainee.setEmployeeMail(mail);       
                isContinue = false;
            } 	             
        }
      
	logger.info("Enter Employee MobileNumber : ");
        String mobileNumber = scanner.nextLine();
        if (!mobileNumber.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.matchRegex("^(([6-9]{1}[0-9]{9})*)$",mobileNumber);
                if (isValid) {
                    mobileNumber = mobileNumber;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   mobileNumber = scanner.nextLine();
                }
            }
	    trainee.setEmployeeMobileNumber(mobileNumber);
        }

        logger.info("Enter Employee CurrentAddress : ");
        String currentAddress = scanner.nextLine();
        if (!currentAddress.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.matchRegex("^(([0-9\sa-zA-Z,.-]{3,150})*)$",currentAddress);
                if (isValid) {
                    currentAddress = currentAddress;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   currentAddress = scanner.nextLine();
                }
            }
	    trainee.setCurrentAddress(currentAddress);
        }

	logger.info("Enter Employee AadharCardNumber : ");            
        String aadharNumber = scanner.nextLine();
        if (!aadharNumber.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.matchRegex("^(([1-9]{1}[0-9]{11})*)$",aadharNumber);
                if (isValid) {
                    aadharNumber = aadharNumber;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   aadharNumber = scanner.nextLine();
                }
            }
	    trainee.setAadharCardNumber(aadharNumber);
        }

	logger.info("Enter Employee PanCardNumber : ");            
        String panNumber = scanner.nextLine();
        if (!panNumber.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.matchRegex("^(([A-Z0-9]{10})*)$",panNumber);
                if (isValid) {
                    panNumber = panNumber;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   panNumber = scanner.nextLine();
                }
            }
	    trainee.setPanCardNumber(panNumber);
        }

	logger.info("Enter your currentTask : ");            
        String currentTask = scanner.nextLine();
        if (!currentTask.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.matchRegex("^(([a-z\sA-Z_$]{3,50})*)$",currentTask);
                if (isValid) {
                    currentTask = currentTask;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   currentTask = scanner.nextLine();
                }
            }
	    trainee.setCurrentTask(currentTask);
        }

	logger.info("Enter your currentTechknowledge : ");            
        String currentTechknowledge = scanner.nextLine();
        if (!currentTechknowledge.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.matchRegex("^(([a-z\sA-Z_$]{3,50})*)$",currentTechknowledge);
                if (isValid) {
                    currentTechknowledge = currentTechknowledge;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   currentTechknowledge = scanner.nextLine();
                }
            }
	    trainee.setCurrentTechknowledge(currentTechknowledge);
        }
        return trainee; 

    }
    
    /**
     * method used to get trainer details from user for update
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @param {@link boolean} isValid 
     * @param {@link Trainee} trainee
     * @return {@link void} returns nothing
     */ 
    public void getInformationForUpdateTrainer(Scanner scanner, boolean isContinue, boolean isValid, Trainer trainer) {
    
        EmployeeUtil util = new EmployeeUtil();  

        scanner.nextLine();
	logger.info("Enter Employee Name : ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            while (isContinue) {
                isValid = util.matchRegex("^(([a-z\sA-Z_$]{3,50})*)$",name);
                if (isValid) {
                    name = name;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   name = scanner.nextLine();
                }
            }            
	    trainer.setEmployeeName(name);
        }

	logger.info("Enter Employee DateOfBirth MM/DD/YYYY:");
        String employeeDateOfBirth = scanner.nextLine();
        if (!employeeDateOfBirth.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.validationOfDateOfBirth(employeeDateOfBirth);
                if (isValid) {
                    employeeDateOfBirth = employeeDateOfBirth;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   employeeDateOfBirth = scanner.nextLine();
                }
            }
            trainer.setEmployeeDateOfBirth(employeeDateOfBirth);
        }

	logger.info("Enter Employee Designation : ");
        String designation = scanner.nextLine();
        if (!designation.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.matchRegex("^(([a-z\sA-Z_$]{3,50})*)$",designation);
                if (isValid) {
                    designation = designation;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   designation = scanner.nextLine();
                }
            }
	    trainer.setEmployeeDesignation(designation);
        }

        logger.info("Enter Employee Mail : ");        
        isContinue = true;
        while (isContinue) {
            String mail = scanner.nextLine();
            if (!mail.isEmpty()) {
                try {
                    isValid = util.validationOfMail(mail);
                } catch (EmailMismatchException e) {
                    logger.error("Exception occured :" + e);
                }
                if (isValid) {
                    trainer.setEmployeeMail(mail);
                    isContinue = false;
                }
            }
        }
      	
       
     
	logger.info("Enter Employee MobileNumber : ");
        String mobileNumber = scanner.nextLine();
        if (!mobileNumber.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.matchRegex("^(([6-9]{1}[0-9]{9})*)$",mobileNumber);
                if (isValid) {
                    mobileNumber = mobileNumber;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   mobileNumber = scanner.nextLine();
                }
            }
	    trainer.setEmployeeMobileNumber(mobileNumber);
        }
	
        logger.info("Enter Employee CurrentAddress : ");
        String currentAddress = scanner.nextLine();
        if (!currentAddress.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.matchRegex("^(([0-9\sa-zA-Z,.-]{3,150})*)$",currentAddress);
                if (isValid) {
                    currentAddress = currentAddress;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   currentAddress = scanner.nextLine();
                }
            }
	    trainer.setCurrentAddress(currentAddress);
        }

	logger.info("Enter Employee AadharCardNumber : ");            
        String aadharNumber = scanner.nextLine();
        if (!aadharNumber.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.matchRegex("^(([1-9]{1}[0-9]{11})*)$",aadharNumber);
                if (isValid) {
                    aadharNumber = aadharNumber;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   aadharNumber = scanner.nextLine();
                }
            }
	    trainer.setAadharCardNumber(aadharNumber);
        }

	logger.info("Enter Employee PanCardNumber : ");            
        String panNumber = scanner.nextLine();
        if (!panNumber.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.matchRegex("^(([A-Z0-9]{10})*)$",panNumber);
                if (isValid) {
                    panNumber = panNumber;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   panNumber = scanner.nextLine();
                }
            }
	    trainer.setPanCardNumber(panNumber);
        }

	logger.info("Enter your currentProject : ");            
        String currentProject = scanner.nextLine();
        if (!currentProject.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.matchRegex("^(([a-z\sA-Z_$]{3,50})*)$",currentProject);
                if (isValid) {
                    currentProject = currentProject;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   currentProject = scanner.nextLine();
                }
            }
	    trainer.setCurrentProject(currentProject);
        }

	logger.info("Enter your achievements : ");            
        String achievement = scanner.nextLine();
        if (!achievement.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.matchRegex("^(([a-z\sA-Z_$]{3,50})*)$",achievement);
                if (isValid) {
                    achievement = achievement;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   achievement = scanner.nextLine();
                }
            }
	    trainer.setAchievement(achievement);
        } 
    }

    /**
     * method used to get trainer details from user for update
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @param {@link boolean} isValid
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     */ 
    public void getInformationForUpdateTrainee(Scanner scanner, boolean isContinue, boolean isValid,Trainee trainee) {

        EmployeeUtil util = new EmployeeUtil();  

        scanner.nextLine();     
	logger.info("Enter Employee Name : ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.matchRegex("^(([a-z\sA-Z_$]{3,50})*)$",name);
                if (isValid) {
                    name = name;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   name = scanner.nextLine();
                }
            }
 	    trainee.setEmployeeName(name);
        }

	logger.info("Enter Employee DateOfBirth MM/DD/YYYY:");
        String employeeDateOfBirth = scanner.nextLine();
        if (!employeeDateOfBirth.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.validationOfDateOfBirth(employeeDateOfBirth);
                if (isValid) {
                    employeeDateOfBirth = employeeDateOfBirth;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   employeeDateOfBirth = scanner.nextLine();
                }
            }
            trainee.setEmployeeDateOfBirth(employeeDateOfBirth);
        }
       
	logger.info("Enter Employee Designation : ");
        String designation = scanner.nextLine();
        if (!designation.isEmpty()) {
            while (isContinue) {
                isValid = util.matchRegex("^(([a-z\sA-Z_$]{3,50})*)$",designation);
                if (isValid) {
                    designation = designation;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   designation = scanner.nextLine();
                }
            }
	    trainee.setEmployeeDesignation(designation);
        }

        logger.info("Enter Employee Mail : ");        
        isContinue = true;
        while (isContinue) {
            String mail = scanner.nextLine();
            if (!mail.isEmpty()) {
                try {
                    isValid = util.validationOfMail(mail);
                } catch (EmailMismatchException e) {
                    logger.error("Exception occured :" + e);
                }
                if (isValid) {
                    trainee.setEmployeeMail(mail);
                    isContinue = false;
                }
            }
        }
      
	logger.info("Enter Employee MobileNumber : ");
        String mobileNumber = scanner.nextLine();
        if (!mobileNumber.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.matchRegex("^(([6-9]{1}[0-9]{9})*)$",mobileNumber);
                if (isValid) {
                    mobileNumber = mobileNumber;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   mobileNumber = scanner.nextLine();
                }
            }
	    trainee.setEmployeeMobileNumber(mobileNumber);
        }

        logger.info("Enter Employee CurrentAddress : ");
        String currentAddress = scanner.nextLine();
        if (!currentAddress.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.matchRegex("^(([0-9\sa-zA-Z,.-]{3,150})*)$",currentAddress);
                if (isValid) {
                    currentAddress = currentAddress;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   currentAddress = scanner.nextLine();
                }
            }
	    trainee.setCurrentAddress(currentAddress);
        }

	logger.info("Enter Employee AadharCardNumber : ");            
        String aadharNumber = scanner.nextLine();
        if (!aadharNumber.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.matchRegex("^(([1-9]{1}[0-9]{11})*)$",aadharNumber);
                if (isValid) {
                    aadharNumber = aadharNumber;
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   aadharNumber = scanner.nextLine();
                }
            }
	    trainee.setAadharCardNumber(aadharNumber);
        }

	logger.info("Enter Employee PanCardNumber : ");            
        String panNumber = scanner.nextLine();
        if (!panNumber.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.matchRegex("^(([A-Z0-9]{10})*)$",panNumber);
                if (isValid) {
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   panNumber = scanner.nextLine();
                }
            }
	    trainee.setPanCardNumber(panNumber);
        }

	logger.info("Enter your currentTask : ");            
        String currentTask = scanner.nextLine();
        if (!currentTask.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.matchRegex("^(([a-z\sA-Z_$]{3,50})*)$",currentTask);
                if (isValid) {
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   currentTask = scanner.nextLine();
                }
            }
	    trainee.setCurrentTask(currentTask);
        }

	logger.info("Enter your currentTechknowledge : ");            
        String currentTechknowledge = scanner.nextLine();
        if (!currentTechknowledge.isEmpty()) {
            isContinue = true;
            while (isContinue) {
                isValid = util.matchRegex("^(([a-z\sA-Z_$]{3,50})*)$",currentTechknowledge);
                if (isValid) {
                    isContinue = false;
                } else {
                   logger.info("not valid");
                   currentTechknowledge = scanner.nextLine();
                }
            }
	    trainee.setCurrentTechknowledge(currentTechknowledge);
        } 
    }
}