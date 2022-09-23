package com.ideas2it.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.ideas2it.exception.EmailMismatchException;
import com.ideas2it.model.Trainer;
import com.ideas2it.model.Trainee;
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
public class TrainerServlet extends HttpServlet {
    private final EmployeeService employeeService = new EmployeeServiceImpl();
    private final EmployeeUtil employeeUtil = new EmployeeUtil();
    private final Logger logger = LoggerFactory.getLogger(TrainerServlet.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = request.getRequestURI();
        logger.info("request URL :" + url);
        String trainerId = request.getParameter("id");
        logger.info(trainerId + " requested trainer Id for view details.");
        Trainer trainer;
        try {
            if (url.equals("/employee_portal/trainer")) {
                logger.info("requested URL is correct. This URl is returns searched trainer details");
                int id = Integer.parseInt(trainerId);
                trainer = employeeService.searchTrainerData(id);
                logger.info("searching successful");
                if (trainer != null) {
                    Map<String, Object> map = employeeService.getTrainer(trainer);
                    outputResponse(response, new Gson().toJson(map));
                    logger.info("details successfully shown");
                    logger.info(map.toString());
                    response.setStatus(200);
                    response.setHeader("Content-Type", "application/json");
                } else {
                    outputResponse(response, new Gson().toJson("{message : searched trainer not found}"));
                    logger.info("searched trainer not found");
                }
            } else if (url.equals("/employee_portal/trainers")) {
                logger.debug("requested URL is correct. This URL is returns all trainer details");
                List<Trainer> trainers;
                trainers = employeeService.getTrainersData();
                if (trainers == null) {
                    outputResponse(response, new Gson().toJson("trainer list is empty"));
                    logger.info("trainer list is empty");
                } else {
                    for (Trainer trainer1 : trainers) {
                        Map<String, Object> map = employeeService.getTrainer(trainer1);
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
        if (pathInfo.equals("/employee_portal/trainer")) {
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
        if (pathInfo.equals("/employee_portal/trainer")) {
            logger.debug("requested URL is correct. This URl is update the exists employee profile");
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String payload = buffer.toString();
            Trainer trainer = mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false).findAndRegisterModules().readValue(payload, Trainer.class);
            try {
                message = employeeService.updateTrainerData(trainer.getTrainerId(), trainer);
                outputResponse(response, new Gson().toJson(message));
            } catch (SQLException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, String.valueOf(e));
                throw new RuntimeException(e);
            }
        } else if (pathInfo.equals("/employee_portal/assign_trainee")) {
            logger.debug("requested URL is correct. This URl create association between trainer to trainees");
            String id = request.getParameter("trainerId");
            logger.info("trainer Id for assign trainees :" + id);
            int trainerId = Integer.parseInt(id);
            try {
                logger.info("trainer id searching...in database");
                Trainer trainer = employeeService.searchTrainerData(trainerId);
                logger.info("trainer Id present...");
                String traineesId = request.getParameter("traineesList");
                logger.info("trainee Id list :" + "[" + traineesId + "]");
                logger.debug("trainer Id and trainee Id's pass to create association");
                assignTrainees(trainer, traineesId, response);
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

    protected void assignTrainees(Trainer trainer, String trainersList, HttpServletResponse response) throws IOException, SQLException {
        String[] traineesId = trainersList.split(",");
        List<Trainee> trainees = employeeService.getTraineesData();
        try {
            logger.info("filtering trainee..... in trainees list");
            for (String s : traineesId) {
                int traineeId = Integer.parseInt(s);
                Set<Trainee> trainee = trainees.stream().filter(filterTrainee ->
                        filterTrainee.getTraineeId() == traineeId).collect(Collectors.toSet());
                List<Trainee> traineeList = new ArrayList<>(trainee);
                if (trainee.size() == 0) {
                    logger.info("couldn't found traineeId :" + traineeId + "in list");
                    outputResponse(response, new Gson().toJson("couldn't found trainee :" + traineeId + "in list"));
                } else {
                    logger.info("trainee :" + traineeId + "found in list");
                    trainer.getTrainees().add(traineeList.get(0));
                }
            }
            String message = employeeService.updateTrainerData(trainer.getTrainerId(), trainer);
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
        String trainerId = request.getParameter("id");
        String message = "";
        if (pathInfo.equals("/employee_portal/trainer")) {
            Trainer trainer;
            int id = Integer.parseInt(trainerId);
            try {
                trainer = employeeService.searchTrainerData(id);
                if (trainer != null) {
                    message = employeeService.deleteTrainerData(id);
                    outputResponse(response, message);
                    logger.info(message);
                    Set<Trainee> trainee = trainer.getTrainees();
                    if (null != trainee) {
                        trainee.removeAll(trainee);
                        message = employeeService.updateTrainerData(id, trainer);
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
        } else if (pathInfo.equals("/employee_portal/un_assign_trainee")) {
            try {
                trainerId = request.getParameter("trainerId");
                String traineeId = request.getParameter("traineeId");
                Trainer trainer = employeeService.searchTrainerData(Integer.parseInt(trainerId));
                if (null != trainer) {
                    Set<Trainee> trainees = trainer.getTrainees();
                    if (!trainees.isEmpty()) {
                        List<Trainee> trainee = new ArrayList<>(trainees);
                        for (int i = 0; i < trainees.size(); i++) {

                            if (Integer.parseInt(traineeId) == trainee.get(i).getTraineeId()) trainee.remove(i);
                        }
                        message = employeeService.updateTrainerData(Integer.parseInt(trainerId), trainer);
                        logger.info("" + message);
                        outputResponse(response, new Gson().toJson(message));
                    } else {
                    logger.info("couldn't found trainee");
                    outputResponse(response, new Gson().toJson("couldn't found trainee"));
                    }
                } else {
                    logger.info("couldn't found entered trainer ");
                    outputResponse(response, new Gson().toJson("couldn't found entered trainer "));
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
     * <h1> validateTrainerName </h1>
     * <p>
     * Method used to validate trainer name from user
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
            count++;
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
            count++;
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
        if (!employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", map.get("currentProject"))) {
            outputResponse(response, new Gson().toJson("enter valid current project"));
            count++;
        }
        if (!employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", map.get("achievement"))) {
            outputResponse(response, new Gson().toJson("enter valid achievement"));
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