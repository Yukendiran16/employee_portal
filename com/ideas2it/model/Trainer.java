/**
* The Trainer class is an POJO class and 
* it extends Employee class
* The class implements an application that
* creates an properties of trainer and then
* using getter and setters for getting and setting properties
*
* @author  Yukendiran K
* @version 1.0
* @since   2022-08-04 
*/



package com.ideas2it.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "trainer_details")
public class Trainer extends Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trainer_id")
    private int trainerId;

    @Column(name = "currentProject")
    private String currentProject;

    @Column(name = "achievement")
    private String achievement;

    @OneToMany(targetEntity = Trainee.class, cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER) 
    @JoinColumn(name = "trainer_id")   
    private List<Trainee> trainees;

    public void setTrainerId(int trainerid) {

	this.trainerId = trainerId;
    }

    public int getTrainerId() {

	return trainerId;
    }	

    public void setCurrentProject(String currentProject) {

        this.currentProject = currentProject;  
    }

    public String getCurrentProject() {

	return currentProject;
    }
     
    public void setAchievement(String achievement) {

        this.achievement = achievement;  
    }

    public String getAchievement() {

	return achievement;
    }

    public void setTrainees(List<Trainee> trainees) {
    
        this.trainees = trainees; 
    }
  
    public List<Trainee> getTrainees() {
  
        return trainees;
    }
}


             

       
	    