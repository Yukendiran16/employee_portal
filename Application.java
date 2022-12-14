import java.util.InputMismatchException;

import org.hibernate.HibernateException; 

import java.sql.SQLException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

import com.ideas2it.controller.TrainerController;
import com.ideas2it.controller.TraineeController;


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

public class Application {
    
    public static void main(String[] args) throws InputMismatchException, SQLException, HibernateException {

    
    Logger logger = LoggerFactory.getLogger(Application.class);
       
        logger.info("\n----------------------------------------------"+
                    "\n*************WELCOME TO IDEAS2IT**************"+
                    "\n----------------------------------------------\n\n");
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;
        boolean isValid = true;  
        
        while (isContinue) {
            try {
                logger.info("\nEnter 1 for Trainer realted operation\n"+ 
			    "Enter 2 for Trainee related operation\n" +
                            " Enter 3 for Exit");
                int option = scanner.nextInt();
                
                if (option == 1) {
                    TrainerController.trainerMenu();
                    isContinue = true;
                } else if (option == 2) {
                    TraineeController.traineeMenu();    
                    isContinue = true;
                } else if (option == 3){
                    isContinue = false; 
                    throw new InputMismatchException(" enter valid data");
                }
            } catch (InputMismatchException e) {
                logger.error("\ninvalid data : " + e);
            } catch (HibernateException e) {
                logger.error("\ninvalid data : " + e);
            }
        }
    } 
}

    













 

