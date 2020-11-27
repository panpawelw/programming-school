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
import com.panpawelw.misc.ValidateParameter;
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
        if (userDAO == null) userDAO = new RealUserDAO(DbUtils.initDB());
        if (userGroupDAO == null) userGroupDAO = new RealUserGroupDAO(DbUtils.initDB());
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setUserGroupDAO(UserGroupDAO userGroupDAO) {
        this.userGroupDAO = userGroupDAO;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        int userGroupId = ValidateParameter.checkInt(idParam, "Incorrect group Id!");
        UserGroup userGroup = userGroupDAO.loadUserGroupById(userGroupId);
        if (userGroupId > 0 && userGroup != null) {
            List<User> groupUsersList = userDAO.loadAllUsersByGroupId(userGroupId);
            request.setAttribute("groupuserslist", groupUsersList);
            request.setAttribute("groupname", userGroup.getName());
            getServletContext().getRequestDispatcher("/jsp/userslistview.jsp")
                    .forward(request, response);
        }
        request.setAttribute("errormessage", "No such user group exists!");
        getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
    }
}
