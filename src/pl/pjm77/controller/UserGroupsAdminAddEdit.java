package pl.pjm77.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.pjm77.DAO.RealUserGroupDAO;
import pl.pjm77.DAO.UserGroupDAO;
import pl.pjm77.misc.ValidateParameter;
import pl.pjm77.model.UserGroup;

@WebServlet("/addeditgroup")
public class UserGroupsAdminAddEdit extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserGroupDAO userGroupDAO;

    public UserGroupsAdminAddEdit() {
        super();
    }

    public void init() {
        userGroupDAO = new RealUserGroupDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        int groupId = ValidateParameter.checkInt(idParam, "Incorrect group Id!");
        if (groupId == 0) {
            request.setAttribute("groupId", groupId);
            request.setAttribute("groupName", null);
            request.setAttribute("button", "Add group");
            getServletContext().getRequestDispatcher("/jsp/usergroupsadminaddeditview.jsp").forward(request, response);
        } else if (groupId > 0) {
            UserGroup userGroup = userGroupDAO.loadUserGroupById(groupId);
            request.setAttribute("groupId", groupId);
            request.setAttribute("groupName", userGroup.getName());
            request.setAttribute("button", "Edit group");
            getServletContext().getRequestDispatcher("/jsp/usergroupsadminaddeditview.jsp").forward(request, response);
        } else {
            getServletContext().getRequestDispatcher("/groupsadminpanel").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String groupName = request.getParameter("group_name");
        int groupId = ValidateParameter.checkInt(idParam, "Incorrect group Id!");
        if (groupName != null && !groupName.equals("") && groupId >= 0) {
            UserGroup userGroup = new UserGroup();
            if (groupId != 0) {
                userGroup = userGroupDAO.loadUserGroupById(groupId);
            }
            userGroup.setName(groupName);
            userGroupDAO.saveUserGroupToDB(userGroup);
        } else {
            request.setAttribute("errorMessage", "Group name can't be empty!");
        }
        getServletContext().getRequestDispatcher("/groupsadminpanel").forward(request, response);
    }
}