package pl.pjm77.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.pjm77.misc.DbUtil;
import pl.pjm77.model.UserGroup;

import javax.sql.DataSource;

public class RealUserGroupDAO implements UserGroupDAO {

    private final DataSource dataSource;

    public RealUserGroupDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void saveUserGroupToDB(UserGroup userGroup) {
        try (Connection con = dataSource.getConnection()) {
            if (userGroup.getId() == 0) {
                String sql = "INSERT INTO usergroup (name) VALUES (?)";
                String[] generatedColumns = { " ID " };
                try (PreparedStatement ps = con.prepareStatement(sql, generatedColumns)) {
                    ps.setString(1, userGroup.getName());
                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            userGroup.setId(rs.getInt(1));
                        }
                    }
                }
            } else {
                String sql = "UPDATE usergroup SET name=? WHERE id=?;";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, userGroup.getName());
                    ps.setInt(2, userGroup.getId());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error!");
            e.printStackTrace();
        }
    }

    public UserGroup loadUserGroupById(int id) {
        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT * FROM usergroup WHERE id=?;";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return loadSingleUserGroup(rs);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error!");
            e.printStackTrace();
        }
        System.out.println("No such user group!");
        return null;
    }

    public UserGroup[] loadAllUserGroups() {
        List<UserGroup> userGroups = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT * FROM usergroup;";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                        userGroups.add(loadSingleUserGroup(rs));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error!");
            e.printStackTrace();
        }
        UserGroup[] gArray = new UserGroup[userGroups.size()];
        gArray = userGroups.toArray(gArray);
        return gArray;
    }

    public void deleteUserGroup(UserGroup userGroup) {
        try (Connection con = dataSource.getConnection()) {
            String sql = "DELETE FROM usergroup WHERE id=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, userGroup.getId());
                ps.executeUpdate();
                userGroup.setId(0);
            }
        } catch (SQLException e) {
            System.out.println("Database error!");
            e.printStackTrace();
        }
    }

    /**
     * Gets single UserGroup object from result set.
     * @param rs - ResultSet
     * @return - UserGroup object
     * @throws SQLException - in case of database problems
     */
    private UserGroup loadSingleUserGroup(ResultSet rs) throws SQLException{
        UserGroup loadedUserGroup = new UserGroup();
        loadedUserGroup.setId(rs.getInt("id"));
        loadedUserGroup.setName(rs.getString("name"));
        return loadedUserGroup;
    }
}