package com.ideas2it.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ideas2it.exception.EmailMismatchException;
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
import java.sql.SQLException;
import java.util.List;

/**
 * <h2> TrainerController </h2>
 * <p>
 * The TrainerController class is an application that
 * do the main operation create, read, update, delete
 * if one will be choosen the respect operation performs
 * until user exit the operation is same for all operation
 * </p>
 *
 * @author Yukendiran K
 * @version 1.0
 * @since 2022-08-04
 */
public class TrainerController extends HttpServlet {
    private final EmployeeService employeeService = new EmployeeServiceImpl();
    private final EmployeeUtil employeeUtil = new EmployeeUtil();
    private final Logger logger = LoggerFactory
            .getLogger(TrainerController.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) {
        String trainerId = req.getParameter("id");
        int id = Integer.parseInt(trainerId);
        Trainer trainer = null;
        try {
            if (id != 0) {
                trainer = employeeService.searchTrainerData(id);
                this.outputResponse(resp, String.valueOf(trainer), 200);
                resp.setStatus(200);
                resp.setHeader("Content-Type", "application/json");
                if (trainer != null) {
                    String object = trainer.toString();
                    logger.info("" + object);
                    resp.getOutputStream().println(String.valueOf(object));
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("\n\nAssigned Trainees........ :");
                    trainer.getTrainees().forEach(trainee -> {
                        stringBuilder.append("\nTrainee Id          : ")
                                .append(trainee.getTraineeId())
                                .append("\tTrainee Name        : ")
                                .append(trainee.getEmployeeName());
                    });
                    logger.info("" + stringBuilder);
                    resp.getOutputStream().println(String.valueOf(stringBuilder));

                } else {
                    resp.getOutputStream().println("no data found");
                    logger.info("no data found");
                }
            } else {
                List<Trainer> trainers = null;
                trainers = employeeService.getTrainersData();
                if (trainers == null) {
                    resp.getOutputStream().println("no data found");
                    logger.info("\nNo data found");
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    trainers.forEach(trainer1 -> {
                        String object = trainer1.toString();
                        try {
                            resp.getOutputStream().println(String.valueOf(object));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String payload = buffer.toString();
            Trainer trainer = mapper.readValue(payload, Trainer.class);
            validationOfInputs(trainer, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
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
            Trainer trainer = mapper.readValue(payload, Trainer.class);
            try {
                message = employeeService.updateTrainerData(trainer.getTrainerId(),trainer);
                response.getOutputStream().println(message);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            response.getOutputStream().println(message);
        }

    }
    @Override
    protected void doDelete(HttpServletRequest request,
                            HttpServletResponse response) throws IOException {
        String trainerId = request.getParameter("id");
        int id = Integer.parseInt(trainerId);
        String message = "";
        if (id != 0) {
            Trainer trainer = null;
            try {
                trainer = employeeService.searchTrainerData(id);
                if (trainer != null) {
                    message = employeeService.deleteTrainerData(id);
                    response.getOutputStream().println(message);
                } else {
                    response.getOutputStream().println(message);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * <h1> validateTrainerName </h1>
     * <p>
     * Method used to validate trainer name from user
     *
     * @param {@link   Trainer} trainer
     * @param trainer
     * @param response
     * @return {@link } returns nothing
     */
    public void validationOfInputs(Trainer trainer,
                                   HttpServletResponse response) {
        String message = "";
        try {
            if (employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", trainer.getEmployeeName()) && EmployeeUtil.validationOfDateOfBirth(trainer.getEmployeeDateOfBirth()) && employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", trainer.getEmployeeDesignation()) && EmployeeUtil.validationOfMail(trainer.getEmployeeMail()) && employeeUtil.matchRegex("^(([6-9]{1}[0-9]{9})*)$", trainer.getEmployeeMobileNumber()) && employeeUtil.matchRegex("^(([0-9\\sa-zA-Z,.-]{3,150})*)$", trainer.getCurrentAddress()) && employeeUtil.matchRegex("^(([1-9]{1}[0-9]{11})*)$", trainer.getAadharCardNumber()) && employeeUtil.matchRegex("^(([A-Z0-9]{10})*)$", trainer.getPanCardNumber()) && employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", trainer.getCurrentProject()) && employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", trainer.getAchievement())) {
                message = employeeService.addTrainer(trainer);
                response.getOutputStream().println(message);
            } else {
                response.getOutputStream().println("some inputs are wrong please enter valid inputs");
            }
        } catch (NumberFormatException | SQLException | IOException | ArrayIndexOutOfBoundsException |
                 EmailMismatchException e) {
            throw new RuntimeException(e);
        }
    }
    private void outputResponse(HttpServletResponse resp, String valueOf, int i) {
    }
}