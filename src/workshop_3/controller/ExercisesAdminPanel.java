package workshop_3.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import workshop_3.DAO.ExerciseDAO;
import workshop_3.model.Exercise;

@WebServlet("/exercisesadminpanel")
public class ExercisesAdminPanel extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public ExercisesAdminPanel() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Exercise[] exercisesList = ExerciseDAO.loadAllExercises();
		request.setAttribute("exerciseslist", exercisesList);
		String errorMessage = (String) request.getAttribute("errorMessage");
		if (errorMessage != null) { // pass on error message to another view if there is any
			request.setAttribute("errorMessage", errorMessage);
		}
		getServletContext().getRequestDispatcher("/jsp/exercisesadminview.jsp").forward(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}