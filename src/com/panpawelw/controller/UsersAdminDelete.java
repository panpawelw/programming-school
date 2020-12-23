package com.panpawelw.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.panpawelw.DAO.RealUserDAO;
import com.panpawelw.DAO.UserDAO;
import com.panpawelw.misc.ValidateParameter;
import com.panpawelw.misc.DbUtils;
import com.panpawelw.model.User;

@WebServlet("/deleteuser")
public class UsersAdminDelete extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;

    public UsersAdminDelete() {
        super();
    }

    public void init() {
        if (userDAO == null) userDAO = new RealUserDAO(DbUtils.initDB());
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        long userId = ValidateParameter.checkLong(idParam, "Incorrect user Id!");
        int result = 0;
        if (userId > 0) {
            User user = userDAO.loadUserById(userId);
            if (user != null) {
                result = userDAO.deleteUser(user);
            }
        }
        if (userId <= 0 || result != 1) {
            request.setAttribute("errormessage", "Error deleting user!");
        }
        getServletContext().getRequestDispatcher("/usersadminpanel").forward(request, response);
    }
}
