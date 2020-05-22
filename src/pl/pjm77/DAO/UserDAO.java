package pl.pjm77.DAO;

import pl.pjm77.model.User;

import java.util.List;

public interface UserDAO {

    int saveUserToDB(User user);
    User loadUserById(long id);
    void deleteUser(User user);
    List<User> loadAllUsers();
    List<User> loadAllUsersByGroupId(int usergroup_id);
}