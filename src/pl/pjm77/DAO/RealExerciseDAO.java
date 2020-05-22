package pl.pjm77.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.pjm77.model.Exercise;

import javax.sql.DataSource;

import static pl.pjm77.misc.DbUtils.prepStatement;

public class RealExerciseDAO implements ExerciseDAO {

    private final DataSource ds;

    public RealExerciseDAO(DataSource ds) {
        this.ds = ds;
    }

    public int saveExerciseToDB(Exercise exercise) {
        int affectedRows = 0;
        try {
            if (exercise.getId() == 0) {
                String[] columnNames = {"ID"};
                try (Connection con = ds.getConnection(); PreparedStatement ps = prepStatement(con,
                  "INSERT INTO exercise (title, description) VALUES (?, ?);", columnNames,
                  exercise.getTitle(), exercise.getDescription()); ResultSet rs = ps.getGeneratedKeys())
                {
                    affectedRows = ps.executeUpdate();
                    if (rs.next()) {
                        exercise.setId(rs.getInt(1));
                    }
                }
            } else {
                try (Connection con = ds.getConnection(); PreparedStatement ps = prepStatement(con,
                  "UPDATE exercise SET title=?, description=? WHERE id=?;",
                  exercise.getTitle(), exercise.getDescription(), exercise.getId()))
                {
                    affectedRows = ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    public Exercise loadExerciseById(int id) {
        try (Connection con = ds.getConnection(); PreparedStatement ps = prepStatement(con,
          "SELECT * FROM exercise WHERE id=?;", id); ResultSet rs = ps.executeQuery())
        {
            if (rs.next()) {
                return loadSingleExercise(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Exercise> loadAllExercises() {
        List<Exercise> exercises = new ArrayList<>();
        try (Connection con = ds.getConnection(); PreparedStatement ps = prepStatement(con,
          "SELECT * FROM exercise;"); ResultSet rs = ps.executeQuery())
        {
            while (rs.next()) {
                exercises.add(loadSingleExercise(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exercises;
    }

    public void deleteExercise(Exercise exercise) {
        try (Connection con = ds.getConnection(); PreparedStatement ps = prepStatement(con,
          "DELETE * FROM exercise WHERE id=?;", exercise.getId()))
        {
            ps.executeUpdate();
            exercise.setId(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets single Exercise object from result set.
     *
     * @param rs - ResultSet
     * @return - Exercise object
     * @throws SQLException - in case of database problems
     */
    private Exercise loadSingleExercise(ResultSet rs) throws SQLException {
        Exercise loadedExercise = new Exercise();
        loadedExercise.setId(rs.getInt("id"));
        loadedExercise.setTitle(rs.getString("title"));
        loadedExercise.setDescription(rs.getString("description"));
        return loadedExercise;
    }
}