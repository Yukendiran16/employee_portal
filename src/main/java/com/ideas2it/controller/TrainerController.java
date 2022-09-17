package com.ideas2it.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.Gson;
import com.ideas2it.exception.EmailMismatchException;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.service.EmployeeService;
import com.ideas2it.service.impl.EmployeeServiceImpl;
import com.ideas2it.util.EmployeeUtil;
import org.hibernate.HibernateException;
import org.json.JSONObject;
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
    private final Logger logger = LoggerFactory.getLogger(TrainerController.class);
    private final ObjectMapper mapper = new ObjectMapper();
    private String payload;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String trainerId = req.getParameter("id");
        int id = Integer.parseInt(trainerId);
        try {
            if (id != 0) {
                Trainer trainer = null;

                trainer = employeeService.searchTrainerData(id);
                this.outputResponse(resp, String.valueOf(trainer), 200);
                resp.setStatus(200);
                resp.setHeader("Content-Type", "application/json");
                if (trainer != null) {
                    String jsonInString = new Gson().toJson(employeeService.toString()+trainer.toString());
                    JSONObject JSONObject = new JSONObject(jsonInString);
                    //resp.getOutputStream().println(jsonInString);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("\nTrainer Detail : ")
                            .append("\nTrainer Id          : ")
                            .append(trainer.getTrainerId())
                            .append("\nTrainer Name        : ")
                            .append(trainer.getEmployeeName())
                            .append("\nTrainer Designation : ")
                            .append(trainer.getEmployeeDesignation())
                            .append("\nTrainer Mail        : ")
                            .append(trainer.getEmployeeMail())
                            .append("\nTrainer MobileNumber: ")
                            .append(trainer.getEmployeeMobileNumber())
                            .append("\nCurrent Address     : ")
                            .append(trainer.getCurrentAddress());

                    resp.getOutputStream().println(String.valueOf(stringBuilder));
                    StringBuilder stringBuilder1 = new StringBuilder();
                    stringBuilder1.append("\n\nAssigned Trainees........ :");
                    trainer.getTrainees().forEach(trainee -> {
                        stringBuilder1.append("\nTrainee Id          : ")
                                .append(trainee.getTraineeId())
                                .append("\tTrainee Name        : ")
                                .append(trainee.getEmployeeName());
                    });
                    logger.info("" + stringBuilder1);
                    resp.getOutputStream().println(String.valueOf(stringBuilder1));

                } else {
                    logger.info("no data found");
                }
            } else {
                List<Trainer> trainers = null;
                trainers = employeeService.getTrainersData();
                if (trainers == null) {
                    logger.info("\nNo data found");
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    trainers.forEach(trainer1 -> {
                        /*String trainerJson = null;
                        try {
                            trainerJson = mapper.writeValueAsString(tr);
                            resp.getOutputStream().println(String.valueOf(trainerJson));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }*/
                        stringBuilder.append("\nTrainer Detail : ").
                                append("\nEmployee Id         : ")
                                .append(trainer1.getTrainerId())
                                .append("\nTrainer Name        : ")
                                .append(trainer1.getEmployeeName())
                                .append("\nTrainer Designation : ")
                                .append(trainer1.getEmployeeDesignation())
                                .append("\nTrainer Mail        : ")
                                .append(trainer1.getEmployeeMail())
                                .append("\nTrainer MobileNumber: ")
                                .append(trainer1.getEmployeeMobileNumber())
                                .append("\nCurrent Address     : ")
                                .append(trainer1.getCurrentAddress());
                    });
                    logger.info("----------------------------------------------" + stringBuilder);
                    resp.getOutputStream().println(String.valueOf(stringBuilder));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
            Trainer trainer = mapper.readValue(payload, Trainer.class);
            try {
                //validationOfInputs(trainer, response);
                message = employeeService.addTrainer(trainer);
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
            Trainer trainer = mapper.readValue(payload, Trainer.class);
            try {
                message = employeeService.addTrainer(trainer);
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

    public EmployeeUtil getEmployeeUtil() {
        return employeeUtil;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    /**
     * <h1> getTraineesForAssignTrainer </h1>
     * <p>
     * Method used to get trainees for assign trainer
     *
     * @param {@link Trainer} trainer
     * @return {@link List<Trainee>} returns list of trainees
     */
    /*public Trainer getTraineesForAssignTrainer(Trainer trainer) throws InputMismatchException, HibernateException, NullPointerException, SQLException {

                List<Trainee> trainees = null;

            trainees = employeeService.getTraineesData();
            List<Integer> list = trainerJson.();
                Set<Trainee> trainee = trainees.stream().
                        filter(filteredTrainee -> filteredTrainee.getTraineeId() == list[i] ).
                        collect(Collectors.toSet());
                List<Trainee> traineeList = new ArrayList<>(trainee);

                if (trainee.size() == 0) {
                    logger.info("couldn't found entered trainee");
                } else {

                    trainer.getTrainees().add(traineeList.get(0));
                }
        String message = employeeService.updateTrainerData(trainer.getTrainerId(), trainer);
        return trainer;
    }



    /**
     * <h1> unAssignAssociation </h1>
     * <p>
     * Method used to get trainer and trainee for unassign
     *
     * @return {@link } returns list nothing.
     */
    /*public void unAssignAssociation() throws InputMismatchException, SQLException, HibernateException, NullPointerException {
        Scanner scanner = new Scanner(System.in);
        logger.info("Enter 0 for stop unassign \nEnter trainer id :");
        int trainerId = scanner.nextInt();
        logger.info("Enter 0 for stop unassign \nEnter trainee id :");
        int traineeId = scanner.nextInt();
        Trainer trainer = employeeService.searchTrainerData(trainerId);

        if (0 != trainerId || 0 != traineeId) {

            if (null != trainer) {
                Set<Trainee> trainees = trainer.getTrainees();
                List<Trainee> trainee = new ArrayList<>(trainees);

                for (int i = 0; i < trainees.size(); i++) {

                    if (trainee.get(i).getTraineeId() == traineeId) {
                        trainee.remove(i);
                        String message = employeeService.updateTrainerData(trainerId, trainer);
                        logger.info("{}" + message);
                    }
                }
                unAssignAssociation();
            } else {
                logger.info("couldn't found entered trainer or trainee");
                unAssignAssociation();
            }
        }
    }*/

    /* /**
     * <h1> validateTrainerName </h1>
     * <p>
     * Method used to validate trainer name from user
     *
     * @param {@link   Trainer} trainer
     * @param trainer
     * @param response
     * @return {@link } returns nothing
     */

    /*;public void validationOfInputs(Trainer trainer, HttpServletResponse response) {
        try {
            if (employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", trainer.getEmployeeName()) &&
                    employeeUtil.validationOfDateOfBirth(trainer.getEmployeeDateOfBirth()) &&
                    employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", trainer.getEmployeeDesignation()) &&
                    employeeUtil.validationOfMail(trainer.getEmployeeMail()) &&
                    employeeUtil.matchRegex("^(([6-9]{1}[0-9]{9})*)$", trainer.getEmployeeMobileNumber()) &&
                    employeeUtil.matchRegex("^(([0-9\\sa-zA-Z,.-]{3,150})*)$", trainer.getCurrentAddress()) &&
                    employeeUtil.matchRegex("^(([1-9]{1}[0-9]{11})*)$", trainer.getAadharCardNumber()) &&
                    employeeUtil.matchRegex("^(([A-Z0-9]{10})*)$", trainer.getPanCardNumber()) &&
                    employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", trainer.getCurrentProject()) &&
                    employeeUtil.matchRegex("^(([a-z\\sA-Z_]{3,50})*)$", trainer.getAchievement())) {

            } else {
                response.getOutputStream().println("some inputs are wrong please enter valid inputs");
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException | EmailMismatchException e) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/
}