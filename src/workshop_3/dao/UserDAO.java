package workshop_3.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import workshop_3.misc.DbUtil;
import workshop_3.model.User;

public class UserDAO {

	public void saveUserToDB(User user) {
		try (Connection con = DbUtil.getConn()) {
			if (user.getId() == 0) {
				String sql = "INSERT INTO user(name, email, password, group_id) VALUES (?, ?, ?, ?);";
				String generatedColumns[] = { " ID " };
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
				String sql = "UPDATE user SET name=?, email=?, password=?, group_id=? WHERE id = ?;";
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
	
	static public User loadUserById(long id) {
		try (Connection con = DbUtil.getConn()) {
			String sql = "SELECT * FROM user WHERE id=?;";
			try (PreparedStatement ps = con.prepareStatement(sql)) {
				ps.setLong(1, id);
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						User loadedUser = new User();
						loadedUser.setId(rs.getLong("id"));
						loadedUser.setName(rs.getString("name"));
						loadedUser.setEmail(rs.getString("email"));
						loadedUser.setPassword(rs.getString("password"));
						loadedUser.setGroup_id(rs.getInt("group_id"));
						return loadedUser;
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
	
	static public User[] loadAllUsers() {
		List<User> users = new ArrayList<User>();
		try (Connection con = DbUtil.getConn()) {
			String sql = "SELECT * FROM user;";
			try (PreparedStatement ps = con.prepareStatement(sql)) {
				try (ResultSet rs = ps.executeQuery()) {
					while(rs.next()) {
						User loadedUser = new User();
						loadedUser.setId(rs.getLong("id"));
						loadedUser.setName(rs.getString("name"));
						loadedUser.setEmail(rs.getString("email"));
						loadedUser.setPassword(rs.getString("password"));
						loadedUser.setGroup_id(rs.getInt("group_id"));
						users.add(loadedUser);
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("Database error!");
			e.printStackTrace();
		}
		User uArray[] = new User[users.size()];
		uArray = users.toArray(uArray);
		return uArray;
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
	
	public static User[] loadAllbyGroupId(int group_id) {
		ArrayList<User> groupUsers = new ArrayList<User>();
		try (Connection con = DbUtil.getConn()) {
			String sql = "SELECT * FROM user WHERE group_id=?;";
			try (PreparedStatement ps = con.prepareStatement(sql)) {
				ps.setInt(1, group_id);
				try (ResultSet rs = ps.executeQuery()) {
					while(rs.next()) {
						User loadedUser = new User();
						loadedUser.setId(rs.getLong("id"));
						loadedUser.setName(rs.getString("name"));
						loadedUser.setEmail(rs.getString("email"));
						loadedUser.setPassword(rs.getString("password"));
						loadedUser.setGroup_id(rs.getInt("group_id"));
						groupUsers.add(loadedUser);
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("Database error!");
			e.printStackTrace();
		}
		User[] guArray = new User[groupUsers.size()];
		guArray = groupUsers.toArray(guArray);
		return guArray;
	} 
}