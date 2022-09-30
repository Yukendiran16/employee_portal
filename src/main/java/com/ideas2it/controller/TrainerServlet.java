package com.ideas2it.controller;

import com.ideas2it.exception.EmployeeNotFoundException;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.service.EmployeeService;
import com.ideas2it.util.EmployeeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <h2> TrainerController </h2>
 * <p>
 * The TrainerController class is an application that
 * do the main operation create, read, update, delete
 * if one will be choose the respect operation performs
 * until user exit the operation is same for all operation
 * </p>
 *
 * @author Yukendiran K
 * @version 1.0
 * @since 2022-08-04
 */
@RestController
public class TrainerServlet extends HttpServlet {

    EmployeeService employeeService;

    public TrainerServlet(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    private final EmployeeUtil employeeUtil = new EmployeeUtil();
    private final Logger logger = LoggerFactory.getLogger(TrainerServlet.class);

    @PostMapping(path = "/trainer",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String addTrainer(@Valid @RequestBody Trainer trainer) {
        String message;
        logger.info("trainer object send to database");
        message = employeeService.addTrainer(trainer);
        logger.info("message from database : " + message);
        return message;
    }

    @GetMapping(path = "/trainers",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Trainer> getTrainers() throws EmployeeNotFoundException {
        logger.debug("requested URL is correct. This URL is returns all trainer details");
        List<Trainer> trainers = employeeService.getTrainersData();
        if (trainers == null) {
            logger.info("trainer list is empty");
            throw new EmployeeNotFoundException("Employee not found empty List");
        } else {
            logger.debug("details successfully shown");
            return trainers;
        }
    }

    @GetMapping(path = "/trainer/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> getTrainer(@PathVariable("id") String id) throws EmployeeNotFoundException {
        logger.debug("requested URL is correct. This URL is returns all trainer details");
        int trainerId = Integer.parseInt(id);
        Trainer trainer = employeeService.searchTrainerData(trainerId);
        logger.info("searching successful");
        if (trainer == null) {
            logger.info("trainer list is empty" + "y");
            throw new EmployeeNotFoundException("Employee not found id : " + trainerId);
        }
        logger.debug("details successfully shown");
        Map<String, Object> trainer1 = employeeService.getTrainer(trainer);
        return trainer1;
    }

    @PutMapping(value = "/trainer",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String updateTrainer(@RequestBody Trainer trainer) {
        logger.debug("requested URL is correct. This URl is update the exists employee profile");
        String message = employeeService.updateTrainerData(trainer.getTrainerId(), trainer);
        return message;
    }

    @PatchMapping(value = "/trainer",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String partialUpdateTrainer(@RequestBody Trainer trainer) {
        logger.debug("requested URL is correct. This URl is update the exists employee profile");
        String message = employeeService.updateTrainerData(trainer.getTrainerId(), trainer);
        return message;
    }

    @DeleteMapping(value = "/trainer/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String deleteTrainer(@PathVariable("id") String id) {
        logger.debug("requested URL is correct. This URL is returns all trainer details");
        int trainerId = Integer.parseInt(id);
        String message = null;
        Trainer trainer = employeeService.searchTrainerData(trainerId);
        logger.info("searching successful");
        if (null != trainer) {
            logger.info("trainer list is empty" + "y");
            message = employeeService.deleteTrainerData(trainerId);
            List<Trainee> trainee = trainer.getTrainees();
            if (null != trainee) {
                trainee.removeAll(trainee);
                message = employeeService.updateTrainerData(trainerId, trainer);
            }
            logger.debug("details successfully shown");
            return message + "trainerId : " + trainerId;
        } else {
            logger.debug("searched ");
            return "successfully deleted" + "trainerId : " + trainerId;
        }
    }

    @PutMapping(value = "/assign_trainee/{trainerId}/{traineesId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String assignTrainee(@PathVariable("trainerId") String trainerId,
                                @PathVariable("traineesId") String traineesId) {
        String message;
        logger.debug("requested URL is correct. This URl create association between trainer to trainees");
        int id = Integer.parseInt(trainerId);
        logger.info("trainer id searching...in database");
        Trainer trainer = employeeService.searchTrainerData(id);
        logger.info("trainer Id present...");
        logger.info("trainee Id list :" + "[" + traineesId + "]");
        String[] idList = traineesId.split(",");
        List<Trainee> trainees = employeeService.getTraineesData();
        logger.info("filtering trainee..... in trainees list");
        for (String s : idList) {
            int traineeId = Integer.parseInt(s);
            List<Trainee> trainee = trainees.stream().filter(filterTrainee ->
                    filterTrainee.getTraineeId() == traineeId).collect(Collectors.toList());
            if (trainee.size() == 0) {
                logger.info("couldn't found traineeId :" + traineeId + "in list");
                message = "couldn't found trainee :" + traineeId + "in list";
                return message;
            } else {
                logger.info("trainee :" + traineeId + "found in list");
                trainer.getTrainees().add(trainee.get(0));
                logger.info(String.valueOf(trainer.getTrainees().size()));
            }
        }
        message = employeeService.updateTrainerData(trainer.getTrainerId(), trainer);
        logger.info(message);
        logger.debug("association successful");
        return message + " association successful";
    }

    @DeleteMapping(value = "/un_assign_trainee/{trainerId}/{traineeId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String un_assignTrainee(@PathVariable("trainerId") String trainerId,
                                   @PathVariable("traineeId") String traineeId) {
        String message = null;
        logger.debug("requested URL is correct. This URl create association between trainer to trainees");
        int id = Integer.parseInt(trainerId);
        logger.info("trainer id searching...in database");
        Trainer trainer = employeeService.searchTrainerData(id);
        logger.info("trainer Id " + id + "present...");
        logger.info("trainee Id list :" + traineeId);
        List<Trainee> trainees = employeeService.getTraineesData();
        logger.info("filtering trainee..... in trainees list");
        Set<Trainee> trainee = trainees.stream().filter(filterTrainee ->
                        filterTrainee.getTraineeId() == Integer.parseInt(traineeId))
                .collect(Collectors.toSet());
        List<Trainee> traineeList = new ArrayList<>(trainee);
        if (trainee.size() == 0) {
            logger.info("couldn't found traineeId :" + traineeId + "in list");
            message = "couldn't found trainee :" + traineeId + "in list";
            return message;
        } else {
            logger.info("trainee :" + traineeId + "found in list");
            trainer.getTrainees().remove(traineeList.get(0));
        }
        message = employeeService.updateTrainerData(trainer.getTrainerId(), trainer);
        logger.info(message);
        logger.debug("association successful");
        return message + "association successful";
    }
}
