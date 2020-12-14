package controller;

import com.panpawelw.controller.UserGroupsAdminDelete;
import mockDAOs.MockUserGroupDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserGroupsAdminDeleteTests {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private UserGroupsAdminDelete userGroupsAdminDelete;

    @Before
    public void setup() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        userGroupsAdminDelete = new UserGroupsAdminDelete();
        userGroupsAdminDelete.setUserGroupDAO(new MockUserGroupDAO());
        userGroupsAdminDelete.init(new MockServletConfig());
    }

    @Test
    public void UserGroupsAdminDeleteForwardTest() throws Exception {
        request.setParameter("id", "0");
        userGroupsAdminDelete.doGet(request, response);
        assertEquals("/groupsadminpanel", response.getForwardedUrl());
    }

    @Test
    public void UserGroupsAdminDeleteIncorrectParameterTest() throws Exception {
        request.setParameter("id","x");
        userGroupsAdminDelete.doGet(request, response);
        assertEquals("Error deleting user group!",
                request.getAttribute("errormessage"));
    }

    @Test
    public void UserGroupsAdminDeleteCorrectParameterTest() throws Exception {
        request.setParameter("id", "2");
        userGroupsAdminDelete.doGet(request, response);
        //noinspection ConstantConditions
        assertNull(request.getAttribute("errormessage"));
        assertEquals("/groupsadminpanel", response.getForwardedUrl());
    }
}
