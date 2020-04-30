package pl.pjm77.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.pjm77.DAO.ExerciseDAO;
import pl.pjm77.DAO.RealExerciseDAO;
import pl.pjm77.misc.DbUtil;
import pl.pjm77.model.Exercise;

@WebServlet("/exercisesadminpanel")
public class ExercisesAdminPanel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ExerciseDAO exerciseDAO;

    public ExercisesAdminPanel() {
        super();
    }

    public void init() {
    	exerciseDAO = new RealExerciseDAO(DbUtil.initDB());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Exercise[] exercisesList = exerciseDAO.loadAllExercises();
		request.setAttribute("exerciseslist", exercisesList);
		String errorMessage = (String) request.getAttribute("errorMessage");
		if (errorMessage != null) {
			request.setAttribute("errorMessage", errorMessage);
		}
		getServletContext().getRequestDispatcher("/jsp/exercisesadminview.jsp").forward(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}