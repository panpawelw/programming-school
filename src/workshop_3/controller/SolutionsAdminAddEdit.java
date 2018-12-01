package workshop_3.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import workshop_3.DAO.SolutionDAO;
import workshop_3.misc.ValPar;
import workshop_3.model.Solution;

@WebServlet("/addeditsolution")
public class SolutionsAdminAddEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SolutionsAdminAddEdit() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idParam = request.getParameter("id");
		int solutionId = ValPar.intVar(idParam, "Incorrect solution Id!");
		if (solutionId == 0) {
			request.setAttribute("solutionId", solutionId);
			request.setAttribute("button", "Add solution");
			request.setAttribute("solutionDescription", null);
			request.setAttribute("solutionExercise_id", 0);
			request.setAttribute("solutionUser_id", 0);
			getServletContext().getRequestDispatcher("/jsp/solutionsadminaddeditview.jsp").forward(request, response);
		} else if (solutionId > 0) {
			Solution solution = SolutionDAO.loadSolutionById(solutionId);
			request.setAttribute("solutionId", solutionId);
			request.setAttribute("button", "Edit solution");
			request.setAttribute("solutionDescription", solution.getDescription());
			request.setAttribute("solutionExercise_id", solution.getExercise_id());
			request.setAttribute("solutionUser_id", solution.getUser_id());
			getServletContext().getRequestDispatcher("/jsp/solutionsadminaddeditview.jsp").forward(request, response);
		} else {
			getServletContext().getRequestDispatcher("/solutionsadminpanel").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idParam = request.getParameter("id");
		String solutionDescription = request.getParameter("description");
		String exercise_idParam = request.getParameter("exercise_id");
		String user_idParam = request.getParameter("user_id");
		long solutionId = ValPar.longVar(idParam, "Incorrect solution Id!");
		int solutionExercise_id = ValPar.intVar(exercise_idParam, "Incorrect exercise Id!");
		long solutionUser_id = ValPar.longVar(user_idParam, "Incorrect user Id!");
		if (solutionDescription != null && !solutionDescription.equals("") && solutionExercise_id != 0 && solutionUser_id != 0 && solutionId >= 0) {
			SolutionDAO solutionDAO = new SolutionDAO();
			Solution solution = new Solution();
			if (solutionId != 0) {
				solution = SolutionDAO.loadSolutionById(solutionId);
				solution.setDescription(solutionDescription);
				solution.setExercise_id(solutionExercise_id);
				solution.setUser_id(solutionUser_id);
				Date date = new Date();
				java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
				solution.setUpdated(sqlDate);
			} else {
				solution.setDescription(solutionDescription);
				solution.setExercise_id(solutionExercise_id);
				solution.setUser_id(solutionUser_id);
				Date date = new Date();
				java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
				solution.setCreated(sqlDate);
			}
			solutionDAO.saveSolutionToDB(solution);
		} else {
			request.setAttribute("errorMessage", "Solution exercise Id, user Id nor description can't be empty!"); // detect empty fields and pass error message
		}
		getServletContext().getRequestDispatcher("/solutionsadminpanel").forward(request, response);
	}
}