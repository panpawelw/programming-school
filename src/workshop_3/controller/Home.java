package workshop_3.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import workshop_3.DAO.SolutionDAO;
import workshop_3.model.Solution;

/**
 * Servlet implementation class Home
 */
@WebServlet("/")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		String contextParam = getServletContext().getInitParameter("number-solutions");
//		int recentSolutions;
//		if(contextParam!=null & contextParam!="") {
//			try {
//				recentSolutions = Integer.parseInt(contextParam);
//			}catch (NumberFormatException n) {
//				System.out.println("Parameter must be an integer value, using default value of 5 instead!");
//			}
//		}
//		Solution[] lastSolutions = SolutionDAO.loadAllSolutions(5);
//		for(Solution s : lastSolutions) {
//			response.getWriter().append("<p align='center'>" + s.toString() + "</p>");
//		}
		request.setAttribute("myAttribute", "myValue");
		getServletContext().getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
//		getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
