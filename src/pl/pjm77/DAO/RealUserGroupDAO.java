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

    private final DataSource dataSource;

    public RealUserGroupDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void saveUserGroupToDB(UserGroup userGroup) {
        try (Connection con = dataSource.getConnection()) {
            if (userGroup.getId() == 0) {
                String sql = "INSERT INTO usergroup (name) VALUES (?)";
                String[] generatedColumns = {" ID "};
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
            e.printStackTrace();
        }
    }

    public UserGroup loadUserGroupById(int id) {
        try (PreparedStatement ps = prepStatement(dataSource.getConnection(),
                "SELECT * FROM usergroup WHERE id=?;", id);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return loadSingleUserGroup(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserGroup[] loadAllUserGroups() {
        List<UserGroup> userGroups = new ArrayList<>();
        try (PreparedStatement ps = prepStatement(dataSource.getConnection(),
                "SELECT * FROM usergroup;");
             ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        userGroups.add(loadSingleUserGroup(rs));
                    }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        UserGroup[] gArray = new UserGroup[userGroups.size()];
        gArray = userGroups.toArray(gArray);
        return gArray;
    }

    public void deleteUserGroup(UserGroup userGroup) {
        try (PreparedStatement ps = prepStatement(dataSource.getConnection(),
                "DELETE * FROM usergroup WHERE id=?;", userGroup.getId())) {
            ps.executeUpdate();
            userGroup.setId(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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