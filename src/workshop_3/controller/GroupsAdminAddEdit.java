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

@WebServlet("/addeditgroup")
public class GroupsAdminAddEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GroupsAdminAddEdit() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idParam = request.getParameter("id").toString();
		int groupId = ValPar.intVar(idParam, "Incorrect group Id!");
		if(groupId == 0) {
			request.setAttribute("groupId", groupId);
			request.setAttribute("groupNamePH", "New group name");
			request.setAttribute("buttonPH", "Add group");
			getServletContext().getRequestDispatcher("/jsp/groupsadminaddeditview.jsp").forward(request, response);
		}else if(groupId > 0){
			UserGroup userGroup = UserGroupDAO.loadUserGroupById(groupId);
			request.setAttribute("groupId", groupId);
			request.setAttribute("groupNamePH", userGroup.getName());
			request.setAttribute("buttonPH", "Edit group");
			getServletContext().getRequestDispatcher("/jsp/groupsadminaddeditview.jsp").forward(request, response);
		}else {
			getServletContext().getRequestDispatcher("/groupsadminpanel").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idParam = request.getParameter("id");
		String groupName = request.getParameter("name");
		int groupId = ValPar.intVar(idParam, "Incorrect group Id!");
		if(groupName!=null && groupName!="" && groupId >= 0) {
			UserGroupDAO userGroupDAO = new UserGroupDAO();
			UserGroup userGroup = new UserGroup();
			if(groupId!=0) {
				userGroup = UserGroupDAO.loadUserGroupById(groupId);
				userGroup.setName(groupName);
			}else {
				userGroup.setName(groupName);
			}
			userGroupDAO.saveUserGroupToDB(userGroup);
		}
		getServletContext().getRequestDispatcher("/groupsadminpanel").forward(request, response);
	}
}