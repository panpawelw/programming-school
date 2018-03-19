package workshop_3.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import workshop_3.DbUtil;
import workshop_3.model.UserGroup;

public class UserGroupDAO {

	public void saveUserGroupToDB(UserGroup userGroup) {
		try (Connection con = DbUtil.getConn()) {
			if (userGroup.getId() == 0) {
				String sql = "INSERT INTO usergroup (name) VALUES (?)";
				String generatedColumns[] = { " ID " };
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
	
	static public UserGroup loadUserGroupById(int id) {
		try (Connection con = DbUtil.getConn()) {
			String sql = "SELECT * FROM usergroup WHERE id=?;";
			try (PreparedStatement ps = con.prepareStatement(sql)) {
				ps.setInt(1, id);
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						UserGroup loadedGroup = new UserGroup();
						loadedGroup.setId(rs.getInt("id"));
						loadedGroup.setName(rs.getString("name"));
						return loadedGroup;
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
	
	static public UserGroup[] loadAllUserGroups() {
		List<UserGroup> userGroups = new ArrayList<UserGroup>();
		try (Connection con = DbUtil.getConn()) {
			String sql = "SELECT * FROM usergroup;";
			try (PreparedStatement ps = con.prepareStatement(sql)) {
				try (ResultSet rs = ps.executeQuery()) {
					while(rs.next()) {
						UserGroup loadedUserGroup = new UserGroup();
						loadedUserGroup.setId(rs.getInt("id"));
						loadedUserGroup.setName(rs.getString("name"));
						userGroups.add(loadedUserGroup);
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("Database error!");
			e.printStackTrace();
		}
		UserGroup gArray[] = new UserGroup[userGroups.size()];
		gArray = userGroups.toArray(gArray);
		return gArray;
	}
	
	public void deleteUserGroup(UserGroup userGroup) {
		try (Connection con = DbUtil.getConn()) {
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
}