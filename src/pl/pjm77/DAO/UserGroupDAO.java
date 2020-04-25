package pl.pjm77.DAO;

import pl.pjm77.model.UserGroup;

public interface UserGroupDAO {

    void saveUserGroupToDB(UserGroup userGroup);
    UserGroup loadUserGroupById(int id);
    UserGroup[] loadAllUserGroups();
    void deleteUserGroup(UserGroup userGroup);

}
