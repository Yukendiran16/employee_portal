package com.ideas2it.service.impl;

import com.ideas2it.Dto.TraineeRequestDto;
import com.ideas2it.Dto.TrainerRequestDto;
import com.ideas2it.dao.TraineeRepository;
import com.ideas2it.dao.TrainerRepository;
import com.ideas2it.exception.AlreadyAssignedException;
import com.ideas2it.exception.EmployeeNotFoundException;
import com.ideas2it.mapper.TraineeMapper;
import com.ideas2it.mapper.TrainerMapper;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ideas2it.util.Constant.IS_ACTIVE;

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

    @Autowired
    private TrainerRepository trainerRepository;
    @Autowired
    private TraineeRepository traineeRepository;

    @Autowired(required = false)
    private TraineeMapper traineeMapper;

    @Autowired(required = false)
    private TrainerMapper trainerMapper;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    /**
     * <h1> addTrainer </h1>
     * <p>
     * method used to get trainer details from controller to pass the details to dao
     *
     * @param trainerDto object
     * @return status of operation
     */
    @Override
    public Trainer addTrainer(TrainerRequestDto trainerDto) {
        Trainer trainer;
        trainer = trainerMapper.TrainerDtoToTrainer(trainerDto);
        return trainerRepository.save(trainer);
    }

    /**
     * <h1> addTrainee </h1>
     * <p>
     * method used to get trainee details from controller to pass the details to dao
     *
     * @param traineeDto object
     * @return status of operation
     */
    @Override
    public Trainee addTrainee(TraineeRequestDto traineeDto) {
        Trainee trainee;
        trainee = traineeMapper.TraineeDtoToTrainee(traineeDto);
        return traineeRepository.save(trainee);
    }

    /**
     * <h1> getTrainersData </h1>
     * <p>
     * method used to call the dao for get all trainer details
     *
     * @return list of trainers Data
     */
    @Override
    public List<Trainer> getTrainersData() {
        return trainerRepository.findByIsActive(IS_ACTIVE);
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
    public Trainer searchTrainerData(int trainerId) {
        return trainerRepository.findById(trainerId).orElseThrow(()
                -> new EmployeeNotFoundException("Trainer not found "));
    }

    /**
     * <h1> getTraineesData </h1>
     * <p>
     * method used to call the dao for get all trainee details
     *
     * @return {@link List<Trainee>} returns trainees Data
     */
    @Override
    public List<Trainee> getTraineesData() {
        return traineeRepository.findByIsActive(IS_ACTIVE);
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
    public Trainee searchTraineeData(int traineeId) {
        return traineeRepository.findById(traineeId).orElseThrow(()
                -> new EmployeeNotFoundException("Trainee not found "));
    }

    /**
     * <h1> updateTrainerData </h1>
     * <p>
     * method used to get updated trainer details from controller to pass the details to dao
     *
     * @param trainerId for
     * @param trainer   object
     * @return status of operation
     */
    @Override
    public Trainer updateTrainerData(int trainerId, Trainer trainer) {
        Trainer updateTrainer = trainerRepository.findById(trainerId).orElseThrow(()
                -> new EmployeeNotFoundException("Trainer not found "));
        return trainerRepository.save(trainer);
    }

    /**
     * <h1> updateTraineeData </h1>
     * <p>
     * method used to get updated trainee details from controller to pass the details to dao
     *
     * @param traineeId for
     * @param trainee   object
     * @return status of operation
     */
    @Override
    public Trainee updateTraineeData(int traineeId, Trainee trainee) {
        Trainee updateTrainee = traineeRepository.findById(traineeId).orElseThrow(()
                -> new EmployeeNotFoundException("Trainee not found"));
        return traineeRepository.save(trainee);
    }

    /**
     * <h1> deleteTrainerData </h1>
     * <p>
     * method used to get employeeId from controller to pass the employeeId to dao
     *
     * @param trainerId for
     */
    @Override
    public void deleteTrainerData(Trainer trainer, int trainerId) {
        Trainer trainer1 = trainerRepository.findById(trainerId).orElseThrow(()
                -> new EmployeeNotFoundException("trainer not found "));
        Set<Trainee> trainee = trainer.getTrainees();
        trainee.removeAll(trainee);
        this.updateTrainerData(trainerId, trainer);
        trainer1.setIsActive(true);
        trainerRepository.save(trainer1);
    }

    /**
     * <h1> deleteTraineeData </h1>
     * <p>
     * method used to get employeeId from controller to pass the employeeId to dao for deleting trainer details
     *
     * @param traineeId for
     */
    @Override
    public void deleteTraineeData(Trainee trainee, int traineeId) {
        Trainee trainee1 = traineeRepository.findById(traineeId).orElseThrow(()
                -> new EmployeeNotFoundException("Trainee not found "));
        Set<Trainer> trainer = trainee.getTrainers();
        trainer.removeAll(trainer);
        this.updateTraineeData(traineeId, trainee);
        trainee1.setIsActive(true);
        traineeRepository.save(trainee1);
    }

    @Override
    public Trainer updateTraineeListInTrainer(Trainer trainer, int traineeId) {
        List<Trainee> trainees = this.getTraineesData();
        logger.info("filtering trainee..... in trainees list");
        List<Trainee> trainee = trainees.stream().filter(filterTrainee ->
                filterTrainee.getTraineeId() == traineeId).collect(Collectors.toList());
        if (trainee.size() == 0) {
            logger.info("couldn't found traineeId :" + traineeId + "in list");
            throw new EmployeeNotFoundException("couldn't found trainee :" + traineeId + "in list ");
        } else {
            for (Trainee trainee1 : trainer.getTrainees()) {
                if (trainee1.getTraineeId() == traineeId) {
                    logger.info("trainee id already exists");
                    throw new AlreadyAssignedException("trainee Id already exists ");
                }
            }
        }
        logger.info("trainee :" + traineeId + "found in list");
        trainer.getTrainees().add(trainee.get(0));
        return trainer;
    }

    @Override
    public Trainee updateTrainerListInTrainee(Trainee trainee, int trainerId) {
        List<Trainer> trainers = this.getTrainersData();
        logger.info("filtering trainee..... in trainees list");
        List<Trainer> trainer = trainers.stream().filter(filterTrainer ->
                filterTrainer.getTrainerId() == trainerId).collect(Collectors.toList());
        if (trainer.size() == 0) {
            logger.info("couldn't found trainerId :" + trainerId + "in list");
            throw new EmployeeNotFoundException("couldn't found trainer :" + trainerId + "in list ");
        } else {
            for (Trainer trainer1 : trainee.getTrainers()) {
                if (trainer1.getTrainerId() == trainerId) {
                    logger.info("trainer id already exists");
                    throw new AlreadyAssignedException("trainer Id already exists ");
                }
            }
            logger.info("trainer :" + trainerId + "found in list");
            trainee.getTrainers().add(trainer.get(0));
        }
        return trainee;
    }

    @Override
    public Trainer deleteTraineeInTrainer(Trainer trainer, int traineeId) {
        Set<Trainee> trainees = trainer.getTrainees();
        logger.info("filtering trainee..... in trainees list");
        if (null != trainees) {
            List<Trainee> trainee = trainees.stream().filter(filterTrainee ->
                            filterTrainee.getTraineeId() == traineeId)
                    .collect(Collectors.toList());
            if (trainee.size() == 0) {
                logger.info("couldn't found traineeId :" + traineeId + "in list");
                throw new EmployeeNotFoundException("couldn't found trainee :" + traineeId + "in list ");
            } else {
                logger.info("trainee :" + traineeId + "found in list");
                trainees.remove(trainee.get(0));
            }
        } else {
            throw new EmployeeNotFoundException("empty list ");
        }
        return trainer;
    }

    @Override
    public Trainee deleteTrainerInTrainee(Trainee trainee, int trainerId) {
        Set<Trainer> trainers = trainee.getTrainers();
        logger.info("filtering trainer..... in trainers list");
        if (null != trainers) {
            List<Trainer> trainer = trainers.stream().filter(filterTrainer ->
                            filterTrainer.getTrainerId() == trainerId)
                    .collect(Collectors.toList());
            if (trainer.size() == 0) {
                logger.info("couldn't found trainerId :" + trainerId + "in list");
                throw new EmployeeNotFoundException("couldn't found trainee :" + trainerId + "in list ");
            } else {
                logger.info("trainee :" + trainerId + "found in list");
                trainers.remove(trainer.get(0));
            }
        } else {
            throw new EmployeeNotFoundException("empty list ");
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


      
 
   