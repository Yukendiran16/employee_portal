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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    EmployeeService employeeService;

    @Autowired
    TraineeMapper traineeMapper;

    private static final Logger logger = LoggerFactory.getLogger(TraineeController.class);

    /*@RequestMapping(path = "/")
    public String login() {
        return "index.html";
    }*/
    @PostMapping("/trainee")
    public String addTrainee(@Valid @ModelAttribute("traineeDto") TraineeRequestDto traineeDto, Model model)
            throws SQLIntegrityConstraintViolationException {
        logger.info("trainee object send to database");
        try {
            model.addAttribute("traineeResponseDto", employeeService.addTrainee(traineeDto));
        } catch (Exception e) {
            throw new SQLIntegrityConstraintViolationException();
        }
        return "view.html";
    }

    @GetMapping(path = "/trainees")
    public ModelAndView getTrainees(ModelAndView model) {
        logger.info("requested URL is correct. This URL is returns all trainee details");
        Trainee trainee = new Trainee();
        List<Trainee> trainees = employeeService.getTraineesData();
        if (null == trainees) throw new EmployeeNotFoundException("empty list ");
        logger.info("details successfully shown");
        List<TraineeResponseDto> traineeList = new ArrayList<>();
        trainees.forEach(trainee1 -> {
            TraineeResponseDto traineeResponseDto = traineeMapper.traineeToTraineeResponseDto(trainee1);
            traineeList.add(traineeResponseDto);
        });
        model.addObject("trainees",traineeList);
        model.setViewName("view");
        return model;
    }

    @GetMapping(path = "/trainee_get")
    public ModelAndView displayTrainee(@RequestParam("id") int traineeId, ModelAndView model) {
        logger.info("requested URL is correct. This URL is returns all trainee details");
        Trainee trainee = employeeService.searchTraineeData(traineeId);
        if (trainee.getIsActive()) throw new EmployeeNotFoundException("trainee is not active ");
        logger.info("searching successful");
        List<TraineeResponseDto> traineeResponseDtos = new ArrayList<>();
        traineeResponseDtos.add(traineeMapper.traineeToTraineeResponseDto(trainee));
        System.out.println(model.addObject("trainees",traineeResponseDtos));
        model.setViewName("view");
        return model;
    }

    @GetMapping(path = "/trainee_details/{id}")
    public ModelAndView ShowFullTraineeDetails(@PathVariable("id") int traineeId, ModelAndView model) {
        logger.info("requested URL is correct. This URL is returns all trainee details");
        Trainee trainee = employeeService.searchTraineeData(traineeId);
        if (trainee.getIsActive()) throw new EmployeeNotFoundException("trainee is not active");
        List<TraineeRequestDto> traineeDto = new ArrayList<>();
        traineeDto.add(traineeMapper.traineeToTraineeRequestDto(trainee));
        logger.info("searching successful");
        model.addObject("trainees", traineeDto);
        model.setViewName("update.html");
        return model;
    }

    @PutMapping(value = "/trainee_edit")
    public ModelAndView updateTrainee(@RequestBody TraineeRequestDto traineeDto, ModelAndView model) {
        logger.info("requested URL is correct. This URl is update the exists employee profile");
        logger.info(String.valueOf(traineeDto.getTraineeId()));
        Trainee trainee = employeeService.searchTraineeData(traineeDto.getTraineeId());
        if (trainee.getIsActive()) throw new EmployeeNotFoundException("trainee is not active");
        trainee = traineeMapper.traineeRequestDtoToTrainee(traineeDto);
        Trainee trainee1 = employeeService.updateTraineeData(trainee);
        TraineeResponseDto traineeResponseDto = traineeMapper.traineeToTraineeResponseDto(trainee1);
        model.setViewName("index.html");
        return model;
    }

    @RequestMapping(value = "/trainee_delete/{id}")
    public ModelAndView deleteTrainee(@PathVariable("id") int traineeId, ModelAndView model) {
        logger.info("requested URL is correct. This URL is returns all trainee details");
        Trainee trainee = employeeService.searchTraineeData(traineeId);
        logger.info("searching successful");
        employeeService.deleteTraineeData(trainee, traineeId);
        logger.info("successfully deleted traineeId : " + traineeId);
        model.setViewName("index.html");
        return model;
    }

}
