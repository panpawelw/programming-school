package pl.pjm77.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.pjm77.misc.DbUtil;
import pl.pjm77.model.Exercise;

public class ExerciseDAO {

    public void saveExerciseToDB(Exercise exercise) {
        try (Connection con = DbUtil.getConn()) {
            if (exercise.getId() == 0) {
                String sql = "INSERT INTO exercise (title, description) VALUES (?, ?)";
                String generatedColumns[] = { " ID " };
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

    public static Exercise loadExerciseById(int id) {
        try (Connection con = DbUtil.getConn()) {
            String sql = "SELECT * FROM exercise WHERE id=?;";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return loadExercise(rs);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error!");
            e.printStackTrace();
        }
        System.out.println("No such exercise!");
        return null;
    }

    public static Exercise[] loadAllExercises() {
        List<Exercise> exercises = new ArrayList<>();
        try (Connection con = DbUtil.getConn()) {
            String sql = "SELECT * FROM exercise;";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                        exercises.add(loadExercise(rs));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error!");
            e.printStackTrace();
        }
        Exercise eArray[] = new Exercise[exercises.size()];
        eArray = exercises.toArray(eArray);
        return eArray;
    }

    public void deleteExercise(Exercise exercise) {
        try (Connection con = DbUtil.getConn()) {
            String sql = "DELETE FROM exercise WHERE id=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, exercise.getId());
                ps.executeUpdate();
                exercise.setId(0);
            }
        } catch (SQLException e) {
            System.out.println("Database error!");
            e.printStackTrace();
        }
    }

    private static Exercise loadExercise(ResultSet rs) throws SQLException{
        Exercise loadedExercise = new Exercise();
        loadedExercise.setId(rs.getInt("id"));
        loadedExercise.setTitle(rs.getString("title"));
        loadedExercise.setDescription(rs.getString("description"));
        return loadedExercise;
    }
}