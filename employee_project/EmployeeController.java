import java.util.Scanner;
import java.util.Map;
import java.util.UUID;
import java.util.InputMismatchException;

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
    
    public static void main(String[] args) throws InputMismatchException {
       
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
                            "Enter 5 for Exit\n\n"+"----------------------------------------------");
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
                    logger.info("----------------------------------------------");
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
     * @param {@link String} uuidIsKey
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     */
    public static void createEmployeeData(Scanner scanner, boolean isContinue) throws InputMismatchException {
        
        boolean isValid = true;
        while (isContinue) {
            logger.info("\nChoose position");
            while (isContinue) {
                try {
	            logger.info("\n1.Trainer\n" + "2.Trainee");
                    String position = scanner.next();
                    logger.info("\n----------------------------------------------");
                    //String employeeId = "";
  
                    if (position.equals("1") || position.equals("2")) {
                        UUID uuid = UUID.randomUUID();
                        String uuidIsKey = uuid.toString();
                        if (position.equals("1")) {
	    	            logger.info("\nEnter Trainer Details :");
                            Trainer trainer = employeeInformation.getInformationFromTrainer(scanner, isContinue, isValid);
                            //employeeId = trainer.getEmployeeId();
                            employeeService.addTrainer(uuidIsKey, trainer);
                            logger.info("\nTrainer Data Added Successfully");
                            logger.info("\n----------------------------------------------");
                            isContinue = false;
                        } else {
	    	            logger.info("\nEnter Trainee Details :");
		            Trainee trainee = employeeInformation.getInformationFromTrainee(scanner, isContinue, isValid);
                            //employeeId = trainee.getEmployeeId();
                            employeeService.addTrainee(uuidIsKey, trainee);
	                    logger.info("\nTrainer Data Added Successfully");
                            logger.info("\n----------------------------------------------");
                            isContinue = false;
                        }
                    } else {
                        isContinue = true; 
                        throw new InputMismatchException("enter valid data");                    
                    }
                } catch (InputMismatchException e) {
                    logger.error("\ninvalid data" + e);
                }
            }
	    logger.info(" \nIf you want to continue add data\n" + "1.yes\n" + "2.no");
            String addAnotherEmployeeData = scanner.next();
            logger.info("----------------------------------------------");
            isContinue = !(addAnotherEmployeeData.equals("2"));                   
        }
    }

    /**
     * method used to read employee profile  
     * @param {@link String} uuidIsKey
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @return {@link void} returns nothing
     */
    public static void readEmployeeData(Scanner scanner, boolean isContinue) throws InputMismatchException {
        
        //String employeeId = "";
        String uuidIsKey = "";
        while (isContinue) { 
            while (isContinue) { 
                try {     
                    logger.info("\nChoose position\n" + "1.Trainer\n" + "2.Trainee");
                    String choiceForReadInformation = scanner.next();
                    logger.info("\n----------------------------------------------");                
        
                    if (choiceForReadInformation.equals("1") || choiceForReadInformation.equals("2")) {
                        logger.info("\n1.Whole Trainer\n" + "2.particular Trainer");
                        String readYourChoiceOfInformation = scanner.next();          
                        logger.info("----------------------------------------------");
                        if ( readYourChoiceOfInformation.equals("1") && choiceForReadInformation.equals("1")) {
                            Map<String, Trainer> trainers = employeeService.getTrainersData();
                            if (trainers.isEmpty()) {
                                logger.info("\nNo data found");
                            } else {
                                trainers.forEach((Id, trainer) -> logger.info("\nKey                 : "+Id+"\n"+"\nCompany Name        : "+trainer.companyName+"\n"+
                                                                                     "EmployeeId          : "+trainer.getEmployeeId()+"\n"+"EmployeeName        : "+trainer.getEmployeeName()+"\n"+
                                               				             "EmployeeDesignation : "+trainer.getEmployeeDesignation()+"\n"+"EmployeeMail        : "+trainer.getEmployeeMail()+"\n"+
                                                   			             "EmployeeMobileNumber: "+trainer.getEmployeeMobileNumber()+"\n"+ "CurrentAddress      : "+trainer.getCurrentAddress()+"\n"+
                                                                                     "----------------------------------------------"));
                                isContinue = false;                 
                            }
                        } else if ( readYourChoiceOfInformation.equals("1") && choiceForReadInformation.equals("2")) {
                            Map<String, Trainee> trainees = employeeService.getTraineesData(); 
                            if (trainees.isEmpty()) {
                               logger.info("\nNo data found");
                            } else {
                                trainees.forEach((Id, trainee) -> logger.info("\nKey                 : "+Id+"\n"+"\nCompany Name        : "+trainee.companyName+"\n"+
                                                                                     "EmployeeId          : "+trainee.getEmployeeId()+"\n"+"EmployeeName        : "+trainee.getEmployeeName()+"\n"+
                                               				             "EmployeeDesignation : "+trainee.getEmployeeDesignation()+"\n"+"EmployeeMail        : "+trainee.getEmployeeMail()+"\n"+                                               				 
                                                			             "EmployeeMobileNumber: "+trainee.getEmployeeMobileNumber()+"\n"+"CurrentAddress      : "+trainee.getCurrentAddress()+"\n"+                                               				
                                                                                     "----------------------------------------------"));
                                isContinue = false;
                            }
                        } else { 
                            logger.info("\nEnter EmployeeId :");
                            while (isContinue) {
                                //employeeId = scanner.next();
                                uuidIsKey = scanner.next();
                                if (choiceForReadInformation.equals("1")) {
                                    Trainer trainer = employeeService.searchTrainerData(uuidIsKey);
                                    if (trainer == null) {
                                        logger.info("\nNo data found\n" + "Enter valid Id");
                                        isContinue = true; 
                                    } else {
                                        logger.info("\nKey                 : "+uuidIsKey+"\n"+"\nCompany Name        : "+trainer.companyName+"\n"+                                               
                                                           "EmployeeId          : "+trainer.getEmployeeId()+"\n"+"EmployeeName        : "+trainer.getEmployeeName()+"\n"+                                               
                                                           "EmployeeDesignation : "+trainer.getEmployeeDesignation()+"\n"+"EmployeeMail        : "+trainer.getEmployeeMail()+"\n"+                                               
                                                           "EmployeeMobileNumber: "+trainer.getEmployeeMobileNumber()+"\n"+"CurrentAddress      : "+trainer.getCurrentAddress());
                                        logger.info("\n----------------------------------------------");
                                        isContinue = false;
                                    }   
                                } else {

                                    Trainee trainee = employeeService.searchTraineeData(uuidIsKey);
                                    if (trainee == null) {                           
                                        logger.info("\nNo data found\n" + "Enter valid Id");
                                        isContinue = true; 
                                    } else {
                                        logger.info("\nKey                 : "+uuidIsKey+"\n"+"\nCompany Name        : "+trainee.companyName+"\n"+                                               
                                                           "EmployeeId          : "+trainee.getEmployeeId()+"\n"+"EmployeeName        : "+trainee.getEmployeeName()+"\n"+                                               
                                                           "EmployeeDesignation : "+trainee.getEmployeeDesignation()+"\n"+"EmployeeMail        : "+trainee.getEmployeeMail()+"\n"+                                               
                                                           "EmployeeMobileNumber: "+trainee.getEmployeeMobileNumber()+"\n"+"CurrentAddress      : "+trainee.getCurrentAddress());                                               
                                        logger.info("\n----------------------------------------------");
                                        isContinue = false;
                                    }
                                }   
                            } 
                        }
                    isContinue = false;
                    } else {
                        isContinue = true;
                        throw new InputMismatchException("enter valid data");
                    }
                } catch (InputMismatchException e3) {
                    logger.error("\nException handled");
                }
            }
            logger.info("\nif you want read another employee\n" + "1.yes\n" + "2.no"); 
            String choiceForReadAnotherInformation = scanner.next();          
            isContinue = !(choiceForReadAnotherInformation.equals("2")); 
        }
    }

    /**
     * method used to update employee profile  
     * @param {@link String} uuidIsKey
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @param {@link boolean} isValid
     * @return {@link void} returns nothing
     */
    public static void updateEmployeeData(Scanner scanner, boolean isContinue) throws InputMismatchException {

        

        boolean isValid = true;
        //String employeeId = "";
        String uuidIsKey = "";
        while (isContinue) {
            while (isContinue) {
                try {      
                    logger.info("\nChoose position\n" + "1.Trainer\n" + "2.Trainee");
                    String updateForEmployeeData = scanner.next();
                    logger.info("\n----------------------------------------------");
        
                    if (updateForEmployeeData.equals("1") || updateForEmployeeData.equals("2")) {
                        logger.info("\nEnter EmployeeId :");
                        while (isContinue) {
                            //employeeId = scanner.next();
                            uuidIsKey = scanner.next();
                            if (updateForEmployeeData.equals("1")) {
                                Trainer trainer = employeeService.searchTrainerData(uuidIsKey);
                                if (trainer == null) {                           
                                    logger.info("\nno data found\n" + "Enter valid Id");
                                    isContinue = true; 
                                } else {
                                    employeeInformation.getInformationForUpdateTrainer(scanner, isContinue, isValid, trainer);
                                    employeeService.updateTrainerData(uuidIsKey, trainer);
                                    isContinue = false;
                                }
                            } else {
                                Trainee trainee = employeeService.searchTraineeData(uuidIsKey);
                                if (trainee == null) {                           
                                    logger.info("\nNo data found\n" + "Enter valid Id");
                                    isContinue = true; 
                                } else {
                                    employeeInformation.getInformationForUpdateTrainee(scanner, isContinue, isValid, trainee);
                                    employeeService.updateTraineeData(uuidIsKey, trainee); 
                                    isContinue = false;
                                }
                            }
                        }
                    isContinue = false; 
                    } else {
                        isContinue = true;
 			throw new InputMismatchException("enter valid data");
                    }
                } catch (InputMismatchException e4) {
           	    logger.error("\nException handled");
       	        } 
            }
            logger.info("\nif you want update another employee\n" + "1.yes\n" + "2.no"); 
            String updateForAnotherEmployeeData = scanner.next();
            isContinue = !(updateForAnotherEmployeeData.equals("2"));
        }
    }

    /**
     * method used to delete employee profile  
     * @param {@link String} uuidIsKey
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @return {@link void} returns nothing
     */
    public static void removeEmployeeData(Scanner scanner, boolean isContinue) throws InputMismatchException {
        
        String uuidIsKey = "";
        while (isContinue) {
            while (isContinue) { 
                try {
                    //String employeeId = "";       
                    logger.info("\nChoose position");
	            logger.info("\n1.Trainer\n" + "2.Trainee");
                    String removeForEmployeeData = scanner.next();
                    logger.info("----------------------------------------------");
 
                    while (isContinue) {
                        if (removeForEmployeeData.equals("1") || removeForEmployeeData.equals("2")) {
                            logger.info("\nEnter EmployeeId :");
                            //employeeId = scanner.next();
                            uuidIsKey = scanner.next();
                            if (removeForEmployeeData.equals("1")) {
                                employeeService.deleteSingleTrainerData(uuidIsKey);
                            } else {
                                employeeService.deleteSingleTraineeData(uuidIsKey);
                            }
                        isContinue = false;
                        } else {
                            isContinue = true;
                            throw new InputMismatchException("enter valid data");
                        }
                    }
                } catch (InputMismatchException e5) {
                    logger.error("\nException handled");
                }  
            }
            logger.info("\nIf you want remove more data in the database : \n" + "1.yes\n" + "2.no" );
            String removeMoreDataTrainer = scanner.next();
            logger.info("\n----------------------------------------------");
            isContinue = !(removeMoreDataTrainer.equals("2"));
        }
    }
}

