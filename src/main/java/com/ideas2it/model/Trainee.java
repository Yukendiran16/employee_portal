package com.ideas2it.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * <h1> Trainee </h1>
 * <p>
 * The Trainee class is an POJO class and
 * it extends Employee class
 * The class implements an application that
 * creates an properties of trainee and then
 * using getter and setters for getting and setting properties
 *
 * @author Yukendiran K
 * @version 1.0
 * @since 2022-08-04
 */
@Entity
@Table(name = "trainee_details")
public class Trainee extends Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int traineeId;

    @ManyToMany(mappedBy = "trainees", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<Trainer> trainers;

    public int getTraineeId() {
        return traineeId;
    }

    public void setTrainers(List<Trainer> trainers) {
        this.trainers = trainers;
    }

    public List<Trainer> getTrainers() {
        return trainers;
    }
}
     

    
 	   
 	    


             

       
	    