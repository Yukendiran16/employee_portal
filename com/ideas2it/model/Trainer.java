package com.ideas2it.model;

import java.util.List;

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
 * <h1> Trainer </h1>
 *
 * The Trainer class is an POJO class and 
 * it extends Employee class
 * The class implements an application that
 * creates an properties of trainer and then
 * using getter and setters for getting and setting properties
 * 
 * @author  Yukendiran K
 * @version 1.0
 * @since   2022-08-04 
 *
 */

@Entity
@Table(name = "trainer_details")
public class Trainer extends Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trainer_id")
    private int trainerId;

    @Column(name = "current_project")
    private String currentProject;

    @Column(name = "achievement")
    private String achievement;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "trainer_id_trainee_id", joinColumns = @JoinColumn(name = "trainer_id"), 
	       	       inverseJoinColumns = @JoinColumn(name = "trainee_id"))
    private List<Trainee> trainees;

    public void setTrainerId(int trainerId) {
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


             

       
	    