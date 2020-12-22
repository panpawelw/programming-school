package controller;

import com.panpawelw.DAO.UserDAO;
import com.panpawelw.DAO.UserGroupDAO;
import com.panpawelw.controller.UsersList;
import com.panpawelw.model.User;
import com.panpawelw.model.UserGroup;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import java.util.List;

import static misc.TestUtils.createMultipleUserGroups;
import static misc.TestUtils.createMultipleUsers;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class UsersListTests {

    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();
    private final UsersList usersList = new UsersList();
    private final UserDAO mockUserDAO = mock(UserDAO.class);
    private final UserGroupDAO mockUserGroupDAO = mock(UserGroupDAO.class);

    @Before
    public void setup() throws Exception {
        usersList.setUserDAO(mockUserDAO);
        usersList.setUserGroupDAO(mockUserGroupDAO);
        usersList.init(new MockServletConfig());
    }

    @Test
    public void usersListForwardTest() throws Exception {
        usersList.doGet(request, response);
        assertEquals("/jsp/error.jsp", response.getForwardedUrl());
    }

    @Test
    public void usersListIncorrectIntegerParameterTest() throws Exception {
        request.setParameter("id", "16456");
        expect(mockUserGroupDAO.loadUserGroupById(16456)).andReturn(null);
        incorrectParameterTest();
    }

    @Test
    public void usersListIncorrectIntegerSubZeroParameterTest() throws Exception {
        request.setParameter("id", "-10");
        incorrectParameterTest();
    }

    @Test
    public void usersListCharacterParameterTest() throws Exception {
        request.setParameter("id", "x");
        incorrectParameterTest();
    }

    @Test
    public void usersListNullParameterTest() throws Exception {
        request.setParameter("id", "null");
        incorrectParameterTest();
    }

    @Test
    public void UsersListCorrectUserGroupIdTest() throws Exception {
        request.setParameter("id", "1");
        List<User> expectedList = createMultipleUsers(10,1);
        expect(mockUserGroupDAO.loadUserGroupById(1))
                .andReturn(new UserGroup(1, "Test user group 1"));
        expect(mockUserDAO.loadAllUsersByGroupId(1)).andReturn(expectedList);
        replay(mockUserGroupDAO, mockUserDAO);
        usersList.doGet(request, response);
        assertEquals(expectedList, request.getAttribute("groupuserslist"));
        String expectedUserGroupName = (String) request.getAttribute("groupname");
        String returnedUserGroupName = createMultipleUserGroups(1).get(0).getName();
        assertEquals(expectedUserGroupName, returnedUserGroupName);
        verify(mockUserGroupDAO, mockUserDAO);
    }

    private void incorrectParameterTest() throws Exception {
        replay(mockUserGroupDAO);
        usersList.doGet(request,response);
        assertEquals("No such user group exists!", request.getAttribute("errormessage"));
        assertEquals("/jsp/error.jsp", response.getForwardedUrl());
        verify(mockUserGroupDAO);
    }
}
