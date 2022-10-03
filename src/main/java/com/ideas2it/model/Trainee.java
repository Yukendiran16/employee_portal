package com.ideas2it.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ideas2it.Dto.TraineeDto;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Set;

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
@Table(name = "trainee_details",uniqueConstraints = {
        @UniqueConstraint(columnNames = "mail"),
        @UniqueConstraint(columnNames = "mobile_number"),
        @UniqueConstraint(columnNames = "aadhaar_card_number"),
        @UniqueConstraint(columnNames = "pan_card_number")})
public class Trainee extends Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int traineeId;

    @ManyToMany(targetEntity = Trainer.class, fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name = "trainer_trainee", joinColumns = @JoinColumn(name = "trainees_id"))
    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private Set<Trainer> trainers;

    public void setTraineeId(int traineeId) {
        this.traineeId = traineeId;
    }
    public int getTraineeId() {
        return traineeId;
    }

    public void setTrainers(Set<Trainer> trainers) {
        this.trainers = trainers;
    }

    public Set<Trainer> getTrainers() {
        return trainers;
    }

    public Trainee TraineeDtoToTrainee(TraineeDto traineeDto) {
        Trainee trainee = new Trainee();
        trainee.setUuid(traineeDto.getUuid());
        trainee.setEmployeeName(traineeDto.getEmployeeName());
        trainee.setEmployeeDateOfBirth(traineeDto.getEmployeeDateOfBirth());
        trainee.setEmployeeDesignation(traineeDto.getEmployeeDesignation());
        trainee.setEmployeeMail(traineeDto.getEmployeeMail());
        trainee.setEmployeeMobileNumber(traineeDto.getEmployeeMobileNumber());
        trainee.setCurrentAddress(traineeDto.getCurrentAddress());
        trainee.setAadhaarCardNumber(traineeDto.getAadhaarCardNumber());
        trainee.setPanCardNumber(traineeDto.getPanCardNumber());
        trainee.setIsActive(traineeDto.getIsActive());
        trainee.setTraineeId(traineeDto.getTraineeId());
        trainee.setTrainers(traineeDto.getTrainers());
        return trainee;
    }
}
     

    
 	   
 	    


             

       
	    