package workshop_3;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import workshop_3.DAO.UserGroupDAO;
import workshop_3.model.UserGroup;

/**
 * Servlet implementation class Test
 */
@WebServlet("/test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Test() {
        super();
        // TODO Auto-generated constructor stub
    }
    
//    public void init() throws ServletException {
//    	System.out.println("xxx");
//    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("<h1>Hello World!</h1>").append(request.getContextPath()+ "<br>");
		UserGroup userGroup = new UserGroup("usergroup9");
		UserGroupDAO userGroupDAO = new UserGroupDAO();
		userGroupDAO.saveUserGroupToDB(userGroup);
		UserGroupDAO.loadGroupById(userGroup.getId());
		userGroup.setName("usergroup9modded");
		userGroupDAO.saveUserGroupToDB(userGroup);
		userGroupDAO.deleteGroup(userGroup);
		UserGroup[] allUserGroups = UserGroupDAO.loadAllGroups();
		for(UserGroup uG : allUserGroups) {
			response.getWriter().append("<br>" + uG.toString());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
