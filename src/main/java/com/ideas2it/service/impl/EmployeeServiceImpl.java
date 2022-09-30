package com.ideas2it.service.impl;

import com.ideas2it.dao.EmployeeDao;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <h2>EmployeeServiceImpl</h2>
 * <p>
 * The EmployeeServiceImpl class is implements EmployeeService and
 * The class implements an application that
 * defines all methods used in EmployeeController class
 * method perform pass the parameters to DAO class
 * and return data's to controller from Dao
 * </p>
 *
 * @author Yukendiran K
 * @version 1.0
 * @since 2022-08-04
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    EmployeeDao employeeDao;

    public EmployeeServiceImpl(EmployeeDao employeedao) {
        this.employeeDao = employeedao;
    }

    /**
     * <h1> addTrainer </h1>
     * <p>
     * method used to get trainer details from controller to pass the details to dao
     *
     * @param trainer object
     * @return status of operation
     */
    @Override
    public String addTrainer(Trainer trainer)  {
        return employeeDao.insertTrainer(trainer);
    }

    /**
     * <h1> addTrainee </h1>
     * <p>
     * method used to get trainee details from controller to pass the details to dao
     *
     * @param trainee object
     * @return status of operation
     */
    @Override
    public String addTrainee(Trainee trainee)  {
        return employeeDao.insertTrainee(trainee);
    }

    /**
     * <h1> getTrainersData </h1>
     * <p>
     * method used to call the dao for get all trainer details
     *
     * @return list of trainers Data
     */
    @Override
    public List<Trainer> getTrainersData()  {
        return employeeDao.retrieveTrainers();
    }

    /**
     * <h1> searchTrainerData </h1>
     * <p>
     * method used to get employeeId from controller to pass the employeeId to dao
     *
     * @param trainerId for
     * @return searched trainer Data
     */
    @Override
    public Trainer searchTrainerData(int trainerId)  {

        return employeeDao.retrieveTrainer(trainerId);
    }

    /**
     * <h1> getTraineesData </h1>
     * <p>
     * method used to call the dao for get all trainee details
     *
     * @return {@link List<Trainee>} returns trainees Data
     */
    @Override
    public List<Trainee> getTraineesData()  {
        return employeeDao.retrieveTrainees();
    }

    /**
     * <h1> searchTraineeData </h1>
     * <p>
     * method used to get employeeId from controller to pass the employeeId to dao
     *
     * @param traineeId for
     * @return searched trainee Data
     */
    @Override
    public Trainee searchTraineeData(int traineeId)  {
        return employeeDao.retrieveTrainee(traineeId);
    }

    /**
     * <h1> updateTrainerData </h1>
     * <p>
     * method used to get updated trainer details from controller to pass the details to dao
     *
     * @param trainerId for
     * @param trainer object
     * @return status of operation
     */
    @Override
    public String updateTrainerData(int trainerId, Trainer trainer)  {
        return employeeDao.updateTrainer(trainerId, trainer);
    }

    /**
     * <h1> updateTraineeData </h1>
     * <p>
     * method used to get updated trainee details from controller to pass the details to dao
     *
     * @param traineeId for
     * @param trainee object
     * @return status of operation
     */
    @Override
    public String updateTraineeData(int traineeId, Trainee trainee)  {
        return employeeDao.updateTrainee(traineeId, trainee);
    }

    /**
     * <h1> deleteTrainerData </h1>
     * <p>
     * method used to get employeeId from controller to pass the employeeId to dao
     *
     * @param trainerId for
     * @return status of operation
     */
    @Override
    public String deleteTrainerData(int trainerId)  {
        return employeeDao.removeTrainer(trainerId);
    }

    /**
     * <h1> deleteTraineeData </h1>
     * <p>
     * method used to get employeeId from controller to pass the employeeId to dao for deleting trainer details
     *
     * @param traineeId for
     * @return {@link String} returns status of operation
     */
    @Override
    public String deleteTraineeData(int traineeId)  {
        return employeeDao.removeTrainee(traineeId);
    }

    @Override
    public Map<String, Object> getTrainer(Trainer trainer) {
        List<Map<String, Object>> traineeList = new ArrayList<>();
        List<Trainee> list = trainer.getTrainees();
        for (Trainee trainee : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("traineeId", trainee.getTraineeId());
            map.put("employeeName", trainee.getEmployeeName());
            traineeList.add(map);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("trainerId", trainer.getTrainerId());
        map.put("employeeName", trainer.getEmployeeName());
        map.put("companyName", trainer.getCompanyName());
        map.put("employeeDesignation", trainer.getEmployeeDesignation());
        map.put("employeeMail", trainer.getEmployeeMail());
        map.put("currentAddress", trainer.getCurrentAddress());
        map.put("trainees", traineeList);
        return map;
    }

    @Override
    public Map<String, Object> getTrainee(Trainee trainee) {
        List<Map<String, Object>> trainerList = new ArrayList<>();
        List<Trainer> list = trainee.getTrainers();
        for (Trainer trainer : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("trainerId", trainer.getTrainerId());
            map.put("employeeName", trainer.getEmployeeName());
            trainerList.add(map);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("traineeId", trainee.getTraineeId());
        map.put("employeeName", trainee.getEmployeeName());
        map.put("companyName", trainee.getCompanyName());
        map.put("employeeDesignation", trainee.getEmployeeDesignation());
        map.put("employeeMail", trainee.getEmployeeMail());
        map.put("currentAddress", trainee.getCurrentAddress());
        map.put("trainees", trainerList);
        return map;
    }
}  


      
 
   