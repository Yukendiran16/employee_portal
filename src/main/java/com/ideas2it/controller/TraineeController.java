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
public class TraineeController extends HttpServlet {
    private final EmployeeService employeeService = new EmployeeServiceImpl();
    private final EmployeeUtil employeeUtil = new EmployeeUtil();
    private final Logger logger = LoggerFactory.getLogger(TraineeController.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = request.getRequestURI();
        String traineeId = request.getParameter("id");
        Trainee trainee;
        try {
            if (url.equals("/Servlet/trainee")) {
                int id = Integer.parseInt(traineeId);
                trainee = employeeService.searchTraineeData(id);
                if (trainee != null) {
                    Map<String,Object> map = employeeService.getTrainee(trainee);
                    outputResponse(response, new Gson().toJson(map));
                    response.setStatus(200);
                    response.setHeader("Content-Type", "application/json");
                } else {
                    outputResponse(response, "no data found");
                    logger.info("no data found");
                }
            }
            if (url.equals("/Servlet/trainees")) {
                List<Trainee> trainees;
                trainees = employeeService.getTraineesData();
                if (trainees == null) {
                    outputResponse(response, "no data found");
                    logger.info("\nNo data found");
                } else {
                    for (Trainee trainee1 : trainees) {
                        Map<String,Object> map = employeeService.getTrainee(trainee1);
                        outputResponse(response, new Gson().toJson(map));
                    }
                }
            }
        } catch (SQLException | IOException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getRequestURI();
        if (pathInfo.equals("/Servlet/trainee")) {
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String payload = buffer.toString();
            Trainee trainee = mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false).findAndRegisterModules().readValue(payload, Trainee.class);
            validationOfInputs(trainee, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getRequestURI();
        String message = "";
        if (pathInfo.equals("/Servlet/trainee")) {
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String payload = buffer.toString();
            Trainee trainee = mapper.readValue(payload, Trainee.class);
            try {
                message = employeeService.updateTraineeData(trainee.getTraineeId(), trainee);
                outputResponse(response, message);
            } catch (SQLException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, String.valueOf(e));
                throw new RuntimeException(e);
            }
        } else if (pathInfo.equals("/Servlet/assign_trainer")) {
            String id = request.getParameter("traineeId");
            int traineeId = Integer.parseInt(id);
            try {
                Trainee trainee = employeeService.searchTraineeData(traineeId);
                String traineesId = request.getParameter("traineesList");
                assignTrainers(trainee, traineesId, response);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, message);
        }

    }

    protected void assignTrainers(Trainee trainee, String trainersList, HttpServletResponse response) throws IOException, SQLException {
        String[] trainersId = trainersList.split(",");
        List<Trainer> trainers = employeeService.getTrainersData();
        try {
            for (String s : trainersId) {
                int trainerId = Integer.parseInt(s);
                Set<Trainer> trainer = trainers.stream().filter(filterTrainer -> filterTrainer.getTrainerId() == trainerId).collect(Collectors.toSet());
                List<Trainer> trainerList = new ArrayList<>(trainer);
                if (trainer.size() == 0) {
                    logger.info("couldn't found entered traineeId :" + trainerId);
                    response.getOutputStream().println("couldn't found entered trainee :" + trainerId);

                } else {
                    trainee.getTrainers().add(trainerList.get(0));
                }
            }
            String message = employeeService.updateTraineeData(trainee.getTraineeId(), trainee);
            outputResponse(response, message);
        } catch (SQLException e) {
            response.sendError(e.getErrorCode());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String traineeId = request.getParameter("id");
        String pathInfo = request.getRequestURI();
        String message = "";
        if (pathInfo.equals("/Servlet/trainee")) {
            Trainee trainee;
            int id = Integer.parseInt(traineeId);
            try {
                trainee = employeeService.searchTraineeData(id);
                if (trainee != null) {
                    message = employeeService.deleteTraineeData(id);
                    outputResponse(response, message);
                } else {
                    outputResponse(response, "no data");
                }
            } catch (SQLException e) {
                response.sendError(e.getErrorCode());
                throw new RuntimeException(e);
            }
        } else if (pathInfo.equals("/Servlet/un_assign_trainer")) {
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
                    response.getOutputStream().println(message);
                } else {
                    logger.info("couldn't found entered trainee or trainee");
                    outputResponse(response, message);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            response.getOutputStream().println(message);
        }
    }

    /**
     * <h1> validateTraineeName </h1>
     * <p>
     * Method used to validate trainee name from user
     * </p>
     */
    public void validationOfInputs(Trainee trainee, HttpServletResponse response) throws IOException {
        String message;
        int count = 0;
        try {
            if (!employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", trainee.getEmployeeName())) {
                response.getOutputStream().println("enter valid name");
                count++;
            }
            if (!EmployeeUtil.validationOfDateOfBirth(String.valueOf(trainee.getEmployeeDateOfBirth()))) {
                response.getOutputStream().println("enter valid date of birth");
                count++;
            }
            if (!employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", trainee.getEmployeeDesignation())) {
                response.getOutputStream().println("enter valid designation");
                count++;
            }
            if (!EmployeeUtil.validationOfMail(trainee.getEmployeeMail())) {
                response.getOutputStream().println("enter valid mail");
                count++;
            }
            if (!employeeUtil.matchRegex("^(([6-9]{1}[0-9]{9})*)$", trainee.getEmployeeMobileNumber())) {
                response.getOutputStream().println("enter valid mobile number");
                count++;
            }
            if (!employeeUtil.matchRegex("^(([0-9\\sa-zA-Z,.-]{3,150})*)$", trainee.getCurrentAddress())) {
                response.getOutputStream().println("enter valid address");
                count++;
            }
            if (!employeeUtil.matchRegex("^(([1-9]{1}[0-9]{11})*)$", trainee.getAadharCardNumber())) {
                response.getOutputStream().println("enter valid aadhar card number");
                count++;
            }
            if (!employeeUtil.matchRegex("^(([A-Z0-9]{10})*)$", trainee.getPanCardNumber())) {
                response.getOutputStream().println("enter valid pan card number");
                count++;
            }
            if (!employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", trainee.getCurrentTask())) {
                response.getOutputStream().println("enter valid current project");
                count++;
            }
            if (!employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", trainee.getCurrentTechknowledge())) {
                response.getOutputStream().println("enter valid achievement");
                count++;
            }
            if (0 == count) {
                message = employeeService.addTrainee(trainee);
                response.getOutputStream().println(message);
            }
        } catch (NumberFormatException | SQLException | IOException | ArrayIndexOutOfBoundsException |
                 EmailMismatchException e) {
            response.getOutputStream().println(String.valueOf(e));
            throw new RuntimeException(e);
        }
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
            response.getOutputStream().println(String.valueOf(e));
            throw new RuntimeException(e);
        }

    }
}