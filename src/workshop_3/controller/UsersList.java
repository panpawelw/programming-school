package workshop_3.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import workshop_3.DAO.UserDAO;
import workshop_3.DAO.UserGroupDAO;
import workshop_3.model.User;
import workshop_3.model.UserGroup;

@WebServlet("/userslist")
public class UsersList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UsersList() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idParam = request.getParameter("id");
		int userGroupId = 0;
		if (idParam != null && !idParam.equals("")) {
			try {
				userGroupId = Integer.parseInt(idParam);
			} catch (NumberFormatException e) {
				System.out.println("Incorrect group Id!");
				e.printStackTrace();
			}
		}
		User[] GroupUsersList = UserDAO.loadAllbyGroupId(userGroupId);
		request.setAttribute("groupuserslist", GroupUsersList);
		UserGroup userGroup = UserGroupDAO.loadUserGroupById(userGroupId);
		request.setAttribute("groupname", userGroup.getName());
		getServletContext().getRequestDispatcher("/jsp/userslistview.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}