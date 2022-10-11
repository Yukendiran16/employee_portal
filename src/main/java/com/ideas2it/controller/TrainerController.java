package com.ideas2it.controller;

import com.google.gson.Gson;
import com.ideas2it.Dto.TrainerRequestDto;
import com.ideas2it.exception.EmployeeNotFoundException;
import com.ideas2it.mapper.TrainerMapper;
import com.ideas2it.model.Trainer;
//import com.ideas2it.security.AppBasicAuthenticationEntryPoint;
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
@RequestMapping("/employee_portal")
public class TrainerController {

    @Autowired
    EmployeeService employeeService;

    //@Autowired
    //private AppBasicAuthenticationEntryPoint logoutHandler;

    @Autowired
    TrainerMapper trainerMapper;

    private static final Logger logger = LoggerFactory.getLogger(TrainerController.class);

    @PostMapping(path = "/trainer",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Trainer> addTrainer(@RequestBody @Valid TrainerRequestDto trainerDto)  {
        logger.info("trainer object send to database");
        return new ResponseEntity<>(employeeService.addTrainer(trainerDto), HttpStatus.CREATED);
    }

    @GetMapping(path = "/trainers",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Map<String, Object>>> getTrainers() {
        logger.info("requested URL is correct. This URL is returns all trainer details");
        List<Trainer> trainers = employeeService.getTrainersData();
        if (null == trainers) throw new EmployeeNotFoundException("empty list ");
        logger.info("details successfully shown");
        List<Map<String, Object>> trainerList = new ArrayList<>();
        trainers.forEach(trainer -> {
            Map<String, Object> trainer1 = employeeService.getTrainer(trainer);
            trainerList.add(trainer1);
        });
        return new ResponseEntity<>(trainerList, HttpStatus.OK);
    }

    @GetMapping(path = "/trainer/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> displayTrainer(@PathVariable("id") int trainerId) {
        logger.info("requested URL is correct. This URL is returns all trainer details");
        Trainer trainer = employeeService.searchTrainerData(trainerId);
        if (trainer.getIsActive()) throw new EmployeeNotFoundException("trainer is not active ");
        Map<String, Object> trainer1 = employeeService.getTrainer(trainer);
        logger.info("searching successful");
        return new ResponseEntity<>(trainer1, HttpStatus.OK);
    }

    @GetMapping(path = "/trainerDetails/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TrainerRequestDto> ShowFullTrainerDetails(@PathVariable("id") int trainerId) {
        logger.info("requested URL is correct. This URL is returns all trainer details");
        Trainer trainer = employeeService.searchTrainerData(trainerId);
        if (trainer.getIsActive()) throw new EmployeeNotFoundException("trainer is not active");
        TrainerRequestDto trainerDto = trainerMapper.TrainerToTrainerDto(trainer);
        logger.info("searching successful");
        return new ResponseEntity<>(trainerDto, HttpStatus.OK);
    }

    @PutMapping(value = "/trainer",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Trainer> updateTrainer(@RequestBody TrainerRequestDto trainerDto) {
        logger.info("requested URL is correct. This URl is update the exists employee profile");
        Trainer trainer = employeeService.searchTrainerData(trainerDto.getTrainerId());
        if (trainer.getIsActive()) throw new EmployeeNotFoundException("trainer is not active");
        trainer = trainerMapper.TrainerDtoToTrainer(trainerDto);
        //Map<String, Object> trainer1 = employeeService.getTrainer(
                //employeeService.updateTrainerData(trainer.getTrainerId(), trainer));
        return new ResponseEntity<>(employeeService.
                updateTrainerData(trainer.getTrainerId(), trainer), HttpStatus.OK);
    }

    @DeleteMapping(value = "/trainer/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> deleteTrainer(@PathVariable("id") int trainerId) {
        logger.info("requested URL is correct. This URL is returns all trainer details");
        Trainer trainer = employeeService.searchTrainerData(trainerId);
        logger.info("searching successful");
        employeeService.deleteTrainerData(trainer, trainerId);
        logger.info("successfully deleted trainerId : " + trainerId);
        return new ResponseEntity<>(new Gson().toJson("message : successfully deleted ," + "trainerId : " + trainerId), HttpStatus.OK);
    }

    @PutMapping(value = "/assign_trainee",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> assignTrainee(@RequestBody Map<String, Integer> assign) {
        logger.info("requested URL is correct. This URl create association between trainer to trainees");
        logger.info("trainer id searching...in database");
        Trainer trainer = employeeService.searchTrainerData(assign.get("trainerId"));
        if (trainer.getIsActive()) throw new EmployeeNotFoundException("trainer is not active ");
        logger.info("trainer Id present...");
        logger.info("trainee Id list :" + "[" + assign.get("traineeId") + "]");
        try {
            employeeService.updateTrainerData(trainer.getTrainerId(),
                    employeeService.updateTraineeListInTrainer(trainer,assign.get("traineeId")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        logger.info("association successful");
        return new ResponseEntity<>(new Gson().toJson("message : " + " association successful"), HttpStatus.OK);
    }

    @DeleteMapping(value = "/un_assign_trainee",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> un_assignTrainee(@RequestBody Map<String, Integer> un_assign)
    {
        logger.info("requested URL is correct. This URl create association between trainer to trainees");
        logger.info("trainer id searching...in database");
        Trainer trainer = employeeService.searchTrainerData(un_assign.get("trainerId"));
        if (trainer.getIsActive()) throw new EmployeeNotFoundException("trainer is not active");
        logger.info("trainer Id " + un_assign.get("trainerId") + "present...");
        logger.info("trainee Id list :" + un_assign.get("traineeId"));
        employeeService.updateTrainerData(trainer.getTrainerId(),
                employeeService.deleteTraineeInTrainer(trainer, un_assign.get("traineeId")));
        logger.info("Un association successful");
        return new ResponseEntity<>(new Gson().toJson("message : " + " Un association successful"), HttpStatus.OK);
    }
}
