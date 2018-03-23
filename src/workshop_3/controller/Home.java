package workshop_3.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import workshop_3.DAO.LastSolutionDAO;
import workshop_3.model.LastSolution;

@WebServlet("/")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Home() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String contextParam = getServletContext().getInitParameter("number-solutions");
		int recentSolutions=5;
		if(contextParam!=null & contextParam!="") {
			try {
				recentSolutions = Integer.parseInt(contextParam);
			}catch (NumberFormatException n) {
				System.out.println("Parameter must be an integer value, using default value of 5 instead!");
			}
		}
		LastSolution[] lastSolutions = LastSolutionDAO.loadAllSolutions(recentSolutions);
		request.setAttribute("lastsolutions", lastSolutions);
		getServletContext().getRequestDispatcher("/jsp/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}