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
import java.util.List;
import java.util.stream.Collectors;

/**
 * <h2> TraineeController </h2>
 * <p>
 * The TraineeController class is an application that
 * do the main operation create, read, update, delete
 * if one will be choose one respect operation will be performs
 * until user exit the operation is same for all operation
 * </p>
 *
 * @author Yukendiran K
 * @version 1.0
 * @since 2022-08-04
 */
@RestController
public class TraineeServlet extends HttpServlet {
    EmployeeService employeeService;

    public TraineeServlet(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    private final EmployeeUtil employeeUtil = new EmployeeUtil();
    private final Logger logger = LoggerFactory.getLogger(TraineeServlet.class);

    @PostMapping(value = "/trainee",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String addTrainee(@RequestBody Trainee trainee) {
        String message;
            logger.info("trainee object send to database");
            message = employeeService.addTrainee(trainee);
            logger.info("message from database : " + message);
        return message;
    }

    @GetMapping(value = "/trainees",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Trainee> getTrainees()  {
        logger.debug("requested URL is correct. This URL is returns all trainee details");
        List<Trainee> trainees = employeeService.getTraineesData();
        if (trainees == null) {
            logger.info("trainee list is empty");
            throw new EmployeeNotFoundException("Employee not found empty List");
        } else {
            logger.debug("details successfully shown");
            return trainees;
        }
    }

    @GetMapping(value = "/trainee/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Trainee getTrainee(@PathVariable("id") String id)  {
        logger.debug("requested URL is correct. This URL is returns all trainee details");
        int traineeId = Integer.parseInt(id);
        Trainee trainee = employeeService.searchTraineeData(traineeId);
        logger.info("searching successful");
        if (trainee == null) {
            logger.info("trainee list is empty" + "y");
            throw new EmployeeNotFoundException("Employee not found id : " + traineeId);
        } else {
            logger.debug("details successfully shown");
            return trainee;
        }
    }

    @PutMapping(value = "/trainee",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String updateTrainee(@RequestBody Trainee trainee)  {
        logger.debug("requested URL is correct. This URl is update the exists employee profile");
        String message = employeeService.updateTraineeData(trainee.getTraineeId(), trainee);
        return message;
    }

    @PatchMapping(value = "/trainee",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String partialUpdateTrainee(@RequestBody Trainee trainee)  {
        logger.debug("requested URL is correct. This URl is update the exists employee profile");
        String message = employeeService.updateTraineeData(trainee.getTraineeId(), trainee);
        return message;
    }

    @PutMapping(value = "/assign_trainer/{traineeId}/{trainersId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String assignTrainee(@PathVariable("traineeId") String traineeId,
                                                @PathVariable("trainersId") String trainersId)  {
        String message = null;
        logger.debug("requested URL is correct. This URl create association between trainee to trainees");
        int id = Integer.parseInt(traineeId);
        logger.info("trainee id searching...in database");
        Trainee trainee = employeeService.searchTraineeData(id);
        if (trainee != null) {
            logger.info("trainee Id present...");
            logger.info("trainer Id list :" + "[" + trainersId + "]");
            String[] idList = trainersId.split(",");
            List<Trainer> trainers = employeeService.getTrainersData();
            logger.info("filtering trainer..... in trainers list");
            for (String s : idList) {
                int trainerId = Integer.parseInt(s);
                List<Trainer> trainer = trainers.stream().filter(filterTrainer ->
                        filterTrainer.getTrainerId() == trainerId).collect(Collectors.toList());
                if (trainer.size() == 0) {
                    logger.info("couldn't found trainerId :" + trainerId + "in list");
                    message = "couldn't found trainer :" + trainerId + "in list";
                    return message;
                } else {
                    logger.info("trainee :" + traineeId + "found in list");
                    trainee.getTrainers().add(trainer.get(0));
                }
            }
            message = employeeService.updateTraineeData(trainee.getTraineeId(), trainee);
            logger.info(message);
            logger.debug("association successful");
            return message + "association successful";
        } else {
            logger.info("couldn't found trainee");
            throw new EmployeeNotFoundException("Employee not found id : " + traineeId);
        }
    }

    @DeleteMapping(value = "/trainee/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String deleteTrainee(@PathVariable("id") String id)  {
        logger.debug("requested URL is correct. This URL is returns all trainee details");
        int traineeId = Integer.parseInt(id);
        String message = null;
        Trainee trainee = employeeService.searchTraineeData(traineeId);
        logger.info("searching successful");
        if (trainee == null) {
            logger.info("trainee list is empty" + "y");
            message = employeeService.deleteTraineeData(traineeId);
            List<Trainer> trainer = trainee.getTrainers();
            if (null != trainer) {
                trainer.removeAll(trainer);
                message = employeeService.updateTraineeData(traineeId, trainee);
            }
            return message;
        } else {
            logger.debug("details successfully shown");
            return message;
        }
    }

    @DeleteMapping(value = "/un_assign_trainer/{trainerId}/{traineeId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String un_assignTrainee(@PathVariable("trainerId") String trainerId,
                                                   @PathVariable("traineeId") String traineeId)  {
        String message = null;
        logger.debug("requested URL is correct. This URl create association between trainee to trainees");
        int id = Integer.parseInt(traineeId);
        logger.info("trainee id searching...in database");
        Trainee trainee = employeeService.searchTraineeData(id);
        logger.info("trainee Id " + id + "present...");
        logger.info("trainee Id list :" + traineeId);
        List<Trainer> trainers = employeeService.getTrainersData();
        logger.info("filtering trainee..... in trainees list");
        List<Trainer> trainer = trainers.stream().filter(filterTrainer ->
                        filterTrainer.getTrainerId() == Integer.parseInt(trainerId))
                .collect(Collectors.toList());
        if (trainer.size() == 0) {
            logger.info("couldn't found traineeId :" + traineeId + "in list");
            message = "couldn't found trainee :" + traineeId + "in list";
            return message;
        } else {
            logger.info("trainee :" + traineeId + "found in list");
            trainee.getTrainers().remove(trainer.get(0));
        }
        message = employeeService.updateTraineeData(trainee.getTraineeId(), trainee);
        logger.info(message);
        logger.debug("un assign successful");
        return message + "un assign successful";
    }
}
