package workshop_3.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import workshop_3.DAO.UserGroupDAO;
import workshop_3.model.UserGroup;

@WebServlet("/addeditgroup")
public class GroupsAdminAddEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GroupsAdminAddEdit() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idParam = request.getParameter("id").toString();
		int groupId = 0;
		if(idParam!=null & idParam!="") {
			try {
				groupId=Integer.parseInt(idParam);
			}catch(NumberFormatException e) {
				System.out.println("Incorrect group Id!");
				e.printStackTrace();
			}
		}
		if(groupId==0) {
			request.setAttribute("groupId", groupId);
			request.setAttribute("groupNamePH", "New group name");
			request.setAttribute("buttonPH", "Add group");
			getServletContext().getRequestDispatcher("/jsp/groupsadminaddeditview.jsp").forward(request, response);
		}else {
			UserGroup userGroup = UserGroupDAO.loadUserGroupById(groupId);
			request.setAttribute("groupId", groupId);																			//???
			request.setAttribute("groupNamePH", userGroup.getName());
			request.setAttribute("buttonPH", "Edit group");
			getServletContext().getRequestDispatcher("/jsp/groupsadminaddeditview.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idParam = request.getParameter("id");
		String nameParam = request.getParameter("name");
		System.out.println(idParam + ", " + nameParam);
		System.out.println("Got it!");
	}
}