package com.ideas2it.service.impl;

import com.ideas2it.Dto.TraineeRequestDto;
import com.ideas2it.Dto.TrainerRequestDto;
import com.ideas2it.Dto.TrainerResponseDto;
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

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
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

    @Override
    public void findExistingTrainer(String mail, String aadhaarNumber, String mobileNumber, String panNumber) throws SQLIntegrityConstraintViolationException {
        if (null != trainerRepository.findTrainerByEmployeeMail(mail)) {
            throw new SQLIntegrityConstraintViolationException("Duplicate entry *** " + mail + " *** for key 'trainer_details.mail'");
        }
        if (null != trainerRepository.findTrainerByEmployeeMobileNumber(aadhaarNumber)) {
            throw new SQLIntegrityConstraintViolationException("Duplicate entry *** " + aadhaarNumber + " *** for key 'trainer_details.employeeMobileNumber'");
        }
        if (null != trainerRepository.findTrainerByAadhaarCardNumber(mobileNumber)) {
            throw new SQLIntegrityConstraintViolationException("Duplicate entry *** " + mobileNumber + " *** for key 'trainer_details.aadhaarCardNumber'");
        }
        if (null != trainerRepository.findTrainerByPanCardNumber(panNumber)) {
            throw new SQLIntegrityConstraintViolationException("Duplicate entry *** " + panNumber + " *** for key 'trainer_details.panCardNumber'");
        }
    }

    @Override
    public void findExistingTrainee(String mail, String aadhaarNumber, String mobileNumber, String panNumber) throws SQLIntegrityConstraintViolationException {
        if (null != traineeRepository.findTraineeByEmployeeMail(mail)) {
            throw new SQLIntegrityConstraintViolationException("Duplicate entry *** " + mail + " *** for key 'trainee_details.mail'");
        }
        if (null != traineeRepository.findTraineeByEmployeeMobileNumber(aadhaarNumber)) {
            throw new SQLIntegrityConstraintViolationException("Duplicate entry *** " + aadhaarNumber + " *** for key 'trainer_details.employeeMobileNumber'");
        }
        if (null != traineeRepository.findTraineeByAadhaarCardNumber(mobileNumber)) {
            throw new SQLIntegrityConstraintViolationException("Duplicate entry *** " + mobileNumber + " *** for key 'trainer_details.aadhaarCardNumber'");
        }
        if (null != traineeRepository.findTraineeByPanCardNumber(panNumber)) {
            throw new SQLIntegrityConstraintViolationException("Duplicate entry *** " + panNumber + " *** for key 'trainer_details.panCardNumber'");
        }
    }

    /**
     * <h1> addTrainer </h1>
     * <p>
     * method used to get trainer details from controller to pass the details to dao
     *
     * @param trainerDto object
     * @return status of operation
     */
    @Override
    public TrainerResponseDto addTrainer(TrainerRequestDto trainerDto) {
        Trainer trainer;
        trainer = trainerMapper.trainerRequestDtoToTrainer(trainerDto);
        return trainerMapper.trainerToTrainerResponseDto(trainerRepository.save(trainer));
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
        trainee = traineeMapper.traineeRequestDtoToTrainee(traineeDto);
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
        //return trainerRepository.findTrainerByIsActive(IS_ACTIVE);
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
        //return traineeRepository.findTraineeByIsActive(IS_ACTIVE);
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
     * @param trainer   object
     * @return status of operation
     */
    @Override
    public Trainer updateTrainerData(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    /**
     * <h1> updateTraineeData </h1>
     * <p>
     * method used to get updated trainee details from controller to pass the details to dao
     *
     * @param trainee   object
     * @return status of operation
     */
    @Override
    public Trainee updateTraineeData(Trainee trainee) {
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
        this.updateTrainerData(trainer);
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
        this.updateTraineeData(trainee);
        trainee1.setIsActive(true);
        traineeRepository.save(trainee1);
    }

    @Override
    public Trainer updateTraineeListInTrainer(Trainer trainer, List<Integer> traineesId) {
        List<Trainee> trainees = this.getTraineesData();
        logger.info("filtering trainee..... in trainees list");
        if (null != trainees) {
            for (Integer traineeId : traineesId) {
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
            }
        } else {
            throw new EmployeeNotFoundException("empty list ");
        }
        return trainer;
    }

    @Override
    public Trainee updateTrainerListInTrainee(Trainee trainee, List<Integer> trainersId) {
        List<Trainer> trainers = this.getTrainersData();
        logger.info("filtering trainee..... in trainees list");
        if (null != trainers) {
            for (Integer trainerId : trainersId) {
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
                }
                logger.info("trainer :" + trainerId + "found in list");
                trainee.getTrainers().add(trainer.get(0));
            }
        } else {
            throw new EmployeeNotFoundException("empty list ");
        }
        return trainee;
    }

    @Override
    public Trainer deleteTraineeInTrainer(Trainer trainer, List<Integer> traineesId) {
        Set<Trainee> trainees = trainer.getTrainees();
        logger.info("filtering trainee..... in trainees list");
        if (null != trainees) {
            for (Integer traineeId : traineesId) {

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
            }
        } else {
            throw new EmployeeNotFoundException("empty list ");
        }
        return trainer;
    }

    @Override
    public Trainee deleteTrainerInTrainee(Trainee trainee, List<Integer> trainersId) {
        Set<Trainer> trainers = trainee.getTrainers();
        logger.info("filtering trainer..... in trainers list");
        if (null != trainers) {
            for (Integer trainerId : trainersId) {

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
            }
        } else {
            throw new EmployeeNotFoundException("empty list ");
        }
        return trainee;
    }
}  


      
 
   