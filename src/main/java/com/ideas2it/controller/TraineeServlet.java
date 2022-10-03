package com.ideas2it.controller;

import com.ideas2it.Dto.TraineeDto;
import com.ideas2it.exception.EmployeeNotFoundException;
import com.ideas2it.model.Trainee;
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
 * <h2> TraineeController </h2>
 * <p>
 * The TraineeController class is an application that
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
public class TraineeServlet extends HttpServlet {

    private final EmployeeService employeeService;

    public TraineeServlet(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    private static final Logger logger = LoggerFactory.getLogger(TraineeServlet.class);

    @PostMapping(path = "/trainee",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> addTrainee(@Valid @RequestBody TraineeDto traineeDto) {
        logger.info("trainee object send to database");
        Trainee trainee = new Trainee();
        trainee = trainee.TraineeDtoToTrainee(traineeDto);
        return new ResponseEntity<>(employeeService.addTrainee(trainee), HttpStatus.CREATED);
    }

    @GetMapping(path = "/trainees",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Map<String,Object>>> displayTrainees() throws EmployeeNotFoundException {
        logger.debug("requested URL is correct. This URL is returns all trainee details");
        List<Trainee> trainees = employeeService.getTraineesData();
        logger.debug("details successfully shown");
        List<Map<String,Object>> traineeList = new ArrayList<>();
        trainees.forEach(trainee -> {
            Map<String,Object> trainee1 = employeeService.getTrainee(trainee);
            traineeList.add(trainee1);});
        return new ResponseEntity<>(traineeList, HttpStatus.OK);
    }

    @GetMapping(path = "/trainee/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String,Object>> displayTrainee(@PathVariable("id") int traineeId) throws EmployeeNotFoundException {
        logger.debug("requested URL is correct. This URL is returns all trainee details");
        Trainee trainee = employeeService.searchTraineeData(traineeId);
        Map<String,Object> trainee1 = employeeService.getTrainee(trainee);
        logger.info("searching successful");
        return new ResponseEntity<>(trainee1, HttpStatus.OK);
    }

    @GetMapping(path = "/traineeDetails/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TraineeDto> showFullTraineeDetails(@PathVariable("id") int traineeId) throws EmployeeNotFoundException {
        logger.debug("requested URL is correct. This URL is returns all trainee details");
        Trainee trainee = employeeService.searchTraineeData(traineeId);
        TraineeDto traineeDto = new TraineeDto();
        traineeDto = traineeDto.TraineeToTraineeDto(trainee);
        logger.info("searching successful");
        return new ResponseEntity<>(traineeDto, HttpStatus.OK);
    }

    @PutMapping(value = "/trainee",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> updateTrainee(@RequestBody TraineeDto traineeDto) throws EmployeeNotFoundException {
        logger.debug("requested URL is correct. This URl is update the exists employee profile");
        Trainee trainee = employeeService.searchTraineeData(traineeDto.getTraineeId());
        trainee = trainee.TraineeDtoToTrainee(traineeDto);
        return new ResponseEntity<>(employeeService.updateTraineeData(trainee.getTraineeId(), trainee), HttpStatus.OK);
    }

    @DeleteMapping(value = "/trainee/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> deleteTrainee(@PathVariable("id") int traineeId) throws EmployeeNotFoundException {
        logger.debug("requested URL is correct. This URL is returns all trainee details");
        String message;
        Trainee trainee = employeeService.searchTraineeData(traineeId);
        logger.info("searching successful");
        message = employeeService.deleteTraineeData(trainee, traineeId);
        logger.debug(message + "traineeId : " + traineeId);
        return new ResponseEntity<>(message + "traineeId : " + traineeId, HttpStatus.OK);
    }

    @PutMapping(value = "/assign_trainer/{traineeId}/{trainersId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> assignTrainee(@PathVariable("traineeId") int traineeId,
                                                @PathVariable("trainersId") String trainersId) throws EmployeeNotFoundException {
        String message;
        logger.debug("requested URL is correct. This URl create association between trainee to trainees");
        logger.info("trainee id searching...in database");
        Trainee trainee = employeeService.searchTraineeData(traineeId);
        logger.info("trainee Id present...");
        logger.info("trainee Id list :" + "[" + trainersId + "]");
        String[] idList = trainersId.split(",");
        trainee = employeeService.updateTrainerListInTrainee(trainee, idList);
        message = employeeService.updateTraineeData(trainee.getTraineeId(), trainee);
        logger.info(message);
        logger.debug("association successful");
        return new ResponseEntity<>(message + " association successful", HttpStatus.OK);
    }

    @DeleteMapping(value = "/un_assign_trainer/{traineeId}/{trainerId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> un_assignTrainee(@PathVariable("traineeId") int traineeId,
                                                   @PathVariable("trainerId") int trainerId) throws EmployeeNotFoundException {
        String message;
        logger.debug("requested URL is correct. This URl create association between trainee to trainees");
        logger.info("trainee id searching...in database");
        Trainee trainee = employeeService.searchTraineeData(traineeId);
        logger.info("trainee Id " + traineeId + "present...");
        logger.info("trainee Id list :" + traineeId);
        trainee = employeeService.deleteTrainerInTrainee(trainee, trainerId);
        message = employeeService.updateTraineeData(trainee.getTraineeId(), trainee);
        logger.info(message);
        logger.debug("Un association successful");
        return new ResponseEntity<>(message + " Un association successful", HttpStatus.OK);
    }
}
