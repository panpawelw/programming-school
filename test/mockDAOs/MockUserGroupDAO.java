package mockDAOs;

import com.panpawelw.DAO.UserGroupDAO;
import com.panpawelw.model.UserGroup;

import java.util.List;

public class MockUserGroupDAO implements UserGroupDAO {

    @Override
    public int saveUserGroupToDB(UserGroup userGroup) {
        return 0;
    }

    @Override
    public UserGroup loadUserGroupById(int id) {
        return null;
    }

    @Override
    public List<UserGroup> loadAllUserGroups() {
        return null;
    }

    @Override
    public int deleteUserGroup(UserGroup userGroup) {
        return 0;
    }
}
