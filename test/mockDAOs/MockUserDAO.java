package mockDAOs;

import com.panpawelw.DAO.UserDAO;
import com.panpawelw.model.User;

import java.util.List;

public class MockUserDAO implements UserDAO {

    @Override
    public int saveUserToDB(User user) {
        return 0;
    }

    @Override
    public User loadUserById(long id) {
        return null;
    }

    @Override
    public int deleteUser(User user) {
        return 0;
    }

    @Override
    public List<User> loadAllUsers() {
        return null;
    }

    @Override
    public List<User> loadAllUsersByGroupId(int usergroup_id) {
        return null;
    }
}
