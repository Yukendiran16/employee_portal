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

public class TrainerController {

    private EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
    private EmployeeUtil util = new EmployeeUtil();
    private Logger logger = LoggerFactory.getLogger(TrainerController.class);

    /**
     *
     * <h1> Trainer Menu </h1>
     *
     * method used to presents the main menu for trainer related operation
     * 
     * operations are......
     * ----> create user profile and store in database
     * ----> display the trainers in our database  
     * ----> update details or change smething in database
     * ----> delete the trainer in our database   
     * ----> create association between trainer and trainees 
     * 
     * @return {@link void} returns nothing
     *
     */  
    public void viewTrainerMenu()  {
       
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;

        while (isContinue) {

            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("\nEnter 1 for Create trainee details in database\n");
	        stringBuilder.append("Enter 2 for Read   trainee details in database\n"); 
		stringBuilder.append("Enter 3 for Update trainee details in database\n"); 
	        stringBuilder.append("Enter 4 for Delete trainee details in database\n");
                stringBuilder.append("Enter 5 for assign trainer for trainees\n");
                stringBuilder.append("Enter 6 for Exit  \n\n\n");
                stringBuilder.append("----------------------------------------------");
                stringBuilder.append("\n----------------------------------------------");
                stringBuilder.append("\n***************** THANK YOU ******************");
                stringBuilder.append("\n----------------------------------------------");
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
     * method used to select the which operation will be happens 
     * 
     * operations are......
     * ----> create user profile and store in database
     * ----> display the trainers in our database  
     * ----> update details or change smething in database
     * ----> delete the trainer in our database   
     * ----> create association between trainer and trainees 
     *
     * @param {@link String} userOption for perform one operation 
     * @param {@link boolean} isContinue for continue operations 
     * @return {@link boolean} return boolean 
     *
     */  
    public boolean Perform(String userOption, boolean isContinue) throws InputMismatchException, SQLException, HibernateException, NullPointerException {
 
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
     *
     * <h1> createTrainerProfile </h1>
     *
     * method used to create Trainer profile  
     *
     * @return {@link void} returns nothing
     */
    public void createTrainerData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {
        
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;
        String position = "";

        while (isContinue) {
            getAndSetData();
            logger.info(" \nIf you want to continue\n" + "1.yes\n" + "2.no");
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
       	 logger.info("\nEnter Trainer Details :");
         Trainer trainer = getInformationFromTrainer(uuid);
         String message = employeeService.addTrainer(trainer);                     
         logger.info("" + message);
         logger.info("\n----------------------------------------------");
    }

    /**
     *
     * <h1> displayTrainerData </h1>
     *
     * method used to select display choise of trainer details 
     *
     * @return {@link void} returns nothing
     *
     */
    public void readTrainerData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {
        
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;

        while (isContinue) { 
	    logger.info("\n1.whole data\n" + "2.particular data");
            String userOption = scanner.next();
            logger.info("\n----------------------------------------------"); 

            if (userOption.equals("1")) {
                readWholeTrainerData();
            } else {
                readParticularTrainerData();
            }        
            logger.info("\nif you want to continue\n" + "1.yes\n" + "2.no"); 
            userOption = scanner.next();          
            isContinue = !(userOption.equals("2")); 
        }
    }

    /**
     *
     * <h1> displayWholeTrainerData </h1>
     *
     * method used to display all trainer details 
     *
     * @return {@link void} returns nothing
     *
     */
    public void readWholeTrainerData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {

        List<Trainer> trainers = employeeService.getTrainersData();

        if (trainers == null) {
            logger.info("\nNo data found");
        } else {
            trainers.forEach(trainer -> { StringBuilder stringBuilder = new StringBuilder();
                                        stringBuilder.append("Trainer Detail : "); 
                                        stringBuilder.append("Employee Id         : "+trainer.getTrainerId());
                                        stringBuilder.append("Trainer Name        : "+trainer.getEmployeeName());
                                        stringBuilder.append("Trainer Designation : "+trainer.getEmployeeDesignation());
                                        stringBuilder.append("Trainer Mail        : "+trainer.getEmployeeMail());
                                       	stringBuilder.append("Trainer MobileNumber: "+trainer.getEmployeeMobileNumber());
                                        stringBuilder.append("Current Address     : "+trainer.getCurrentAddress());
                                        logger.info("----------------------------------------------" + stringBuilder);});                             
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
    public void readParticularTrainerData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {

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
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Trainer Detail : ");
                stringBuilder.append("Trainer Id          : " + trainer.getTrainerId());
                stringBuilder.append("Trainer Name        : " + trainer.getEmployeeName());
                stringBuilder.append("Trainer Designation : " + trainer.getEmployeeDesignation());
                stringBuilder.append("Trainer Mail        : " + trainer.getEmployeeMail());
                stringBuilder.append("Trainer MobileNumber: " + trainer.getEmployeeMobileNumber());
                stringBuilder.append("Current Address     : " + trainer.getCurrentAddress());
                logger.info("" + stringBuilder);
                trainer.getTrainees().forEach(trainee -> { StringBuilder stringBuilder1 = new StringBuilder();
                                                           stringBuilder1.append("Assigned Trainees :");
                                                           stringBuilder1.append("Trainee Id          : " + trainee.getTraineeId());
                                                           stringBuilder1.append("Trainee Name        : " + trainee.getEmployeeName());
                                                           logger.info("-----------------------------------------------" + stringBuilder1);});
                isValidId = false;
            }
        }
    }

    /**
     *
     * <h1> updateTrainerData </h1>
     *
     * method used to update the changes for trainer details 
     *
     * @return {@link void} returns nothing
     *
     */
    public void updateTrainerData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {

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
            String userOption = scanner.next();
            isContinue = !(userOption.equals("2"));
        }
    }

    /**
     *
     * <h1> removeTrainerData </h1>
     *
     * method used to remove trainer details  
     *
     * @return {@link void} returns nothing
     *
     */
    public void removeTrainerData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {
        
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
            String userOption = scanner.next();
            logger.info("\n----------------------------------------------");
            isContinue = !(userOption.equals("2")); 
        }
    }

    /**
     *
     * <h1> assignTrainerForTrainees </h1>
     *
     * method used to assign trainer for multiple trainees 
     *
     * @return {@link void} returns nothing
     *
     */
    public void assignTrainerForTrainees() throws InputMismatchException, SQLException, HibernateException, NullPointerException {

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
            String userOption = scanner.next();
            logger.info("\n----------------------------------------------");
            isContinue = !(userOption.equals("2"));
        }
    }

    /**
     *
     * <h1> getTraineesForAssignTrainer </h1>
     *
     * method used to get trainees for assign trainer 
     *
     * @param {@link Trainer} trainer
     * @return {@link List<Trainees>} returns list of trainees 
     *
     */
    public Trainer getTraineesForAssignTrainer(Trainer trainer) throws InputMismatchException, SQLException, HibernateException, NullPointerException {

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
                        }
                    }
                }
            } while (traineeId != 0); 
        }  
        return trainer;
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
    public Trainer getInformationFromTrainer(String uuid) throws SQLException {

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
     *
     * <h1> getInformationFofUpdateTrainer </h1>
     *
     * method used to get trainer details from user for update profile
     *
     * @param {@link Trainer} trainer
     * @return {@link Trainer} returns trainer
     *
     */ 
    public Trainer getInformationForUpdateTrainer(Trainer trainer) throws SQLException {
 
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

    /**
     *
     * <h1> getTrainerName </h1>
     *
     * method used to get trainer name from user
     *
     * @param {@link Trainer} trainer
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */ 
    public void getTrainerName(Trainer trainer,boolean  isValid) {

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

    /**
     *
     * <h1> getTrainerDateofBirth </h1>
     *
     * method used to get trainer date of birth from user
     *
     * @param {@link Trainer} trainer
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */ 
    public void getTrainerDateofBirth(Trainer trainer,boolean  isValid) {

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

    /**
     *
     * <h1> getTrainerDesignation </h1>
     *
     * method used to get trainer designation from user
     *
     * @param {@link Trainer} trainer
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */ 
    public void getTrainerDesignation(Trainer trainer,boolean  isValid) {

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

    /**
     *
     * <h1> getTrainerMailId </h1>
     *
     * method used to get trainer mail Id from user
     *
     * @param {@link Trainer} trainer
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */ 
    public void getTrainerMailId(Trainer trainer,boolean  isValid) {

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

    /**
     *
     * <h1> getTrainerMobileNumber </h1>
     *
     * method used to get trainer mobile number from user
     *
     * @param {@link Trainer} trainer
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */  
    public void getTrainerMobileNumber(Trainer trainer,boolean  isValid) {

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

    /**
     *
     * <h1> getTrainerAddress </h1>
     *
     * method used to get trainer address from user
     *
     * @param {@link Trainer} trainer
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */         
    public void getTrainerAddress(Trainer trainer,boolean  isValid) {

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

    /**
     *
     * <h1> getTrainerAadharNumber </h1>
     *
     * method used to get trainer aadhar number from user
     *
     * @param {@link Trainer} trainer
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */ 
    public void getTrainerAadharNumber(Trainer trainer,boolean  isValid) {

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

    /**
     *
     * <h1> getTrainerPanNumber </h1>
     *
     * method used to get trainer pan number from user
     *
     * @param {@link Trainer} trainer
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */ 
    public void getTrainerPanNumber(Trainer trainer,boolean  isValid) {

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

    /**
     *
     * <h1> getTrainerCurrentProject </h1>
     *
     * method used to get trainer current project from user
     *
     * @param {@link Trainer} trainer
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */ 
    public void getTrainerCurrentProject(Trainer trainer,boolean  isValid) {

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

    /**
     *
     * <h1> getTrainerAchievement </h1>
     *
     * method used to get trainer achievement from user
     *
     * @param {@link Trainer} trainer
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */ 
    public void getTrainerAchievement(Trainer trainer,boolean  isValid) {

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

    /**
     *
     * <h1> getName </h1>
     *
     * method used to get trainer name from user
     *
     * @param {@link Trainer} trainer
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */ 
    public void getName(Trainer trainer,boolean  isValid) {

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

    /**
     *
     * <h1> getDateofBirth </h1>
     *
     * method used to get trainer date of birth from user
     *
     * @param {@link Trainer} trainer
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */ 
    public void getDateofBirth(Trainer trainer,boolean  isValid) {


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

    /**
     *
     * <h1> getDesignation </h1>
     *
     * method used to get trainer designation from user
     *
     * @param {@link Trainer} trainer
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */ 
    public void getDesignation(Trainer trainer,boolean  isValid) {

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

    /**
     *
     * <h1> getMailId </h1>
     *
     * method used to get trainer mail Id  from user
     *
     * @param {@link Trainer} trainer
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */ 
    public void getMailId(Trainer trainer,boolean  isValid) {

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

    /**
     *
     * <h1> getMobileNumber </h1>
     *
     * method used to get trainer mobile number from user
     *
     * @param {@link Trainer} trainer
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */  
    public void getMobileNumber(Trainer trainer,boolean  isValid) {

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

    /**
     *
     * <h1> getAddress </h1>
     *
     * method used to get trainer address from user
     *
     * @param {@link Trainer} trainer
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */         
    public void getAddress(Trainer trainer,boolean  isValid) {

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

    /**
     *
     * <h1> getAadharNumber </h1>
     *
     * method used to get trainer aadhar number from user
     *
     * @param {@link Trainer} trainer
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */ 
    public void getAadharNumber(Trainer trainer,boolean  isValid) {

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

    /**
     *
     * <h1> getPanNumber </h1>
     *
     * method used to get trainer pan number from user
     *
     * @param {@link Trainer} trainer
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */ 
    public void getPanNumber(Trainer trainer,boolean  isValid) {

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

    /**
     *
     * <h1> getCurrentProject </h1>
     *
     * method used to get trainer current project name from user
     *
     * @param {@link Trainer} trainer
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */ 
    public void getCurrentProject(Trainer trainer,boolean  isValid) {

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

    /**
     *
     * <h1> getAchievement </h1>
     *
     * method used to get trainer achievement from user
     *
     * @param {@link Trainer} trainer
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     *
     */ 
    public void getAchievement(Trainer trainer,boolean  isValid) {

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