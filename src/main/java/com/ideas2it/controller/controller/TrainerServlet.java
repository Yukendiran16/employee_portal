package com.ideas2it.controller.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ideas2it.exception.EmailMismatchException;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.service.EmployeeService;
import com.ideas2it.service.impl.EmployeeServiceImpl;
import com.ideas2it.util.EmployeeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Autowired
    private final EmployeeService employeeService = new EmployeeServiceImpl();
    @Autowired
    private final EmployeeUtil employeeUtil = new EmployeeUtil();
    @Autowired
    private final Logger logger = LoggerFactory.getLogger(TrainerServlet.class);
    @Autowired
    private final ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/trainer",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public ResponseEntity<String> addTrainer(@RequestBody Trainer trainer) throws IOException, SQLException {
        String message = null;
        Map<String, String> map = new Gson().fromJson(String.valueOf(trainer), Map.class);
        logger.info("details successfully received from user and send it for validation");
        int valid = validationOfInputs(map);
        logger.info("validation successful");
        if (0 == valid) {
            logger.info("trainer object send to database");
            message = employeeService.addTrainer(trainer);
            logger.info("message from database : " + message);
        }
        return new ResponseEntity<String>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/trainers", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public ResponseEntity<List<Trainer>> getTrainers() throws SQLException {
        logger.debug("requested URL is correct. This URL is returns all trainer details");
        List<Trainer> trainers = employeeService.getTrainersData();
        if (trainers == null) {
            logger.info("trainer list is empty");
            return new ResponseEntity<List<Trainer>>(HttpStatus.NOT_FOUND);
        } else {
            logger.debug("details successfully shown");
            return new ResponseEntity<List<Trainer>>(trainers, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/trainer/{id}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public ResponseEntity<Trainer> getTrainer(@PathVariable("id") String id) throws SQLException {
        logger.debug("requested URL is correct. This URL is returns all trainer details");
        int trainerId = Integer.parseInt(id);
        Trainer trainer = employeeService.searchTrainerData(trainerId);
        logger.info("searching successful");
        if (trainer == null) {
            logger.info("trainer list is empty" + "y");
            return new ResponseEntity<Trainer>(HttpStatus.NOT_FOUND);
        } else {
            logger.debug("details successfully shown");
            return new ResponseEntity<Trainer>(trainer, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/trainer",
            method = RequestMethod.PUT,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public ResponseEntity<String> updateTrainer(@RequestBody Trainer trainer) throws SQLException {
        logger.debug("requested URL is correct. This URl is update the exists employee profile");
        String message = employeeService.updateTrainerData(trainer.getTrainerId(), trainer);
        return new ResponseEntity<String>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/assign_trainee/{trainerId,traineesId}",
            method = RequestMethod.PUT,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public ResponseEntity<String> assignTrainee(@PathVariable("trainerId") String trainerId,
                                                @PathVariable("traineesId") String traineesId) throws SQLException {
        String message = null;
        logger.debug("requested URL is correct. This URl create association between trainer to trainees");
        int id = Integer.parseInt(trainerId);
        logger.info("trainer id searching...in database");
        Trainer trainer = employeeService.searchTrainerData(id);
        logger.info("trainer Id present...");
        logger.info("trainee Id list :" + "[" + traineesId + "]");
        String[] idList = traineesId.split(",");
        List<Trainee> trainees = employeeService.getTraineesData();
        logger.info("filtering trainee..... in trainees list");
        for (String s : idList) {
            int traineeId = Integer.parseInt(s);
            Set<Trainee> trainee = trainees.stream().filter(filterTrainee ->
                    filterTrainee.getTraineeId() == traineeId).collect(Collectors.toSet());
            List<Trainee> traineeList = new ArrayList<>(trainee);
            if (trainee.size() == 0) {
                logger.info("couldn't found traineeId :" + traineeId + "in list");
                message = "couldn't found trainee :" + traineeId + "in list";
                return new ResponseEntity<String>(message, HttpStatus.OK);
            } else {
                logger.info("trainee :" + traineeId + "found in list");
                trainer.getTrainees().add(traineeList.get(0));
            }
        }
        message = employeeService.updateTrainerData(trainer.getTrainerId(), trainer);
        logger.info(message);
        logger.debug("association successful");
        return new ResponseEntity<String>(message + "association successful", HttpStatus.OK);
    }

    @RequestMapping(value = "/trainer/{id}", method = RequestMethod.DELETE,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public ResponseEntity<String> deleteTrainer(@PathVariable("id") String id) throws SQLException {
        logger.debug("requested URL is correct. This URL is returns all trainer details");
        int trainerId = Integer.parseInt(id);
        String message = null;
        Trainer trainer = employeeService.searchTrainerData(trainerId);
        logger.info("searching successful");
        if (trainer == null) {
            logger.info("trainer list is empty" + "y");
            message = employeeService.deleteTrainerData(trainerId);
            Set<Trainee> trainee = trainer.getTrainees();
            if (null != trainee) {
                trainee.removeAll(trainee);
                message = employeeService.updateTrainerData(trainerId, trainer);
            }
            return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
        } else {
            logger.debug("details successfully shown");
            return new ResponseEntity<String>(message, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/assign_trainee/{trainerId,traineeId}",
            method = RequestMethod.DELETE,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public ResponseEntity<String> un_assignTrainee(@PathVariable("trainerId") String trainerId,
                                                   @PathVariable("traineeId") String traineeId) throws SQLException {
        String message = null;
        logger.debug("requested URL is correct. This URl create association between trainer to trainees");
        int id = Integer.parseInt(trainerId);
        logger.info("trainer id searching...in database");
        Trainer trainer = employeeService.searchTrainerData(id);
        logger.info("trainer Id " + id + "present...");
        logger.info("trainee Id list :" + traineeId);
        List<Trainee> trainees = employeeService.getTraineesData();
        logger.info("filtering trainee..... in trainees list");
        Set<Trainee> trainee = trainees.stream().filter(filterTrainee ->
                        filterTrainee.getTraineeId() == Integer.parseInt(traineeId))
                .collect(Collectors.toSet());
        List<Trainee> traineeList = new ArrayList<>(trainee);
        if (trainee.size() == 0) {
            logger.info("couldn't found traineeId :" + traineeId + "in list");
            message = "couldn't found trainee :" + traineeId + "in list";
            return new ResponseEntity<String>(message, HttpStatus.OK);
        } else {
            logger.info("trainee :" + traineeId + "found in list");
            trainer.getTrainees().remove(traineeList.get(0));
        }
        message = employeeService.updateTrainerData(trainer.getTrainerId(), trainer);
        logger.info(message);
        logger.debug("association successful");
        return new ResponseEntity<String>(message + "association successful", HttpStatus.OK);
    }

    /**
     * <h1> validateTrainerName </h1>
     * <p>
     * Method used to validate trainer name from user
     * </p>
     */
    public int validationOfInputs(Map<String, String> map) throws IOException {
        int count = 0;
        if (!employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", map.get("employeeName"))) {
            logger.info("wrong name");
            new Gson().toJson("enter valid name");
            count++;
        }
        try {
            if (!EmployeeUtil.validationOfDateOfBirth(map.get("employeeDateOfBirth"))) {
                count++;
                logger.info("wrong employeeDateOfBirth");
                new Gson().toJson("enter valid date of birth");
            }
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            count++;
            logger.info("wrong employeeDateOfBirth");
            new Gson().toJson("enter valid date of birth");
        }
        if (!employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", map.get("employeeDesignation"))) {
            logger.info("wrong employeeDesignation");
            new Gson().toJson("enter valid designation");
            count++;
        }
        try {
            if (!EmployeeUtil.validationOfMail(map.get("employeeMail"))) {
                logger.info("wrong employeeMail");
                new Gson().toJson("enter valid mail");
                count++;
            }
        } catch (EmailMismatchException e) {
            logger.info("employeeMail");
            count++;
            new Gson().toJson("enter valid date of email");
        }
        if (!employeeUtil.matchRegex("^(([6-9]{1}[0-9]{9})*)$", map.get("employeeMobileNumber"))) {
            logger.info("wrong employeeMobileNumber");
            new Gson().toJson("enter valid mobile number");
            count++;
        }
        if (!employeeUtil.matchRegex("^(([0-9\\sa-zA-Z,.-]{3,150})*)$", map.get("currentAddress"))) {
            logger.info("wrong currentAddress");
            new Gson().toJson("enter valid address");
            count++;
        }
        if (!employeeUtil.matchRegex("^(([1-9]{1}[0-9]{11})*)$", map.get("aadharCardNumber"))) {
            logger.info("wrong panCardNumber");
            new Gson().toJson("enter valid aadhar Card number");
            count++;
        }
        if (!employeeUtil.matchRegex("^(([A-Z0-9]{10})*)$", map.get("panCardNumber"))) {
            logger.info("wrong panCardNumber");
            new Gson().toJson("enter valid pan card number");
            count++;
        }
        if (!employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", map.get("currentProject"))) {
            logger.info("wrong currentProject");
            new Gson().toJson("enter valid current project");
            count++;
        }
        if (!employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", map.get("achievement"))) {
            logger.info("wrong achievement");
            new Gson().toJson("enter valid achievement");
            count++;
        }
        return count;
    }
}
