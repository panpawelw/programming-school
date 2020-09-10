package com.panpawelw.DAO;

import com.panpawelw.model.User;

import java.util.List;

public interface UserDAO {

    int saveUserToDB(User user);
    User loadUserById(long id);
    int deleteUser(User user);
    List<User> loadAllUsers();
    List<User> loadAllUsersByGroupId(int usergroup_id);
}