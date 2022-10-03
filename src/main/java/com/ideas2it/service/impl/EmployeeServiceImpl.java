package com.ideas2it.service.impl;

import com.ideas2it.dao.EmployeeDao;
import com.ideas2it.exception.EmployeeNotFoundException;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

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
    public List<Trainer> getTrainersData() throws EmployeeNotFoundException {
        List<Trainer> trainer = employeeDao.retrieveTrainers();
        if (trainer == null) {
            logger.info("trainer list is empty");
            throw new EmployeeNotFoundException("trainer list is empty");
        }
        return trainer;
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
    public Trainer searchTrainerData(int trainerId) throws EmployeeNotFoundException {
        Trainer trainer = employeeDao.retrieveTrainer(trainerId);
        if (trainer == null) {
            logger.info("trainer list is empty" + "y");
            throw new EmployeeNotFoundException("Employee not found id : " + trainerId);
        }
        return trainer;
    }

    /**
     * <h1> getTraineesData </h1>
     * <p>
     * method used to call the dao for get all trainee details
     *
     * @return {@link List<Trainee>} returns trainees Data
     */
    @Override
    public List<Trainee> getTraineesData()throws  EmployeeNotFoundException {
        List<Trainee> trainee = employeeDao.retrieveTrainees();
        if (trainee == null) {
            logger.info("trainee list is empty");
            throw new EmployeeNotFoundException("trainee list is empty");
        }
        return trainee;
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
    public Trainee searchTraineeData(int traineeId) throws EmployeeNotFoundException {
        Trainee trainee = employeeDao.retrieveTrainee(traineeId);
        if (trainee == null) {
            logger.info("Employee not found id : " + traineeId);
            throw new EmployeeNotFoundException("Employee not found id : " + traineeId);
        }
        return trainee;
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
    public String updateTraineeData(int traineeId, Trainee trainee) {
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
    public String deleteTrainerData(Trainer trainer, int trainerId) throws EmployeeNotFoundException {
        if (null == trainer) {
            logger.debug("Employee not found id : " + trainerId);
            throw new EmployeeNotFoundException("Employee not found id : " + trainerId);
        } else {
            Set<Trainee> trainee = trainer.getTrainees();
            if (null != trainee) {
                trainee.removeAll(trainee);
            }
            this.updateTrainerData(trainerId, trainer);
        }
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
    public String deleteTraineeData(Trainee trainee, int traineeId) throws EmployeeNotFoundException {
        if (null == trainee) {
            logger.debug("Employee not found id : " + traineeId);
            throw new EmployeeNotFoundException("Employee not found id : " + traineeId);
        } else {
            Set<Trainer> trainer = trainee.getTrainers();
            if (null != trainer) {
                trainer.removeAll(trainer);
            }
            this.updateTraineeData(traineeId, trainee);
        }
        return employeeDao.removeTrainee(traineeId);
    }

    @Override
    public Trainer updateTraineeListInTrainer(Trainer trainer, String[] idList) throws EmployeeNotFoundException {
        List<Trainee> trainees = this.getTraineesData();
        logger.info("filtering trainee..... in trainees list");
        for (String s : idList) {
            int traineeId = Integer.parseInt(s);
            Set<Trainee> trainee = trainees.stream().filter(filterTrainee ->
                    filterTrainee.getTraineeId() == traineeId).collect(Collectors.toSet());
            if (trainee.size() == 0) {
                logger.info("couldn't found traineeId :" + traineeId + "in list");
                throw new EmployeeNotFoundException("couldn't found trainee :" + traineeId + "in list");
            } else {
                logger.info("trainee :" + traineeId + "found in list");
                trainer.getTrainees().add(trainee.iterator().next());
                logger.info(String.valueOf(trainer.getTrainees().size()));
            }
        }
        return trainer;
    }

    @Override
    public Trainee updateTrainerListInTrainee(Trainee trainee, String[] idList) throws EmployeeNotFoundException {
        List<Trainer> trainers = this.getTrainersData();
        logger.info("filtering trainee..... in trainees list");
        for (String s : idList) {
            int trainerId = Integer.parseInt(s);
            Set<Trainer> trainer = trainers.stream().filter(filterTrainer ->
                    filterTrainer.getTrainerId() == trainerId).collect(Collectors.toSet());
            if (trainer.size() == 0) {
                logger.info("couldn't found trainerId :" + trainerId + "in list");
                throw new EmployeeNotFoundException("couldn't found trainer :" + trainerId + "in list");
            } else {
                logger.info("trainer :" + trainerId + "found in list");
                trainee.getTrainers().add(trainer.iterator().next());
                logger.info(String.valueOf(trainee.getTrainers().size()));
            }
        }
        return trainee;
    }

    @Override
    public Trainer deleteTraineeInTrainer(Trainer trainer, int traineeId)throws EmployeeNotFoundException {
        List<Trainee> trainees = this.getTraineesData();
        logger.info("filtering trainee..... in trainees list");
        Set<Trainee> trainee = trainees.stream().filter(filterTrainee ->
                        filterTrainee.getTraineeId() == traineeId)
                .collect(Collectors.toSet());
        if (trainee.size() == 0) {
            logger.info("couldn't found traineeId :" + traineeId + "in list");
            throw new EmployeeNotFoundException("couldn't found trainee :" + traineeId + "in list");
        } else {
            logger.info("trainee :" + traineeId + "found in list");
            trainer.getTrainees().remove(trainee);
        }
        return trainer;
    }

    @Override
    public Trainee deleteTrainerInTrainee(Trainee trainee, int trainerId) throws EmployeeNotFoundException {
        List<Trainer> trainers = this.getTrainersData();
        logger.info("filtering trainer..... in trainers list");
        Set<Trainer> trainer = trainers.stream().filter(filterTrainer ->
                        filterTrainer.getTrainerId() == trainerId)
                .collect(Collectors.toSet());
        if (trainer.size() == 0) {
            logger.info("couldn't found trainerId :" + trainerId + "in list");
            throw new EmployeeNotFoundException("couldn't found trainee :" + trainerId + "in list");
        } else {
            logger.info("trainee :" + trainerId + "found in list");
            trainee.getTrainers().remove(trainer);
        }
        return trainee;
    }

    public Map<String, Object> getTrainer(Trainer trainer) {
        List<Map<String, Object>> traineeList = new ArrayList<>();
        Set<Trainee> list = trainer.getTrainees();
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
        Set<Trainer> list = trainee.getTrainers();
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


      
 
   