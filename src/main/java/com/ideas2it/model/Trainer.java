package com.ideas2it.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Set;

/**
 * <h1> Trainer </h1>
 * <p>
 * The Trainer class is an POJO class and
 * it extends Employee class
 * The class implements an application that
 * creates an properties of trainer and then
 * using getter and setters for getting and setting properties
 *
 * @author Yukendiran K
 * @version 1.0
 * @since 2022-08-04
 */

@Entity
@Table(name = "trainer_details")
public class Trainer extends Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int trainerId;

    @Column(name = "current_project")
    private String currentProject;

    @Column(name = "achievement")
    private String achievement;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(name = "trainer_trainee", joinColumns = @JoinColumn(name = "trainer_id"),
            inverseJoinColumns = @JoinColumn(name = "trainee_id"))
    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    @JsonManagedReference
    private Set<Trainee> trainees;
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

    public Set<Trainee> getTrainees() {
        return trainees;
    }
    public void setTrainees(Set<Trainee> trainees) {
        this.trainees = trainees;
    }
}



             

       
	    