package com.ideas2it.controller;

import com.google.gson.Gson;
import com.ideas2it.Dto.TraineeDto;
import com.ideas2it.Dto.TrainerDto;
import com.ideas2it.exception.EmployeeNotFoundException;
import com.ideas2it.mapper.TraineeMapper;
import com.ideas2it.mapper.TrainerMapper;
import com.ideas2it.model.Trainee;
import com.ideas2it.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/employee_portal")
public class TraineeController {

    @Autowired
    EmployeeService employeeService;

    //@Autowired
    //TraineeMapper traineeMapper;
    private static final Logger logger = LoggerFactory.getLogger(TraineeController.class);

    @PostMapping(path = "/trainee",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Trainee> addTrainee(@Valid @RequestBody TraineeDto traineeDto) {
        logger.info("trainee object send to database");
        Trainee trainee = new Trainee();
        trainee = trainee.TraineeDtoToTrainee(traineeDto);
        return new ResponseEntity<>(employeeService.addTrainee(trainee), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/trainees", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Map<String,Object>>> displayTrainees() throws EmployeeNotFoundException {
        logger.debug("requested URL is correct. This URL is returns all trainee details");
        List<Trainee> trainees = employeeService.getTraineesData();
        if (null == trainees) throw new EmployeeNotFoundException("list is empty");
        logger.debug("details successfully shown");
        List<Map<String, Object>> traineeList = new ArrayList<>();
        trainees.forEach(trainee -> {
            Map<String, Object> trainee1 = employeeService.getTrainee(trainee);
            traineeList.add(trainee1);});
        return new ResponseEntity<>(traineeList, HttpStatus.OK);
    }

    @GetMapping(path = "/trainee/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> displayTrainee(@PathVariable("id") int traineeId) throws EmployeeNotFoundException {
        logger.debug("requested URL is correct. This URL is returns all trainee details");
        Trainee trainee = employeeService.searchTraineeData(traineeId);
        if (trainee.getIsActive()) throw new EmployeeNotFoundException("trainee is not active");
        Map<String, Object> trainee1 = employeeService.getTrainee(trainee);
        logger.info("searching successful");
        return new ResponseEntity<>(trainee1, HttpStatus.OK);
    }

    @GetMapping(path = "/traineeDetails/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TraineeDto> showFullTraineeDetails(@PathVariable("id") int traineeId) throws EmployeeNotFoundException {
        logger.debug("requested URL is correct. This URL is returns all trainee details");
        Trainee trainee = employeeService.searchTraineeData(traineeId);
        if (trainee.getIsActive()) throw new EmployeeNotFoundException("trainee is not active");
        TraineeDto traineeDto = TraineeMapper.TraineeToTraineeDto(trainee);
        logger.info("searching successful");
        return new ResponseEntity<>(traineeDto, HttpStatus.OK);
    }

    @PutMapping(value = "/trainee",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Trainee> updateTrainee(@RequestBody TraineeDto traineeDto) throws EmployeeNotFoundException {
        logger.debug("requested URL is correct. This URl is update the exists employee profile");
        Trainee trainee = employeeService.searchTraineeData(traineeDto.getTraineeId());
        trainee = trainee.TraineeDtoToTrainee(traineeDto);
        return new ResponseEntity<>(employeeService.updateTraineeData(trainee.getTraineeId(), trainee), HttpStatus.OK);
    }

    @DeleteMapping(value = "/trainee/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> deleteTrainee(@PathVariable("id") int traineeId) throws EmployeeNotFoundException {
        logger.debug("requested URL is correct. This URL is returns all trainee details");
        Trainee trainee = employeeService.searchTraineeData(traineeId);
        logger.info("searching successful");
        employeeService.deleteTraineeData(trainee, traineeId);
        logger.debug("Successfully deleted traineeId : " + traineeId);
        return new ResponseEntity<>(new Gson().toJson("message : Successfully deleted ," + "traineeId : " + traineeId), HttpStatus.OK);
    }

    @PutMapping(value = "/assign_trainer",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> assignTrainee(@RequestBody Map<String,String> assign) throws EmployeeNotFoundException {
        logger.debug("requested URL is correct. This URl create association between trainee to trainees");
        logger.info("trainee id searching...in database");
        Trainee trainee = employeeService.searchTraineeData(Integer.parseInt(assign.get("traineeId")));
        logger.info("trainee Id present...");
        logger.info("trainee Id list :" + "[" + assign.get("trainerId") + "]");
        String[] idList = assign.get("trainerId").split(",");
        employeeService.updateTraineeData(trainee.getTraineeId(),
                employeeService.updateTrainerListInTrainee(trainee, idList));
        logger.debug("association successful");
        return new ResponseEntity<>(new Gson().toJson("message : " + " association successful"), HttpStatus.OK);
    }

    @DeleteMapping(value = "/un_assign_trainer",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> un_assignTrainee(@RequestBody Map<String,Integer> un_assign) throws EmployeeNotFoundException {
        logger.debug("requested URL is correct. This URl create association between trainee to trainees");
        logger.info("trainee id searching...in database");
        Trainee trainee = employeeService.searchTraineeData(un_assign.get("traineeId"));
        logger.info("trainee Id " + un_assign.get("traineeId") + "present...");
        employeeService.updateTraineeData(trainee.getTraineeId(),
                employeeService.deleteTrainerInTrainee(trainee, un_assign.get("trainerId")));
        logger.debug("Un association successful");
        return new ResponseEntity<>(new Gson().toJson("message : " + " Un association successful"), HttpStatus.OK);
    }
}
