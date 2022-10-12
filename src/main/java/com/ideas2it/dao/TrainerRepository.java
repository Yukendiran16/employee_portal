package com.ideas2it.dao;

import com.ideas2it.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Integer>,EmployeeRepository {

    List<Trainer> findByIsActive(boolean IS_ACTIVE);
    Trainer findTrainerByEmployeeMail(String mail);
    Trainer findTrainerByEmployeeMobileNumber(String mobileNumber);
    Trainer findTrainerByAadhaarCardNumber(String aadhaarNumber);
    Trainer findTrainerByPanCardNumber(String panNumber);

}
