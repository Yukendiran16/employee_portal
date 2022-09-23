package com.ideas2it.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.ideas2it.exception.EmailMismatchException;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.service.EmployeeService;
import com.ideas2it.service.impl.EmployeeServiceImpl;
import com.ideas2it.util.EmployeeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
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
public class TraineeServlet extends HttpServlet {
    private final EmployeeService employeeService = new EmployeeServiceImpl();
    private final EmployeeUtil employeeUtil = new EmployeeUtil();
    private final Logger logger = LoggerFactory.getLogger(TraineeServlet.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = request.getRequestURI();
        logger.debug("request URL :" + url);
        String traineeId = request.getParameter("id");
        logger.info(traineeId + "requested trainee Id for view details.");
        Trainee trainee;
        try {
            if (url.equals("/employee_portal/trainee")) {
                logger.debug("requested URL is correct. This URl is returns searched trainee details");
                int id = Integer.parseInt(traineeId);
                trainee = employeeService.searchTraineeData(id);
                logger.debug("searching successful");
                if (trainee != null) {
                    Map<String, Object> map = employeeService.getTrainee(trainee);
                    outputResponse(response, new Gson().toJson(map));
                    logger.debug("details successfully shown");
                    logger.info(map.toString());
                    response.setStatus(200);
                    response.setHeader("Content-Type", "application/json");
                } else {
                    outputResponse(response, new Gson().toJson("searched trainee not found"));
                    logger.info("searched trainee not found");
                }
            } else if (url.equals("/employee_portal/trainees")) {
                logger.debug("requested URL is correct. This URL is returns all trainee details");
                List<Trainee> trainees;
                trainees = employeeService.getTraineesData();
                if (trainees == null) {
                    outputResponse(response, new Gson().toJson("trainee list is empty"));
                    logger.info("trainee list is empty");
                } else {
                    for (Trainee trainee1 : trainees) {
                        Map<String, Object> map = employeeService.getTrainee(trainee1);
                        outputResponse(response, new Gson().toJson(map));
                    }
                    logger.debug("details successfully shown");
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                logger.error("error occurs : " + HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (SQLException | IOException e) {
            logger.error("error occurs : " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getRequestURI();
        logger.debug("request URL :" + pathInfo);
        if (pathInfo.equals("/employee_portal/trainee")) {
            logger.debug("requested URL is correct. This URl create new employee profile");
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String message;
            String str = buffer.toString();
            Map<String, String> map = new Gson().fromJson(str, Map.class);
            logger.info("details successfully received from user and send it for validation");
            int valid = validationOfInputs(map, response);
            logger.info("validation successful");
            if (0 == valid) {
                logger.info("details is mapped to trainer using ObjectMapper");
                Trainer trainer = mapper.configure(SerializationFeature
                        .WRITE_DATES_AS_TIMESTAMPS, false).findAndRegisterModules()
                        .readValue(buffer.toString(), Trainer.class);
                logger.info("mapping successful");
                try {
                    logger.info("trainer object send to database");
                    message = employeeService.addTrainer(trainer);
                    outputResponse(response, new Gson().toJson(message));
                    logger.info("message from database : " + message);
                } catch (SQLException e) {
                    logger.error("error occurs : " + e);
                    throw new RuntimeException(e);
                }
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            logger.error("error occurs : " + HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getRequestURI();
        logger.debug("request URL :" + pathInfo);
        String message;
        if (pathInfo.equals("/employee_portal/trainee")) {
            logger.debug("requested URL is correct. This URl is update the exists employee profile");
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String payload = buffer.toString();
            Trainee trainee = mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false).findAndRegisterModules().readValue(payload, Trainee.class);
            try {
                message = employeeService.updateTraineeData(trainee.getTraineeId(), trainee);
                outputResponse(response, new Gson().toJson(message));
            } catch (SQLException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, String.valueOf(e));
                throw new RuntimeException(e);
            }
        } else if (pathInfo.equals("/employee_portal/assign_trainer")) {
            logger.debug("requested URL is correct. This URl create association between trainee to trainers");
            String id = request.getParameter("traineeId");
            logger.info("trainee Id for assign trainers :" + id);
            int traineeId = Integer.parseInt(id);
            try {
                logger.info("trainee id searching...in database");
                Trainee trainee = employeeService.searchTraineeData(traineeId);
                logger.info("trainee Id present...");
                String trainersId = request.getParameter("trainersList");
                logger.info("trainer Id list :" + "["+trainersId+"]");
                logger.debug("trainee Id and trainer Id's pass to create association");
                assignTrainers(trainee, trainersId, response);
                logger.debug("association successful");
            } catch (SQLException | IOException e) {
                logger.error("error occurs : " + e);
                throw new RuntimeException(e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            logger.error("error occurs : " + HttpServletResponse.SC_BAD_REQUEST);
        }

    }

    protected void assignTrainers(Trainee trainee, String trainersList, HttpServletResponse response) throws IOException, SQLException {
        String[] trainersId = trainersList.split(",");
        List<Trainer> trainers = employeeService.getTrainersData();
        try {
            logger.info("filtering trainer..... in trainers list");
            for (String s : trainersId) {
                int trainerId = Integer.parseInt(s);
                Set<Trainer> trainer = trainers.stream().filter(filterTrainer ->
                        filterTrainer.getTrainerId() == trainerId).collect(Collectors.toSet());
                List<Trainer> trainerList = new ArrayList<>(trainer);
                if (trainer.size() == 0) {
                    logger.info("couldn't found trainerId :" + trainerId + "in list");
                    outputResponse(response, new Gson().toJson("couldn't found trainer :" + trainerId + "in list"));
                } else {
                    logger.info("trainer :" + trainerId + "found in list");
                    trainee.getTrainers().add(trainerList.get(0));
                }
            }
            String message = employeeService.updateTraineeData(trainee.getTraineeId(), trainee);
            logger.info(message);
            outputResponse(response, new Gson().toJson(message));
        } catch (SQLException e) {
            logger.error("error occurs : " + e);
            response.sendError(Integer.parseInt(new Gson().toJson(e.getErrorCode())));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getRequestURI();
        logger.debug("request URL :" + pathInfo);
        String traineeId = request.getParameter("id");
        String message = "";
        if (pathInfo.equals("/employee_portal/trainee")) {
            Trainee trainee;
            int id = Integer.parseInt(traineeId);
            try {
                trainee = employeeService.searchTraineeData(id);
                if (trainee != null) {
                    message = employeeService.deleteTraineeData(id);
                    outputResponse(response, message);
                    logger.info(message);
                    Set<Trainer> trainer = trainee.getTrainers();
                    if (null != trainer) {
                        for (Trainer tr : trainer) {
                            trainer.remove(tr.getTrainerId());
                        }
                        message = employeeService.updateTraineeData(id,trainee);
                    }
                } else {
                    outputResponse(response, new Gson().toJson("no data found"));
                    logger.info("no data found");
                }
            } catch (SQLException e) {
                logger.error("error occurs : " + e);
                response.sendError(Integer.parseInt(new Gson().toJson(e.getErrorCode())));
                throw new RuntimeException(e);
            }
        } else if (pathInfo.equals("/employee_portal/un_assign_trainer")) {
            try {
                traineeId = request.getParameter("traineeId");
                String trainerId = request.getParameter("trainerId");
                Trainee trainee = employeeService.searchTraineeData(Integer.parseInt(traineeId));
                if (null != trainee) {
                    Set<Trainer> trainers = trainee.getTrainers();
                    List<Trainer> trainer = new ArrayList<>(trainers);

                    for (int i = 0; i < trainers.size(); i++) {

                        if (Integer.parseInt(trainerId) == trainer.get(i).getTrainerId()) trainer.remove(i);
                    }
                    message = employeeService.updateTraineeData(Integer.parseInt(traineeId), trainee);
                    logger.info("" + message);
                    outputResponse(response, new Gson().toJson(message));
                } else {
                    logger.info("couldn't found entered trainee or trainee");
                    outputResponse(response, new Gson().toJson(message));
                }
            } catch (SQLException e) {
                logger.error("error occurs : " + e);
                throw new RuntimeException(e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            logger.error("error occurs : " + HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * <h1> validateTraineeName </h1>
     * <p>
     * Method used to validate trainee name from user
     * </p>
     */
    public int validationOfInputs(Map<String, String> map, HttpServletResponse response) throws IOException {
        int count = 0;
        if (!employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", map.get("employeeName"))) {
            outputResponse(response, new Gson().toJson("enter valid name"));
            count++;
        }
        try {
            if (!EmployeeUtil.validationOfDateOfBirth(map.get("employeeDateOfBirth"))) {
                count++;
                outputResponse(response, new Gson().toJson("enter valid date of birth"));
            }
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            count ++;
            outputResponse(response, new Gson().toJson("enter valid date of birth"));
        }
        if (!employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", map.get("employeeDesignation"))) {
            outputResponse(response, new Gson().toJson("enter valid designation"));
            count++;
        }
        try {
            if (!EmployeeUtil.validationOfMail(map.get("employeeMail"))) {
                outputResponse(response, new Gson().toJson("enter valid mail"));
                count++;
            }
        } catch (EmailMismatchException e) {
            count ++;
            outputResponse(response, new Gson().toJson("enter valid date of email"));
        }
        if (!employeeUtil.matchRegex("^(([6-9]{1}[0-9]{9})*)$", map.get("employeeMobileNumber"))) {
            outputResponse(response, new Gson().toJson("enter valid mobile number"));
            count++;
        }
        if (!employeeUtil.matchRegex("^(([0-9\\sa-zA-Z,.-]{3,150})*)$", map.get("currentAddress"))) {
            outputResponse(response, new Gson().toJson("enter valid address"));
            count++;
        }
        if (!employeeUtil.matchRegex("^(([1-9]{1}[0-9]{11})*)$", map.get("aadharCardNumber"))) {
            outputResponse(response, new Gson().toJson("enter valid aadhar Card number"));
            count++;
        }
        if (!employeeUtil.matchRegex("^(([A-Z0-9]{10})*)$", map.get("panCardNumber"))) {
            outputResponse(response, new Gson().toJson("enter valid pan card number"));
            count++;
        }
        if (!employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", map.get("currentTask"))) {
            outputResponse(response, new Gson().toJson("enter valid current task"));
            count++;
        }
        if (!employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", map.get("currentTechknowledge"))) {
            outputResponse(response, new Gson().toJson("enter valid techknowledge"));
            count++;
        }
        return count;
    }

    private void outputResponse(HttpServletResponse response, String payload) throws IOException {
        response.setHeader("Content-Type", "application/json");
        try {
            response.setStatus(200);
            if (payload != null) {
                OutputStream outputStream = response.getOutputStream();
                outputStream.write(payload.getBytes());
                outputStream.flush();
            }
        } catch (IOException e) {
            outputResponse(response, new Gson().toJson(String.valueOf(e)));
            throw new RuntimeException(e);
        }

    }
}