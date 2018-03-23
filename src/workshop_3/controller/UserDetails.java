package workshop_3.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import workshop_3.DAO.LastSolutionDAO;
import workshop_3.DAO.SolutionDAO;
import workshop_3.DAO.UserDAO;
import workshop_3.DAO.UserGroupDAO;
import workshop_3.model.LastSolution;
import workshop_3.model.Solution;
import workshop_3.model.User;
import workshop_3.model.UserGroup;

@WebServlet("/userdetails")
public class UserDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserDetails() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idParam = request.getParameter("id").toString();
		int userId = 0;
		if(idParam!=null & idParam!="") {
			try {
				userId = Integer.parseInt(idParam);
			}catch(NumberFormatException e) {
				System.out.println("Incorrect user Id!");
				e.printStackTrace();
			}
		}
		User user = UserDAO.loadUserById(userId);
		request.setAttribute("user", user);
		UserGroup userGroup = UserGroupDAO.loadUserGroupById(user.getGroup_id());
		request.setAttribute("groupname", userGroup.getName());
		LastSolution[] usersSolutions = LastSolutionDAO.loadAllSolutionsByUserId(userId);
		request.setAttribute("userssolutions", usersSolutions);
		getServletContext().getRequestDispatcher("/jsp/userdetailsview.jsp").forward(request, response);
		response.getWriter().append("" + userId).append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}