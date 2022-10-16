import java.util.Scanner;
import java.util.UUID;
import java.util.InputMismatchException;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

import com.ideas2it.model.Employee;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.service.EmployeeService;
import com.ideas2it.service.impl.EmployeeServiceImpl;



/**
*
* <h2>EmployeController</h2>
*
* The EmployeeController class is an application that
* do the main operation create, read, update, delete 
* if one will be choosen the respect operation performs 
* until user exit the operation is same for all operation 
* 
* @author  Yukendiran K
* @version 1.0
* @since   2022-08-04 
*
*/

public class EmployeeController {

    private static EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
    private static EmployeeInformation employeeInformation = new EmployeeInformation();

    private static Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    
    public static void main(String[] args) throws InputMismatchException, SQLException {
       
        logger.info("\n----------------------------------------------"+
                    "\n*************WELCOME TO IDEAS2IT**************"+
                    "\n----------------------------------------------\n\n");
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;
        boolean isValid = true;  
        
        while (isContinue) {
            try {
                logger.info("\nEnter 1 for Create employee details in database\n"+ 
			    "Enter 2 for Read   employee details in database\n"+ 
			    "Enter 3 for Update employee details in database\n"+ 
	                    "Enter 4 for Delete employee details in database\n"+
                            "Enter 5 for create association between trainer to trainee\n"+
                            "Enter 6 for Read association between trainer to trainee\n"+
                            "Enter \" exitprogram \" for Exit all operations in full application  \n\n\n"+"----------------------------------------------");
                String userOption = scanner.next();
                if (userOption.equals("1")) {	   
     	               createEmployeeData(scanner, isContinue);
                       isContinue = true;
                } else if (userOption.equals("2")) {
                        readEmployeeData(scanner, isContinue);
                        isContinue = true;
                } else if (userOption.equals("3")) {
                        updateEmployeeData(scanner, isContinue);
                        isContinue = true; 
                } else if (userOption.equals("4")) {
                        removeEmployeeData(scanner, isContinue);
                        isContinue = true;
                } else if (userOption.equals("5")) {
                        createAssociation(scanner, isContinue);
                        isContinue = true;
                } else if (userOption.equals("6")) {
                        readAssociation(scanner, isContinue);
                        isContinue = true;
                } else if (userOption.equals("exitprogram")) {
                    logger.info("\n----------------------------------------------");
                    logger.info("***************** THANK YOU ******************");
                    logger.info("----------------------------------------------");    
                    isContinue = false;
                } else {
                    isContinue = true; 
                    throw new InputMismatchException(" enter valid data");
                }
            } catch (InputMismatchException e) {
                logger.error("\ninvalid data : " + e);
            }
        }
    } 

    /**
     * method used to create employee profile  
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @return {@link void} returns nothing
     */
    public static void createEmployeeData(Scanner scanner, boolean isContinue) throws InputMismatchException, SQLException {
        
        String position = "";
        while (isContinue) {
            logger.info("\nChoose position");
            boolean isValidPosition = true;
            while (isValidPosition) {
                try {
	            logger.info("\n1 for Trainer\n" + "2 for Trainee");
                    position = scanner.next();
                    logger.info("\n\n----------------------------------------------");
  
                    if (position.equals("1") || position.equals("2")) {
                        UUID uuid = UUID.randomUUID();
                        String temp = uuid.toString();
                        String[] arr = temp.split("-");
                        String uuidIsKey = arr[0]+arr[3];
                        if (position.equals("1")) {
	    	            logger.info("\nEnter Trainer Details :");
                            Trainer trainer = employeeInformation.getInformationFromTrainer(uuidIsKey, scanner);
                            employeeService.addTrainer(trainer, logger);                     
                            logger.info("\nTrainer Data Added Successfully");
                            logger.info("\n----------------------------------------------");
                            isValidPosition = false;
                        } else {
	    	            logger.info("\nEnter Trainee Details :");
		            Trainee trainee = employeeInformation.getInformationFromTrainee(uuidIsKey, scanner);
                            employeeService.addTrainee(trainee, logger);
	                    logger.info("\nTrainer Data Added Successfully");
                            logger.info("\n----------------------------------------------");
                            isValidPosition = false;
                        }
                    } else if (position.equals("exitprogram")) {
                        isValidPosition = false;
                    } else {
                        isValidPosition = true; 
                        throw new InputMismatchException("enter valid data");                    
                    }
                } catch (InputMismatchException e) {
                    logger.error("\ninvalid data" + e);
                } catch (SQLException e) {
                    logger.error("",e);
                }
            }
            if (position.equals("exitprogram")) {
                isContinue = false;
            } else {
	        logger.info(" \nIf you want to continue add data\n" + "1.yes\n" + "2.no");
                String addAnotherEmployeeData = scanner.next();
                logger.info("----------------------------------------------");
                isContinue = !(addAnotherEmployeeData.equals("2"));                   
            }
        }
    }

