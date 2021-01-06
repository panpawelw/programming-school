package controller;

import com.panpawelw.DAO.UserGroupDAO;
import com.panpawelw.controller.UserGroupsAdminAddEdit;
import com.panpawelw.model.UserGroup;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class UserGroupsAdminAddEditTests {

    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();
    private final UserGroupsAdminAddEdit userGroupsAdminAddEdit = new UserGroupsAdminAddEdit();
    private final UserGroupDAO mockUserGroupDAO = mock(UserGroupDAO.class);

    @Before
    public void setup() throws Exception {
        userGroupsAdminAddEdit.setUserGroupDAO(mockUserGroupDAO);
        userGroupsAdminAddEdit.init(new MockServletConfig());
    }

    @Test
    public void userGroupsAdminAddEditGetForwardTest() throws Exception {
        userGroupsAdminAddEdit.doGet(request, response);
        assertEquals("/groupsadminpanel", response.getForwardedUrl());
    }

    @Test
    public void userGroupsAdminAddEditGetIncorrectUserGroupIdTest() throws Exception {
        request.setParameter("id", "16456");
        expect(mockUserGroupDAO.loadUserGroupById(16456)).andReturn(null);
        testGetMethod("/groupsadminpanel", "No such user group exists!");
    }

    @Test
    public void userGroupsAdminAddEditGetNewUserGroupTest() throws Exception {
        request.setParameter("id", "0");
        testGetMethod("/jsp/usergroupsadminaddeditview.jsp", null,
                new UserGroup(0, null));
    }

    @Test
    public void userGroupsAdminAddEditGetExistingUserGroupTest() throws Exception {
        request.setParameter("id", "5");
        UserGroup expectedUserGroup = new UserGroup(5, "Test name");
        expect(mockUserGroupDAO.loadUserGroupById(5)).andReturn(expectedUserGroup);
        testGetMethod("/jsp/usergroupsadminaddeditview.jsp",
                null, expectedUserGroup);
    }

    @Test
    public void userGroupsAdminAddEditPostForwardTest() throws Exception {
        userGroupsAdminAddEdit.doPost(request, response);
        assertEquals("/groupsadminpanel", response.getForwardedUrl());
    }

    @Test
    public void userGroupsAdminAddEditPostIncorrectIdParameterTest() throws Exception {
        request.setParameter("id", "xxx");
        request.setParameter("group_name", "Test name");
        testPostMethod("Incorrect parameters!");
    }

    @Test
    public void userGroupsAdminAddEditPostNullIdParameterTest() throws Exception {
        request.setParameter("group_name", "Test name");
        testPostMethod("Incorrect parameters!");
    }

    @Test
    public void userGroupsAdminAddEditPostEmptyNameParameterTest() throws Exception {
        request.setParameter("id", "1");
        request.setParameter("group_name", "");
        testPostMethod("Incorrect parameters!");
    }

    @Test
    public void userGroupsAdminAddEditPostNullNameParameterTest() throws Exception {
        request.setParameter("id", "1");
        testPostMethod("Incorrect parameters!");
    }

    @Test
    public void userGroupsAdminAddEditPostNewUserGroupTest() throws Exception {
        request.setParameter("id", "0");
        request.setParameter("group_name", "Test name");
        expect(mockUserGroupDAO.saveUserGroupToDB(new UserGroup(0, "Test name")))
                .andReturn(1);
        testPostMethod(null);
    }

    @Test
    public void userGroupsAdminAddEditPostExistingUserGroupTest() throws Exception {
        request.setParameter("id", "1");
        request.setParameter("group_name", "Test name");
        expect(mockUserGroupDAO.loadUserGroupById(1)).andReturn(new UserGroup(1, "Test name"));
        expect(mockUserGroupDAO.saveUserGroupToDB(new UserGroup(1, "Test name"))).andReturn(1);
        testPostMethod(null);
    }

    @Test
    public void userGroupsAdminAddEditPostDatabaseErrorTest() throws Exception {
        request.setParameter("id", "1");
        request.setParameter("group_name", "Test name");
        expect(mockUserGroupDAO.loadUserGroupById(1)).andReturn(new UserGroup(1, "Test name"));
        expect(mockUserGroupDAO.saveUserGroupToDB(new UserGroup(1, "Test name"))).andReturn(0);
        testPostMethod("Database error!");
    }

    private void testGetMethod(String expectedUrl, String expectedErrorMessage,
                               UserGroup... expectedUserGroup) throws Exception {
        replay(mockUserGroupDAO);
        userGroupsAdminAddEdit.doGet(request,response);
        assertEquals(expectedUrl, response.getForwardedUrl());
        if (expectedErrorMessage != null) {
            assertEquals(expectedErrorMessage, request.getAttribute("errormessage"));
        }
        if (expectedUserGroup.length > 0) {
            assertEquals(expectedUserGroup[0], request.getAttribute("group"));
        }
        verify(mockUserGroupDAO);
    }

    private void testPostMethod(String errorMessage) throws Exception {
        replay(mockUserGroupDAO);
        userGroupsAdminAddEdit.doPost(request, response);
        if (errorMessage != null) {
            assertEquals(errorMessage, request.getAttribute("errormessage"));
        }
        assertEquals("/groupsadminpanel", response.getForwardedUrl());
        verify(mockUserGroupDAO);
    }
}
