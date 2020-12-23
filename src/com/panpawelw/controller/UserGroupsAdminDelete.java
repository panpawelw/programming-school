package com.panpawelw.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.panpawelw.DAO.RealUserGroupDAO;
import com.panpawelw.DAO.UserGroupDAO;
import com.panpawelw.misc.ValidateParameter;
import com.panpawelw.misc.DbUtils;
import com.panpawelw.model.UserGroup;

@WebServlet("/deletegroup")
public class UserGroupsAdminDelete extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserGroupDAO userGroupDAO;

    public UserGroupsAdminDelete() {
        super();
    }

    public void init() {
        if (userGroupDAO == null) this.userGroupDAO = new RealUserGroupDAO(DbUtils.initDB());
    }

    public void setUserGroupDAO(UserGroupDAO userGroupDAO) {
        this.userGroupDAO = userGroupDAO;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        int userGroupId = ValidateParameter.checkInt(idParam, "Incorrect group Id!");
        int result = 0;
        if (userGroupId > 0) {
            UserGroup userGroup = userGroupDAO.loadUserGroupById(userGroupId);
            if (userGroup != null) {
                result = userGroupDAO.deleteUserGroup(userGroup);
            }
        }
        if (userGroupId <= 0 || result != 1) {
            request.setAttribute("errormessage", "Error deleting user group!");
        }
        getServletContext().getRequestDispatcher("/groupsadminpanel").forward(request, response);
    }
}
