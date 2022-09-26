package com.ideas2it.controller.controller;

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
    @Autowired
    private final EmployeeService employeeService = new EmployeeServiceImpl();
    @Autowired
    private final EmployeeUtil employeeUtil = new EmployeeUtil();
    @Autowired
    private final Logger logger = LoggerFactory.getLogger(TraineeServlet.class);

    @RequestMapping(value = "/trainee",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public ResponseEntity<String> addTrainee(@RequestBody Trainee trainee) throws IOException, SQLException {
        String message = null;
        Map<String, String> map = new Gson().fromJson(String.valueOf(trainee), Map.class);
        logger.info("details successfully received from user and send it for validation");
        int valid = validationOfInputs(map);
        logger.info("validation successful");
        if (0 == valid) {
            logger.info("trainee object send to database");
            message = employeeService.addTrainee(trainee);
            logger.info("message from database : " + message);
        }
        return new ResponseEntity<String>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/trainees", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public ResponseEntity<List<Trainee>> getTrainees() throws SQLException {
        logger.debug("requested URL is correct. This URL is returns all trainee details");
        List<Trainee> trainees = employeeService.getTraineesData();
        if (trainees == null) {
            logger.info("trainee list is empt" + "y");
            return new ResponseEntity<List<Trainee>>(HttpStatus.NOT_FOUND);
        } else {
            logger.debug("details successfully shown");
            return new ResponseEntity<List<Trainee>>(trainees, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/trainee/{id}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public ResponseEntity<Trainee> getTrainee(@PathVariable("id") String id) throws SQLException {
        logger.debug("requested URL is correct. This URL is returns all trainee details");
        int traineeId = Integer.parseInt(id);
        Trainee trainee = employeeService.searchTraineeData(traineeId);
        logger.info("searching successful");
        if (trainee == null) {
            logger.info("trainee list is empty" + "y");
            return new ResponseEntity<Trainee>(HttpStatus.NOT_FOUND);
        } else {
            logger.debug("details successfully shown");
            return new ResponseEntity<Trainee>(trainee, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/trainee",
            method = RequestMethod.PUT,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public ResponseEntity<String> updateTrainee(@RequestBody Trainee trainee) throws SQLException {
        logger.debug("requested URL is correct. This URl is update the exists employee profile");
        String message = employeeService.updateTraineeData(trainee.getTraineeId(), trainee);
        return new ResponseEntity<String>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/assign_trainer/{traineeId,trainersId}",
            method = RequestMethod.PUT,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public ResponseEntity<String> assignTrainee(@PathVariable("traineeId") String traineeId,
                                                @PathVariable("trainersId") String trainersId) throws SQLException {
        String message = null;
        logger.debug("requested URL is correct. This URl create association between trainee to trainees");
        int id = Integer.parseInt(traineeId);
        logger.info("trainee id searching...in database");
        Trainee trainee = employeeService.searchTraineeData(id);
        if (trainee != null) {
            logger.info("trainee Id present...");
            logger.info("trainer Id list :" + "[" + trainersId + "]");
            String[] idList = trainersId.split(",");
            List<Trainer> trainers = employeeService.getTrainersData();
            logger.info("filtering trainer..... in trainers list");
            for (String s : idList) {
                int trainerId = Integer.parseInt(s);
                Set<Trainer> trainer = trainers.stream().filter(filterTrainer ->
                        filterTrainer.getTrainerId() == trainerId).collect(Collectors.toSet());
                List<Trainer> trainerList = new ArrayList<>(trainer);
                if (trainer.size() == 0) {
                    logger.info("couldn't found trainerId :" + trainerId + "in list");
                    message = "couldn't found trainer :" + trainerId + "in list";
                    return new ResponseEntity<String>(message, HttpStatus.OK);
                } else {
                    logger.info("trainee :" + traineeId + "found in list");
                    trainee.getTrainers().add(trainerList.get(0));
                }
            }
            message = employeeService.updateTraineeData(trainee.getTraineeId(), trainee);
            logger.info(message);
            logger.debug("association successful");
            return new ResponseEntity<String>(message + "association successful", HttpStatus.OK);
        } else {
            logger.info("couldn't found trainee");
            message = "couldn't found trainee ";
            return new ResponseEntity<String>(message, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/trainee/{id}", method = RequestMethod.DELETE,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public ResponseEntity<String> deleteTrainee(@PathVariable("id") String id) throws SQLException {
        logger.debug("requested URL is correct. This URL is returns all trainee details");
        int traineeId = Integer.parseInt(id);
        String message = null;
        Trainee trainee = employeeService.searchTraineeData(traineeId);
        logger.info("searching successful");
        if (trainee == null) {
            logger.info("trainee list is empty" + "y");
            message = employeeService.deleteTraineeData(traineeId);
            Set<Trainer> trainer = trainee.getTrainers();
            if (null != trainer) {
                trainer.removeAll(trainer);
                message = employeeService.updateTraineeData(traineeId, trainee);
            }
            return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
        } else {
            logger.debug("details successfully shown");
            return new ResponseEntity<String>(message, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/un_assign_trainee/{trainerId,traineeId}",
            method = RequestMethod.DELETE,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public ResponseEntity<String> un_assignTrainee(@PathVariable("trainerId") String trainerId,
                                                   @PathVariable("traineeId") String traineeId) throws SQLException {
        String message = null;
        logger.debug("requested URL is correct. This URl create association between trainee to trainees");
        int id = Integer.parseInt(traineeId);
        logger.info("trainee id searching...in database");
        Trainee trainee = employeeService.searchTraineeData(id);
        logger.info("trainee Id " + id + "present...");
        logger.info("trainee Id list :" + traineeId);
        List<Trainer> trainers = employeeService.getTrainersData();
        logger.info("filtering trainee..... in trainees list");
        Set<Trainer> trainer = trainers.stream().filter(filterTrainer ->
                        filterTrainer.getTrainerId() == Integer.parseInt(trainerId))
                .collect(Collectors.toSet());
        List<Trainer> trainerList = new ArrayList<>(trainer);
        if (trainer.size() == 0) {
            logger.info("couldn't found traineeId :" + traineeId + "in list");
            message = "couldn't found trainee :" + traineeId + "in list";
            return new ResponseEntity<String>(message, HttpStatus.OK);
        } else {
            logger.info("trainee :" + traineeId + "found in list");
            trainee.getTrainers().remove(trainerList.get(0));
        }
        message = employeeService.updateTraineeData(trainee.getTraineeId(), trainee);
        logger.info(message);
        logger.debug("un assign successful");
        return new ResponseEntity<String>(message + "un assign successful", HttpStatus.OK);
    }

    /**
     * <h1> validateTraineeName </h1>
     * <p>
     * Method used to validate trainee name from user
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
        if (!employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", map.get("currenTask"))) {
            logger.info("wrong currentTask");
            new Gson().toJson("enter valid current Task");
            count++;
        }
        if (!employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", map.get("currentTechknowledge"))) {
            logger.info("wrong currentTechknowledge");
            new Gson().toJson("enter valid currentTechknowledge");
            count++;
        }
        return count;
    }
}
