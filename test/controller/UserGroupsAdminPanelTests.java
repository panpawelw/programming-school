package controller;

import com.panpawelw.DAO.UserGroupDAO;
import com.panpawelw.controller.UserGroupsAdminPanel;
import com.panpawelw.model.UserGroup;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import java.util.List;

import static misc.TestUtils.createMultipleUserGroups;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class UserGroupsAdminPanelTests {

    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();
    private final UserGroupsAdminPanel userGroupsAdminPanel = new UserGroupsAdminPanel();
    private final UserGroupDAO mockUserGroupDAO = mock(UserGroupDAO.class);

    @Before
    public void setup() throws Exception {
        userGroupsAdminPanel.setUserGroupDAO(mockUserGroupDAO);
        userGroupsAdminPanel.init(new MockServletConfig());
    }

    @Test
    public void userGroupsAdminPanelForwardTest() throws Exception {
        userGroupsAdminPanel.doGet(request, response);
        assertEquals("/jsp/usergroupsadminview.jsp", response.getForwardedUrl());
    }

    @Test
    public void userGroupsAdminPanelListsMatchTest() throws Exception {
        List<UserGroup> expectedList = createMultipleUserGroups(10);
        expect(mockUserGroupDAO.loadAllUserGroups()).andReturn(expectedList);
        replay(mockUserGroupDAO);
        userGroupsAdminPanel.doGet(request, response);
        assertEquals(expectedList, request.getAttribute("usergroupslist"));
        assertEquals("/jsp/usergroupsadminview.jsp", response.getForwardedUrl());
        verify(mockUserGroupDAO);
    }
}