    /**
     * method used to read employee profile  
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @return {@link void} returns nothing
     */
    public static void readEmployeeData(Scanner scanner, boolean isContinue) throws InputMismatchException, SQLException {
        
        String employeeId = "";
        String choiceForReadInformation = "";
        while (isContinue) { 
            boolean isValidPosition = true;
            while (isValidPosition) {
                try {     
                    logger.info("\nChoose position\n" + "1.Trainer\n" + "2.Trainee");
                    choiceForReadInformation = scanner.next();
                    logger.info("\n----------------------------------------------");                
        
                    if (choiceForReadInformation.equals("1") || choiceForReadInformation.equals("2")) {
                        logger.info("\n1.Whole Trainer\n" + "2.particular Trainer");
                        String readYourChoiceOfInformation = scanner.next();          
                        logger.info("\n----------------------------------------------");
                        if ( readYourChoiceOfInformation.equals("1") && choiceForReadInformation.equals("1")) {
                            List<Trainer> trainers = employeeService.getTrainersData(logger);
                            if (trainers == null) {
                                logger.info("\nNo data found");
                            } else {
                                trainers.forEach(trainer -> logger.info("EmployeeId          : "+trainer.getEmployeeId()+"\n"+"EmployeeName        : "+trainer.getEmployeeName()+"\n"+
                                               				"EmployeeDesignation : "+trainer.getEmployeeDesignation()+"\n"+"EmployeeMail        : "+trainer.getEmployeeMail()+"\n"+
                                                   			"EmployeeMobileNumber: "+trainer.getEmployeeMobileNumber()+"\n"+ "CurrentAddress      : "+trainer.getCurrentAddress()+"\n"+
                                                                        "----------------------------------------------"));
                                isValidPosition = false;                 
                            }
                        } else if ( readYourChoiceOfInformation.equals("1") && choiceForReadInformation.equals("2")) {
                            List<Trainee> trainees = employeeService.getTraineesData(logger);
                            if (trainees == null) {
                                logger.info("\nNo data found");
                            } else {
                                trainees.forEach(trainee -> logger.info("EmployeeId          : "+trainee.getEmployeeId()+"\n"+"EmployeeName        : "+trainee.getEmployeeName()+"\n"+
                                               				"EmployeeDesignation : "+trainee.getEmployeeDesignation()+"\n"+"EmployeeMail        : "+trainee.getEmployeeMail()+"\n"+
                                                   			"EmployeeMobileNumber: "+trainee.getEmployeeMobileNumber()+"\n"+ "CurrentAddress      : "+trainee.getCurrentAddress()+"\n"+
                                                                        "----------------------------------------------"));
                                isValidPosition = false;
                            }
                        } else { 
                            logger.info("\nEnter EmployeeId :");
                            boolean isValidId = true;
                            while (isValidId) {
                                employeeId = scanner.next();
                                if (choiceForReadInformation.equals("1")) {
                                    Trainer trainer = employeeService.searchTrainerData(employeeId, logger);
                                    if (trainer == null) {
                                        logger.info("\nNo data found\n" + "Enter valid Id");
                                        isValidId = true; 
                                    } else {
                                        logger.info("Trainer Detail :"+"\n"+
                                                    "EmployeeId          : "+trainer.getEmployeeId()+"\n"+"EmployeeName        : "+trainer.getEmployeeName()+"\n"+
                                               	    "EmployeeDesignation : "+trainer.getEmployeeDesignation()+"\n"+"EmployeeMail        : "+trainer.getEmployeeMail()+"\n"+
                                                    "EmployeeMobileNumber: "+trainer.getEmployeeMobileNumber()+"\n"+ "CurrentAddress      : "+trainer.getCurrentAddress()+"\n"+
                                                    "----------------------------------------------");
                                        isValidId = false;
                                    }   
                                } else {
                                    Trainee trainee = employeeService.searchTraineeData(employeeId, logger);
                                    if (trainee == null) {                           
                                        logger.info("\nNo data found\n" + "Enter valid Id");
                                        isValidId = true; 
                                    } else {
                                        logger.info("Trainer Detail :"+"\n"+
                                                    "EmployeeId          : "+trainee.getEmployeeId()+"\n"+"EmployeeName        : "+trainee.getEmployeeName()+"\n"+
                                                    "EmployeeDesignation : "+trainee.getEmployeeDesignation()+"\n"+"EmployeeMail        : "+trainee.getEmployeeMail()+"\n"+
                                                    "EmployeeMobileNumber: "+trainee.getEmployeeMobileNumber()+"\n"+ "CurrentAddress      : "+trainee.getCurrentAddress()+"\n"+
                                                    "----------------------------------------------");
                                        isValidId = false;
                                    }
                                }   
                            }
                            isValidPosition = false; 
                        }
                    } else if (choiceForReadInformation.equals("exitprogram")) {
                        isValidPosition = false;               
                    } else {
                        isValidPosition = true;
                        throw new InputMismatchException("enter valid data");
                    }
                } catch (InputMismatchException e) {
                    logger.error("\nException occured" + e);
                } catch (SQLException e) {
                    logger.error("",e);
                }
            }
            if (choiceForReadInformation.equals("exitprogram")) {
                isContinue = false;               
            } else {
                logger.info("\nif you want read another employee\n" + "1.yes\n" + "2.no"); 
                String choiceForReadAnotherInformation = scanner.next();          
                isContinue = !(choiceForReadAnotherInformation.equals("2")); 
            }
        }
    }

