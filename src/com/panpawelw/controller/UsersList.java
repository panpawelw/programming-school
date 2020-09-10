package com.panpawelw.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.panpawelw.DAO.RealUserDAO;
import com.panpawelw.DAO.RealUserGroupDAO;
import com.panpawelw.DAO.UserDAO;
import com.panpawelw.DAO.UserGroupDAO;
import com.panpawelw.misc.DbUtils;
import com.panpawelw.model.User;
import com.panpawelw.model.UserGroup;

@WebServlet("/userslist")
public class UsersList extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;
    private UserGroupDAO userGroupDAO;

    public UsersList() {
        super();
    }

    public void init() {
        userDAO = new RealUserDAO(DbUtils.initDB());
        userGroupDAO = new RealUserGroupDAO(DbUtils.initDB());
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
        List<User> GroupUsersList = userDAO.loadAllUsersByGroupId(userGroupId);
        request.setAttribute("groupuserslist", GroupUsersList);
        UserGroup userGroup = userGroupDAO.loadUserGroupById(userGroupId);
        request.setAttribute("groupname", userGroup.getName());
        getServletContext().getRequestDispatcher("/jsp/userslistview.jsp").forward(request, response);
    }
}