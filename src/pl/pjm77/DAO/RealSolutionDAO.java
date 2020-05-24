package pl.pjm77.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.pjm77.model.Solution;

import javax.sql.DataSource;

import static pl.pjm77.misc.DbUtils.prepStatement;

public class RealSolutionDAO implements SolutionDAO {

    private final DataSource ds;

    public RealSolutionDAO(DataSource ds) {
        this.ds = ds;
    }

    public int saveSolutionToDB(Solution solution) {
        int affectedRows = 0;
        try {
            if (solution.getId() == 0) {
                String[] columnNames = {"ID"};
                java.sql.Timestamp created = new java.sql.Timestamp(new Date().getTime());
                try (Connection con = ds.getConnection(); PreparedStatement ps = prepStatement(con,
                  "INSERT INTO solution(created, updated, description, exercise_id, user_id) " +
                    "VALUES (?, ?, ?, ?, ?);", columnNames, created, null, solution.getDescription(),
                  solution.getExercise_id(), solution.getUser_id()); ResultSet rs = ps.getGeneratedKeys())
                {
                    affectedRows = ps.executeUpdate();
                    if (rs.next()) {
                        solution.setId(rs.getLong(1));
                    }
                }
            } else {
                try (Connection con = ds.getConnection(); PreparedStatement ps = prepStatement(con,
                  "UPDATE solution SET updated=Now(), description=?, exercise_id=?, user_id=? " +
                    "WHERE id = ?;", solution.getDescription(), solution.getExercise_id(),
                  solution.getUser_id(), solution.getId()))
                {
                    affectedRows =  ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    public Solution loadSolutionById(long id) {
        try (Connection con = ds.getConnection(); PreparedStatement ps = prepStatement(con,
          "SELECT * FROM solution WHERE id=?;", id); ResultSet rs = ps.executeQuery())
        {
            if (rs.next()) {
                return loadSingleSolution(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int deleteSolution(Solution solution) {
        int affectedRows = 0;
        try (Connection con = ds.getConnection(); PreparedStatement ps = prepStatement(con,
          "DELETE FROM solution WHERE id=?;", solution.getId()))
        {
            affectedRows = ps.executeUpdate();
            solution.setId(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    public List<Solution> loadAllSolutions() {
        return executeQuery("SELECT * FROM solution;");
    }

    public List<Solution> loadAllSolutionsByUserId(long id) {
        return executeQuery("SELECT * FROM solution WHERE user_id=?;", id);
    }

    /**
     * executes SQL Query with optional parameter.
     *
     * @param sqlQuery - query to execute
     * @param param    - optional parameter
     * @return list of Solution objects
     */
    private List<Solution> executeQuery(String sqlQuery, Object... param) {
        List<Solution> solutions = new ArrayList<>();
        try (Connection con = ds.getConnection(); PreparedStatement ps =
          prepStatement(con, sqlQuery, param); ResultSet rs = ps.executeQuery())
        {
            while (rs.next()) {
                solutions.add(loadSingleSolution(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return solutions;
    }

    /**
     * Gets single Solution object from result set.
     *
     * @param rs - ResultSet
     * @return - Solution object
     * @throws SQLException - in case of database problems
     */
    private Solution loadSingleSolution(ResultSet rs) throws SQLException {
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