    /**
     * method used to update employee profile  
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @return {@link void} returns nothing
     */
    public static void updateEmployeeData(Scanner scanner, boolean isContinue) throws InputMismatchException, SQLException {

        String employeeId = "";
        String updateForEmployeeData = "";
        while (isContinue) {
            boolean isValidPosition = true;
            while (isValidPosition) {
                try {      
                    logger.info("\nChoose position\n" + "1.Trainer\n" + "2.Trainee");
                    updateForEmployeeData = scanner.next();
                    logger.info("\n----------------------------------------------");
        
                    if (updateForEmployeeData.equals("1") || updateForEmployeeData.equals("2")) {
                        logger.info("\nEnter EmployeeId :");
                        boolean isValidId = true;
                        while (isValidId) {
                            employeeId = scanner.next();
                            if (updateForEmployeeData.equals("1")) {
                                Trainer trainer = employeeService.searchTrainerData(employeeId, logger);
                                if (trainer == null) {                           
                                    logger.info("\nno data found\n" + "Enter valid Id");
                                    isValidId = true; 
                                } else {
                                    employeeInformation.getInformationForUpdateTrainer(scanner, trainer);
                                    employeeService.updateTrainerData(employeeId, trainer, logger);
                                    isValidId = false;
                                }
                            } else {
                                Trainee trainee = employeeService.searchTraineeData(employeeId, logger);
                                if (trainee == null) {                           
                                    logger.info("\nNo data found\n" + "Enter valid Id");
                                    isValidId = true; 
                                } else {
                                    employeeInformation.getInformationForUpdateTrainee(scanner, trainee);
                                    employeeService.updateTraineeData(employeeId, trainee, logger); 
                                    isValidId = false;
                                }
                            }

                            isValidPosition = false; 
                        }
                    } else if (updateForEmployeeData.equals("exitprogram")) {
                        isValidPosition = false;               
                    } else {
                        isValidPosition = true;
 			throw new InputMismatchException("enter valid data");
                    }
                } catch (InputMismatchException e) {
           	    logger.error("\nException occured" + e);
       	        } catch (SQLException e) {
                    logger.error("",e);
                }
            }
            if (updateForEmployeeData.equals("exitprogram")) {
                isContinue = false;
            } else {
                logger.info("\nif you want update another employee\n" + "1.yes\n" + "2.no"); 
                String updateForAnotherEmployeeData = scanner.next();
                isContinue = !(updateForAnotherEmployeeData.equals("2"));
            }
        }
    }

