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

    private final DataSource dataSource;

    public RealSolutionDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void saveSolutionToDB(Solution solution) {
        try {
            if (solution.getId() == 0) {
                String[] columnNames = {" ID "};
                java.sql.Timestamp created = new java.sql.Timestamp(new Date().getTime());
                try (PreparedStatement ps = prepStatement(dataSource.getConnection(),
                        "INSERT INTO solutiom(created, updated, description, exercise_id," +
                                " user_id) VALUES (?, ?, ?, ?, ?);", columnNames, created,
                        null, solution.getDescription(), solution.getExercise_id(),
                        solution.getUser_id()); ResultSet rs = ps.getGeneratedKeys()) {
                    ps.executeUpdate();
                    if (rs.next()) {
                        solution.setId(rs.getLong(1));
                    }}
                } else {
                try (PreparedStatement ps = prepStatement(dataSource.getConnection(),
                        "UPDATE solutiom SET updated=Now(), description=?, exercise_id=?, " +
                                "user_id=? WHERE id = ?;", solution.getDescription(),
                        solution.getExercise_id(), solution.getUser_id(), solution.getId())) {
                    ps.executeUpdate();
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Solution loadSolutionById(long id) {
        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT * FROM solution WHERE id=?;";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setLong(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return loadSingleSolution(rs);
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
        try (Connection con = dataSource.getConnection()) {
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

    public Solution[] loadAllSolutions() {
        return executeQuery("SELECT * FROM solution;");
    }

    public Solution[] loadAllSolutionsByUserId(long id) {
        return executeQuery("SELECT * FROM solution WHERE user_id=?;", id);
    }

    /**
     * executes SQL Query with optional parameter.
     *
     * @param sqlQuery - query to execute
     * @param param    - optional parameter
     * @return user objects array
     */
    private Solution[] executeQuery(String sqlQuery, long... param) {
        List<Solution> solutions = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(sqlQuery)) {
                if (param.length != 0) ps.setLong(1, param[0]);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        solutions.add(loadSingleSolution(rs));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error!");
            e.printStackTrace();
        }
        Solution[] sArray = new Solution[solutions.size()];
        sArray = solutions.toArray(sArray);
        return sArray;
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