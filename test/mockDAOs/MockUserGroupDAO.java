package mockDAOs;

import com.panpawelw.DAO.UserGroupDAO;
import com.panpawelw.model.UserGroup;
import misc.TestUtils;

import java.util.ArrayList;
import java.util.List;

public class MockUserGroupDAO implements UserGroupDAO {

    @Override
    public int saveUserGroupToDB(UserGroup userGroup) {
        return 1;
    }

    @Override
    public UserGroup loadUserGroupById(int id) {
        return TestUtils.createMultipleUserGroups(1).get(0);
    }

    @Override
    public List<UserGroup> loadAllUserGroups() {
        return new ArrayList<>();
    }

    @Override
    public int deleteUserGroup(UserGroup userGroup) {
        return 1;
    }
}
