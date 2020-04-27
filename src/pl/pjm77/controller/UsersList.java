package pl.pjm77.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.pjm77.DAO.RealUserDAO;
import pl.pjm77.DAO.RealUserGroupDAO;
import pl.pjm77.DAO.UserDAO;
import pl.pjm77.misc.RealDataSource;
import pl.pjm77.model.User;
import pl.pjm77.model.UserGroup;

@WebServlet("/userslist")
public class UsersList extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;

    public UsersList() {
        super();
    }

    public void init() {
        userDAO = new RealUserDAO(RealDataSource.initDB());
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
        User[] GroupUsersList = userDAO.loadAllUsersByGroupId(userGroupId);
        request.setAttribute("groupuserslist", GroupUsersList);
        UserGroup userGroup = new RealUserGroupDAO().loadUserGroupById(userGroupId);
        request.setAttribute("groupname", userGroup.getName());
        getServletContext().getRequestDispatcher("/jsp/userslistview.jsp").forward(request, response);
    }
}