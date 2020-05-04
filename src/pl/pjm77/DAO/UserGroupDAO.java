package pl.pjm77.DAO;

import pl.pjm77.model.UserGroup;

import java.util.List;

public interface UserGroupDAO {

    void saveUserGroupToDB(UserGroup userGroup);
    UserGroup loadUserGroupById(int id);
    List<UserGroup> loadAllUserGroups();
    void deleteUserGroup(UserGroup userGroup);

}
