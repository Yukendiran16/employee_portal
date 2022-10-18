package com.ideas2it.controller;

import com.ideas2it.Dto.TrainerRequestDto;
import com.ideas2it.Dto.TrainerResponseDto;
import com.ideas2it.exception.EmployeeNotFoundException;
import com.ideas2it.mapper.TrainerMapper;
import com.ideas2it.model.Trainer;
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
public class TrainerController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    TrainerMapper trainerMapper;
    private static final Logger logger = LoggerFactory.getLogger(TrainerController.class);

    @RequestMapping(path = "/")
    public ModelAndView login() {
        return new ModelAndView("CreateEmployee");
    }

    @PostMapping("/employee_portal/trainer")
    public String addTrainer(@Valid @RequestBody TrainerRequestDto trainerDto, Model model) throws SQLIntegrityConstraintViolationException {
        logger.info("trainer object send to database");
        try {
            model.addAttribute("trainerResponseDto", employeeService.addTrainer(trainerDto));
        } catch (Exception e) {
            throw new SQLIntegrityConstraintViolationException("Duplicate user entry");
        }
        return "CreateEmployee";
    }

    @GetMapping(path = "/employee_portal/trainers")
    public List<TrainerResponseDto> getTrainers() {
        logger.info("requested URL is correct. This URL is returns all trainer details");
        List<Trainer> trainers = employeeService.getTrainersData();
        if (null == trainers) throw new EmployeeNotFoundException("empty list ");
        logger.info("details successfully shown");
        List<TrainerResponseDto> trainerList = new ArrayList<>();
        trainers.forEach(trainer1 -> {
            TrainerResponseDto trainerResponseDto = trainerMapper.trainerToTrainerResponseDto(trainer1);
            trainerList.add(trainerResponseDto);
        });
        return trainerList;
    }

    @GetMapping(path = "employee_portal/trainer_get/{id}")
    public TrainerResponseDto displayTrainer(@PathVariable("id") int trainerId) {
        logger.info("requested URL is correct. This URL is returns all trainer details");
        Trainer trainer = employeeService.searchTrainerData(trainerId);
        if (trainer.getIsActive()) throw new EmployeeNotFoundException("trainer is not active ");
        logger.info("searching successful");
        return trainerMapper.trainerToTrainerResponseDto(trainer);
    }

    @GetMapping(path = "/employee_portal/trainer_details/{id}")
    public TrainerRequestDto ShowFullTrainerDetails(@PathVariable("id") int trainerId) {
        logger.info("requested URL is correct. This URL is returns all trainer details");
        Trainer trainer = employeeService.searchTrainerData(trainerId);
        if (trainer.getIsActive()) throw new EmployeeNotFoundException("trainer is not active");
        logger.info("searching successful");
        return trainerMapper.trainerToTrainerRequestDto(trainer);
    }

    @PutMapping(value = "/employee_portal/trainer_edit")
    public String updateTrainer(@RequestBody TrainerRequestDto trainerDto) {
        logger.info("requested URL is correct. This URl is update the exists employee profile");
        logger.info(String.valueOf(trainerDto.getTrainerId()));
        Trainer trainer = employeeService.searchTrainerData(trainerDto.getTrainerId());
        if (trainer.getIsActive()) throw new EmployeeNotFoundException("trainer is not active");
        trainer = trainerMapper.trainerRequestDtoToTrainer(trainerDto);
        Trainer trainer1 = employeeService.updateTrainerData(trainer);
        return "createEmployee";
    }

    @RequestMapping(value = "/employee_portal/trainer_delete/{id}")
    public String deleteTrainer(@PathVariable("id") int trainerId) {
        logger.info("requested URL is correct. This URL is returns all trainer details");
        Trainer trainer = employeeService.searchTrainerData(trainerId);
        logger.info("searching successful");
        employeeService.deleteTrainerData(trainer, trainerId);
        logger.info("successfully deleted trainerId : " + trainerId);
        return "createEmployee";
    }

}
