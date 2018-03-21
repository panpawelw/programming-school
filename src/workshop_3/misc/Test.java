package workshop_3.misc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import workshop_3.DAO.UserGroupDAO;
import workshop_3.model.UserGroup;
import workshop_3.DAO.ExerciseDAO;
import workshop_3.DAO.SolutionDAO;
import workshop_3.DAO.UserDAO;
import workshop_3.model.Exercise;
import workshop_3.model.Solution;
import workshop_3.model.User;

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
		response.getWriter().append("<h1>Hello World!</h1>").append(request.getContextPath()+ "<br>");
		Solution solution = new Solution("description7", 6, 6);
		SolutionDAO solutionDAO = new SolutionDAO();
		solutionDAO.saveSolutionToDB(solution);
//		solution = SolutionDAO.loadSolutionById(solution.getId());
//		solution.setDescription("description6modded");
//		solution.setExercise_id(6);
//		solution.setUser_id(6);
//		solutionDAO.saveSolutionToDB(solution);
//		solutionDAO.deleteSolution(solution);
//		Solution[] allSolutions = SolutionDAO.loadAllSolutions();
//		for(Solution s : allSolutions) {
//			response.getWriter().append("<br>" + s.toString());
//		}
//		User user = new User("user10", "email10", "password10", 8);
//		UserDAO userDAO = new UserDAO();
//		userDAO.saveUserToDB(user);
//		UserDAO.loadUserById(user.getId());
//		user.setName("user9modded");
//		user.setEmail("email9modded");
//		user.setPassword("password9modded");
//		user.setGroup_id(8);
//		userDAO.saveUserToDB(user);
//		userDAO.deleteUser(user);
//		User[] allUsers = UserDAO.loadAllUsers();
//		for(User u : allUsers) {
//			response.getWriter().append("<br>" + u.toString());
//		}
//		Exercise exercise = new Exercise("exercise8", "description8");
//		ExerciseDAO exerciseDAO = new ExerciseDAO();
//		exerciseDAO.saveExerciseToDB(exercise);
//		ExerciseDAO.loadExerciseById(exercise.getId());
//		exercise.setTitle("exercise8modded");
//		exercise.setDescription("description8modded");
//		exerciseDAO.saveExerciseToDB(exercise);
//		exerciseDAO.deleteExercise(exercise);
//		Exercise[] allExercises = ExerciseDAO.loadAllExercises();
//		for(Exercise e : allExercises) {
//			response.getWriter().append("<br>" + e.toString());
//		}
//		UserGroup userGroup = new UserGroup("usergroup9");
//		UserGroupDAO userGroupDAO = new UserGroupDAO();
//		userGroupDAO.saveUserGroupToDB(userGroup);
//		UserGroupDAO.loadUserGroupById(userGroup.getId());
//		userGroup.setName("usergroup9modded");
//		userGroupDAO.saveUserGroupToDB(userGroup);
//		userGroupDAO.deleteUserGroup(userGroup);
//		UserGroup[] allUserGroups = UserGroupDAO.loadAllUserGroups();
//		for(UserGroup uG : allUserGroups) {
//			response.getWriter().append("<br>" + uG.toString());
//		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
