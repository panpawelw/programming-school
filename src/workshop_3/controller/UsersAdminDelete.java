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

@WebServlet("/deleteuser")
public class UsersAdminDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UsersAdminDelete() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idParam = request.getParameter("id").toString();
		long userId = ValPar.longVar(idParam, "Incorrect user Id!");
		if(userId >= 0) {
			UserDAO userDAO=new UserDAO();
			User user=UserDAO.loadUserById(userId);
			userDAO.deleteUser(user);
		}
		getServletContext().getRequestDispatcher("/usersadminpanel").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}