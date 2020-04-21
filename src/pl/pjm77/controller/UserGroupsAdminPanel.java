package pl.pjm77.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.pjm77.DAO.UserGroupDAO;
import pl.pjm77.model.UserGroup;

@WebServlet("/groupsadminpanel")
public class UserGroupsAdminPanel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserGroupsAdminPanel() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserGroup[] groupsList = new UserGroupDAO().loadAllUserGroups();
		request.setAttribute("groupslist", groupsList);
		String errorMessage = (String) request.getAttribute("errorMessage");
		if (errorMessage != null) { // pass on error message to another view if there is any
			request.setAttribute("errorMessage", errorMessage);
		}
		getServletContext().getRequestDispatcher("/jsp/usergroupsadminview.jsp").forward(request, response);
	}
}