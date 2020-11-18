package com.panpawelw.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.panpawelw.DAO.ExerciseDAO;
import com.panpawelw.DAO.RealExerciseDAO;
import com.panpawelw.misc.ValidateParameter;
import com.panpawelw.misc.DbUtils;
import com.panpawelw.model.Exercise;

@WebServlet("/addeditexercise")
public class ExercisesAdminAddEdit extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ExerciseDAO exerciseDAO;

    public ExercisesAdminAddEdit() {
        super();
    }

    public void init() {
        if(exerciseDAO == null) exerciseDAO = new RealExerciseDAO(DbUtils.initDB());
    }

    public void setExerciseDAO(ExerciseDAO exerciseDAO) {
        this.exerciseDAO = exerciseDAO;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        int exerciseId = ValidateParameter.checkInt(idParam, "Incorrect exercise Id!");
        if(exerciseId < 0) getServletContext().getRequestDispatcher("/exercisesadminpanel")
                .forward(request, response);
        if(exerciseId == 0) {
            request.setAttribute("exercise", new Exercise(0));
            request.setAttribute("button", "Add exercise");
        } else {
            request.setAttribute("exercise", exerciseDAO.loadExerciseById(exerciseId));
            request.setAttribute("button", "Edit exercise");
        }
        getServletContext().getRequestDispatcher("/jsp/exercisesadminaddeditview.jsp")
                .forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String exerciseTitle = request.getParameter("title");
        String exerciseDescription = request.getParameter("description");
        int exerciseId = ValidateParameter.checkInt(idParam, "Incorrect exercise Id!");
        if (exerciseTitle != null && !exerciseTitle.equals("") && exerciseDescription != null
                && !exerciseDescription.equals("") && exerciseId >= 0) {
            Exercise exercise = new Exercise();
            if (exerciseId != 0) {
                exercise = exerciseDAO.loadExerciseById(exerciseId);
            }
            exercise.setTitle(exerciseTitle);
            exercise.setDescription(exerciseDescription);
            exerciseDAO.saveExerciseToDB(exercise);
        } else {
            request.setAttribute("errorMessage", "Exercise title nor description can't be empty!");
        }
        getServletContext().getRequestDispatcher("/exercisesadminpanel").forward(request, response);
    }
}