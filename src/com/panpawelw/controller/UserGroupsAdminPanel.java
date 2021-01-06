package com.panpawelw.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.panpawelw.DAO.UserGroupDAO;
import com.panpawelw.misc.DbUtils;
import com.panpawelw.model.UserGroup;

@WebServlet("/usergroupsadminpanel")
public class UserGroupsAdminPanel extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserGroupDAO userGroupDAO;

    public UserGroupsAdminPanel() {
        super();
    }

    public void init() {
        if(userGroupDAO == null) this.userGroupDAO = new UserGroupDAO(DbUtils.initDB());
    }

    public void setUserGroupDAO(UserGroupDAO userGroupDAO) {
        this.userGroupDAO = userGroupDAO;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserGroup> userGroupsList = userGroupDAO.loadAllUserGroups();
        request.setAttribute("usergroupslist", userGroupsList);
        getServletContext().getRequestDispatcher("/jsp/usergroupsadminview.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
