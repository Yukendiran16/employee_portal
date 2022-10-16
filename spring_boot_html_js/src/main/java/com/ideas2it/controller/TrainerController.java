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
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("employee_portal/trainer")
    public String addTrainer(@Valid @ModelAttribute("trainerDto") TrainerRequestDto trainerDto, Model model)
            throws SQLIntegrityConstraintViolationException {
        logger.info("trainer object send to database");
        try {
            model.addAttribute("trainerResponseDto", employeeService.addTrainer(trainerDto));
        } catch (Exception e){
            throw new SQLIntegrityConstraintViolationException("Duplicate user entry");
        }
        return "CreateEmployee";
    }

    @GetMapping(path = "employee_portal/trainers")
    public ModelAndView getTrainers(ModelAndView model) {
        logger.info("requested URL is correct. This URL is returns all trainer details");
        Trainer trainer = new Trainer();
        List<Trainer> trainers = employeeService.getTrainersData();
        if (null == trainers) throw new EmployeeNotFoundException("empty list ");
        logger.info("details successfully shown");
        List<TrainerResponseDto> trainerList = new ArrayList<>();
        trainers.forEach(trainer1 -> {
            TrainerResponseDto trainerResponseDto = trainerMapper.trainerToTrainerResponseDto(trainer1);
            trainerList.add(trainerResponseDto);
        });
        model.addObject("trainers",trainerList);
        model.setViewName("view.html");
        return model;
    }

    @GetMapping(path = "employee_portal/trainer_get")
    public ModelAndView displayTrainer(@RequestParam("id") int trainerId, ModelAndView model) {
        logger.info("requested URL is correct. This URL is returns all trainer details");
        Trainer trainer = employeeService.searchTrainerData(trainerId);
        if (trainer.getIsActive()) throw new EmployeeNotFoundException("trainer is not active ");
        logger.info("searching successful");
        List<TrainerResponseDto> trainerResponseDto = new ArrayList<>();
        trainerResponseDto.add(trainerMapper.trainerToTrainerResponseDto(trainer));
        System.out.println(model.addObject("employee",trainerResponseDto));
        model.setViewName("view.html");
        return model;
    }

    @GetMapping(path = "/trainer_details/{id}")
    public ModelAndView ShowFullTrainerDetails(@PathVariable("id") int trainerId, ModelAndView model) {
        logger.info("requested URL is correct. This URL is returns all trainer details");
        Trainer trainer = employeeService.searchTrainerData(trainerId);
        if (trainer.getIsActive()) throw new EmployeeNotFoundException("trainer is not active");
        List<TrainerRequestDto> trainerDto = new ArrayList<>();
        trainerDto.add(trainerMapper.trainerToTrainerRequestDto(trainer));
        logger.info("searching successful");
        model.addObject("trainers", trainerDto);
        model.setViewName("update.html");
        return model;
    }

    @PutMapping(value = "/trainer_edit")
    public ModelAndView updateTrainer(@RequestBody TrainerRequestDto trainerDto, ModelAndView model) {
        logger.info("requested URL is correct. This URl is update the exists employee profile");
        logger.info(String.valueOf(trainerDto.getTrainerId()));
        Trainer trainer = employeeService.searchTrainerData(trainerDto.getTrainerId());
        if (trainer.getIsActive()) throw new EmployeeNotFoundException("trainer is not active");
        trainer = trainerMapper.trainerRequestDtoToTrainer(trainerDto);
        Trainer trainer1 = employeeService.updateTrainerData(trainer);
        TrainerResponseDto trainerResponseDto = trainerMapper.trainerToTrainerResponseDto(trainer1);
        model.setViewName("index.html");
        return model;
    }

    @RequestMapping(value = "/trainer_delete/{id}")
    public ModelAndView deleteTrainer(@PathVariable("id") int trainerId, ModelAndView model) {
        logger.info("requested URL is correct. This URL is returns all trainer details");
        Trainer trainer = employeeService.searchTrainerData(trainerId);
        logger.info("searching successful");
        employeeService.deleteTrainerData(trainer, trainerId);
        logger.info("successfully deleted trainerId : " + trainerId);
        model.setViewName("index.html");
        return model;
    }

}
