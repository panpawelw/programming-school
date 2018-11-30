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
		String idParam = request.getParameter("id");
		int exerciseId = ValPar.intVar(idParam, "Incorrect exercise Id!");
		if(exerciseId == 0) {
			request.setAttribute("exerciseId", exerciseId);
			request.setAttribute("exerciseTitle", "");
			request.setAttribute("exerciseDescription", null);
			request.setAttribute("button", "Add exercise");
			getServletContext().getRequestDispatcher("/jsp/exercisesadminaddeditview.jsp").forward(request, response);
		}else if(exerciseId > 0){
			Exercise exercise = ExerciseDAO.loadExerciseById(exerciseId);
			request.setAttribute("exerciseId", exerciseId);
			request.setAttribute("exerciseTitle", exercise.getTitle());
			request.setAttribute("exerciseDescription", exercise.getDescription());
			request.setAttribute("button", "Edit exercise");
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
		if(exerciseTitle!=null && !exerciseTitle.equals("") && exerciseDescription!=null && !exerciseDescription.equals("") && exerciseId >= 0) {
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
			System.out.println(exercise.toString());
			exerciseDAO.saveExerciseToDB(exercise);
		}
		System.out.println("OK ąćźż");
		getServletContext().getRequestDispatcher("/exercisesadminpanel").forward(request, response);
	}
}