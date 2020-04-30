package pl.pjm77.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.pjm77.DAO.RealUserGroupDAO;
import pl.pjm77.DAO.UserGroupDAO;
import pl.pjm77.misc.DbUtils;
import pl.pjm77.model.UserGroup;

@WebServlet("/usergroups")
public class UserGroups extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserGroupDAO userGroupDAO;

    public UserGroups() {
        super();
    }

    public void init() {
        this.userGroupDAO = new RealUserGroupDAO(DbUtils.initDB());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserGroup[] userGroups = userGroupDAO.loadAllUserGroups();
        request.setAttribute("usergroups", userGroups);
        getServletContext().getRequestDispatcher("/jsp/usergroupsview.jsp").forward(request, response);
    }
}