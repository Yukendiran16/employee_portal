package com.ideas2it.controller;

import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.service.EmployeeService;
import com.ideas2it.service.impl.EmployeeServiceImpl;
import org.hibernate.HibernateException;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class associationServlet extends HttpServlet {
    EmployeeService employeeService = new EmployeeServiceImpl();
    private static final Logger logger = (Logger) LoggerFactory.getLogger(associationServlet.class);

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("trainerId");
        int trainerId = Integer.parseInt(id);
        try {
            Trainer trainer = employeeService.searchTrainerData(trainerId);
            String[] traineesId = request.getParameterValues("traineesList");
            assignTrainees(trainer, traineesId, response);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    protected void assignTrainees(Trainer trainer, String[] traineesId,
                                  HttpServletResponse response) throws IOException,
            InputMismatchException, HibernateException, NullPointerException, SQLException {

        List<Trainee> trainees = employeeService.getTraineesData();
        for (String s : traineesId) {
            int traineeId = Integer.parseInt(s);
            Set<Trainee> trainee = trainees.stream().filter(filteredTrainee -> filteredTrainee.getTraineeId() == traineeId).collect(Collectors.toSet());
            List<Trainee> traineeList = new ArrayList<>(trainee);
            if (trainee.size() == 0) {
                logger.info("couldn't found entered traineeId :" + traineeId);

                response.getOutputStream().println("couldn't found entered trainee :" + traineeId);

            } else {
                trainer.getTrainees().add(traineeList.get(0));
            }
        }
        String message = employeeService.updateTrainerData(trainer.getTrainerId(), trainer);
        response.getOutputStream().println(message);
    }

    /**
     * <h1> unAssignAssociation </h1>
     * <p>
     * Method used to get trainer and trainee for unassigned
     * </p>
     */
    public void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws InputMismatchException,
            HibernateException, NullPointerException, IOException {
        try {
            String trainerId = request.getParameter("trainerId");
            String traineeId = request.getParameter("traineeId");
            Trainer trainer = employeeService.searchTrainerData(Integer.parseInt(trainerId));
            if (null != trainer) {
                Set<Trainee> trainees = trainer.getTrainees();
                List<Trainee> trainee = new ArrayList<>(trainees);

                for (int i = 0; i < trainees.size(); i++) {

                    if (Integer.parseInt(traineeId) == trainee.get(i)
                            .getTraineeId()) trainee.remove(i);
                }
                String message = employeeService.updateTrainerData
                        (Integer.parseInt(trainerId), trainer);
                logger.info("" + message);
                response.getOutputStream().println(message);
            } else {
                logger.info("couldn't found entered trainer or trainee");
                response.getOutputStream().println("couldn't found entered trainee :" + traineeId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}