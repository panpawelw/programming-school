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

@WebServlet("/deleteexercise")
public class ExercisesAdminDelete extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ExerciseDAO exerciseDAO;

    public ExercisesAdminDelete() {
        super();
    }

    public void init() {
        if(exerciseDAO == null) exerciseDAO = new RealExerciseDAO(DbUtils.initDB());
    }

    public void setExerciseDAO(ExerciseDAO exerciseDAO) {
        this.exerciseDAO = exerciseDAO;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        int exerciseId = ValidateParameter.checkInt(idParam, "Incorrect exercise Id!");
        if (exerciseId >= 0) {
            Exercise exercise = exerciseDAO.loadExerciseById(exerciseId);
            exerciseDAO.deleteExercise(exercise);
        }
        getServletContext().getRequestDispatcher("/exercisesadminpanel").forward(request, response);
    }
}