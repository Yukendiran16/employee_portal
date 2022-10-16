package com.ideas2it.controller;

import java.lang.ArrayIndexOutOfBoundsException;
import java.lang.NullPointerException; 
import java.lang.NumberFormatException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Set;
import java.util.Scanner;
import java.util.stream.Collectors;
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
 * <h2> TrainerController </h2>
 * <p>
 * The TrainerController class is an application that
 * do the main operation create, read, update, delete 
 * if one will be choosen the respect operation performs 
 * until user exit the operation is same for all operation 
 * </p>
 * @author  Yukendiran K
 * @version 1.0
 * @since   2022-08-04 
 *
 */
public class TrainerController {

    private final EmployeeService employeeService = new EmployeeServiceImpl();
    private final EmployeeUtil employeeUtil = new EmployeeUtil();
    private Logger logger = LoggerFactory.getLogger(TrainerController.class);

    /**
     *
     * <h1> Trainer Menu </h1>
     *
     * Method used to presents the main menu for trainer related operation
     * operations are......
     * ----> create user profile and store in database
     * ----> display the trainers in our database  
     * ----> update details or change smething in database
     * ----> delete the trainer in our database   
     * ----> create association between trainer and trainees 
     *
     *
     */  
    public void viewTrainerMenu()  {
       
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;

        while (isContinue) {

            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("\nEnter 1 for Create trainee details in database\n")
	                     .append("Enter 2 for display trainee details in database\n")
		             .append("Enter 3 for Update trainee details in database\n") 
	                     .append("Enter 4 for Delete trainee details in database\n")
                             .append("Enter 5 for assign trainer for trainees\n")
                             .append("Enter 6 for unassign trainer trainee\n")
                             .append("Enter 7 for Exit  \n\n\n")
                             .append("\n----------------------------------------------")
                             .append("\n***************** THANK YOU ******************")
                             .append("\n----------------------------------------------");
                logger.info("" + stringBuilder.substring(1,286));    
                String userOption = scanner.next();
                isContinue = Perform(userOption, isContinue);
                logger.info("" + stringBuilder.substring(288));

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
     * ----> display the trainers in our database  
     * ----> update details or change smething in database
     * ----> delete the trainer in our database   
     * ----> create association between trainer and trainees 
     *
     * @param {@link String} userOption for perform one operation 
     * @param {@link boolean} isContinue for continue operations 
     * @return {@link boolean} return boolean Unable to create requested service [org.hibernate.engine.jdbc.env.spi.JdbcEnvironment
     *
     */  
    public boolean Perform(String userOption, boolean isContinue) throws InputMismatchException, SQLException, HibernateException, NullPointerException {
 
        switch (userOption) {
        case "1":	   
            createTrainerData();                
            break;
        case "2":
            displayTrainerData();                
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
            unAssignAssociation();
            break;
        case "7":
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
     * Method used to create Trainer profile  
     *
     * @return {@link void} returns nothing
     */
    public void createTrainerData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {
        
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;
        String position = "";

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
     * Method used to select display choise of trainer details 
     *
     * @return {@link void} returns nothing
     *
     */
    public void displayTrainerData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {
        
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;

        while (isContinue) { 
	    logger.info("\n1.whole data\n2.particular data");
            String userOption = scanner.next();
            logger.info("\n----------------------------------------------"); 

            if (userOption.equals("1")) {
                displayWholeTrainerData();
            } else {
                displayParticularTrainerData();
            }        
            logger.info("\nif you want to continue\n1.yes\n2.no"); 
            userOption = scanner.next();          
            isContinue = (userOption.equals("1")); 
        }
    }

    /**
     *
     * <h1> displayWholeTrainerData </h1>
     *
     * Method used to display all trainer details 
     *
     * @return {@link void} returns nothing
     *
     */
    public void displayWholeTrainerData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {

        List<Trainer> trainers = employeeService.getTrainersData();

        if (trainers == null) {
            logger.info("\nNo data found");
        } else {
            trainers.forEach(trainer -> {
                String stringBuilder = "\nTrainer Detail : " +
                        "\nEmployee Id         : " + trainer.getTrainerId() +
                        "\nTrainer Name        : " + trainer.getEmployeeName() +
                        "\nTrainer Designation : " + trainer.getEmployeeDesignation() +
                        "\nTrainer Mail        : " + trainer.getEmployeeMail() +
                        "\nTrainer MobileNumber: " + trainer.getEmployeeMobileNumber() +
                        "\nCurrent Address     : " + trainer.getCurrentAddress();
            logger.info("----------------------------------------------" + stringBuilder);});                             
        }
    } 

    /**
     *
     * <h1> displayParticularTrainerData </h1>
     *
     * Method used to particular trainer details 
     *
     * @return {@link void} returns nothing
     *
     */ 
    public void displayParticularTrainerData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {

        Scanner scanner = new Scanner(System.in);
        logger.info("\nEnter TrainerId :");
        int trainerId = scanner.nextInt();
        Trainer trainer = employeeService.searchTrainerData(trainerId);

        if (trainer != null) {
            String stringBuilder = "\nTrainer Detail : " +
                    "\nTrainer Id          : " + trainer.getTrainerId() +
                    "\nTrainer Name        : " + trainer.getEmployeeName() +
                    "\nTrainer Designation : " + trainer.getEmployeeDesignation() +
                    "\nTrainer Mail        : " + trainer.getEmployeeMail() +
                    "\nTrainer MobileNumber: " + trainer.getEmployeeMobileNumber() +
                    "\nCurrent Address     : " + trainer.getCurrentAddress();
            logger.info("" + stringBuilder);
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append("\n\nAssigned Trainees........ :");
            trainer.getTrainees().forEach(trainee -> { 
                    stringBuilder1.append("\nTrainee Id          : ").append(trainee.getTraineeId())
                                  .append("\tTrainee Name        : ").append(trainee.getEmployeeName());});
            logger.info("" + stringBuilder1);  
            
        } else {
            logger.info("no data found");
        }
    }

    /**
     *
     * <h1> updateTrainerData </h1>
     *
     * Method used to update the changes for trainer details 
     *
     * @return {@link void} returns nothing
     *
     */
    public void updateTrainerData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {

        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;

        while (isContinue) {
            logger.info("\nEnter EmployeeId :");                
            int trainerId = scanner.nextInt();                                 
            Trainer trainer = employeeService.searchTrainerData(trainerId);
 
            if (trainer != null) {
                Trainer updateTrainer = getInformationForUpdateTrainer(trainer);
                String message = employeeService.updateTrainerData(trainerId, updateTrainer);
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
     * <h1> removeTrainerData </h1>
     *
     * Method used to remove trainer details  
     *
     * @return {@link void} returns nothing
     *
     */
    public void removeTrainerData() throws InputMismatchException, SQLException, HibernateException, NullPointerException {
        
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;

        while (isContinue) {
            logger.info("\nEnter EmployeeId :");                
            int trainerId = scanner.nextInt();                                 
            Trainer trainer = employeeService.searchTrainerData(trainerId);
 
            if (trainer != null) {
                String message = employeeService.deleteTrainerData(trainerId);
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
     * <h1> assignTrainerForTrainees </h1>
     *
     * Method used to assign trainer for multiple trainees
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
            logger.info("\nIf you want to continue : \n1.yes\n2.no" );
            String userOption = scanner.next();
            logger.info("\n----------------------------------------------");
            isContinue = (userOption.equals("1"));
        }
    } 

    /**
     *
     * <h1> getTraineesForAssignTrainer </h1>
     *
     * Method used to get trainees for assign trainer 
     *
     * @param {@link Trainer} trainer
     * @return {@link List<Trainees>} returns list of trainees 
     *
     */
    public Trainer getTraineesForAssignTrainer(Trainer trainer) throws InputMismatchException, SQLException, HibernateException, NullPointerException {

        Scanner scanner = new Scanner(System.in);
        List<Trainee> trainees = employeeService.getTraineesData();
        trainees.forEach(trainee -> {
            String stringBuilder = "\nTrainee Id          : " + trainee.getTraineeId() +
                    "\tTrainee Name        : " + trainee.getEmployeeName();
                    logger.info("" + stringBuilder);  });
            
        if (trainees == null) {
            logger.info("\nNo data found");
        } else {
            logger.info("Enter 0 for stop assigning.....\nEnter trainee Id :");
            int traineeId;

            do {  
                traineeId = scanner.nextInt();
                int temp =  traineeId;                

                if (traineeId != 0) {
                    Set<Trainee> trainee = trainees.stream().
                            filter(filteredTrainee -> filteredTrainee.getTraineeId() == temp).
                            collect(Collectors.toSet());
                    List<Trainee> traineeList = new ArrayList<>(trainee);

                    if (trainee.size() == 0) {
                        logger.info("couldn't found entered trainee");
                    } else {
                        trainer.getTrainees().add(traineeList.get(0));                        
                    }
                }
            } while (traineeId != 0); 
        }  
        return trainer;
    }

    /**
     *
     * <h1> unAssignAssociation </h1>
     *
     * Method used to get trainer and trainee for unassign 
     *
     * @return {@link void} returns list nothing. 
     *
     */
    public void unAssignAssociation() throws InputMismatchException, SQLException, HibernateException, NullPointerException {
        Scanner scanner = new Scanner(System.in);
        logger.info("Enter 0 for stop unassign \nEnter trainer id :");
        int trainerId = scanner.nextInt();
        logger.info("Enter 0 for stop unassign \nEnter trainee id :");
        int traineeId = scanner.nextInt();  
        Trainer trainer = employeeService.searchTrainerData(trainerId);

        if (0 != trainerId || 0 != traineeId) {

            if (null != trainer) {      
                Set<Trainee> trainees = trainer.getTrainees();
                List<Trainee> trainee = new ArrayList<>(trainees);
  
                for (int i = 0; i < trainees.size(); i++) {

                    if (trainee.get(i).getTraineeId() == traineeId) {
                        trainee.remove(i);
                        String message = employeeService.updateTrainerData(trainerId, trainer);
                        logger.info("{}" + message);
                    } 
                } 
                unAssignAssociation();           
            } else {
                logger.info("couldn't found entered trainer or trainee");
                unAssignAssociation();  
            }
        }
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
    public Trainer getInformationFromTrainer(String uuid) throws SQLException {

        Trainer trainer = new Trainer();
        trainer.setUuid(uuid); 
        validateTrainerName(trainer);
        validateTrainerDateofBirth(trainer);
        validateTrainerDesignation(trainer);
        validateTrainerMailId(trainer);
        validateTrainerMobileNumber(trainer);
        validateTrainerAddress(trainer);
        validateTrainerAadharNumber(trainer);
        validateTrainerPanNumber(trainer);
        validateTrainerCurrentProject(trainer);
        validateTrainerAchievement(trainer);
        return trainer;
    }

    /**
     *
     * <h1> validateInformationFofUpdateTrainer </h1>
     *
     * Method used to validate trainer details from user for update profile
     *
     * @param {@link Trainer} trainer
     * @return {@link Trainer} returns trainer
     *
     */ 
    public Trainer getInformationForUpdateTrainer(Trainer trainer) throws SQLException {
 
        validateName(trainer);
        validateDateofBirth(trainer);
        validateDesignation(trainer);
        validateMailId(trainer);
        validateMobileNumber(trainer);
        validateAddress(trainer);
        validateAadharNumber(trainer);
        validatePanNumber(trainer);
        validateCurrentProject(trainer);
        validateAchievement(trainer);
        return trainer;
    }

    /**
     *
     * <h1> validateTrainerName </h1>
     *
     * Method used to validate trainer name from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */ 
    public void validateTrainerName(Trainer trainer) {

        Scanner scanner = new Scanner(System.in); 
	logger.info("Enter Trainer Name : ");
        String name = scanner.nextLine();

        if (!name.isEmpty()) {

            if (employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",name)) {
                trainer.setEmployeeName(name);
            } else {
                logger.info("not valid");
                validateTrainerName(trainer);
            }       	    
        }
    }

    /**
     *
     * <h1> validateTrainerDateofBirth </h1>
     *
     * Method used to validate trainer date of birth from user
     *
     * @param {@link Trainer} trainer
     *
     */ 
    public void validateTrainerDateofBirth(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainer DateOfBirth YYYY-MM-DD:");
        String employeeDateOfBirth = scanner.nextLine();

        if (!employeeDateOfBirth.isEmpty()) {
            
            try {
                
                if (EmployeeUtil.validationOfDateOfBirth(employeeDateOfBirth)) {
                    trainer.setEmployeeDateOfBirth(employeeDateOfBirth);   
                } else {
                    logger.error("invalid date format");
                    validateTrainerDateofBirth(trainer);
                }      
            } catch (NumberFormatException e) {
                logger.error("invalid date format");
                validateTrainerDateofBirth(trainer);
            } catch ( ArrayIndexOutOfBoundsException e) {
                logger.error("invalid date format");
                validateTrainerDateofBirth(trainer);
            }
        }
    }

    /**
     *
     * <h1> validateTrainerDesignation </h1>
     *
     * Method used to validate trainer designation from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */ 
    public void validateTrainerDesignation(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainer Designation : ");
        String designation = scanner.nextLine();

        if (!designation.isEmpty()) {

            if (employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",designation)) {
                trainer.setEmployeeDesignation(designation);
            } else {
                logger.info("not valid");
                validateTrainerDesignation(trainer);
            }	    
        }  
    }

    /**
     *
     * <h1> validateTrainerMailId </h1>
     *
     * Method used to validate trainer mail Id from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */ 
    public void validateTrainerMailId(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);
        logger.info("Enter Trainer Mail : ");
        String mail = scanner.nextLine();

        if (!mail.isEmpty()) {
                
            try {
                EmployeeUtil.validationOfMail(mail);
                trainer.setEmployeeMail(mail);            
            } catch (EmailMismatchException e) {
                logger.error("Exception occured :" + e);
                validateTrainerMailId(trainer);
            }             
        }
    }

    /**
     *
     * <h1> validateTrainerMobileNumber </h1>
     *
     * Method used to validate trainer mobile number from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */  
    public void validateTrainerMobileNumber(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);     
	logger.info("Enter Trainer MobileNumber : ");
        String mobileNumber = scanner.nextLine();

        if (!mobileNumber.isEmpty()) {

            if (employeeUtil.matchRegex("^(([6-9]{1}[0-9]{9})*)$",mobileNumber)) {
                trainer.setEmployeeMobileNumber(mobileNumber);
            } else {
                logger.info("not valid");
                validateTrainerMobileNumber(trainer);
            }	    
        }
    }

    /**
     *
     * <h1> validateTrainerAddress </h1>
     *
     * Method used to validate trainer address from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */         
    public void validateTrainerAddress(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);
        logger.info("Enter Trainer CurrentAddress : ");
        String currentAddress = scanner.nextLine();

        if (!currentAddress.isEmpty()) {

            if (employeeUtil.matchRegex("^(([0-9\\sa-zA-Z,.-]{3,150})*)$",currentAddress)) {
                trainer.setCurrentAddress(currentAddress);
            } else {
                logger.info("not valid");
                validateTrainerAddress(trainer);
            }	    
        }
    }

    /**
     *
     * <h1> validateTrainerAadharNumber </h1>
     *
     * Method used to validate trainer aadhar number from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */ 
    public void validateTrainerAadharNumber(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainer AadharCardNumber : ");            
        String aadharNumber = scanner.nextLine();

        if (!aadharNumber.isEmpty()) {

            if (employeeUtil.matchRegex("^(([1-9]{1}[0-9]{11})*)$",aadharNumber)) {
                trainer.setAadharCardNumber(aadharNumber);
            } else {
                logger.info("not valid");
                validateTrainerAadharNumber(trainer);
            }	    
        }
    }

    /**
     *
     * <h1> validateTrainerPanNumber </h1>
     *
     * Method used to validate trainer pan number from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */ 
    public void validateTrainerPanNumber(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainer PanCardNumber : ");            
        String panNumber = scanner.nextLine();

        if (!panNumber.isEmpty()) {

            if (employeeUtil.matchRegex("^(([A-Z0-9]{10})*)$",panNumber)) {
                trainer.setPanCardNumber(panNumber);
            } else {
                logger.info("not valid");
                validateTrainerPanNumber(trainer);
            }	    
        }
    }

    /**
     *
     * <h1> validateTrainerCurrentProject </h1>
     *
     * Method used to validate trainer current project from user
     *
     * @param {@link Trainer} trainer

     * @return {@link void} returns nothing
     *
     */ 
    public void validateTrainerCurrentProject(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter your currentProject : ");            
        String currentProject = scanner.nextLine();

        if (!currentProject.isEmpty()) {

            if (employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",currentProject)) {
                trainer.setCurrentProject(currentProject);
            } else {
                logger.info("not valid");
                validateTrainerCurrentProject(trainer);
            }
        }
    }

    /**
     *
     * <h1> validateTrainerAchievement </h1>
     *
     * Method used to validate trainer achievement from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */ 
    public void validateTrainerAchievement(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);	
	logger.info("Enter your achievements : ");            
        String achievement = scanner.nextLine();

        if (!achievement.isEmpty()) {

            if (employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",achievement)) {
                trainer.setAchievement(achievement);
            } else {
                logger.info("not valid");
                validateTrainerAchievement(trainer);
            }
        }
    }

    /**
     *
     * <h1> validateName </h1>
     *
     * Method used to validate trainer name from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */ 
    public void validateName(Trainer trainer) {

        Scanner scanner = new Scanner(System.in); 
	logger.info("Enter Trainer Name : ");
        String name = scanner.nextLine();

        if (!name.isEmpty()) {

            if (employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",name)) {
                trainer.setEmployeeName(name);
            } else {
                logger.info("not valid");
                validateName(trainer);
            }           	    
        }
    }

    /**
     *
     * <h1> validateDateofBirth </h1>
     *
     * Method used to validate trainer date of birth from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */ 
    public void validateDateofBirth(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);	
        logger.info("Enter Trainer DateOfBirth YYYY-MM-DD:");
        String employeeDateOfBirth = scanner.nextLine();

        if (!employeeDateOfBirth.isEmpty()) {

            try {

                if (EmployeeUtil.validationOfDateOfBirth(employeeDateOfBirth)) {
                    trainer.setEmployeeDateOfBirth(employeeDateOfBirth);   
                } else {
                    logger.error("invalid date format");
                    validateTrainerDateofBirth(trainer);
                }   
            } catch (NumberFormatException e) {
                logger.error("invalid date format");
                validateTrainerDateofBirth(trainer);
            } catch ( ArrayIndexOutOfBoundsException e) {
                logger.error("invalid date format");
                validateTrainerDateofBirth(trainer);
            }
        }
    }

    /**
     *
     * <h1> validateDesignation </h1>
     *
     * Method used to validate trainer designation from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */ 
    public void validateDesignation(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainer Designation : ");
        String designation = scanner.nextLine();

        if (!designation.isEmpty()) {

            if (employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",designation)) {
                trainer.setEmployeeDesignation(designation);
            } else {
                logger.info("not valid");
                validateDesignation(trainer);
            }  
        }  
    }

    /**
     *
     * <h1> validateMailId </h1>
     *
     * Method used to validate trainer mail Id  from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */ 
    public void validateMailId(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);
        logger.info("Enter Trainer Mail : ");
        String mail = scanner.nextLine();

        if (!mail.isEmpty()) {
           
            try {
                EmployeeUtil.validationOfMail(mail);
                trainer.setEmployeeMail(mail);                 
            } catch (EmailMismatchException e) {
                logger.error("Exception occured :" + e);
                validateMailId(trainer);
            }             
        }
    }

    /**
     *
     * <h1> validateMobileNumber </h1>
     *
     * Method used to validate trainer mobile number from user
     *
     * @param {@link Trainer} trainer
     *
     */  
    public void validateMobileNumber(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);     
	logger.info("Enter Trainer MobileNumber : ");
        String mobileNumber = scanner.nextLine();

        if (!mobileNumber.isEmpty()) {

            if (employeeUtil.matchRegex("^(([6-9]{1}[0-9]{9})*)$",mobileNumber)) {
                trainer.setEmployeeMobileNumber(mobileNumber);
            } else {
                logger.info("not valid");
                validateMobileNumber(trainer);
            }	    
        }
    }

    /**
     *
     * <h1> validateAddress </h1>
     *
     * Method used to validate trainer address from user
     *
     * @param {@link Trainer} trainer
     *
     */         
    public void validateAddress(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);
        logger.info("Enter Trainer CurrentAddress : ");
        String currentAddress = scanner.nextLine();

        if (!currentAddress.isEmpty()) {

            if (employeeUtil.matchRegex("^(([0-9\\sa-zA-Z,.-]{3,150})*)$",currentAddress)) {
                trainer.setCurrentAddress(currentAddress);
            } else {
                logger.info("not valid");
                validateAddress(trainer);
            }	    
        }
    }

    /**
     *
     * <h1> validateAadharNumber </h1>
     *
     * Method used to validate trainer aadhar number from user
     *
     * @param {@link Trainer} trainer
     *
     */ 
    public void validateAadharNumber(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainer AadharCardNumber : ");            
        String aadharNumber = scanner.nextLine();

        if (!aadharNumber.isEmpty()) {

            if (employeeUtil.matchRegex("^(([1-9]{1}[0-9]{11})*)$",aadharNumber)) {
                trainer.setAadharCardNumber(aadharNumber);
            } else {
                logger.info("not valid");
                validateAadharNumber(trainer);
            }	    
        }
    }

    /**
     *
     * <h1> validatePanNumber </h1>
     *
     * Method used to validate trainer pan number from user
     *
     * @param {@link Trainer} trainer
     *
     */ 
    public void validatePanNumber(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainer PanCardNumber : ");            
        String panNumber = scanner.nextLine();

        if (!panNumber.isEmpty()) {

            if (employeeUtil.matchRegex("^(([A-Z0-9]{10})*)$",panNumber)) {
                trainer.setPanCardNumber(panNumber);
            } else {
                logger.info("not valid");
                validatePanNumber(trainer);
            }	    
        }
    }

    /**
     *
     * <h1> validateCurrentProject </h1>
     *
     * Method used to validate trainer current project name from user
     *
     * @param {@link Trainer} trainer
     *
     */ 
    public void validateCurrentProject(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter your currentProject : ");            
        String currentProject = scanner.nextLine();

        if (!currentProject.isEmpty()) {

            if (employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",currentProject)) {
                trainer.setCurrentProject(currentProject);
            } else {
                logger.info("not valid");
                validateCurrentProject(trainer);
            }	    
        }
    }

    /**
     *
     * <h1> validateAchievement </h1>
     *
     * Method used to validate trainer achievement from user
     *
     * @param {@link Trainer} trainer
     *
     */ 
    public void validateAchievement(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);	
	logger.info("Enter your achievement : ");            
        String achievement = scanner.nextLine();

        if (!achievement.isEmpty()) {

            if (employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",achievement)) {
                trainer.setAchievement(achievement);
            } else {
                logger.info("not valid");
                validateAchievement(trainer);
            }	    
        }
    }
}