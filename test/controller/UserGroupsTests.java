package controller;

import com.panpawelw.DAO.UserGroupDAO;
import com.panpawelw.controller.UserGroups;
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

public class UserGroupsTests {

    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response  = new MockHttpServletResponse();
    private final UserGroups userGroups = new UserGroups();
    private final UserGroupDAO mockUserGroupDAO = mock(UserGroupDAO.class);

    @Before
    public void setup() throws Exception {
        userGroups.setUserGroupDAO(mockUserGroupDAO);
        userGroups.init(new MockServletConfig());
    }

    @Test
    public void userGroupsForwardTest() throws Exception {
        userGroups.doGet(request, response);
        assertEquals("/jsp/usergroupsview.jsp", response.getForwardedUrl());
    }

    @Test
    public void userGroupsListsMatch() throws Exception {
        List<UserGroup> expectedList = createMultipleUserGroups(7);
        expect(mockUserGroupDAO.loadAllUserGroups()).andReturn(expectedList);
        replay(mockUserGroupDAO);
        userGroups.doGet(request, response);
        assertEquals(expectedList, request.getAttribute("usergroups"));
        verify(mockUserGroupDAO);
    }
}
