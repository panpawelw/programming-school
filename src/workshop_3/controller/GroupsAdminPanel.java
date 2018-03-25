package workshop_3.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import workshop_3.DAO.UserGroupDAO;
import workshop_3.model.UserGroup;

@WebServlet("/groupsadminpanel")
public class GroupsAdminPanel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GroupsAdminPanel() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Manage groups: ").append(request.getContextPath());
		UserGroup[] groupsList = UserGroupDAO.loadAllUserGroups();
		request.setAttribute("groupslist", groupsList);
		getServletContext().getRequestDispatcher("/jsp/groupsadminview.jsp").forward(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}