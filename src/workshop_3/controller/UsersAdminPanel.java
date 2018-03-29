package workshop_3.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import workshop_3.DAO.UserDAO;
import workshop_3.model.User;

@WebServlet("/usersadminpanel")
public class UsersAdminPanel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UsersAdminPanel() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User[] usersList = UserDAO.loadAllUsers();
		request.setAttribute("userslist", usersList);
		getServletContext().getRequestDispatcher("/jsp/usersadminview.jsp").forward(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}