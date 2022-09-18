package com.ideas2it.controller;

import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.service.EmployeeService;
import com.ideas2it.service.impl.EmployeeServiceImpl;
import org.hibernate.HibernateException;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class associationServlet extends HttpServlet {
    EmployeeService employeeService = new EmployeeServiceImpl();
    private static Logger logger = (Logger) LoggerFactory.getLogger(associationServlet.class);
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("trainerId");
        int trainerId = Integer.valueOf(id);
        try {
            Trainer trainer = employeeService.searchTrainerData(trainerId);
        String[] traineesId = request.getParameterValues("traineesList");
        assignTrainees(trainer,traineesId,response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    protected void assignTrainees(Trainer trainer, String[] traineesId, HttpServletResponse response) throws InputMismatchException,
            HibernateException, NullPointerException, SQLException {

        List<Trainee> trainees = employeeService.getTraineesData();
        for (int i = 0; i < traineesId.length; i++) {
            int traineeId = Integer.parseInt(traineesId[i]);
            Set<Trainee> trainee = trainees.stream().
                    filter(filteredTrainee -> filteredTrainee.getTraineeId() == traineeId).
                    collect(Collectors.toSet());
            List<Trainee> traineeList = new ArrayList<>(trainee);
            if (trainee.size() == 0) {
                logger.info("couldn't found entered traineeId :" + traineeId);
                response.getOutputStream.println("couldn't found entered trainee :");
            } else {

                trainer.getTrainees().add(traineeList.get(0));
            }
        }
        String message = employeeService.updateTrainerData(trainer.getTrainerId(), trainer);
    }

/**
 * <h1> unAssignAssociation </h1>
 * <p>
 * Method used to get trainer and trainee for unassign
 *
 * @return {@link } returns list nothing.
 */
    /*public void unAssignAssociation() throws InputMismatchException,
    SQLException, HibernateException, NullPointerException {
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
    }
}
