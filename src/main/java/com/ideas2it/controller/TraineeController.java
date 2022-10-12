package com.ideas2it.controller;

import com.google.gson.Gson;
import com.ideas2it.Dto.TraineeAssociationDto;
import com.ideas2it.Dto.TraineeDto;
import com.ideas2it.Dto.TraineeRequestDto;
import com.ideas2it.Dto.TraineeResponseDto;
import com.ideas2it.exception.EmployeeNotFoundException;
import com.ideas2it.mapper.TraineeMapper;
import com.ideas2it.model.Trainee;
import com.ideas2it.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

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
@RequestMapping("/employee_portal")
public class TraineeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TraineeMapper traineeMapper;
    private static final Logger logger = LoggerFactory.getLogger(TraineeController.class);

    @PostMapping(path = "/trainee",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TraineeResponseDto> addTrainee(@Valid @RequestBody TraineeRequestDto traineeDto)
            throws SQLIntegrityConstraintViolationException {
        logger.info("trainee object send to database");
        employeeService.findExistingTrainee(traineeDto.getEmployeeMail(), traineeDto.getAadhaarCardNumber(),
                traineeDto.getEmployeeMobileNumber(), traineeDto.getPanCardNumber());
        Trainee trainee = employeeService.addTrainee(traineeDto);
        TraineeResponseDto traineeResponseDto = traineeMapper.traineeToTraineeResponseDto(trainee);
        return new ResponseEntity<>(traineeResponseDto, HttpStatus.CREATED);
    }

    @GetMapping(value = "/trainees",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<TraineeResponseDto>> displayTrainees() {
        logger.debug("requested URL is correct. This URL is returns all trainee details");
        List<Trainee> trainees = employeeService.getTraineesData();
        if (null == trainees) throw new EmployeeNotFoundException("list is empty ");
        logger.debug("details successfully shown");
        List<TraineeResponseDto> traineeList = new ArrayList<>();
        trainees.forEach(trainee -> {
            TraineeResponseDto traineeResponseDto = traineeMapper.traineeToTraineeResponseDto(trainee);
            traineeList.add(traineeResponseDto);});
        return new ResponseEntity<>(traineeList, HttpStatus.OK);
    }

    @GetMapping(path = "/trainee/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TraineeResponseDto> displayTrainee(@PathVariable("id") int traineeId) {
        logger.debug("requested URL is correct. This URL is returns all trainee details");
        Trainee trainee = employeeService.searchTraineeData(traineeId);
        if (trainee.getIsActive()) throw new EmployeeNotFoundException("trainee is not active ");
        TraineeResponseDto traineeResponseDto = traineeMapper.traineeToTraineeResponseDto(trainee);
        logger.info("searching successful");
        return new ResponseEntity<>(traineeResponseDto, HttpStatus.OK);
    }

    @GetMapping(path = "/traineeDetails/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TraineeDto> showFullTraineeDetails(@PathVariable("id") int traineeId) {
        logger.debug("requested URL is correct. This URL is returns all trainee details");
        Trainee trainee = employeeService.searchTraineeData(traineeId);
        if (trainee.getIsActive()) throw new EmployeeNotFoundException("trainee is not active ");
        TraineeDto traineeDto = traineeMapper.traineeToTraineeDto(trainee);
        logger.info("searching successful");
        return new ResponseEntity<>(traineeDto, HttpStatus.OK);
    }

    @PutMapping(value = "/trainee",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TraineeResponseDto> updateTrainee(@RequestBody TraineeRequestDto traineeDto) {
        logger.debug("requested URL is correct. This URl is update the exists employee profile");
        Trainee trainee = employeeService.searchTraineeData(traineeDto.getTraineeId());
        if (trainee.getIsActive()) throw new EmployeeNotFoundException("trainee is not active ");
        trainee = traineeMapper.traineeRequestDtoToTrainee(traineeDto);
        Trainee trainee1 = employeeService.updateTraineeData(trainee);
        TraineeResponseDto traineeResponseDto = traineeMapper.traineeToTraineeResponseDto(trainee1);
        return new ResponseEntity<>(traineeResponseDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/trainee/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> deleteTrainee(@PathVariable("id") int traineeId) {
        logger.debug("requested URL is correct. This URL is returns all trainee details");
        Trainee trainee = employeeService.searchTraineeData(traineeId);
        if (trainee.getIsActive()) throw new EmployeeNotFoundException("trainee is not active ");
        logger.info("searching successful");
        employeeService.deleteTraineeData(trainee, traineeId);
        logger.debug("Successfully deleted traineeId : " + traineeId);
        return new ResponseEntity<>(new Gson().toJson("message : Successfully deleted ," + "traineeId : " + traineeId), HttpStatus.OK);
    }

    @PutMapping(value = "/assign_trainer",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> assignTrainee(@RequestBody TraineeAssociationDto assign) {
        logger.debug("requested URL is correct. This URl create association between trainee to trainees");
        logger.info("trainee id searching...in database");
        Trainee trainee = employeeService.searchTraineeData(assign.getTraineeId());
        if (trainee.getIsActive()) throw new EmployeeNotFoundException("trainee is not active ");
        logger.info("trainee Id" + assign.getTraineeId() + "present...");
        logger.info("trainee Id list :" + "[" + assign.getTrainersList() + "]");
        employeeService.updateTraineeData(
                employeeService.updateTrainerListInTrainee(trainee, assign.getTrainersList()));
        logger.debug("association successful");
        return new ResponseEntity<>(new Gson().toJson("message : " + " association successful"), HttpStatus.OK);
    }

    @DeleteMapping(value = "/un_assign_trainer",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> un_assignTrainee(@RequestBody TraineeAssociationDto un_assign) {
        logger.debug("requested URL is correct. This URl create association between trainee to trainees");
        logger.info("trainee id searching...in database");
        Trainee trainee = employeeService.searchTraineeData(un_assign.getTraineeId());
        if (trainee.getIsActive()) throw new EmployeeNotFoundException("trainee is not active ");
        logger.info("trainee Id " + un_assign.getTraineeId() + "present...");
        logger.info("trainee Id list :" + "[" + un_assign.getTrainersList() + "]");
        employeeService.updateTraineeData(
                employeeService.deleteTrainerInTrainee(trainee, un_assign.getTrainersList()));
        logger.debug("Un association successful");
        return new ResponseEntity<>(new Gson().toJson("message : " + " Un association successful"), HttpStatus.OK);
    }
}
