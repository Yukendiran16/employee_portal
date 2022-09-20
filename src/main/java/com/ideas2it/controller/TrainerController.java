package com.ideas2it.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ideas2it.exception.EmailMismatchException;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.service.EmployeeService;
import com.ideas2it.service.impl.EmployeeServiceImpl;
import com.ideas2it.util.EmployeeUtil;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.*;
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
public class TrainerController extends HttpServlet {
    private final EmployeeService employeeService = new EmployeeServiceImpl();
    private final EmployeeUtil employeeUtil = new EmployeeUtil();
    private final Logger logger = LoggerFactory.getLogger(TrainerController.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = request.getRequestURI();
        String trainerId = request.getParameter("id");
        Trainer trainer;
        try {
            if (url.equals("/Servlet/trainer")) {
                int id = Integer.parseInt(trainerId);
                trainer = employeeService.searchTrainerData(id);
                if (trainer != null) {
                    String trainerJson = mapper.writeValueAsString(trainer);
                    outputResponse(response, new Gson().toJson(trainerJson));
                    response.setStatus(200);
                    response.setHeader("Content-Type", "application/json");
                } else {
                    outputResponse(response,"no data found");
                    logger.info("no data found");
                }
            }
            if (url.equals("/Servlet/trainers")) {
                List<Trainer> trainers;
                trainers = employeeService.getTrainersData();
                if (trainers == null) {
                    outputResponse(response, "no data found");
                    logger.info("\nNo data found");
                } else {
                    for (Trainer trainer1 : trainers) {;
                        String trainerJson = mapper.writeValueAsString(trainer1);
                        outputResponse(response, new Gson().toJson(trainerJson));
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
        if (pathInfo.equals("/Servlet/trainer")) {
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
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getRequestURI();
        String message = "";
        if (pathInfo.equals("/Servlet/trainer")) {
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String payload = buffer.toString();
            Trainer trainer = mapper.readValue(payload, Trainer.class);
            try {
                message = employeeService.updateTrainerData(trainer.getTrainerId(), trainer);
                outputResponse(response,message);
            } catch (SQLException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, String.valueOf(e));
                throw new RuntimeException(e);
            }
        } else if (pathInfo.equals("/Servlet/assign_trainee")) {
            String id = request.getParameter("trainerId");
            int trainerId = Integer.parseInt(id);
            try {
                Trainer trainer = employeeService.searchTrainerData(trainerId);
                String traineesId = request.getParameter("traineesList");
                assignTrainees(trainer, traineesId, response);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,message);
        }

    }

    protected void assignTrainees(Trainer trainer, String traineesList, HttpServletResponse response) throws IOException, InputMismatchException, HibernateException, NullPointerException, SQLException {
        String[] traineesId = traineesList.split(",");
        List<Trainee> trainees = employeeService.getTraineesData();
        Map<Integer,String> listOfTrainees = new HashMap<>();
        for (String s : traineesId) {
            int traineeId = Integer.parseInt(s);
            trainees.forEach(trainee -> { trainees
                    .stream().filter(x->x.getTraineeId() == traineeId)
                    .collect(Collectors.toList());
                listOfTrainees.put(trainee.getTraineeId(),trainee.getEmployeeName());
            });
            String message = employeeService.updateTrainerData(trainer.getTrainerId(), trainer);
        }
        String message = employeeService.updateTrainerData(trainer.getTrainerId(), trainer);
        outputResponse(response,message);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String trainerId = request.getParameter("id");
        String pathInfo = request.getRequestURI();
        String message = "";
        if (pathInfo.equals("/Servlet/trainer")) {
            Trainer trainer;
            int id = Integer.parseInt(trainerId);
            try {
                trainer = employeeService.searchTrainerData(id);
                if (trainer != null) {
                    message = employeeService.deleteTrainerData(id);
                    outputResponse(response,message);
                } else {
                    outputResponse(response,"no data");
                }
            } catch (SQLException e) {
                response.sendError(e.getErrorCode());
                throw new RuntimeException(e);
            }
        } else if (pathInfo.equals("/Servlet/un_assign_trainee")) {
            try {
                trainerId = request.getParameter("trainerId");
                String traineeId = request.getParameter("traineeId");
                Trainer trainer = employeeService.searchTrainerData(Integer.parseInt(trainerId));
                if (null != trainer) {
                    Set<Trainee> trainees = trainer.getTrainees();
                    List<Trainee> trainee = new ArrayList<>(trainees);

                    for (int i = 0; i < trainees.size(); i++) {

                        if (Integer.parseInt(traineeId) == trainee.get(i)
                                .getTraineeId()) trainee.remove(i);
                    }
                    message = employeeService.updateTrainerData(Integer.parseInt(trainerId), trainer);
                    logger.info("" + message);
                    response.getOutputStream().println(message);
                } else {
                    logger.info("couldn't found entered trainer or trainee");
                    response.getOutputStream().println("couldn't found entered trainee :" + traineeId);
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
     * <h1> validateTrainerName </h1>
     * <p>
     * Method used to validate trainer name from user
     * </p>
     *
     */
    public void validationOfInputs(Trainer trainer, HttpServletResponse response) throws IOException {
        String message;
        int count = 0;
        try {
            if (!employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", trainer.getEmployeeName())) {
                response.getOutputStream().println("enter valid name");
                count++;
            }
            if (!EmployeeUtil.validationOfDateOfBirth(trainer.getEmployeeDateOfBirth())) {
                response.getOutputStream().println("enter valid date of birth");
                count++;
            }
            if (!employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", trainer.getEmployeeDesignation())) {
                response.getOutputStream().println("enter valid designation");
                count++;
            }
            if (!EmployeeUtil.validationOfMail(trainer.getEmployeeMail())) {
                response.getOutputStream().println("enter valid mail");
                count++;
            }
            if (!employeeUtil.matchRegex("^(([6-9]{1}[0-9]{9})*)$", trainer.getEmployeeMobileNumber())) {
                response.getOutputStream().println("enter valid mobile number");
                count++;
            }
            if (!employeeUtil.matchRegex("^(([0-9\\sa-zA-Z,.-]{3,150})*)$", trainer.getCurrentAddress())) {
                response.getOutputStream().println("enter valid address");
                count++;
            }
            if (!employeeUtil.matchRegex("^(([1-9]{1}[0-9]{11})*)$", trainer.getAadharCardNumber())) {
                response.getOutputStream().println("enter valid aadhar Card number");
                count++;
            }
            if (!employeeUtil.matchRegex("^(([A-Z0-9]{10})*)$", trainer.getPanCardNumber())) {
                response.getOutputStream().println("enter valid pan card number");
                count++;
            }
            if (!employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", trainer.getCurrentProject())) {
                response.getOutputStream().println("enter valid current project");
                count++;
            }
            if (!employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", trainer.getAchievement())) {
                response.getOutputStream().println("enter valid achievement");
                count++;
            }
            if (0 == count) {
                message = employeeService.addTrainer(trainer);
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