package com.ideas2it.dao;

import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    /*@PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Trainer> findTrainerByIsActive(boolean isActive) {
        List<Trainer> trainers = null;
        try {
            Session session = null;
            Query query = session.createQuery("from trainer e where e.employee_status=?")
                    .setParameter(0, isActive);
            trainers = query.getResultList();
            return trainers;
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public List<Trainee> findTraineeByIsActive(boolean isActive) {
        List<Trainee> trainees = null;
        try {
            Session session = null;
            Query query = session.createQuery("from trainee e where e.employee_status=?")
                    .setParameter(0, isActive);
            trainees = query.getResultList();
            return trainees;
        } catch (NullPointerException e) {
            return null;
        }
    }*/
}
