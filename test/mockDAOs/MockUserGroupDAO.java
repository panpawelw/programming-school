package mockDAOs;

import com.panpawelw.DAO.UserGroupDAO;
import com.panpawelw.model.UserGroup;
import java.util.List;

import static misc.TestUtils.createMultipleUserGroups;

public class MockUserGroupDAO implements UserGroupDAO {

    @Override
    public int saveUserGroupToDB(UserGroup userGroup) {
        return 1;
    }

    @Override
    public UserGroup loadUserGroupById(int id) {
        return createMultipleUserGroups(1).get(0);
    }

    @Override
    public List<UserGroup> loadAllUserGroups() {
        return createMultipleUserGroups(10);
    }

    @Override
    public int deleteUserGroup(UserGroup userGroup) {
        return 1;
    }
}
