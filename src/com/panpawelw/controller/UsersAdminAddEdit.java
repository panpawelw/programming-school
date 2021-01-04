package com.panpawelw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.panpawelw.DAO.UserDAO;
import com.panpawelw.misc.ValidateParameter;
import com.panpawelw.passwordEncoder.BCryptPasswordEncoder;
import com.panpawelw.passwordEncoder.PasswordEncoder;
import com.panpawelw.misc.DbUtils;
import com.panpawelw.model.User;

@WebServlet("/addedituser")
public class UsersAdminAddEdit extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;
    private PasswordEncoder passwordEncoder;

    public UsersAdminAddEdit() {
        super();
    }

    public void init() {
        if(passwordEncoder == null) passwordEncoder = new BCryptPasswordEncoder();
        if(userDAO == null) userDAO = new UserDAO(DbUtils.initDB());
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        int userId = ValidateParameter.checkInt(idParam, "Incorrect user Id!");
        if(userId == 0) {
            request.setAttribute("user", new User(0));
            request.setAttribute("button", "Add user");
            getServletContext().getRequestDispatcher("/jsp/usersadminaddeditview.jsp")
                    .forward(request, response);
            return;
        } else if (userId > 0){
            User user = userDAO.loadUserById(userId);
            if (user != null) {
                request.setAttribute("user", user);
                request.setAttribute("button", "Edit user");
                getServletContext().getRequestDispatcher("/jsp/usersadminaddeditview.jsp")
                        .forward(request, response);
                return;
            }
        }
        request.setAttribute("errormessage", "No such user exists!");
        getServletContext().getRequestDispatcher("/usersadminpanel")
                .forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String userName = request.getParameter("name");
        String userEmail = request.getParameter("email");
        String userPassword = request.getParameter("password");
        String group_idParam = request.getParameter("group_id");
        long userId = ValidateParameter.checkLong(idParam, "Incorrect user Id!");
        int userGroup_id = ValidateParameter.checkInt(group_idParam, "Incorrect group Id!");
        if (userName != null && !userName.equals("") && userEmail != null && !userEmail.equals("")
                && userPassword != null && !userPassword.equals("") && userGroup_id > 0 && userId >= 0) {
            User user = new User();
            if (userId != 0) {
                user = userDAO.loadUserById(userId);
            }
            user.setName(userName);
            user.setEmail(userEmail);
            userPassword = passwordEncoder.encodePassword(userPassword);
            user.setPassword(userPassword);
            user.setGroup_id(userGroup_id);
            int result = userDAO.saveUserToDB(user);
            if (result == 0) {
                request.setAttribute("errormessage", "Database error!");
            }
        } else {
            request.setAttribute("errormessage", "Incorrect parameters!");
        }
        getServletContext().getRequestDispatcher("/usersadminpanel").forward(request, response);
    }
}
