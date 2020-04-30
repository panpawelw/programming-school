package pl.pjm77.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.pjm77.model.Exercise;

import javax.sql.DataSource;

import static pl.pjm77.misc.DbUtil.createStatement;

public class RealExerciseDAO implements ExerciseDAO {
	
	private final DataSource dataSource;
	
	public RealExerciseDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void saveExerciseToDB(Exercise exercise) {
		try (Connection con = dataSource.getConnection()) {
			if (exercise.getId() == 0) {
				String sql = "INSERT INTO exercise (title, description) VALUES (?, ?)";
				String[] generatedColumns = { " ID " };
				try (PreparedStatement ps = con.prepareStatement(sql, generatedColumns)) {
					ps.setString(1, exercise.getTitle());
					ps.setString(2, exercise.getDescription());
					ps.executeUpdate();
					try (ResultSet rs = ps.getGeneratedKeys()) {
						if (rs.next()) {
							exercise.setId(rs.getInt(1));
						}
					}
				}
			} else {
				String sql = "UPDATE exercise SET title=?, description=? WHERE id=?;";
				try (PreparedStatement ps = con.prepareStatement(sql)) {
					ps.setString(1, exercise.getTitle());
					ps.setString(2, exercise.getDescription());
					ps.setInt(3, exercise.getId());
					ps.executeUpdate();
				}
			}
		} catch (SQLException e) {
			System.out.println("Database error!");
			e.printStackTrace();
		}
	}
	
	public Exercise loadExerciseById(int id) {
		try (PreparedStatement ps = createStatement(dataSource.getConnection(),
				"SELECT * FROM exercise WHERE id=?;", id);
			 ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				return loadSingleExercise(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Exercise[] loadAllExercises() {
		List<Exercise> exercises = new ArrayList<>();
		try (PreparedStatement ps = createStatement(dataSource.getConnection(),
				"SELECT * FROM exercise;");
			 ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				exercises.add(loadSingleExercise(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Exercise[] gArray = new Exercise[exercises.size()];
		gArray = exercises.toArray(gArray);
		return gArray;
	}
	
	public void deleteExercise(Exercise exercise) {
		try (PreparedStatement ps = createStatement(dataSource.getConnection(),
				"DELETE * FROM exercise WHERE id=?;", exercise.getId())) {
			ps.executeUpdate();
			exercise.setId(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets single Exercise object from result set.
	 * @param rs - ResultSet
	 * @return - Exercise object
	 * @throws SQLException - in case of database problems
	 */
	private Exercise loadSingleExercise(ResultSet rs) throws SQLException{
		Exercise loadedExercise = new Exercise();
		loadedExercise.setId(rs.getInt("id"));
		loadedExercise.setTitle(rs.getString("title"));
		loadedExercise.setDescription(rs.getString("description"));
		return loadedExercise;
	}
}