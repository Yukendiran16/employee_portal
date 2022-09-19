package com.ideas2it.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ideas2it.exception.EmailMismatchException;
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
import java.util.List;

/**
 * <h2> TraineeController </h2>
 * <p>
 * The TraineeController class is an application that
 * do the main operation create, read, update, delete
 * if one will be choosen the respect operation performs
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
    private String payload;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        String traineeId = request.getParameter("id");
        int id = Integer.parseInt(traineeId);
        try {
        if (id != 0) {
            Trainee trainee = null;

                trainee = employeeService.searchTraineeData(id);
                this.outputResponse(response, String.valueOf(trainee), 200);
                response.setStatus(200);
                response.setHeader("Content-Type", "application/json");
                if (null != trainee) {
                    String object = trainee.toString();
                    logger.info("" + object);
                    response.getOutputStream().println(String.valueOf(object));
                    StringBuilder stringBuilder1 = new StringBuilder();
                    stringBuilder1.append("\n\nAssigned Trainees........ :");
                    trainee.getTrainers().forEach(trainer -> {
                        stringBuilder1.append("\nTrainee Id          : ")
                                .append(trainer.getTrainerId())
                                .append("\tTrainee Name        : ")
                                .append(trainer.getEmployeeName());
                    });
                    logger.info("" + stringBuilder1);
                    response.getOutputStream().println(String.valueOf(stringBuilder1));
                } else {
                    response.getOutputStream().println("no data found");
                    logger.info("no data found");
                }
            }else {
                List<Trainee> trainees = null;
                trainees = employeeService.getTraineesData();
                if (trainees == null) {
                    response.getOutputStream().println("no data found");
                    logger.info("\nNo data found");
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    trainees.forEach(trainer1 -> {
                        String object = trainer1.toString();
                        try {
                            response.getOutputStream().println(String.valueOf(object));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
        }
        } catch (SQLException | IOException e) {
            response.getOutputStream().println(String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String payload = buffer.toString();
            Trainee trainee = mapper.readValue(payload, Trainee.class);
            validationOfInputs(trainee, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        String message = "";
        if (pathInfo == null || pathInfo.equals("/")) {
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
                response.getOutputStream().println(message);
            } catch (SQLException e) {
                response.getOutputStream().println(String.valueOf(e));
                throw new RuntimeException(e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            response.getOutputStream().println(message);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String traineeId = request.getParameter("id");
        int id = Integer.parseInt(traineeId);
        String message = "";
        if (id != 0) {
            Trainee trainee = null;
            try {
                trainee = employeeService.searchTraineeData(id);
                if (trainee != null) {
                    message = employeeService.deleteTraineeData(id);
                    response.getOutputStream().println(message);
                } else {
                    response.getOutputStream().println(message);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void validationOfInputs(Trainee trainee, HttpServletResponse response) {
        try {
            if (employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", trainee.getEmployeeName()) &&
                    EmployeeUtil.validationOfDateOfBirth(trainee.getEmployeeDateOfBirth()) &&
                    employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", trainee.getEmployeeDesignation()) &&
                    EmployeeUtil.validationOfMail(trainee.getEmployeeMail()) &&
                    employeeUtil.matchRegex("^(([6-9]{1}[0-9]{9})*)$", trainee.getEmployeeMobileNumber()) &&
                    employeeUtil.matchRegex("^(([0-9\\sa-zA-Z,.-]{3,150})*)$", trainee.getCurrentAddress()) &&
                    employeeUtil.matchRegex("^(([1-9]{1}[0-9]{11})*)$", trainee.getAadharCardNumber()) &&
                    employeeUtil.matchRegex("^(([A-Z0-9]{10})*)$", trainee.getPanCardNumber()) &&
                    employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", trainee.getCurrentTask()) &&
                    employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", trainee.getCurrentTechknowledge())) {
                String message = employeeService.addTrainee(trainee);
                response.getOutputStream().println(message);
            } else {
                response.getOutputStream().println("some inputs are wrong please enter valid inputs");
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException | EmailMismatchException e) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void outputResponse(HttpServletResponse response, String payload, int status) throws IOException {
        response.setHeader("Content-Type", "application/json");
        try {
            response.setStatus(status);
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