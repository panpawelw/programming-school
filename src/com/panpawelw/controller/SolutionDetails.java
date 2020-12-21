package com.panpawelw.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.panpawelw.DAO.*;
import com.panpawelw.misc.DbUtils;
import com.panpawelw.misc.ValidateParameter;
import com.panpawelw.model.Exercise;
import com.panpawelw.model.Solution;
import com.panpawelw.model.User;

@WebServlet("/solutiondetails")
public class SolutionDetails extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;
    private ExerciseDAO exerciseDAO;
    private SolutionDAO solutionDAO;

    public SolutionDetails() {
        super();
    }

    public void init() {
        if(userDAO == null) userDAO = new RealUserDAO(DbUtils.initDB());
        if(exerciseDAO == null) exerciseDAO = new RealExerciseDAO(DbUtils.initDB());
        if(solutionDAO == null) solutionDAO = new RealSolutionDAO(DbUtils.initDB());
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    public void setExerciseDAO(ExerciseDAO exerciseDAO) {
        this.exerciseDAO = exerciseDAO;
    }
    public void setSolutionDAO(SolutionDAO solutionDAO) {
        this.solutionDAO = solutionDAO;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        long solutionId = ValidateParameter.checkLong(idParam, "Incorrect solution Id!");
        if (solutionId > 0) {
            Solution solution = solutionDAO.loadSolutionById(solutionId);
            if (solution != null) {
                User user = userDAO.loadUserById(solution.getUser_id());
                Exercise exercise = exerciseDAO.loadExerciseById(solution.getExercise_id());
                request.setAttribute("solution", solution);
                request.setAttribute("user", user);
                request.setAttribute("exercise", exercise);
                getServletContext().getRequestDispatcher("/jsp/solutiondetailsview.jsp").forward(request, response);
                return;
            }
        }
        request.setAttribute("errormessage", "No such solution exists!");
        getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
    }
}
