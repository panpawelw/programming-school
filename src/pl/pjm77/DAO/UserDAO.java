package pl.pjm77.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.pjm77.misc.DbUtil;
import pl.pjm77.model.User;

public class UserDAO {

	public void saveUserToDB(User user) {
		try (Connection con = DbUtil.getConn()) {
			if (user.getId() == 0) {
				String sql = "INSERT INTO User(username, email, password, usergroup_id) VALUES (?, ?, ?, ?);";
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
				String sql = "UPDATE User SET username=?, email=?, password=?, usergroup_id=? WHERE id = ?;";
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
	
	public static User loadUserById(long id) {
		try (Connection con = DbUtil.getConn()) {
			String sql = "SELECT * FROM User WHERE id=?;";
			try (PreparedStatement ps = con.prepareStatement(sql)) {
				ps.setLong(1, id);
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						return loadUser(rs);
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
			String sql = "DELETE FROM User WHERE id=?";
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

	public static User[] loadAllUsers() {
		return loadUsersBy(true, 0);
	}

	public static User[] loadAllbyGroupId(int usergroup_id) {
		return loadUsersBy(false, usergroup_id);
	}

	private static User[] loadUsersBy(boolean loadAll, long param){
		String sql;
		if(loadAll) sql = "SELECT * FROM User;";
		else sql = "SELECT * FROM User WHERE usergroup_id=?;";
		List<User> usersByParamArrayList = new ArrayList<>();
		try (Connection con = DbUtil.getConn()) {
			try (PreparedStatement ps = con.prepareStatement(sql)) {
				if(param!=0) ps.setLong(1, param);
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						usersByParamArrayList.add(loadUser(rs));
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

	private static User loadUser(ResultSet rs) throws SQLException{
		User loadedUser = new User();
		loadedUser.setId(rs.getLong("id"));
		loadedUser.setName(rs.getString("username"));
		loadedUser.setEmail(rs.getString("email"));
		loadedUser.setPassword(rs.getString("password"));
		loadedUser.setGroup_id(rs.getInt("usergroup_id"));
		return loadedUser;
	}
}