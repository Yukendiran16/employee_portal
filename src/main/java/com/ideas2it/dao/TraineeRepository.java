package com.ideas2it.dao;

import com.ideas2it.model.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee,Integer> {
    List<Trainee> findByIsActive(boolean isActive);
}
