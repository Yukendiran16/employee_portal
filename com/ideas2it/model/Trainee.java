/**
* The Trainee class is an POJO class and 
* it extends Employee class
* The class implements an application that
* creates an properties of trainee and then
* using getter and setters for getting and setting properties
*
* @author  Yukendiran K
* @version 1.0
* @since   2022-08-04 
*/



package com.ideas2it.model;

import javax.persistence.*;

@Entity
@Table(name = "trainee_details")
public class Trainee extends Employee {

    @Column(name = "currentTask")
    private String currentTask;

    @Column(name = "currentTechknowledge")
    private String currentTechknowledge;	

    public void setCurrentTask(String currentTask) {

        this.currentTask = currentTask;  
    }

    public String getCurrentTask() {

	return currentTask;
    }
     
    public void setCurrentTechknowledge(String currentTechknowledge) {

        this.currentTechknowledge = currentTechknowledge;  
    }

    public String getCurrentTechknowledge() {

	return currentTechknowledge;
    }
}
     

    
 	   
 	    


             

       
	    