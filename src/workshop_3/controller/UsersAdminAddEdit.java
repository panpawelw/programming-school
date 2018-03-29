package workshop_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import workshop_3.DAO.UserDAO;
import workshop_3.misc.ValPar;
import workshop_3.model.User;

@WebServlet("/addedituser")
public class UsersAdminAddEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UsersAdminAddEdit() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idParam = request.getParameter("id").toString();
		int userId = ValPar.intVar(idParam, "Incorrect user Id!");
		if (userId == 0) {
			request.setAttribute("userId", userId);
			request.setAttribute("buttonPH", "Add user");
			request.setAttribute("userNamePH", "New user name");
			request.setAttribute("userEmailPH", "New user email");
			request.setAttribute("userPasswordPH", "New user password");
			request.setAttribute("userGroup_idPH", 0);
			getServletContext().getRequestDispatcher("/jsp/usersadminaddeditview.jsp").forward(request, response);
		} else if(userId > 0){
			User user = UserDAO.loadUserById(userId);
			request.setAttribute("userId", userId);
			request.setAttribute("buttonPH", "Edit user");
			request.setAttribute("userNamePH", user.getName());
			request.setAttribute("userEmailPH", user.getEmail());
			request.setAttribute("userPasswordPH", "New user password");
			request.setAttribute("userGroup_idPH", user.getGroup_id());
			getServletContext().getRequestDispatcher("/jsp/usersadminaddeditview.jsp").forward(request, response);
		} else {
			getServletContext().getRequestDispatcher("/usersadminpanel").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idParam = request.getParameter("id");
		String userName = request.getParameter("name");
		String userEmail = request.getParameter("email");
		String userPassword = request.getParameter("password");
		String group_idParam = request.getParameter("group_id");
		long userId = ValPar.longVar(idParam, "Incorrect user Id!");
		int userGroup_id = ValPar.intVar(group_idParam, "Incorrect group Id!");
		if(userName!=null && userName!="" && userEmail!=null && userEmail!="" && userPassword!=null && userPassword!="" && userGroup_id>0 && userId >=0) {
			UserDAO userDAO = new UserDAO();
			User user = new User();
			if(userId!=0) {
				user = UserDAO.loadUserById(userId);
				user.setName(userName);
				user.setEmail(userEmail);
				user.setPassword(userPassword);
				user.setGroup_id(userGroup_id);
			}else {
				user.setName(userName);
				user.setEmail(userEmail);
				user.setPassword(userPassword);
				user.setGroup_id(userGroup_id);
			}
			userDAO.saveUserToDB(user);
		}
		getServletContext().getRequestDispatcher("/usersadminpanel").forward(request, response);
	}
}