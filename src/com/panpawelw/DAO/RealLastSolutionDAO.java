package com.panpawelw.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.panpawelw.model.LastSolution;

import javax.sql.DataSource;

import static com.panpawelw.misc.DbUtils.prepStatement;

public class RealLastSolutionDAO implements LastSolutionDAO {

    private final DataSource ds;

    public RealLastSolutionDAO(DataSource ds) {
        this.ds = ds;
    }

    public List<LastSolution> loadMostRecentSolutions(long number) {
        return executeQuery("SELECT exercise.title, user.username, IF(solution.updated > " +
          "solution.created, solution.updated, solution.created), solution.id FROM solution " +
          "LEFT JOIN exercise ON solution.exercise_id=exercise.id LEFT JOIN user ON " +
          "solution.user_id=user.id ORDER BY IF(updated > created, updated, created)" +
          " DESC LIMIT ?;", number);
    }

    public List<LastSolution> loadMostRecentSolutionsByUserId(long id) {
        return executeQuery("SELECT exercise.title, user.username, IF(solution.updated > " +
          "solution.created, solution.updated, solution.created), solution.id FROM solution " +
          "LEFT JOIN exercise ON solution.exercise_id=exercise.id LEFT JOIN user ON " +
          "solution.user_id=user.id  WHERE solution.user_id=? ORDER BY IF(updated > created, " +
          "updated, created) DESC;", id);
    }

    /**
     * executes SQL Query with optional parameter.
     *
     * @param sqlQuery - query to execute
     * @param param    - optional parameter
     * @return array of LastSolution objects
     */
    private List<LastSolution> executeQuery(String sqlQuery, Object... param) {
        List<LastSolution> lastSolutions = new ArrayList<>();
        try (Connection con = ds.getConnection(); PreparedStatement ps =
          prepStatement(con, sqlQuery, param); ResultSet rs = ps.executeQuery())
        {
            while (rs.next()) {
                LastSolution loadedSolution = new LastSolution();
                loadedSolution.setTitle(rs.getString(1));
                loadedSolution.setName(rs.getString(2));
                loadedSolution.setModified(rs.getTimestamp(3));
                loadedSolution.setId(rs.getLong(4));
                lastSolutions.add(loadedSolution);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastSolutions;
    }
}