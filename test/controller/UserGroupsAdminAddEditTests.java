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
    public void userGroupsAdminAddEditPostForwardTest() throws Exception {
        userGroupsAdminAddEdit.doPost(request, response);
        assertEquals("/groupsadminpanel", response.getForwardedUrl());
    }

    @Test
    public void userGroupsAdminAddEditGetIncorrectIntegerParameterTest() throws Exception {
        request.setParameter("id", "16456");
        expect(mockUserGroupDAO.loadUserGroupById(16456)).andReturn(null);
        incorrectParameterTest();
    }

    @Test
    public void userGroupsAdminAddEditGetIncorrectIntegerSubZeroParameterTest() throws Exception {
        request.setParameter("id", "-10");
        incorrectParameterTest();
    }

    @Test
    public void userGroupsAdminAddEditGetCharacterParameterTest() throws Exception {
        request.setParameter("id", "x");
        incorrectParameterTest();
    }

    @Test
    public void userGroupsAdminAddEditGetNullParameterTest() throws Exception {
        request.setParameter("id", "null");
        incorrectParameterTest();
    }

    @Test
    public void userGroupsAdminAddEditGetCorrectParameterTest() throws Exception {
        request.setParameter("id", "5");
        UserGroup expectedUserGroup = new UserGroup(3, "Test name");
        expect(mockUserGroupDAO.loadUserGroupById(5)).andReturn(expectedUserGroup);
        replay(mockUserGroupDAO);
        userGroupsAdminAddEdit.doGet(request,response);
        assertEquals(expectedUserGroup, request.getAttribute("group"));
        assertEquals("/jsp/usergroupsadminaddeditview.jsp", response.getForwardedUrl());
        verify(mockUserGroupDAO);
    }

    private void incorrectParameterTest() throws Exception {
        replay(mockUserGroupDAO);
        userGroupsAdminAddEdit.doGet(request,response);
        assertEquals("No such user group exists!", request.getAttribute("errormessage"));
        assertEquals("/groupsadminpanel", response.getForwardedUrl());
        verify(mockUserGroupDAO);
    }
}
