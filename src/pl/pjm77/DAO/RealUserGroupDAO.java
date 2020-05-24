package pl.pjm77.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.pjm77.model.UserGroup;

import javax.sql.DataSource;

import static pl.pjm77.misc.DbUtils.prepStatement;

public class RealUserGroupDAO implements UserGroupDAO {

    private final DataSource ds;

    public RealUserGroupDAO(DataSource ds) {
        this.ds = ds;
    }

    public int saveUserGroupToDB(UserGroup userGroup) {
        int affectedRows = 0;
        try {
            if (userGroup.getId() == 0) {
                String[] columnNames = {"ID"};
                try (Connection con = ds.getConnection(); PreparedStatement ps = prepStatement(con,
                  "INSERT INTO usergroup (name) VALUES (?);", columnNames, userGroup.getName());
                     ResultSet rs = ps.getGeneratedKeys())
                {
                    affectedRows = ps.executeUpdate();
                    if (rs.next()) userGroup.setId(rs.getInt(1));
                }
            } else {
                try (Connection con = ds.getConnection(); PreparedStatement ps = prepStatement(con,
                  "UPDATE usergroup SET name=? WHERE id=?;", userGroup.getName(),
                  userGroup.getId()))
                {
                    affectedRows = ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    public UserGroup loadUserGroupById(int id) {
        try (Connection con = ds.getConnection(); PreparedStatement ps = prepStatement(con,
          "SELECT * FROM usergroup WHERE id=?;", id); ResultSet rs = ps.executeQuery())
        {
            if (rs.next()) {
                return loadSingleUserGroup(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<UserGroup> loadAllUserGroups() {
        List<UserGroup> userGroups = new ArrayList<>();
        try (Connection con = ds.getConnection(); PreparedStatement ps = prepStatement(con,
          "SELECT * FROM usergroup;"); ResultSet rs = ps.executeQuery())
        {
            while (rs.next()) {
                userGroups.add(loadSingleUserGroup(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userGroups;
    }

    public int deleteUserGroup(UserGroup userGroup) {
        int affectedRows = 0;
        try (Connection con = ds.getConnection(); PreparedStatement ps = prepStatement(con,
          "DELETE FROM usergroup WHERE id=?;", userGroup.getId()))
        {
            affectedRows = ps.executeUpdate();
            userGroup.setId(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    /**
     * Gets single UserGroup object from result set.
     *
     * @param rs - ResultSet
     * @return - UserGroup object
     * @throws SQLException - in case of database problems
     */
    private UserGroup loadSingleUserGroup(ResultSet rs) throws SQLException {
        UserGroup loadedUserGroup = new UserGroup();
        loadedUserGroup.setId(rs.getInt("id"));
        loadedUserGroup.setName(rs.getString("name"));
        return loadedUserGroup;
    }
}