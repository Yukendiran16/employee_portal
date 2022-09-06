package com.ideas2it.model;
  
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * <h1> Trainee </h1>
 *
 * The Trainee class is an POJO class and 
 * it extends Employee class
 * The class implements an application that
 * creates an properties of trainee and then
 * using getter and setters for getting and setting properties
 *
 * @author  Yukendiran K
 * @version 1.0
 * @since   2022-08-04 
 *
 */
@Entity
@Table(name = "trainee_details")
public class Trainee extends Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int traineeId;

    @Column(name = "current_task")
    private String currentTask;

    @Column(name = "current_techknowledge")
    private String currentTechknowledge;

    @ManyToMany(/*mappedBy = "trainees"*/targetEntity = Trainer.class, fetch = FetchType.EAGER, 
                cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(name = "trainer_id_trainee_id", joinColumns = @JoinColumn(name = "trainer_id"))
    private Set<Trainer> trainers;

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

    public void setTrainers(Set<Trainer> trainers) {
        this.trainers = trainers;
    }

    public Set<Trainer> getTrainers() {
        return trainers;
    }
}
     

    
 	   
 	    


             

       
	    