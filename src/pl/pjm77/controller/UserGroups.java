package pl.pjm77.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.pjm77.DAO.UserGroupDAO;
import pl.pjm77.model.UserGroup;

@WebServlet("/usergroups")
public class UserGroups extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UserGroups() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserGroup[] userGroups = new UserGroupDAO().loadAllUserGroups();
        request.setAttribute("usergroups", userGroups);
        getServletContext().getRequestDispatcher("/jsp/usergroupsview.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}