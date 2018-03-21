package workshop_3.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SolutionDetails
 */
@WebServlet("/solutiondetails")
public class SolutionDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SolutionDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		String idParam = request.getParameter("id").toString();
		System.out.println(idParam);
		int solutionId = 0;
		solutionId = Integer.parseInt(idParam);
//		if(idParam!=null & idParam!="") {
//			try {
//				solutionId = Integer.parseInt("idParam");
//			}catch(NumberFormatException e) {
//				System.out.println("Incorrect solution Id!");
//				e.printStackTrace();
//			}
//		}
		response.getWriter().append("Solution Id : " + solutionId);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}