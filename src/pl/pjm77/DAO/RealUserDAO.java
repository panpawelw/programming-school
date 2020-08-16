package pl.pjm77.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.pjm77.model.User;

import javax.sql.DataSource;

import static pl.pjm77.misc.DbUtils.prepStatement;

public class RealUserDAO implements UserDAO {

    private final DataSource ds;

    public RealUserDAO(DataSource ds) {
        this.ds = ds;
    }

    public int saveUserToDB(User user) {
        int affectedRows = 0;
        try {
            if (user.getId() == 0) {
                String[] columnNames = {"ID"};
                try (Connection con = ds.getConnection(); PreparedStatement ps = prepStatement(con,
                  "INSERT INTO user(username, email, password, usergroup_id) VALUES (?, ?, ?, ?);",
                  columnNames, user.getName(), user.getEmail(), user.getPassword(),
                  user.getGroup_id()); ResultSet rs = ps.getGeneratedKeys())
                {
                    affectedRows = ps.executeUpdate();
                    if (rs.next()) {
                        user.setId(rs.getLong(1));
                    }
                }
            } else {
                try (Connection con = ds.getConnection(); PreparedStatement ps = prepStatement(con,
                  "UPDATE user SET username=?, email=?, password=?, usergroup_id=? WHERE id = ?;",
                  user.getName(), user.getEmail(), user.getPassword(),
                  user.getGroup_id(), user.getId()))
                {
                    affectedRows = ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(affectedRows);
        return affectedRows;
    }

    public User loadUserById(long id) {
        try (Connection con = ds.getConnection(); PreparedStatement ps = prepStatement(con,
          "SELECT * FROM user WHERE id=?;", id); ResultSet rs = ps.executeQuery())
        {
            if (rs.next()) {
                return loadSingleUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int deleteUser(User user) {
        int affectedRows = 0;
        try (Connection con = ds.getConnection(); PreparedStatement ps = prepStatement(con,
          "DELETE FROM user WHERE id=?", user.getId()))
        {
            affectedRows = ps.executeUpdate();
            user.setId(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    public List<User> loadAllUsers() {
        return executeQuery("SELECT * FROM user;");
    }

    public List<User> loadAllUsersByGroupId(int usergroup_id) {
        return executeQuery("SELECT * FROM user WHERE usergroup_id=?;", usergroup_id);
    }

    /**
     * executes SQL Query with optional parameter.
     *
     * @param sqlQuery - query to execute
     * @param param    - optional parameter
     * @return list of User objects
     */
    private List<User> executeQuery(String sqlQuery, Object... param) {
        List<User> users = new ArrayList<>();
        try (Connection con = ds.getConnection(); PreparedStatement ps =
          prepStatement(con, sqlQuery, param); ResultSet rs = ps.executeQuery())
        {
            while (rs.next()) {
                users.add(loadSingleUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Gets single User object from result set.
     *
     * @param rs - ResultSet object
     * @return - User object
     * @throws SQLException - in case of database problems
     */
    private User loadSingleUser(ResultSet rs) throws SQLException {
        User loadedUser = new User();
        loadedUser.setId(rs.getLong("id"));
        loadedUser.setName(rs.getString("username"));
        loadedUser.setEmail(rs.getString("email"));
        loadedUser.setPassword(rs.getString("password"));
        loadedUser.setGroup_id(rs.getInt("usergroup_id"));
        return loadedUser;
    }
}