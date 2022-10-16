package com.ideas2it.dao;

import com.ideas2it.model.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee,Integer>, EmployeeRepository {

    List<Trainee> findByIsActive(boolean IS_ACTIVE);
    Trainee findTraineeByEmployeeMail(String mail);
    Trainee findTraineeByEmployeeMobileNumber(String mobileNumber);
    Trainee findTraineeByAadhaarCardNumber(String aadhaarNumber);
    Trainee findTraineeByPanCardNumber(String panNumber);
}
