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
        userDAO = new RealUserDAO(DbUtils.initDB());
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