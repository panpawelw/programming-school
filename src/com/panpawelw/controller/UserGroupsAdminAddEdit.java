package com.panpawelw.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.panpawelw.DAO.UserGroupDAO;
import com.panpawelw.misc.ValidateParameter;
import com.panpawelw.misc.DbUtils;
import com.panpawelw.model.UserGroup;

@WebServlet("/addeditgroup")
public class UserGroupsAdminAddEdit extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserGroupDAO userGroupDAO;

    public UserGroupsAdminAddEdit() {
        super();
    }

    public void init() {
        if(userGroupDAO == null) this.userGroupDAO = new UserGroupDAO(DbUtils.initDB());
    }

    public void setUserGroupDAO(UserGroupDAO userGroupDAO) {
        this.userGroupDAO = userGroupDAO;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        int groupId = ValidateParameter.checkInt(idParam, "Incorrect group Id!");
        if(groupId == 0) {
            request.setAttribute("usergroup", new UserGroup(0));
            request.setAttribute("button", "Add group");
            getServletContext().getRequestDispatcher("/jsp/usergroupsadminaddeditview.jsp")
                    .forward(request, response);
            return;
        } else if (groupId > 0) {
            UserGroup userGroup = userGroupDAO.loadUserGroupById(groupId);
            if (userGroup != null) {
                request.setAttribute("usergroup", userGroup);
                request.setAttribute("button", "Edit group");
                getServletContext().getRequestDispatcher("/jsp/usergroupsadminaddeditview.jsp")
                        .forward(request, response);
                return;
            }
        }
        request.setAttribute("errormessage", "No such user group exists!");
        getServletContext().getRequestDispatcher("/usergroupsadminpanel")
                .forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String groupName = request.getParameter("usergroup_name");
        int groupId = ValidateParameter.checkInt(idParam, "Incorrect group Id!");
        if (groupName != null && !groupName.equals("") && groupId >= 0) {
            UserGroup userGroup = new UserGroup();
            if (groupId != 0) {
                userGroup = userGroupDAO.loadUserGroupById(groupId);
            }
            userGroup.setName(groupName);
            int result = userGroupDAO.saveUserGroupToDB(userGroup);
            if (result == 0) {
                request.setAttribute("errormessage", "Database error!");
            }
        } else {
            request.setAttribute("errormessage", "Incorrect parameters!");
        }
        getServletContext().getRequestDispatcher("/usergroupsadminpanel").forward(request, response);
    }
}
