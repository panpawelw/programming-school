package controller;

import com.panpawelw.controller.UserGroupsAdminPanel;
import com.panpawelw.model.UserGroup;
import mockDAOs.MockUserGroupDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class UserGroupsAdminPanelTests {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private UserGroupsAdminPanel userGroupsAdminPanel;

    @Before
    public void setup() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        userGroupsAdminPanel = new UserGroupsAdminPanel();
        userGroupsAdminPanel.setUserGroupDAO(new MockUserGroupDAO());
        userGroupsAdminPanel.init(new MockServletConfig());
    }

    @Test
    public void userGroupsAdminPanelForwardTest() throws Exception {
        userGroupsAdminPanel.doGet(request, response);
        assertEquals("/jsp/usergroupsadminview.jsp", response.getForwardedUrl());
    }

    @Test
    public void userGroupsAdminPanelListsMatchTest() throws Exception {
        userGroupsAdminPanel.doGet(request, response);
        Object rawList = request.getAttribute("groupslist");
        List<UserGroup> returnedList =
                ((List<?>) rawList).stream().map(el -> (UserGroup) el).collect(Collectors.toList());
        List<UserGroup> expectedList = new MockUserGroupDAO().loadAllUserGroups();
        assertEquals(returnedList, expectedList);
        assertEquals("/jsp/usergroupsadminview.jsp", response.getForwardedUrl());
    }
}
