package pl.pjm77.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.pjm77.misc.DbUtil;
import pl.pjm77.model.User;

public class RealUserDAO implements UserDAO {

    public void saveUserToDB(User user) {
        try (Connection con = DbUtil.getConn()) {
            if (user.getId() == 0) {
                String sql = "INSERT INTO user(username, email, password, usergroup_id) VALUES " +
						"(?, ?, ?, ?);";
                String[] generatedColumns = {" ID "};
                try (PreparedStatement ps = con.prepareStatement(sql, generatedColumns)) {
                    ps.setString(1, user.getName());
                    ps.setString(2, user.getEmail());
                    ps.setString(3, user.getPassword());
                    ps.setInt(4, user.getGroup_id());
                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            user.setId(rs.getLong(1));
                        }
                    }
                }
            } else {
                String sql = "UPDATE user SET username=?, email=?, password=?, usergroup_id=? " +
						"WHERE id = ?;";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, user.getName());
                    ps.setString(2, user.getEmail());
                    ps.setString(3, user.getPassword());
                    ps.setInt(4, user.getGroup_id());
                    ps.setLong(5, user.getId());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error!");
            e.printStackTrace();
        }
    }

    public User loadUserById(long id) {
        try (Connection con = DbUtil.getConn()) {
            String sql = "SELECT * FROM user WHERE id=?;";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setLong(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return loadSingleUser(rs);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error!");
            e.printStackTrace();
        }
        System.out.println("No such user!");
        return null;
    }

    public void deleteUser(User user) {
        try (Connection con = DbUtil.getConn()) {
            String sql = "DELETE FROM user WHERE id=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setLong(1, user.getId());
                ps.executeUpdate();
                user.setId(0);
            }
        } catch (SQLException e) {
            System.out.println("Database error!");
            e.printStackTrace();
        }
    }

    public User[] loadAllUsers() {
        return executeQuery("SELECT * FROM user;");
    }

    public User[] loadAllUsersByGroupId(int usergroup_id) {
        return executeQuery("SELECT * FROM user WHERE usergroup_id=?;", usergroup_id);
    }

    /**
     * executes SQL Query with optional parameter.
     * @param sqlQuery - query to execute
     * @param param - optional parameter
     * @return user objects array
     */
    private User[] executeQuery(String sqlQuery, long...param) {
        List<User> usersByParamArrayList = new ArrayList<>();
        try (Connection con = DbUtil.getConn()) {
            try (PreparedStatement ps = con.prepareStatement(sqlQuery)) {
                if (param.length != 0) ps.setLong(1, param[0]);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        usersByParamArrayList.add(loadSingleUser(rs));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error!");
            e.printStackTrace();
        }
        User[] usersByParamArray = new User[usersByParamArrayList.size()];
        usersByParamArray = usersByParamArrayList.toArray(usersByParamArray);
        return usersByParamArray;
    }

    /**
     * Gets single User object from result set.
     * @param rs - ResultSet
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