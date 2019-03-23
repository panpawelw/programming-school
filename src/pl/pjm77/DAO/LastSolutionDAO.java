package pl.pjm77.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.pjm77.misc.DbUtil;
import pl.pjm77.model.LastSolution;

public class LastSolutionDAO {

    public static LastSolution[] loadAllSolutions(int number) {
        return loadSolutionsBy(true, number, 0);
    }

    public static LastSolution[] loadAllSolutionsByUserId(long user_id) {
        return loadSolutionsBy(false, 0, user_id);
    }

    private static LastSolution[] loadSolutionsBy(boolean all, int number, long param) {
        List<LastSolution> solutions = new ArrayList<>();
        String sql;
        if(all) sql = "SELECT Exercise.title, User.username, IF(Solution.updated > Solution.created, Solution.updated, Solution.created),"
                + " Solution.id FROM Solution LEFT JOIN Exercise ON Solution.exercise_id=Exercise.id"
                + " LEFT JOIN User ON Solution.user_id=User.id ORDER BY IF(updated > created, updated, created) DESC LIMIT ?;";
        else sql = "SELECT Exercise.title, User.username, IF(Solution.updated > Solution.created, Solution.updated, Solution.created),"
                + " Solution.id FROM Solution LEFT JOIN Exercise ON Solution.exercise_id=Exercise.id"
                + " LEFT JOIN User ON Solution.user_id=User.id  WHERE Solution.user_id=? ORDER BY IF(updated > created, updated, created) DESC;";
        try (Connection con = DbUtil.getConn()) {
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                if (all) ps.setInt(1,number);
                else ps.setLong(1, param);
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