    /**
     * method used to delete employee profile  
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @return {@link void} returns nothing
     */
    public static void removeEmployeeData(Scanner scanner, boolean isContinue) throws InputMismatchException, SQLException {
        
        String removeForEmployeeData = "";
        while (isContinue) {
            boolean isValidPosition = true;
            while (isValidPosition) {
                try {
                    String employeeId = "";       
                    logger.info("\nChoose position");
	            logger.info("\n1.Trainer\n" + "2.Trainee");
                    removeForEmployeeData = scanner.next();
                    logger.info("\n----------------------------------------------");
 
                    while (isValidPosition) {
                        if (removeForEmployeeData.equals("1") || removeForEmployeeData.equals("2")) {
                            logger.info("\nEnter EmployeeId :");
                            employeeId = scanner.next();
                            if (removeForEmployeeData.equals("1")) {
                                employeeService.deleteTrainerData(employeeId, logger);
                            } else {
                                employeeService.deleteTraineeData(employeeId, logger);
                            }
                            isValidPosition = false;
                        } else if (removeForEmployeeData.equals("exitprogram")) {
                            isValidPosition = false;               
                        } else {
                            isValidPosition = true;
                            throw new InputMismatchException("enter valid data");
                        }
                    }
                } catch (InputMismatchException e) {
                    logger.error("\nException occured" + e);
                } catch (SQLException e) {
                    logger.error("",e);
                }
            }
            if (removeForEmployeeData.equals("exitprogram")) {
                isContinue = false;
            } else {
                logger.info("\nIf you want remove more data in the database : \n" + "1.yes\n" + "2.no" );
                String removeMoreDataTrainer = scanner.next();
                logger.info("\n----------------------------------------------");
                isContinue = !(removeMoreDataTrainer.equals("2"));
            }
        }
    }

    /**
     * method used to read employee profile  
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @return {@link void} returns nothing
     */
    public static void createAssociation(Scanner scanner, boolean isContinue) throws InputMismatchException, SQLException {
 
        String joins = "";
        while (isContinue) { 
            boolean isValidPosition = true;
            while (isValidPosition) {
                try {
                    logger.info("1 for create trainer to trainee\n2 for create trainee to trainer ");           //create = assign
                    joins = scanner.next();
                    List<String> traineeId = new ArrayList<String>();
                    List<String> trainerId = new ArrayList<String>();

                    if (joins.equals("1")) {
                        logger.info("Enter trainerId :");
                        String trId = scanner.next();
                        trainerId.add(trId);
                        logger.info("" + trainerId);
                        logger.info("Enter no of trainees :");
                        int count = scanner.nextInt();
                        String teId = "";
                        logger.info("Enter traineeId :");
                        for (int i = 0; i < count; i++) {
                            teId = scanner.next();
                            traineeId.add(teId);
                        }
                        logger.info("" + traineeId);
                        employeeService.createAssociation(trainerId, traineeId);
                        isValidPosition = false;

                    } else if (joins.equals("2")) {
                        logger.info("Enter traineeId :");
                        String teId = scanner.next();
                        traineeId.add(teId);
                        logger.info("Enter no of trainers :");
                        int count = scanner.nextInt();
                        logger.info("Enter trainerId :");
                        for (int i = 0; i < count; i++) {
                            String trId = scanner.next();
                            trainerId.add(trId);
                        }
                        employeeService.createAssociation(trainerId, traineeId);
                        isValidPosition = false;
                    } else if (joins.equals("exitprogram")) {
                        isValidPosition = false;               
                    } else {
                        isValidPosition = true;
                        throw new InputMismatchException("enter valid data");
                    }
                } catch (InputMismatchException e) {
                    logger.error("\nException occured" + e);
                } catch (SQLException e) {
                    logger.error("",e);
                }
            }
            if (joins.equals("exitprogram")) {
                isContinue = false;               
            } else {
                logger.info("\nIf you want create another association : \n" + "1.yes\n" + "2.no" );
                String createNextAssociation = scanner.next();
                logger.info("\n----------------------------------------------");
                isContinue = !(createNextAssociation.equals("2"));
            }
        }
    }      

