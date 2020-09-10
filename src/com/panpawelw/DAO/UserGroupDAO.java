package com.panpawelw.DAO;

import com.panpawelw.model.UserGroup;

import java.util.List;

public interface UserGroupDAO {

    int saveUserGroupToDB(UserGroup userGroup);
    UserGroup loadUserGroupById(int id);
    List<UserGroup> loadAllUserGroups();
    int deleteUserGroup(UserGroup userGroup);
}