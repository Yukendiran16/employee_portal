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

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.service.EmployeeService;
import com.ideas2it.service.impl.EmployeeServiceImpl;
import com.ideas2it.controller.EmployeeInformation;


public class TrainerController {

    private static EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
    private static EmployeeInformation employeeInformation = new EmployeeInformation();

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
                            "Enter 6 for Read association between trainee to trainer\n"+
                            "Enter 7 for Exit  \n\n\n"+"----------------------------------------------");
                int userOption = scanner.nextInt();
                switch (userOption) {
                    case 1:	   
     	                createTrainerData();
                        break;
                    case 2:
                        readTrainerData();
                        break;
                    case 3:
                        updateTrainerData();
                        break; 
                    case 4:
                        removeTrainerData();
                        break;
                    case 5:
                        assignTrainerForTrainees();
                        break;
                    case 6:
                        readTraineesOfGivenTrainer();
                        isContinue = false;
                        break;
                    case 7:
                        isContinue = false;
                        break;
                    default:
                        break;
                }
                 
                logger.info("\n----------------------------------------------");
                logger.info("***************** THANK YOU ******************");
                logger.info("----------------------------------------------");    
            } catch (InputMismatchException e) {
                logger.error("\ninvalid data : " + e);
            } catch (HibernateException e) {
                logger.error("\nException occured" + e);
            } catch (SQLException e) {
                logger.error("",e);
            }
        }
    }  

    /**
     * method used to create employee profile  
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @return {@link void} returns nothing
     */
    public static void createTrainerData() throws InputMismatchException, SQLException, HibernateException {
        
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;
        String position = "";
        while (isContinue) {

            try {
                UUID uuId = UUID.randomUUID();
                String temp = uuId.toString();
                String[] arr = temp.split("-");
                String uuid = arr[0]+arr[3];
       	    	logger.info("\nEnter Trainer Details :");
                Trainer trainer = employeeInformation.getInformationFromTrainer(uuid);
                String message = employeeService.addTrainer(trainer);                     
                logger.info("" + message);
                logger.info("\n----------------------------------------------");
                isContinue = false;
 
            } catch (InputMismatchException e) {
                logger.error("\ninvalid data" + e);
            } catch (HibernateException e) {
                logger.error("\nException occured" + e);
            } catch (SQLException e) {
                logger.error("",e);
            }
            logger.info(" \nIf you want to continue\n" + "1.yes\n" + "2.no");
            String addAnotherEmployeeData = scanner.next();
            logger.info("----------------------------------------------");
            isContinue = !(addAnotherEmployeeData.equals("2"));                   
        }
    }

    /**
     * method used to read employee profile  
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @return {@link void} returns nothing
     */
    public static void readTrainerData() throws InputMismatchException, SQLException, HibernateException{
        
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;
        int id;
        while (isContinue) { 

            try {        
	        logger.info("\n1.whole data\n" + "2.particular data");
                String removeForEmployeeData = scanner.next();
                logger.info("\n----------------------------------------------"); 
                if (removeForEmployeeData.equals("1")) {
                    List<Trainer> trainers = employeeService.getTrainersData();
                    if (trainers == null) {
                        logger.info("\nNo data found");
                    } else {
                        trainers.forEach(trainer -> logger.info("EmployeeId          : "+trainer.getId()+"\n"+"EmployeeName        : "+trainer.getEmployeeName()+"\n"+
                                                 	         "EmployeeDesignation : "+trainer.getEmployeeDesignation()+"\n"+"EmployeeMail        : "+trainer.getEmployeeMail()+"\n"+
                                          	                 "EmployeeMobileNumber: "+trainer.getEmployeeMobileNumber()+"\n"+ "CurrentAddress      : "+trainer.getCurrentAddress()+"\n"+
                                                                 "----------------------------------------------"));
                        isContinue = false;                 
                    } 
                } else {
                    logger.info("\nEnter EmployeeId :");
                    boolean isValidId = true;
                    while (isValidId) {
                        id = scanner.nextInt();
                        Trainer trainer = employeeService.searchTrainerData(id);
                        if (trainer == null) {
                            logger.info("\nNo data found\n" + "Enter valid Id");
                            isValidId = true; 
                        } else {
                            logger.info("Trainer Detail :"+"\n"+
                                        "EmployeeId          : "+trainer.getId()+"\n"+"EmployeeName        : "+trainer.getEmployeeName()+"\n"+
                               	        "EmployeeDesignation : "+trainer.getEmployeeDesignation()+"\n"+"EmployeeMail        : "+trainer.getEmployeeMail()+"\n"+
                                        "EmployeeMobileNumber: "+trainer.getEmployeeMobileNumber()+"\n"+ "CurrentAddress      : "+trainer.getCurrentAddress()+"\n"+
                                        "----------------------------------------------");
                            isValidId = false;
                        }
                    }
                } 
            } catch (InputMismatchException e) {
                logger.error("\nException occured" + e);
            } catch (HibernateException e) {
                logger.error("\nException occured" + e);
            } catch (SQLException e) {
                logger.error("",e);
            }
            logger.info("\nif you want to continue\n" + "1.yes\n" + "2.no"); 
            String choiceForReadAnotherInformation = scanner.next();          
            isContinue = !(choiceForReadAnotherInformation.equals("2")); 
        }
    }

    /**
     * method used to update employee profile  
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @return {@link void} returns nothing
     */
    public static void updateTrainerData() throws InputMismatchException, SQLException, HibernateException {

        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;
        int id;
        while (isContinue) {
           
            try {
                logger.info("\nEnter EmployeeId :");
                boolean isValidId = true;
                while (isValidId) {
                    id = scanner.nextInt();                
                    Trainer trainer = employeeService.searchTrainerData(id);
                    if (trainer == null) {                           
                        logger.info("\nno data found\n" + "Enter valid Id");
                        isValidId = true; 
                    } else {
                        employeeInformation.getInformationForUpdateTrainer(trainer);
                        String message = employeeService.updateTrainerData(trainer);
                        logger.info("" + message);
                        isValidId = false;
                    }
                }
            } catch (InputMismatchException e) {
        	logger.error("\nException occured" + e);
            } catch (HibernateException e) {
                logger.error("\nException occured" + e);
       	    } catch (SQLException e) {
                logger.error("",e);
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
    public static void removeTrainerData() throws InputMismatchException, SQLException, HibernateException {
        
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;
        String removeForEmployeeData = "";
        while (isContinue) {

            try {
                int id; 
                boolean isValidId = true;      
                while (isValidId) {
           
                    logger.info("\nEnter EmployeeId :");
                    id = scanner.nextInt();
                    String message = employeeService.deleteTrainerData(id);
                    logger.info("" + message);
                    isValidId = false;
                }
            } catch (InputMismatchException e) {
                logger.error("\nException occured" + e);
            } catch (SQLException e) {
                logger.error("",e);
            } catch (HibernateException e) {
                logger.error("\nException occured" + e);
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
    public static void assignTrainerForTrainees() throws InputMismatchException, SQLException {

        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;  
        String message = null;
 
        while (isContinue) { 

            try {
                Trainee trainee = null;
                logger.info("\nEnter Trainer Id :");
                int trainerId = scanner.nextInt();         
                Trainer trainer = employeeService.searchTrainerData(trainerId);
                trainee.setTrainer(trainer);
                List<Trainee> traineesList = employeeService.getTraineesData();
                traineesList.forEach(listOfTrainees -> logger.info("Associated trainees :"+"\n"+
                                                                 "EmployeeId          : "+trainee.getId()+"\n"+"EmployeeName        : "+trainee.getEmployeeName()));

                List<Trainee> assignedTrainees = new ArrayList<>();

                logger.info("Enter no of trainees :");
                int count = scanner.nextInt();
                logger.info("Enter trainee Id :");

                for (int i = 0; i < count; i++) {
                    int traineeId = scanner.nextInt();
                    for (Trainee traineeById : traineesList) {
                        if (traineeId == traineeById.getId()) {
                            assignedTrainees.add(traineeById);
                        }
                    }
                }

                trainer.setTrainees(assignedTrainees);
                message = employeeService.updateTrainerData(trainer);
                logger.info("{}" + message);
            } catch (InputMismatchException e) {
                logger.error("\nException occured" + e);
            } catch (SQLException e) {
                logger.error("",e);
            }

            logger.info("\nIf you want to continue : \n" + "1.yes\n" + "2.no" );
            String assignNextAssociation = scanner.next();
            logger.info("\n----------------------------------------------");
            isContinue = !(assignNextAssociation.equals("2"));
        }
    }

    /**
     * method used to read employee profile  
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @return {@link void} returns nothing
     */
    public static void readTraineesOfGivenTrainer() throws InputMismatchException, SQLException {
        
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true; 
        int id;
        while (isContinue) { 
            
            try {     

               logger.info("\nEnter EmployeeId :");                            
               boolean isValidId = true;
               while (isValidId) {
                     id = scanner.nextInt();
                     Trainer trainer = employeeService.searchTrainerData(id);
                     if (trainer == null) {
                         logger.info("\nNo data found\n" + "Enter valid Id");
                         isValidId = true; 
                     } else {
                         logger.info("Trainer Detail :"+"\n"+
                                     "EmployeeId          : "+trainer.getId()+"\n"+"EmployeeName        : "+trainer.getEmployeeName()+"\n"+
                                     "EmployeeDesignation : "+trainer.getEmployeeDesignation()+"\n"+"EmployeeMail        : "+trainer.getEmployeeMail()+"\n"+
                                     "EmployeeMobileNumber: "+trainer.getEmployeeMobileNumber()+"\n"+ "CurrentAddress      : "+trainer.getCurrentAddress()+"\n"+
                                     "----------------------------------------------");

                         trainer.getTrainees().forEach(trainee -> logger.info("Associated trainees :"+"\n"+
                                                                 "EmployeeId          : "+trainee.getId()+"\n"+"EmployeeName        : "+trainee.getEmployeeName()));
                         isValidId = false;
                    }
                }
            } catch (InputMismatchException e) {
                logger.error("\nException occured" + e);
            } catch (SQLException e) {
                logger.error("",e);
            }
 
            logger.info("\nif you want read another association\n" + "1.yes\n" + "2.no"); 
            String choiceForReadAnotherInformation = scanner.next();          
            isContinue = !(choiceForReadAnotherInformation.equals("2")); 
        }
    }
}