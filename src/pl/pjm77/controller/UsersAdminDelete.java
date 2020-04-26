package pl.pjm77.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.pjm77.DAO.RealUserDAO;
import pl.pjm77.DAO.UserDAO;
import pl.pjm77.misc.ValidateParameter;
import pl.pjm77.model.User;

@WebServlet("/deleteuser")
public class UsersAdminDelete extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;

    public UsersAdminDelete() {
        super();
    }

    public void init() {
        userDAO = new RealUserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        long userId = ValidateParameter.checkLong(idParam, "Incorrect user Id!");
        if (userId >= 0) {
            User user = userDAO.loadUserById(userId);
            userDAO.deleteUser(user);
        }
        getServletContext().getRequestDispatcher("/usersadminpanel").forward(request, response);
    }
}