package controller;

import com.panpawelw.DAO.UserGroupDAO;
import com.panpawelw.controller.UserGroupsAdminDelete;
import com.panpawelw.model.UserGroup;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserGroupsAdminDeleteTests {

    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();
    private final UserGroupsAdminDelete userGroupsAdminDelete = new UserGroupsAdminDelete();
    private final UserGroupDAO mockUserGroupDAO = mock(UserGroupDAO.class);

    @Before
    public void setup() throws Exception {
        userGroupsAdminDelete.setUserGroupDAO(mockUserGroupDAO);
        userGroupsAdminDelete.init(new MockServletConfig());
    }

    @Test
    public void userGroupsAdminDeleteForwardTest() throws Exception {
        request.setParameter("id","0");
        userGroupsAdminDelete.doGet(request, response);
        assertEquals("/groupsadminpanel", response.getForwardedUrl());
    }

    @Test
    public void userGroupsAdminDeleteIncorrectIntegerParameterTest() throws Exception {
        request.setParameter("id", "16456");
        expect(mockUserGroupDAO.loadUserGroupById(16456)).andReturn(null);
        incorrectParameterTest();
    }

    @Test
    public void userGroupsAdminDeleteIncorrectIntegerSubZeroParameterTest() throws Exception {
        request.setParameter("id", "-10");
        incorrectParameterTest();
    }

    @Test
    public void userGroupsAdminDeleteCharacterParameterTest() throws Exception {
        request.setParameter("id", "x");
        incorrectParameterTest();
    }

    @Test
    public void userGroupsAdminDeleteNullParameterTest() throws Exception {
        request.setParameter("id", "null");
        incorrectParameterTest();
    }

    @Test
    public void userGroupsAdminDeleteCorrectParameterTest() throws Exception {
        request.setParameter("id","4");
        UserGroup userGroupToDelete = new UserGroup(4, "Test name");
        expect(mockUserGroupDAO.loadUserGroupById(4)).andReturn(userGroupToDelete);
        expect(mockUserGroupDAO.deleteUserGroup(userGroupToDelete)).andReturn(1);
        replay(mockUserGroupDAO);
        userGroupsAdminDelete.doGet(request, response);
        //noinspection ConstantConditions
        assertNull(request.getAttribute("errormessage"));
        assertEquals("/groupsadminpanel", response.getForwardedUrl());
        verify(mockUserGroupDAO);
    }

    private void incorrectParameterTest() throws Exception {
        replay(mockUserGroupDAO);
        userGroupsAdminDelete.doGet(request,response);
        assertEquals("Error deleting user group!", request.getAttribute("errormessage"));
        assertEquals("/groupsadminpanel", response.getForwardedUrl());
        verify(mockUserGroupDAO);
    }
}