    /**
     * method used to read employee profile  
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @return {@link void} returns nothing
     */
    public static void readAssociation(Scanner scanner, boolean isContinue) throws InputMismatchException, SQLException {
        
        String employeeId = "";
        String choiceForReadInformation = "";
        while (isContinue) { 
            boolean isValidPosition = true;
            while (isValidPosition) {
                try {     
                    logger.info("\nChoose position\n" + "1.Trainer\n" + "2.Trainee");
                    choiceForReadInformation = scanner.next();
                    logger.info("\n----------------------------------------------");                
        
                    if (choiceForReadInformation.equals("1") || choiceForReadInformation.equals("2")) {
                        logger.info("\nEnter EmployeeId :");                            
                        boolean isValidId = true;
                        while (isValidId) {
                            employeeId = scanner.next();
                            if (choiceForReadInformation.equals("1")) {
                                Trainer trainer = employeeService.searchTrainerData(employeeId, logger);
                                List<Trainee> trainees = employeeService.associateTrainee(employeeId);
                                if (trainer == null) {
                                    logger.info("\nNo data found\n" + "Enter valid Id");
                                    isValidId = true; 
                                } else {
                                    logger.info("Trainer Detail :"+"\n"+
                                                "EmployeeId          : "+trainer.getEmployeeId()+"\n"+"EmployeeName        : "+trainer.getEmployeeName()+"\n"+
                                        	"EmployeeDesignation : "+trainer.getEmployeeDesignation()+"\n"+"EmployeeMail        : "+trainer.getEmployeeMail()+"\n"+
                                                "EmployeeMobileNumber: "+trainer.getEmployeeMobileNumber()+"\n"+ "CurrentAddress      : "+trainer.getCurrentAddress()+"\n"+
                                                "----------------------------------------------");

                                    trainees.forEach(trainee -> logger.info("Associated trainees :"+"\n"+
                                                                                "EmployeeId          : "+trainee.getEmployeeId()+"\n"+"EmployeeName        : "+trainee.getEmployeeName()));
                                    isValidId = false;
                                }   
                            } else {
                                Trainee trainee = employeeService.searchTraineeData(employeeId, logger);
                                List<Trainer> trainers = employeeService.associateTrainer(employeeId);
                                if (trainee == null) {                           
                                    logger.info("\nNo data found\n" + "Enter valid Id");
                                    isValidId = true; 
                                } else {
                                    logger.info("Trainer Detail :"+"\n"+
                                                "EmployeeId          : "+trainee.getEmployeeId()+"\n"+"EmployeeName        : "+trainee.getEmployeeName()+"\n"+
                                                "EmployeeDesignation : "+trainee.getEmployeeDesignation()+"\n"+"EmployeeMail        : "+trainee.getEmployeeMail()+"\n"+
                                                "EmployeeMobileNumber: "+trainee.getEmployeeMobileNumber()+"\n"+ "CurrentAddress      : "+trainee.getCurrentAddress()+"\n"+
                                                "----------------------------------------------");
                                    trainers.forEach(trainer -> logger.info("Associated trainers :"+"\n"+
                                                                                "EmployeeId          : "+trainer.getEmployeeId()+"\n"+"EmployeeName        : "+trainer.getEmployeeName()));
                                    isValidId = false;
                                }
                            } 
                            isValidPosition = false;  
                        }
                    } else if (choiceForReadInformation.equals("exitprogram")) {
                        isValidPosition = false;
                    } else {
                        isValidPosition = true;
                        throw new InputMismatchException("enter valid data");
                    }
                } catch (InputMismatchException e) {
                    logger.error("\nException occured" + e);
                } catch (SQLException e) {
                    logger.error("",e);
                }
            } 
            if (choiceForReadInformation.equals("exitprogram")) {
                isContinue = false;
            } else {
                logger.info("\nif you want read another association\n" + "1.yes\n" + "2.no"); 
                String choiceForReadAnotherInformation = scanner.next();          
                isContinue = !(choiceForReadAnotherInformation.equals("2")); 
            }
        }
    }
}

