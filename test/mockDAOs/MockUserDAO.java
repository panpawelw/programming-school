package mockDAOs;

import com.panpawelw.DAO.UserDAO;
import com.panpawelw.model.User;

import java.util.List;

import static misc.TestUtils.createMultipleUsers;

public class MockUserDAO implements UserDAO {

    @Override
    public int saveUserToDB(User user) {
        return 1;
    }

    @Override
    public User loadUserById(long id) {
        return createMultipleUsers(1).get(0);
    }

    @Override
    public int deleteUser(User user) {
        return 1;
    }

    @Override
    public List<User> loadAllUsers() {
        return createMultipleUsers(10);
    }

    @Override
    public List<User> loadAllUsersByGroupId(int usergroup_id) {
        return createMultipleUsers(10, usergroup_id);
    }
}
