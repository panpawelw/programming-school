package com.panpawelw.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.panpawelw.DAO.RealUserDAO;
import com.panpawelw.DAO.UserDAO;
import com.panpawelw.misc.DbUtils;
import com.panpawelw.model.User;

@WebServlet("/usersadminpanel")
public class UsersAdminPanel extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;

    public UsersAdminPanel() {
        super();
    }

    public void init() {
        userDAO = new RealUserDAO(DbUtils.initDB());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> usersList = userDAO.loadAllUsers();
        request.setAttribute("userslist", usersList);
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
        }
        getServletContext().getRequestDispatcher("/jsp/usersadminview.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}