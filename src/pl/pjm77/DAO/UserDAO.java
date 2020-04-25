package pl.pjm77.DAO;

import pl.pjm77.model.User;

public interface UserDAO {

    void saveUserToDB(User user);
    User loadUserById(long id);
    void deleteUser(User user);
    User[] loadAllUsers();
    User[] loadAllUsersByGroupId(int usergroup_id);

}