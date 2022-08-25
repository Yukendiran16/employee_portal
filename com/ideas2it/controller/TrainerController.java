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

    public static void trainerMenu() throws InputMismatchException, SQLException, HibernateEXception {
       
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
                            "Enter 7 for Update and assign trainee for trainer\n"+
                            "Enter 8 for Delete association between trainee to trainer\n"+ 
                            "Enter 9 for Exit  \n\n\n"+"----------------------------------------------");
                int userOption = scanner.nextInt();
                switch (userOption) {
                    case 1:	   
     	                createTrainerData();
                        isContinue = true;
                        break;
                    case 2:
                        readTrainerData();
                        isContinue = true;
                        break;
                    case 3:
                        updateTrainerData();
                        isContinue = true;
                        break; 
                    case 4:
                        removeTrainerData();
                        isContinue = true;
                        break;
                    case 5:
                        assignTrainerForTrainees();
                        isContinue = true;
                        break;
                    case 6:
                        readTraineesOfGivenTrainer();
                        isContinue = true;
                        break;
                    case 7:
                        changeAndAssignTrainerForTrainees();
                        isContinue = true; 
                        break;
                    case 8:
                        deleteAssocitionTrainerToTrainee();
                        isContinue = true;
                        break;
                    case 9:
                        isContinue = false;
                        break;
                    default:
                        break;
                }
                isContinue = true; 
                logger.info("\n----------------------------------------------");
                logger.info("***************** THANK YOU ******************");
                logger.info("----------------------------------------------");    
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
                employeeService.addTrainer(trainer);                     
                logger.info("\nTrainer Data Added Successfully");
                logger.info("\n----------------------------------------------");
                isContinue = false;
 
            } catch (InputMismatchException e) {
                logger.error("\ninvalid data" + e);
            } catch (HibernateEXception e) {
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
        String employeeId = "";
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
                        trainers.forEach(trainer -> logger.info("EmployeeId          : "+trainer.getEmployeeId()+"\n"+"EmployeeName        : "+trainer.getEmployeeName()+"\n"+
                                                 	         "EmployeeDesignation : "+trainer.getEmployeeDesignation()+"\n"+"EmployeeMail        : "+trainer.getEmployeeMail()+"\n"+
                                          	                 "EmployeeMobileNumber: "+trainer.getEmployeeMobileNumber()+"\n"+ "CurrentAddress      : "+trainer.getCurrentAddress()+"\n"+
                                                                 "----------------------------------------------"));
                        isContinue = false;                 
                    } 
                } else {
                    logger.info("\nEnter EmployeeId :");
                    boolean isValidId = true;
                    while (isValidId) {
                        employeeId = scanner.next();
                        Trainer trainer = employeeService.searchTrainerData(employeeId);
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
                    }
                } 
            } catch (InputMismatchException e) {
                logger.error("\nException occured" + e);
            } catch (HibernateEXception e) {
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
        String employeeId = "";
        while (isContinue) {
           
            try {
                logger.info("\nEnter EmployeeId :");
                boolean isValidId = true;
                while (isValidId) {
                    employeeId = scanner.next();                
                    Trainer trainer = employeeService.searchTrainerData(employeeId);
                    if (trainer == null) {                           
                        logger.info("\nno data found\n" + "Enter valid Id");
                        isValidId = true; 
                    } else {
                        employeeInformation.getInformationForUpdateTrainer(trainer);
                        employeeService.updateTrainerData(employeeId, trainer);
                        isValidId = false;
                    }
                }
            } catch (InputMismatchException e) {
        	logger.error("\nException occured" + e);
            } catch (HibernateEXception e) {
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
                String employeeId = ""; 
                boolean isValidId = true;      
                while (isValidId) {
           
                    logger.info("\nEnter EmployeeId :");
                    employeeId = scanner.next();
                    employeeService.deleteTrainerData(employeeId);
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
 
        while (isContinue) { 

            try {

                List<String> traineeId = new ArrayList<String>();

                logger.info("Enter trainerId :");
                String trainerId = scanner.next();
                logger.info("Enter no of trainees :");
                int count = scanner.nextInt();
                logger.info("Enter traineeId :");

                for (int i = 0; i < count; i++) {
                    String teId = scanner.next();
                    traineeId.add(teId);
                }
                employeeService.assignTrainerForTrainees(trainerId, traineeId);

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
        String employeeId = "";
        while (isContinue) { 
            
            try {     

               logger.info("\nEnter EmployeeId :");                            
               boolean isValidId = true;
               while (isValidId) {
                     employeeId = scanner.next();
                     Trainer trainer = employeeService.searchTrainerData(employeeId);
                     List<Trainee> trainees = employeeService.readTraineesOfGivenTrainer(employeeId);
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

    /**
     * method used to read employee profile  
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @return {@link void} returns nothing
     */
    public static void changeAndAssignTrainerForTrainees() throws InputMismatchException, SQLException {

        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;
 
        while (isContinue) { 

            try {

                List<String> traineeId = new ArrayList<String>();

                logger.info("Enter trainerId :");
                String trainerId = scanner.next();
                logger.info("Enter no of trainees :");
                int count = scanner.nextInt();
                logger.info("Enter traineeId :");

                for (int i = 0; i < count; i++) {
                    String teId = scanner.next();
                    traineeId.add(teId);
                }
                employeeService.changeAndAssignTraineeForTrainers(trainerId, traineeId);

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
    public static void deleteAssocitionTrainerToTrainee() throws InputMismatchException, SQLException {

        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;
 
        while (isContinue) { 

            try {

                logger.info("Enter trainerId :");
                String trainerId = scanner.next();

                logger.info("Enter traineeId :");
                String traineeId = scanner.next();

                employeeService.deleteAssociationTrainerToTrainee(trainerId, traineeId);

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
}
