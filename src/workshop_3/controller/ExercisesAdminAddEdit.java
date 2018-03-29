package workshop_3.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import workshop_3.DAO.ExerciseDAO;
import workshop_3.misc.ValPar;
import workshop_3.model.Exercise;

@WebServlet("/addeditexercise")
public class ExercisesAdminAddEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExercisesAdminAddEdit() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idParam = request.getParameter("id").toString();
		int exerciseId = ValPar.intVar(idParam, "Incorrect exercise Id!");
		if(exerciseId == 0) {
			request.setAttribute("exerciseId", exerciseId);
			request.setAttribute("exerciseTitlePH", "New exercise title");
			request.setAttribute("exerciseDescriptionPH", "New exercise description");
			request.setAttribute("buttonPH", "Add exercise");
			getServletContext().getRequestDispatcher("/jsp/exercisesadminaddeditview.jsp").forward(request, response);
		}else if(exerciseId > 0){
			Exercise exercise = ExerciseDAO.loadExerciseById(exerciseId);
			request.setAttribute("exerciseId", exerciseId);
			request.setAttribute("exerciseTitlePH", exercise.getTitle());
			request.setAttribute("exerciseDescriptionPH", exercise.getDescription());
			request.setAttribute("buttonPH", "Edit exercise");
			getServletContext().getRequestDispatcher("/jsp/exercisesadminaddeditview.jsp").forward(request, response);
		}else {
			getServletContext().getRequestDispatcher("/exercisesadminpanel").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idParam = request.getParameter("id");
		String exerciseTitle = request.getParameter("title");
		String exerciseDescription = request.getParameter("description");
		int exerciseId = ValPar.intVar(idParam, "Incorrect exercise Id!");
		if(exerciseTitle!=null && exerciseTitle!="" && exerciseDescription!=null && exerciseDescription!="" && exerciseId >= 0) {
			ExerciseDAO exerciseDAO = new ExerciseDAO();
			Exercise exercise = new Exercise();
			if(exerciseId!=0) {
				exercise = ExerciseDAO.loadExerciseById(exerciseId);
				exercise.setTitle(exerciseTitle);
				exercise.setDescription(exerciseDescription);
			}else {
				exercise.setTitle(exerciseTitle);
				exercise.setDescription(exerciseDescription);
			}
			exerciseDAO.saveExerciseToDB(exercise);
		}
		getServletContext().getRequestDispatcher("/exercisesadminpanel").forward(request, response);
	}
}