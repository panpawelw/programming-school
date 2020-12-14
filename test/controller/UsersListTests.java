package controller;

import com.panpawelw.controller.UsersList;
import com.panpawelw.model.User;
import mockDAOs.MockUserDAO;
import mockDAOs.MockUserGroupDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import java.util.List;
import java.util.stream.Collectors;

import static misc.TestUtils.createMultipleUserGroups;
import static misc.TestUtils.createMultipleUsers;
import static org.junit.Assert.assertEquals;

public class UsersListTests {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private UsersList userslist;

    @Before
    public void setup() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        userslist = new UsersList();
        userslist.setUserDAO(new MockUserDAO());
        userslist.setUserGroupDAO(new MockUserGroupDAO());
        userslist.init(new MockServletConfig());
    }

    @Test
    public void UsersListForwardTest() throws Exception {
        userslist.doGet(request, response);
        assertEquals("/jsp/error.jsp", response.getForwardedUrl());
    }

    @Test
    public void UsersListCorrectUserGroupIdTest() throws Exception {
        request.setParameter("id", "1");
        userslist.doGet(request, response);
        Object rawList = request.getAttribute("groupuserslist");
        List<User> expectedList = createMultipleUsers(1);
        List<User> returnedList =
                ((List<?>) rawList).stream().map(el -> (User) el).collect(Collectors.toList());
        assertEquals(expectedList, returnedList);
        String expectedUserGroupName = (String) request.getAttribute("groupname");
        String returnedUserGroupName = createMultipleUserGroups(1).get(0).getName();
        assertEquals(expectedUserGroupName, returnedUserGroupName);
    }

    @Test
    public void UsersListIncorrectUserGroupIdTest() throws Exception {
        request.setParameter("id", "x");
        userslist.doGet(request, response);
        assertEquals("/jsp/error.jsp", response.getForwardedUrl());
    }
}
