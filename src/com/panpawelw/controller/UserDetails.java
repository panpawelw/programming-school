package com.panpawelw.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.panpawelw.DAO.*;
import com.panpawelw.misc.DbUtils;
import com.panpawelw.misc.ValidateParameter;
import com.panpawelw.model.LastSolution;
import com.panpawelw.model.User;
import com.panpawelw.model.UserGroup;

@WebServlet("/userdetails")
public class UserDetails extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;
    private UserGroupDAO userGroupDAO;
    private LastSolutionDAO lastSolutionDAO;

    public UserDetails() {
        super();
    }

    public void init() {
        if(userDAO == null) userDAO = new RealUserDAO(DbUtils.initDB());
        if(userGroupDAO == null) userGroupDAO = new RealUserGroupDAO(DbUtils.initDB());
        if(lastSolutionDAO == null) lastSolutionDAO = new RealLastSolutionDAO(DbUtils.initDB());
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    public void setUserGroupDAO(UserGroupDAO userGroupDAO) {
        this.userGroupDAO = userGroupDAO;
    }
    public void setLastSolutionDAO(LastSolutionDAO lastSolutionDAO) {
        this.lastSolutionDAO = lastSolutionDAO;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        int userId = ValidateParameter.checkInt(idParam, "Incorrect user Id!");
        if (userId > 0) {
            User user = userDAO.loadUserById(userId);
            if (user != null) {
                UserGroup userGroup = userGroupDAO.loadUserGroupById(user.getGroup_id());
                List<LastSolution> usersLastSolutions =
                        lastSolutionDAO.loadMostRecentSolutionsByUserId(userId);
                request.setAttribute("user", user);
                request.setAttribute("groupname", userGroup.getName());
                request.setAttribute("userslastsolutions", usersLastSolutions);
                getServletContext()
                        .getRequestDispatcher("/jsp/userdetailsview.jsp").forward(request, response);
                return;
            }
        }
        request.setAttribute("errormessage", "No such user exists!");
        getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
    }
}
