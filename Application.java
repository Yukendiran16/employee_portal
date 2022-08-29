import java.util.Scanner;
import java.util.InputMismatchException;

import java.sql.SQLException;

import org.hibernate.HibernateException; 

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
    
    public static void main(String[] args)  {

        Logger logger = LoggerFactory.getLogger(Application.class);
       
        logger.info("\n----------------------------------------------"+
                    "\n*************WELCOME TO IDEAS2IT**************"+
                    "\n----------------------------------------------\n\n");
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true; 
        
        while (isContinue) {

            logger.info("\nEnter 1 for Trainer realted operation\n"+ 
		        "Enter 2 for Trainee related operation\n" +
                        "Enter 3 for Exit");
            try {

                String option = scanner.next();
                switch (option){

                    case "1":
                        TrainerController.trainerMenu();
                        break;
                    case "2":    
                        TraineeController.traineeMenu();
                        break;   
                    case "3":
                        isContinue = false;
                        break; 
                    default :
                        break;
                }

            } catch (InputMismatchException e) {

                logger.error("\ninvalid data : " + e);
            }
        }
    } 
}

    













 

