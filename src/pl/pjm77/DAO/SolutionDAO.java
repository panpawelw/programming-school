package pl.pjm77.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.pjm77.misc.DbUtil;
import pl.pjm77.model.Solution;

public class SolutionDAO {

    public void saveSolutionToDB(Solution solution) {
        try (Connection con = DbUtil.getConn()) {
            if (solution.getId() == 0) {
                String sql = "INSERT INTO Solution(created, updated, description, exercise_id, user_id) VALUES (?, ?, ?, ?, ?);";
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
                String sql = "UPDATE Solution SET updated=Now(), description=?, exercise_id=?, user_id=? WHERE id = ?;";
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
            String sql = "SELECT * FROM Solution WHERE id=?;";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setLong(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return loadSolution(rs);
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

    public void deleteSolution(Solution solution) {
        try (Connection con = DbUtil.getConn()) {
            String sql = "DELETE FROM Solution WHERE id=?";
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

    static public Solution[] loadAllSolutions() {
        return loadSolutionsBy(true, 0);
    }

    public static Solution[] loadAllByUserId(int user_id) {
        return loadSolutionsBy(false, user_id);
    }

    private static Solution[] loadSolutionsBy(boolean loadAll, long param){
        String sql;
        if(loadAll) sql = "SELECT * FROM Solution;";
        else sql = "SELECT * FROM Solution WHERE user_id=?;";
        List<Solution> solutions = new ArrayList<>();
        try (Connection con = DbUtil.getConn()) {
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                if(param!=0) ps.setLong(1, param);
                try (ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                        solutions.add(loadSolution(rs));
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

    private static Solution loadSolution(ResultSet rs) throws SQLException{
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