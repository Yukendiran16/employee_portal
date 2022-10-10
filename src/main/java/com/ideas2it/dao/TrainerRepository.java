package com.ideas2it.dao;

import com.ideas2it.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Integer> {
    List<Trainer> findByIsActive(boolean isActive);
}
