package com.ideas2it.controller;

import com.google.gson.Gson;
import com.ideas2it.Dto.TrainerDto;
import com.ideas2it.exception.EmployeeNotFoundException;
import com.ideas2it.model.Trainer;
import com.ideas2it.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private final EmployeeService employeeService;

    public TrainerServlet(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    private static final Logger logger = LoggerFactory.getLogger(TrainerServlet.class);

    @PostMapping(path = "/trainer",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> addTrainer(@Valid @RequestBody TrainerDto trainerDto) {
        Trainer trainer = new Trainer();
        trainer = trainer.TrainerDtoToTrainer(trainerDto);
        logger.info("trainer object send to database");
        return new ResponseEntity<>(new Gson().toJson("message : " + employeeService.addTrainer(trainer)), HttpStatus.CREATED);
    }

    @GetMapping(path = "/trainers",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Map<String,Object>>> getTrainers() throws EmployeeNotFoundException {
        logger.debug("requested URL is correct. This URL is returns all trainer details");
        List<Trainer> trainers = employeeService.getTrainersData();
        logger.debug("details successfully shown");
        List<Map<String, Object>> trainerList = new ArrayList<>();
        trainers.forEach(trainer -> {
            Map<String,Object> trainer1 = employeeService.getTrainer(trainer);
            trainerList.add(trainer1);});
        return new ResponseEntity<>(trainerList,HttpStatus.OK);
    }

    @GetMapping(path = "/trainer/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String,Object>> displayTrainer(@PathVariable("id") int trainerId) throws EmployeeNotFoundException {
        logger.debug("requested URL is correct. This URL is returns all trainer details");
        Trainer trainer = employeeService.searchTrainerData(trainerId);
        Map<String,Object> trainer1 = employeeService.getTrainer(trainer);
        logger.info("searching successful");
        return new ResponseEntity<>(trainer1, HttpStatus.OK);
    }

    @GetMapping(path = "/trainerDetails/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TrainerDto> ShowFullTrainerDetails(@PathVariable("id") int trainerId) throws EmployeeNotFoundException {
        logger.debug("requested URL is correct. This URL is returns all trainer details");
        Trainer trainer = employeeService.searchTrainerData(trainerId);
        TrainerDto trainerDto = new TrainerDto();
        trainerDto = trainerDto.TrainerToTrainerDto(trainer);
        logger.info("searching successful");
        return new ResponseEntity<>(trainerDto, HttpStatus.OK);
    }

    @PutMapping(value = "/trainer",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> updateTrainer(@RequestBody TrainerDto trainerDto) throws EmployeeNotFoundException {
        logger.debug("requested URL is correct. This URl is update the exists employee profile");
        Trainer trainer = employeeService.searchTrainerData(trainerDto.getTrainerId());
        trainer = trainer.TrainerDtoToTrainer(trainerDto);
        return new ResponseEntity<>(new Gson().toJson("message : " + employeeService.updateTrainerData(trainer.getTrainerId(), trainer)), HttpStatus.OK);
    }

    @DeleteMapping(value = "/trainer/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> deleteTrainer(@PathVariable("id") int trainerId) throws EmployeeNotFoundException {
        logger.debug("requested URL is correct. This URL is returns all trainer details");
        String message;
        Trainer trainer = employeeService.searchTrainerData(trainerId);
        logger.info("searching successful");
        message = employeeService.deleteTrainerData(trainer, trainerId);
        logger.debug(message + "trainerId : " + trainerId);
        return new ResponseEntity<>(new Gson().toJson("message : " + message + "," + "trainerId : " + trainerId), HttpStatus.OK);
    }

    @PutMapping(value = "/assign_trainee/{trainerId}/{traineesId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> assignTrainee(@PathVariable("trainerId") int trainerId,
                                                @PathVariable("traineesId") String traineesId) throws Exception {
        String message;
        logger.debug("requested URL is correct. This URl create association between trainer to trainees");
        logger.info("trainer id searching...in database");
        Trainer trainer = employeeService.searchTrainerData(trainerId);
        logger.info("trainer Id present...");
        logger.info("trainee Id list :" + "[" + traineesId + "]");
        String[] idList = traineesId.split(",");
        trainer = employeeService.updateTraineeListInTrainer(trainer, idList);
        message = employeeService.updateTrainerData(trainer.getTrainerId(), trainer);
        logger.info(message);
        logger.debug("association successful");
        return new ResponseEntity<>(new Gson().toJson("message : " + " association successful"), HttpStatus.OK);
    }

    @DeleteMapping(value = "/un_assign_trainee/{trainerId}/{traineeId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> un_assignTrainee(@PathVariable("trainerId") int trainerId,
                                                   @PathVariable("traineeId") int traineeId) throws EmployeeNotFoundException {
        String message;
        logger.debug("requested URL is correct. This URl create association between trainer to trainees");
        logger.info("trainer id searching...in database");
        Trainer trainer = employeeService.searchTrainerData(trainerId);
        logger.info("trainer Id " + trainerId + "present...");
        logger.info("trainee Id list :" + traineeId);
        trainer = employeeService.deleteTraineeInTrainer(trainer, traineeId);
        message = employeeService.updateTrainerData(trainer.getTrainerId(), trainer);
        logger.info(message);
        logger.debug("Un association successful");
        return new ResponseEntity<>(new Gson().toJson("message : " + " Un association successful"), HttpStatus.OK);
    }
}
