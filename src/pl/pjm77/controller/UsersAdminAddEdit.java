package pl.pjm77.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.pjm77.DAO.RealUserDAO;
import pl.pjm77.misc.ValidateParameter;
import pl.pjm77.model.User;
import pl.pjm77.passwordEncoder.BCryptPasswordEncoder;
import pl.pjm77.passwordEncoder.PasswordEncoder;

@WebServlet("/addedituser")
public class UsersAdminAddEdit extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private PasswordEncoder passwordEncoder;

    public UsersAdminAddEdit() {
        super();
    }

    public void init() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        int userId = ValidateParameter.checkInt(idParam, "Incorrect user Id!");
        if (userId == 0) {
            request.setAttribute("userId", 0);
            request.setAttribute("button", "Add user");
            request.setAttribute("userName", null);
            request.setAttribute("userEmail", null);
            request.setAttribute("userGroup_id", null);
            getServletContext().getRequestDispatcher("/jsp/usersadminaddeditview.jsp").forward(request, response);
        } else if (userId > 0) {
            User user = new RealUserDAO().loadUserById(userId);
            request.setAttribute("userId", userId);
            request.setAttribute("button", "Edit user");
            request.setAttribute("userName", user.getName());
            request.setAttribute("userEmail", user.getEmail());
            request.setAttribute("userGroup_id", user.getGroup_id());
            getServletContext().getRequestDispatcher("/jsp/usersadminaddeditview.jsp").forward(request, response);
        } else {
            getServletContext().getRequestDispatcher("/usersadminpanel").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String userName = request.getParameter("name");
        String userEmail = request.getParameter("email");
        String userPassword = request.getParameter("password");
        String group_idParam = request.getParameter("group_id");
        long userId = ValidateParameter.checkLong(idParam, "Incorrect user Id!");
        int userGroup_id = ValidateParameter.checkInt(group_idParam, "Incorrect group Id!");
        if (userName != null && !userName.equals("") && userEmail != null && !userEmail.equals("")
                && userPassword != null && !userPassword.equals("") && userGroup_id > 0 && userId >= 0) {
            RealUserDAO realUserDAO = new RealUserDAO();
            User user = new User();
            if (userId != 0) {
                user = realUserDAO.loadUserById(userId);
            }
            user.setName(userName);
            user.setEmail(userEmail);
            userPassword = passwordEncoder.encodePassword(userPassword);
            user.setPassword(userPassword);
            user.setGroup_id(userGroup_id);
            realUserDAO.saveUserToDB(user);
        } else {
            request.setAttribute("errorMessage",
                    "User name, email, password nor group can't be empty!");
        }
        getServletContext().getRequestDispatcher("/usersadminpanel").forward(request, response);
    }
}