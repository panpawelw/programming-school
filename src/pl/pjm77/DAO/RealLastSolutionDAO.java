package pl.pjm77.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.pjm77.misc.DbUtil;
import pl.pjm77.model.LastSolution;

public class RealLastSolutionDAO implements LastSolutionDAO {

    public LastSolution[] loadMostRecentSolutions(long number) {
        return executeQuery("SELECT exercise.title, user.username, IF(solution.updated > " +
                "solution.created, solution.updated, solution.created), solution.id FROM solution " +
                "LEFT JOIN exercise ON solution.exercise_id=exercise.id LEFT JOIN user ON " +
                "solution.user_id=user.id ORDER BY IF(updated > created, updated, created)" +
                " DESC LIMIT ?;", number);
    }

    public LastSolution[] loadMostRecentSolutionsByUserId(long id) {
        return executeQuery("SELECT exercise.title, user.username, IF(solution.updated > " +
                "solution.created, solution.updated, solution.created), solution.id FROM solution " +
                "LEFT JOIN exercise ON solution.exercise_id=exercise.id LEFT JOIN user ON " +
                "solution.user_id=user.id  WHERE solution.user_id=? ORDER BY IF(updated > created, " +
                "updated, created) DESC;", id);
    }

    private LastSolution[] executeQuery(String sqlQuery, long...param) {
        List<LastSolution> solutions = new ArrayList<>();
        try (Connection con = DbUtil.getConn()) {
            try (PreparedStatement ps = con.prepareStatement(sqlQuery)) {
                ps.setLong(1, param[0]);
                try (ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                        LastSolution loadedSolution = new LastSolution();
                        loadedSolution.setTitle(rs.getString(1));
                        loadedSolution.setName(rs.getString(2));
                        loadedSolution.setModified(rs.getTimestamp(3));
                        loadedSolution.setId(rs.getLong(4));
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