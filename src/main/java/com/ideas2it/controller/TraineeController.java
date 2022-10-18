package com.ideas2it.controller;

import com.ideas2it.Dto.TraineeRequestDto;
import com.ideas2it.Dto.TraineeResponseDto;
import com.ideas2it.exception.EmployeeNotFoundException;
import com.ideas2it.mapper.TraineeMapper;
import com.ideas2it.model.Trainee;
import com.ideas2it.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/employee_portal/trainee")
    public String addTrainee(@Valid @RequestBody TraineeRequestDto traineeDto, Model model) throws SQLIntegrityConstraintViolationException {
        logger.info("trainee object send to database");
        try {
            model.addAttribute("traineeResponseDto", employeeService.addTrainee(traineeDto));
        } catch (Exception e) {
            throw new SQLIntegrityConstraintViolationException("Duplicate user entry");
        }
        return "CreateEmployee";
    }

    @GetMapping(path = "/employee_portal/trainees")
    public List<TraineeResponseDto> getTrainees() {
        logger.info("requested URL is correct. This URL is returns all trainee details");
        List<Trainee> trainees = employeeService.getTraineesData();
        if (null == trainees) throw new EmployeeNotFoundException("empty list ");
        logger.info("details successfully shown");
        List<TraineeResponseDto> traineeList = new ArrayList<>();
        trainees.forEach(trainee1 -> {
            TraineeResponseDto traineeResponseDto = traineeMapper.traineeToTraineeResponseDto(trainee1);
            traineeList.add(traineeResponseDto);
        });
        return traineeList;
    }

    @GetMapping(path = "employee_portal/trainee_get/{id}")
    public TraineeResponseDto displayTrainee(@PathVariable("id") int traineeId) {
        logger.info("requested URL is correct. This URL is returns all trainee details");
        Trainee trainee = employeeService.searchTraineeData(traineeId);
        if (trainee.getIsActive()) throw new EmployeeNotFoundException("trainee is not active ");
        logger.info("searching successful");
        return traineeMapper.traineeToTraineeResponseDto(trainee);
    }

    @GetMapping(path = "/employee_portal/trainee_details/{id}")
    public TraineeRequestDto ShowFullTraineeDetails(@PathVariable("id") int traineeId) {
        logger.info("requested URL is correct. This URL is returns all trainee details");
        Trainee trainee = employeeService.searchTraineeData(traineeId);
        if (trainee.getIsActive()) throw new EmployeeNotFoundException("trainee is not active");
        logger.info("searching successful");
        return traineeMapper.traineeToTraineeRequestDto(trainee);
    }

    @PutMapping(value = "/employee_portal/trainee_edit")
    public String updateTrainee(@RequestBody TraineeRequestDto traineeDto) {
        logger.info("requested URL is correct. This URl is update the exists employee profile");
        logger.info(String.valueOf(traineeDto.getTraineeId()));
        Trainee trainee = employeeService.searchTraineeData(traineeDto.getTraineeId());
        if (trainee.getIsActive()) throw new EmployeeNotFoundException("trainee is not active");
        trainee = traineeMapper.traineeRequestDtoToTrainee(traineeDto);
        Trainee trainee1 = employeeService.updateTraineeData(trainee);
        return "createEmployee";
    }

    @RequestMapping(value = "/employee_portal/trainee_delete/{id}")
    public String deleteTrainee(@PathVariable("id") int traineeId) {
        logger.info("requested URL is correct. This URL is returns all trainee details");
        Trainee trainee = employeeService.searchTraineeData(traineeId);
        logger.info("searching successful");
        employeeService.deleteTraineeData(trainee, traineeId);
        logger.info("successfully deleted traineeId : " + traineeId);
        return "createEmployee";
    }

}
