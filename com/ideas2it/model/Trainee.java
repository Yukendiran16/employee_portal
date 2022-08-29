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

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;



@Entity
@Table(name = "trainee_details")
public class Trainee extends Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trainee_id")
    private int traineeId;

    @Column(name = "currentTask")
    private String currentTask;

    @Column(name = "currentTechknowledge")
    private String currentTechknowledge;

    @ManyToOne(targetEntity=Trainer.class, fetch = FetchType.LAZY)
    private Trainer trainer;

    public void setTraineeId(int traineeId) {

	this.traineeId = traineeId;
    }

    public int getTraineeId() {

	return traineeId;
    }	

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

    public void setTrainer(Trainer trainer) {

	this.trainer = trainer;
    }

    public Trainer getTrainer() {

	return trainer;
    }	

}
     

    
 	   
 	    


             

       
	    