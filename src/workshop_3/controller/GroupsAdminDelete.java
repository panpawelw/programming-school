package workshop_3.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import workshop_3.DAO.UserGroupDAO;
import workshop_3.misc.ValPar;
import workshop_3.model.UserGroup;

@WebServlet("/deletegroup")
public class GroupsAdminDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GroupsAdminDelete() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idParam = request.getParameter("id");
		int groupId = ValPar.intVar(idParam, "Incorrect group Id!");
		if(groupId >= 0) {
			UserGroupDAO userGroupDAO=new UserGroupDAO();
			UserGroup userGroup=UserGroupDAO.loadUserGroupById(groupId);
			userGroupDAO.deleteUserGroup(userGroup);
		}
		getServletContext().getRequestDispatcher("/groupsadminpanel").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}