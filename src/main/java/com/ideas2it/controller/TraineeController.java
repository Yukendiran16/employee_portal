package com.ideas2it.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.Gson;
import com.ideas2it.exception.EmailMismatchException;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainee;
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
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

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
    private Logger logger = LoggerFactory.getLogger(TraineeController.class);
    private ObjectMapper mapper = new ObjectMapper();
    private String payload;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String traineeId = req.getParameter("id");
        int id = Integer.parseInt(traineeId);
        if (id != 0) {
            Trainee trainee = null;
            try {
                trainee = employeeService.searchTraineeData(id);
                this.outputResponse(resp, String.valueOf(trainee), 200);
                resp.setStatus(200);
                resp.setHeader("Content-Type", "application/json");
                /*if (trainee != null) {
                    /*Gson gson = new Gson();
                    String json = gson.toJson(trainee);
                    String traineeJson = mapper.writeValueAsString(trainee);
                    resp.getOutputStream().println(json);
                    resp.getOutputStream().println(String.valueOf(trainee));
                    String stringBuilder = "\nTrainee Detail : " +
                            "\nTrainee Id          : " + trainee.getTraineeId() +
                            "\nTrainee Name        : " + trainee.getEmployeeName() +
                            "\nTrainee Designation : " + trainee.getEmployeeDesignation() +
                            "\nTrainee Mail        : " + trainee.getEmployeeMail() +
                            "\nTrainee MobileNumber: " + trainee.getEmployeeMobileNumber() +
                            "\nCurrent Address     : " + trainee.getCurrentAddress();

                    resp.getOutputStream().println(String.valueOf(stringBuilder));
                    StringBuilder stringBuilder1 = new StringBuilder();
                    stringBuilder1.append("\n\nAssigned Trainees........ :");
                    trainee.getTrainers().forEach(trainer -> {
                        stringBuilder1.append("\nTrainee Id          : ")
                                .append(trainer.getTrainerId())
                                .append("\tTrainee Name        : ")
                                .append(trainer.getEmployeeName());
                    });
                    logger.info("" + stringBuilder1);
                    resp.getOutputStream().println(String.valueOf(stringBuilder1));

                } else {
                    logger.info("no data found");
                }*/

                List<Trainee> trainees = null;
                trainees = employeeService.getTraineesData();
                if (trainees == null) {
                    logger.info("\nNo data found");
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    trainees.forEach(trainee1 -> {
                        /*String traineeJson = null;
                        try {
                            traineeJson = mapper.writeValueAsString(tr);
                            resp.getOutputStream().println(String.valueOf(traineeJson));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }*/
                        stringBuilder.append("\nTrainee Detail : ").
                                append("\nEmployee Id         : ")
                                .append(trainee1.getTraineeId())
                                .append("\nTrainee Name        : ")
                                .append(trainee1.getEmployeeName())
                                .append("\nTrainee Designation : ")
                                .append(trainee1.getEmployeeDesignation())
                                .append("\nTrainee Mail        : ")
                                .append(trainee1.getEmployeeMail())
                                .append("\nTrainee MobileNumber: ")
                                .append(trainee1.getEmployeeMobileNumber())
                                .append("\nCurrent Address     : ")
                                .append(trainee1.getCurrentAddress());});
                    logger.info("----------------------------------------------" + stringBuilder);
                    resp.getOutputStream().println(String.valueOf(stringBuilder));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void outputResponse(HttpServletResponse resp, String valueOf, int i) {
    }
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

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
                //validationOfInputs(trainee, response);
                message = employeeService.addTrainee(trainee);
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
                message = employeeService.addTrainee(trainee);
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

    /**
     * <h1> getTraineesForAssignTrainee </h1>
     * <p>
     * Method used to get trainees for assign trainee
     *
     * @param {@link Trainee} trainee
     * @return {@link List<Trainee>} returns list of trainees
     */
    /*public Trainee getTraineesForAssignTrainee(Trainee trainee) throws InputMismatchException, HibernateException, NullPointerException, SQLException {

                List<Trainee> trainees = null;

            trainees = employeeService.getTraineesData();
            List<Integer> list = traineeJson.();
                Set<Trainee> trainee = trainees.stream().
                        filter(filteredTrainee -> filteredTrainee.getTraineeId() == list[i] ).
                        collect(Collectors.toSet());
                List<Trainee> traineeList = new ArrayList<>(trainee);

                if (trainee.size() == 0) {
                    logger.info("couldn't found entered trainee");
                } else {

                    trainee.getTrainees().add(traineeList.get(0));
                }
        String message = employeeService.updateTraineeData(trainee.getTraineeId(), trainee);
        return trainee;
    }



    /**
     * <h1> unAssignAssociation </h1>
     * <p>
     * Method used to get trainee and trainee for unassign
     *
     * @return {@link } returns list nothing.
     */
    /*public void unAssignAssociation() throws InputMismatchException, SQLException, HibernateException, NullPointerException {
        Scanner scanner = new Scanner(System.in);
        logger.info("Enter 0 for stop unassign \nEnter trainee id :");
        int traineeId = scanner.nextInt();
        logger.info("Enter 0 for stop unassign \nEnter trainee id :");
        int traineeId = scanner.nextInt();
        Trainee trainee = employeeService.searchTraineeData(traineeId);

        if (0 != traineeId || 0 != traineeId) {

            if (null != trainee) {
                Set<Trainee> trainees = trainee.getTrainees();
                List<Trainee> trainee = new ArrayList<>(trainees);

                for (int i = 0; i < trainees.size(); i++) {

                    if (trainee.get(i).getTraineeId() == traineeId) {
                        trainee.remove(i);
                        String message = employeeService.updateTraineeData(traineeId, trainee);
                        logger.info("{}" + message);
                    }
                }
                unAssignAssociation();
            } else {
                logger.info("couldn't found entered trainee or trainee");
                unAssignAssociation();
            }
        }
    }*/

    /* /**
     * <h1> validateTraineeName </h1>
     * <p>
     * Method used to validate trainee name from user
     *
     * @param {@link   Trainee} trainee
     * @param trainee
     * @param response
     * @return {@link } returns nothing
     */

    /*public void validationOfInputs(Trainee trainee, HttpServletResponse response) {
        try {
            if (employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", trainee.getEmployeeName()) &&
                    employeeUtil.validationOfDateOfBirth(trainee.getEmployeeDateOfBirth()) &&
                    employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", trainee.getEmployeeDesignation()) &&
                    employeeUtil.validationOfMail(trainee.getEmployeeMail()) &&
                    employeeUtil.matchRegex("^(([6-9]{1}[0-9]{9})*)$", trainee.getEmployeeMobileNumber()) &&
                    employeeUtil.matchRegex("^(([0-9\\sa-zA-Z,.-]{3,150})*)$", trainee.getCurrentAddress()) &&
                    employeeUtil.matchRegex("^(([1-9]{1}[0-9]{11})*)$", trainee.getAadharCardNumber()) &&
                    employeeUtil.matchRegex("^(([A-Z0-9]{10})*)$", trainee.getPanCardNumber()) &&
                    employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", trainee.getCurrentProject()) &&
                    employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", trainee.getAchievement())) {

            } else {
                response.getOutputStream().println("some inputs are wrong please enter valid inputs");
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException | EmailMismatchException e) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/
}