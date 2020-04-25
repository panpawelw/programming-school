package pl.pjm77.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.pjm77.DAO.RealUserGroupDAO;
import pl.pjm77.misc.ValidateParameter;
import pl.pjm77.model.UserGroup;

@WebServlet("/deletegroup")
public class UserGroupsAdminDelete extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UserGroupsAdminDelete() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        int groupId = ValidateParameter.checkInt(idParam, "Incorrect group Id!");
        if (groupId >= 0) {
            RealUserGroupDAO realUserGroupDAO = new RealUserGroupDAO();
            UserGroup userGroup = new RealUserGroupDAO().loadUserGroupById(groupId);
            realUserGroupDAO.deleteUserGroup(userGroup);
        }
        getServletContext().getRequestDispatcher("/groupsadminpanel").forward(request, response);
    }
}