package pl.pjm77.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.pjm77.DAO.ExerciseDAO;
import pl.pjm77.misc.ValPar;
import pl.pjm77.model.Exercise;

@WebServlet("/deleteexercise")
public class ExercisesAdminDelete extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ExercisesAdminDelete() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        int exerciseId = ValPar.intVar(idParam, "Incorrect exercise Id!");
        if (exerciseId >= 0) {
            ExerciseDAO exerciseDAO = new ExerciseDAO();
            Exercise exercise = new ExerciseDAO().loadExerciseById(exerciseId);
            exerciseDAO.deleteExercise(exercise);
        }
        getServletContext().getRequestDispatcher("/exercisesadminpanel").forward(request, response);
    }
}