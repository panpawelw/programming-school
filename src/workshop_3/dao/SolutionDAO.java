package workshop_3.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import workshop_3.DbUtil;
import workshop_3.model.Solution;

public class SolutionDAO {

	public void saveSolutionToDB(Solution solution) {
		try (Connection con = DbUtil.getConn()) {
			if (solution.getId() == 0) {
				String sql = "INSERT INTO solution(created, updated, description, exercise_id, user_id) VALUES (?, ?, ?, ?, ?);";
				String generatedColumns[] = { " ID " };
				Date date = new Date();
				java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
				try (PreparedStatement ps = con.prepareStatement(sql, generatedColumns)) {
					ps.setTimestamp(1, sqlDate);
					ps.setTimestamp(2, null);
					ps.setString(3, solution.getDescription());
					ps.setInt(4, solution.getExercise_id());
					ps.setLong(5, solution.getUser_id());
					ps.executeUpdate();
					try (ResultSet rs = ps.getGeneratedKeys()) {
						if (rs.next()) {
							solution.setId(rs.getLong(1));
						}
					}
				}
			} else {
				String sql = "UPDATE solution SET updated=Now(), description=?, exercise_id=?, user_id=? WHERE id = ?;";
				try (PreparedStatement ps = con.prepareStatement(sql)) {
					ps.setString(1, solution.getDescription());
					ps.setInt(2, solution.getExercise_id());
					ps.setLong(3, solution.getUser_id());
					ps.setLong(4, solution.getId());
					ps.executeUpdate();
				}
			}
		} catch (SQLException e) {
			System.out.println("Database error!");
			e.printStackTrace();
		}
	}
	
	static public Solution loadSolutionById(long id) {
		try (Connection con = DbUtil.getConn()) {
			String sql = "SELECT * FROM solution WHERE id=?;";
			try (PreparedStatement ps = con.prepareStatement(sql)) {
				ps.setLong(1, id);
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						Solution loadedSolution = new Solution();
						loadedSolution.setId(rs.getLong("id"));
						loadedSolution.setDescription(rs.getString("description"));
						loadedSolution.setCreated(rs.getTimestamp("created"));
						loadedSolution.setUpdated(rs.getTimestamp("updated"));
						loadedSolution.setExercise_id(rs.getInt("exercise_id"));
						loadedSolution.setUser_id(rs.getLong("user_id"));
						return loadedSolution;
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("Database error!");
			e.printStackTrace();
		}
		System.out.println("No such solution!");
		return null;
	}
	
	static public Solution[] loadAllSolutions() {
		List<Solution> solutions = new ArrayList<Solution>();
		try (Connection con = DbUtil.getConn()) {
			String sql = "SELECT * FROM solution;";
			try (PreparedStatement ps = con.prepareStatement(sql)) {
				try (ResultSet rs = ps.executeQuery()) {
					while(rs.next()) {
						Solution loadedSolution = new Solution();
						loadedSolution.setId(rs.getLong("id"));
						loadedSolution.setDescription(rs.getString("description"));
						loadedSolution.setCreated(rs.getTimestamp("created"));
						loadedSolution.setUpdated(rs.getTimestamp("updated"));
						loadedSolution.setExercise_id(rs.getInt("exercise_id"));
						loadedSolution.setUser_id(rs.getLong("user_id"));
						solutions.add(loadedSolution);
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("Database error!");
			e.printStackTrace();
		}
		Solution sArray[] = new Solution[solutions.size()];
		sArray = solutions.toArray(sArray);
		return sArray;
	}
	
	public void deleteSolution(Solution solution) {
		try (Connection con = DbUtil.getConn()) {
			String sql = "DELETE FROM solution WHERE id=?";
			try (PreparedStatement ps = con.prepareStatement(sql)) {
				ps.setLong(1, solution.getId());
				ps.executeUpdate();
				solution.setId(0);
			}
		} catch (SQLException e) {
			System.out.println("Database error!");
			e.printStackTrace();
		}
	}
}
