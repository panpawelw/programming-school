package programmingSchool.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import programmingSchool.DAO.SolutionDAO;
import programmingSchool.misc.ValPar;
import programmingSchool.model.Solution;

@WebServlet("/deletesolution")
public class SolutionsAdminDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SolutionsAdminDelete() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idParam = request.getParameter("id");
		long solutionId = ValPar.longVar(idParam, "Incorrect solution Id!");
		if(solutionId >= 0) {
			SolutionDAO solutionDAO=new SolutionDAO();
			Solution solution=SolutionDAO.loadSolutionById(solutionId);
			solutionDAO.deleteSolution(solution);
		}
		getServletContext().getRequestDispatcher("/solutionsadminpanel").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}