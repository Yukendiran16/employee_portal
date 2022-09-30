package com.ideas2it.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

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

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(name = "trainer_trainee", joinColumns = @JoinColumn(name = "trainer_id"),
            inverseJoinColumns = @JoinColumn(name = "trainee_id"))
    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<Trainee> trainees;
    public int getTrainerId() {
        return trainerId;
    }

    public List<Trainee> getTrainees() {
        return trainees;
    }
    public void setTrainees(List<Trainee> trainees) {
        this.trainees = trainees;
    }
}

       
	    