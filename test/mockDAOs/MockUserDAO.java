package mockDAOs;

import com.panpawelw.DAO.UserDAO;
import com.panpawelw.model.User;

import java.util.ArrayList;
import java.util.List;

public class MockUserDAO implements UserDAO {

    @Override
    public int saveUserToDB(User user) {
        return 1;
    }

    @Override
    public User loadUserById(long id) {
        return new User();
    }

    @Override
    public int deleteUser(User user) {
        return 1;
    }

    @Override
    public List<User> loadAllUsers() {
        return new ArrayList<>();
    }

    @Override
    public List<User> loadAllUsersByGroupId(int usergroup_id) {
        return new ArrayList<>();
    }
}
