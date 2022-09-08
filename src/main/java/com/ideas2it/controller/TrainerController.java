package com.ideas2it.controller;

import java.lang.ArrayIndexOutOfBoundsException;
import java.lang.NullPointerException; 
import java.lang.NumberFormatException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
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

    private EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
    private EmployeeUtil employeeUtil = new EmployeeUtil();
    private Logger logger = LoggerFactory.getLogger(TrainerController.class);

    /**
     *
     * <h1> Trainer Menu </h1>
     *
     * Method used to presents the main menu for trainer related operation
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
     * @return {@link boolean} return boolean 
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
	    logger.info("\n1.whole data\n" + "2.particular data");
            String userOption = scanner.next();
            logger.info("\n----------------------------------------------"); 

            if (userOption.equals("1")) {
                displayWholeTrainerData();
            } else {
                displayParticularTrainerData();
            }        
            logger.info("\nif you want to continue\n" + "1.yes\n" + "2.no"); 
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
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("\nTrainer Detail : ")
                                 .append("\nEmployee Id         : ").append(trainer.getTrainerId())
                                 .append("\nTrainer Name        : ").append(trainer.getEmployeeName())
                                 .append("\nTrainer Designation : ").append(trainer.getEmployeeDesignation())
                                 .append("\nTrainer Mail        : ").append(trainer.getEmployeeMail())
                                 .append("\nTrainer MobileNumber: ").append(trainer.getEmployeeMobileNumber())
                                 .append("\nCurrent Address     : ").append(trainer.getCurrentAddress());
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
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\nTrainer Detail : ")
                         .append("\nTrainer Id          : ").append(trainer.getTrainerId())
                         .append("\nTrainer Name        : ").append(trainer.getEmployeeName())
                         .append("\nTrainer Designation : ").append(trainer.getEmployeeDesignation())
                         .append("\nTrainer Mail        : ").append(trainer.getEmployeeMail())
                         .append("\nTrainer MobileNumber: ").append(trainer.getEmployeeMobileNumber())
                         .append("\nCurrent Address     : ").append(trainer.getCurrentAddress())
                         .append("\n\nAssigned Trainees........ :");
            logger.info("" + stringBuilder);
            trainer.getTrainees().forEach(trainee -> { 
                    StringBuilder stringBuilder1 = new StringBuilder();
                    stringBuilder1.append("\nTrainee Id          : ").append(trainee.getTraineeId())
                                  .append("\tTrainee Name        : ").append(trainee.getEmployeeName());
                    logger.info("" + stringBuilder1);  });
            
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
            logger.info("\nif you want to continue\n" + "1.yes\n" + "2.no"); 
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
            logger.info("\nif you want to continue\n" + "1.yes\n" + "2.no"); 
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
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("\nTrainee Id          : ").append(trainee.getTraineeId())
                                 .append("\tTrainee Name        : ").append(trainee.getEmployeeName());
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
                    List<Trainee> trainee = trainees.stream().
                            filter(filteredTrainee -> filteredTrainee.getTraineeId() == temp).
                            collect(Collectors.toList());
                    if (trainee.size() == 0) {
                        logger.info("couldn't found entered trainee");
                    } else {
                        trainer.getTrainees().add(trainee.get(0));                        
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
                List<Trainee> trainees = trainer.getTrainees();
  
                for (int i = 0; i < trainees.size(); i++) {

                    if (trainer.getTrainees().get(i).getTraineeId() == traineeId) {
                        trainees.remove(i);
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
        getTrainerName(trainer);
        getTrainerDateofBirth(trainer);
        getTrainerDesignation(trainer);
        getTrainerMailId(trainer);
        getTrainerMobileNumber(trainer);
        getTrainerAddress(trainer);
        getTrainerAadharNumber(trainer);
        getTrainerPanNumber(trainer);
        getTrainerCurrentProject(trainer);
        getTrainerAchievement(trainer);
        return trainer;
    }

    /**
     *
     * <h1> getInformationFofUpdateTrainer </h1>
     *
     * Method used to get trainer details from user for update profile
     *
     * @param {@link Trainer} trainer
     * @return {@link Trainer} returns trainer
     *
     */ 
    public Trainer getInformationForUpdateTrainer(Trainer trainer) throws SQLException {
 
        getName(trainer);
        getDateofBirth(trainer);
        getDesignation(trainer);
        getMailId(trainer);
        getMobileNumber(trainer);
        getAddress(trainer);
        getAadharNumber(trainer);
        getPanNumber(trainer);
        getCurrentProject(trainer);
        getAchievement(trainer);
        return trainer;
    }

    /**
     *
     * <h1> getTrainerName </h1>
     *
     * Method used to get trainer name from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */ 
    public void getTrainerName(Trainer trainer) {

        Scanner scanner = new Scanner(System.in); 
	logger.info("Enter Trainer Name : ");
        String name = scanner.nextLine();

        if (!name.isEmpty()) {
            boolean isValid = employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",name);

            if (isValid) {
                trainer.setEmployeeName(name);
            } else {
                logger.info("not valid");
                getTrainerName(trainer);
            }       	    
        }
    }

    /**
     *
     * <h1> getTrainerDateofBirth </h1>
     *
     * Method used to get trainer date of birth from user
     *
     * @param {@link Trainer} trainer

     * @return {@link void} returns nothing
     *
     */ 
    public void getTrainerDateofBirth(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainer DateOfBirth YYYY-MM-DD:");
        String employeeDateOfBirth = scanner.nextLine();

        if (!employeeDateOfBirth.isEmpty()) {
            
            try {
                boolean bool = employeeUtil.validationOfDateOfBirth(employeeDateOfBirth);
                if (bool == true) {
                    trainer.setEmployeeDateOfBirth(employeeDateOfBirth);   
                } else {
                    logger.error("invalid date format");
                    getTrainerDateofBirth(trainer);
                }      
            } catch (NumberFormatException e) {
                logger.error("invalid date format");
                getTrainerDateofBirth(trainer);
            } catch ( ArrayIndexOutOfBoundsException e) {
                logger.error("invalid date format");
                getTrainerDateofBirth(trainer);
            }
        }
    }

    /**
     *
     * <h1> getTrainerDesignation </h1>
     *
     * Method used to get trainer designation from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */ 
    public void getTrainerDesignation(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainer Designation : ");
        String designation = scanner.nextLine();

        if (!designation.isEmpty()) {
            boolean isValid = employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",designation);

            if (isValid) {
                trainer.setEmployeeDesignation(designation);
            } else {
                logger.info("not valid");
                getTrainerDesignation(trainer);
            }	    
        }  
    }

    /**
     *
     * <h1> getTrainerMailId </h1>
     *
     * Method used to get trainer mail Id from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */ 
    public void getTrainerMailId(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);
        logger.info("Enter Trainer Mail : ");
        String mail = scanner.nextLine();

        if (!mail.isEmpty()) {
                
            try {
                employeeUtil.validationOfMail(mail);
                trainer.setEmployeeMail(mail);            
            } catch (EmailMismatchException e) {
                logger.error("Exception occured :" + e);
                getTrainerMailId(trainer);
            }             
        }
    }

    /**
     *
     * <h1> getTrainerMobileNumber </h1>
     *
     * Method used to get trainer mobile number from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */  
    public void getTrainerMobileNumber(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);     
	logger.info("Enter Trainer MobileNumber : ");
        String mobileNumber = scanner.nextLine();

        if (!mobileNumber.isEmpty()) {
            boolean isValid = employeeUtil.matchRegex("^(([6-9]{1}[0-9]{9})*)$",mobileNumber);

            if (isValid) {
                trainer.setEmployeeMobileNumber(mobileNumber);
            } else {
                logger.info("not valid");
                getTrainerMobileNumber(trainer);
            }	    
        }
    }

    /**
     *
     * <h1> getTrainerAddress </h1>
     *
     * Method used to get trainer address from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */         
    public void getTrainerAddress(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);
        logger.info("Enter Trainer CurrentAddress : ");
        String currentAddress = scanner.nextLine();

        if (!currentAddress.isEmpty()) {
            boolean isValid = employeeUtil.matchRegex("^(([0-9\\sa-zA-Z,.-]{3,150})*)$",currentAddress);

            if (isValid) {
                trainer.setCurrentAddress(currentAddress);
            } else {
                logger.info("not valid");
                getTrainerAddress(trainer);
            }	    
        }
    }

    /**
     *
     * <h1> getTrainerAadharNumber </h1>
     *
     * Method used to get trainer aadhar number from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */ 
    public void getTrainerAadharNumber(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainer AadharCardNumber : ");            
        String aadharNumber = scanner.nextLine();

        if (!aadharNumber.isEmpty()) {
            boolean isValid = employeeUtil.matchRegex("^(([1-9]{1}[0-9]{11})*)$",aadharNumber);

            if (isValid) {
                trainer.setAadharCardNumber(aadharNumber);
            } else {
                logger.info("not valid");
                getTrainerAadharNumber(trainer);
            }	    
        }
    }

    /**
     *
     * <h1> getTrainerPanNumber </h1>
     *
     * Method used to get trainer pan number from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */ 
    public void getTrainerPanNumber(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainer PanCardNumber : ");            
        String panNumber = scanner.nextLine();

        if (!panNumber.isEmpty()) {
            boolean isValid = employeeUtil.matchRegex("^(([A-Z0-9]{10})*)$",panNumber);

            if (isValid) {
                trainer.setPanCardNumber(panNumber);
            } else {
                logger.info("not valid");
                getTrainerPanNumber(trainer);
            }	    
        }
    }

    /**
     *
     * <h1> getTrainerCurrentProject </h1>
     *
     * Method used to get trainer current project from user
     *
     * @param {@link Trainer} trainer

     * @return {@link void} returns nothing
     *
     */ 
    public void getTrainerCurrentProject(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter your currentProject : ");            
        String currentProject = scanner.nextLine();

        if (!currentProject.isEmpty()) {
            boolean isValid = employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",currentProject);

            if (isValid) {
                trainer.setCurrentProject(currentProject);
            } else {
                logger.info("not valid");
                getTrainerCurrentProject(trainer);
            }
        }
    }

    /**
     *
     * <h1> getTrainerAchievement </h1>
     *
     * Method used to get trainer achievement from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */ 
    public void getTrainerAchievement(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);	
	logger.info("Enter your achievements : ");            
        String achievement = scanner.nextLine();

        if (!achievement.isEmpty()) {
            boolean isValid = employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",achievement);

            if (isValid) {
                trainer.setAchievement(achievement);
            } else {
                logger.info("not valid");
                getTrainerAchievement(trainer);
            }
        }
    }

    /**
     *
     * <h1> getName </h1>
     *
     * Method used to get trainer name from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */ 
    public void getName(Trainer trainer) {

        Scanner scanner = new Scanner(System.in); 
	logger.info("Enter Trainer Name : ");
        String name = scanner.nextLine();

        if (!name.isEmpty()) {
            boolean isValid = employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",name);

            if (isValid) {
                trainer.setEmployeeName(name);
            } else {
                logger.info("not valid");
                getName(trainer);
            }           	    
        }
    }

    /**
     *
     * <h1> getDateofBirth </h1>
     *
     * Method used to get trainer date of birth from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */ 
    public void getDateofBirth(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);	
        logger.info("Enter Trainer DateOfBirth YYYY-MM-DD:");
        String employeeDateOfBirth = scanner.nextLine();

        if (!employeeDateOfBirth.isEmpty()) {

            try {
                boolean bool = employeeUtil.validationOfDateOfBirth(employeeDateOfBirth);
                if (bool = true) {
                    trainer.setEmployeeDateOfBirth(employeeDateOfBirth);   
                } else {
                    logger.error("invalid date format");
                    getTrainerDateofBirth(trainer);
                }   
            } catch (NumberFormatException e) {
                logger.error("invalid date format");
                getTrainerDateofBirth(trainer);
            } catch ( ArrayIndexOutOfBoundsException e) {
                logger.error("invalid date format");
                getTrainerDateofBirth(trainer);
            }
        }
    }

    /**
     *
     * <h1> getDesignation </h1>
     *
     * Method used to get trainer designation from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */ 
    public void getDesignation(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainer Designation : ");
        String designation = scanner.nextLine();

        if (!designation.isEmpty()) {
            boolean isValid = employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",designation);

            if (isValid) {
                trainer.setEmployeeDesignation(designation);
            } else {
                logger.info("not valid");
                getDesignation(trainer);
            }  
        }  
    }

    /**
     *
     * <h1> getMailId </h1>
     *
     * Method used to get trainer mail Id  from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */ 
    public void getMailId(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);
        logger.info("Enter Trainer Mail : ");
        String mail = scanner.nextLine();

        if (!mail.isEmpty()) {
           
            try {
                employeeUtil.validationOfMail(mail);
                trainer.setEmployeeMail(mail);                 
            } catch (EmailMismatchException e) {
                logger.error("Exception occured :" + e);
                getMailId(trainer);
            }             
        }
    }

    /**
     *
     * <h1> getMobileNumber </h1>
     *
     * Method used to get trainer mobile number from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */  
    public void getMobileNumber(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);     
	logger.info("Enter Trainer MobileNumber : ");
        String mobileNumber = scanner.nextLine();

        if (!mobileNumber.isEmpty()) {
            boolean isValid = employeeUtil.matchRegex("^(([6-9]{1}[0-9]{9})*)$",mobileNumber);

            if (isValid) {
                trainer.setEmployeeMobileNumber(mobileNumber);
            } else {
                logger.info("not valid");
                getMobileNumber(trainer);
            }	    
        }
    }

    /**
     *
     * <h1> getAddress </h1>
     *
     * Method used to get trainer address from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */         
    public void getAddress(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);
        logger.info("Enter Trainer CurrentAddress : ");
        String currentAddress = scanner.nextLine();

        if (!currentAddress.isEmpty()) {
            boolean isValid = employeeUtil.matchRegex("^(([0-9\\sa-zA-Z,.-]{3,150})*)$",currentAddress);

            if (isValid) {
                trainer.setCurrentAddress(currentAddress);
            } else {
                logger.info("not valid");
                getAddress(trainer);
            }	    
        }
    }

    /**
     *
     * <h1> getAadharNumber </h1>
     *
     * Method used to get trainer aadhar number from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */ 
    public void getAadharNumber(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainer AadharCardNumber : ");            
        String aadharNumber = scanner.nextLine();

        if (!aadharNumber.isEmpty()) {
            boolean isValid = employeeUtil.matchRegex("^(([1-9]{1}[0-9]{11})*)$",aadharNumber);

            if (isValid) {
                trainer.setAadharCardNumber(aadharNumber);
            } else {
                logger.info("not valid");
                getAadharNumber(trainer);
            }	    
        }
    }

    /**
     *
     * <h1> getPanNumber </h1>
     *
     * Method used to get trainer pan number from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */ 
    public void getPanNumber(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter Trainer PanCardNumber : ");            
        String panNumber = scanner.nextLine();

        if (!panNumber.isEmpty()) {
            boolean isValid = employeeUtil.matchRegex("^(([A-Z0-9]{10})*)$",panNumber);

            if (isValid) {
                trainer.setPanCardNumber(panNumber);
            } else {
                logger.info("not valid");
                getPanNumber(trainer);
            }	    
        }
    }

    /**
     *
     * <h1> getCurrentProject </h1>
     *
     * Method used to get trainer current project name from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */ 
    public void getCurrentProject(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);
	logger.info("Enter your currentProject : ");            
        String currentProject = scanner.nextLine();

        if (!currentProject.isEmpty()) {
            boolean isValid = employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",currentProject);

            if (isValid) {
                trainer.setCurrentProject(currentProject);
            } else {
                logger.info("not valid");
                getCurrentProject(trainer);
            }	    
        }
    }

    /**
     *
     * <h1> getAchievement </h1>
     *
     * Method used to get trainer achievement from user
     *
     * @param {@link Trainer} trainer
     * @return {@link void} returns nothing
     *
     */ 
    public void getAchievement(Trainer trainer) {

        Scanner scanner = new Scanner(System.in);	
	logger.info("Enter your achievement : ");            
        String achievement = scanner.nextLine();

        if (!achievement.isEmpty()) {
            boolean isValid = employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$",achievement);

            if (isValid) {
                trainer.setAchievement(achievement);
            } else {
                logger.info("not valid");
                getAchievement(trainer);
            }	    
        }
    }
}