package workshop_3.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import workshop_3.misc.DbUtil;
import workshop_3.model.LastSolution;

public class LastSolutionDAO {

	static public LastSolution[] loadAllSolutions(int number) {
		List<LastSolution> solutions = new ArrayList<LastSolution>();
		try (Connection con = DbUtil.getConn()) {
			String sql = "SELECT exercise.title, user.name, IF(solution.updated > solution.created, solution.updated, solution.created),"
					+ " solution.id FROM solution LEFT JOIN exercise ON solution.exercise_id=exercise.id"
					+ " LEFT JOIN user ON solution.user_id=user.id ORDER BY IF(updated > created, updated, created) DESC LIMIT ?;";
			try (PreparedStatement ps = con.prepareStatement(sql)) {
				ps.setInt(1, number);
				try (ResultSet rs = ps.executeQuery()) {
					while(rs.next()) {
						LastSolution loadedSolution = new LastSolution();
						loadedSolution.title = rs.getString(1);
						loadedSolution.name = rs.getString(2);
						loadedSolution.modified = rs.getTimestamp(3);
						loadedSolution.id = rs.getInt(4);
						solutions.add(loadedSolution);
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("Database error!");
			e.printStackTrace();
		}
		LastSolution[] sArray = new LastSolution[solutions.size()];
		sArray = solutions.toArray(sArray);
		return sArray;
	}
}