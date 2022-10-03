package com.ideas2it.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ideas2it.Dto.TrainerDto;
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
@Table(name = "trainer_details",uniqueConstraints = {
        @UniqueConstraint(columnNames = "mail"),
        @UniqueConstraint(columnNames = "mobile_number"),
        @UniqueConstraint(columnNames = "aadhaar_card_number"),
        @UniqueConstraint(columnNames = "pan_card_number")})
public class Trainer extends Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int trainerId;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name = "trainer_trainee", joinColumns = @JoinColumn(name = "trainers_id"))
    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private Set<Trainee> trainees;

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public Set<Trainee> getTrainees() {
        return trainees;
    }
    public void setTrainees(Set<Trainee> trainees) {
        this.trainees = trainees;
    }

    public Trainer TrainerDtoToTrainer(TrainerDto trainerDto) {
        Trainer trainer = new Trainer();
        trainer.setUuid(trainerDto.getUuid());
        trainer.setEmployeeName(trainerDto.getEmployeeName());
        trainer.setEmployeeDateOfBirth(trainerDto.getEmployeeDateOfBirth());
        trainer.setEmployeeDesignation(trainerDto.getEmployeeDesignation());
        trainer.setEmployeeMail(trainerDto.getEmployeeMail());
        trainer.setEmployeeMobileNumber(trainerDto.getEmployeeMobileNumber());
        trainer.setCurrentAddress(trainerDto.getCurrentAddress());
        trainer.setAadhaarCardNumber(trainerDto.getAadhaarCardNumber());
        trainer.setPanCardNumber(trainerDto.getPanCardNumber());
        trainer.setIsActive(trainerDto.getIsActive());
        trainer.setTrainerId(trainerDto.getTrainerId());
        trainer.setTrainees(trainerDto.getTrainees());
        return trainer;
    }

}


