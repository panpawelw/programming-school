package pl.pjm77.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.pjm77.DAO.ExerciseDAO;
import pl.pjm77.misc.ValidateParameter;
import pl.pjm77.model.Exercise;

@WebServlet("/addeditexercise")
public class ExercisesAdminAddEdit extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ExercisesAdminAddEdit() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        int exerciseId = ValidateParameter.checkInt(idParam, "Incorrect exercise Id!");
        if (exerciseId == 0) {
            request.setAttribute("exerciseId", 0);
            request.setAttribute("exerciseTitle", null);
            request.setAttribute("exerciseDescription", null);
            request.setAttribute("button", "Add exercise");
            getServletContext().getRequestDispatcher("/jsp/exercisesadminaddeditview.jsp").forward(request, response);
        } else if (exerciseId > 0) {
            Exercise exercise = new ExerciseDAO().loadExerciseById(exerciseId);
            request.setAttribute("exerciseId", exerciseId);
            request.setAttribute("exerciseTitle", exercise.getTitle());
            request.setAttribute("exerciseDescription", exercise.getDescription());
            request.setAttribute("button", "Edit exercise");
            getServletContext().getRequestDispatcher("/jsp/exercisesadminaddeditview.jsp").forward(request, response);
        } else {
            getServletContext().getRequestDispatcher("/exercisesadminpanel").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String exerciseTitle = request.getParameter("title");
        String exerciseDescription = request.getParameter("description");
        int exerciseId = ValidateParameter.checkInt(idParam, "Incorrect exercise Id!");
        if (exerciseTitle != null && !exerciseTitle.equals("") && exerciseDescription != null && !exerciseDescription.equals("") && exerciseId >= 0) {
            ExerciseDAO exerciseDAO = new ExerciseDAO();
            Exercise exercise = new Exercise();
            if (exerciseId != 0) {
                exercise = new ExerciseDAO().loadExerciseById(exerciseId);
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