package com.panpawelw.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.panpawelw.DAO.*;
import com.panpawelw.misc.DbUtils;
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
        userDAO = new RealUserDAO(DbUtils.initDB());
        exerciseDAO = new RealExerciseDAO(DbUtils.initDB());
        solutionDAO = new RealSolutionDAO(DbUtils.initDB());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        long solutionId = 0;
        if (idParam != null && !idParam.equals("")) {
            try {
                solutionId = Long.parseLong(idParam);
            } catch (NumberFormatException e) {
                System.out.println("Incorrect solution Id!");
                e.printStackTrace();
            }
        }
        response.getWriter().append("Solution Id : ").append(String.valueOf(solutionId));
        Solution solution = solutionDAO.loadSolutionById(solutionId);
        request.setAttribute("solution", solution);
        User user = userDAO.loadUserById(solution.getUser_id());
        request.setAttribute("user", user);
        Exercise exercise = exerciseDAO.loadExerciseById(solution.getExercise_id());
        request.setAttribute("exercise", exercise);
        getServletContext().getRequestDispatcher("/jsp/solutiondetailsview.jsp").forward(request, response);
    }
}