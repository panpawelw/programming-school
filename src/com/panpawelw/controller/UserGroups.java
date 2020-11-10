package com.panpawelw.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.panpawelw.DAO.RealUserGroupDAO;
import com.panpawelw.DAO.UserGroupDAO;
import com.panpawelw.misc.DbUtils;
import com.panpawelw.model.UserGroup;

@WebServlet("/usergroups")
public class UserGroups extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserGroupDAO userGroupDAO;

    public UserGroups() {
        super();
    }

    public void init() {
        if(userGroupDAO == null) this.userGroupDAO = new RealUserGroupDAO(DbUtils.initDB());
    }

    public void setUserGroupDAO(UserGroupDAO userGroupDAO) {
        this.userGroupDAO = userGroupDAO;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserGroup> userGroups = userGroupDAO.loadAllUserGroups();
        request.setAttribute("usergroups", userGroups);
        getServletContext().getRequestDispatcher("/jsp/usergroupsview.jsp").forward(request, response);
    }
}