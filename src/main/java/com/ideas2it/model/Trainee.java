package com.ideas2it.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
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
@Getter
@Setter
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE table trainee_details SET employee_status = true WHERE id=?")
@Where(clause = "employee_status = 0")
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

    @ManyToMany(mappedBy = "trainees", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private Set<Trainer> trainers = new HashSet<>();

}
     

    
 	   
 	    


             

       
	    