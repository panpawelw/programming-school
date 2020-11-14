package controller;

import com.panpawelw.controller.UserGroupsAdminAddEdit;
import mockDAOs.MockUserGroupDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import static org.junit.Assert.assertEquals;

public class UserGroupsAdminAddEditTests {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockServletConfig config;
    private UserGroupsAdminAddEdit userGroupsAdminAddEdit;

    @Before
    public void setup() throws Exception {

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        config = new MockServletConfig();
        userGroupsAdminAddEdit = new UserGroupsAdminAddEdit();
    }

    @Test
    public void userGroupsAdminAddEditGetForwardTest() throws Exception {
        userGroupsAdminAddEdit.setUserGroupDAO(new MockUserGroupDAO());
        request.setParameter("id","0");
        userGroupsAdminAddEdit.init(config);
        userGroupsAdminAddEdit.doGet(request, response);
        assertEquals("/jsp/usergroupsadminaddeditview.jsp", response.getForwardedUrl());
    }

    @Test
    public void userGroupsAdminAddEditPostForwardTest() throws Exception {
        userGroupsAdminAddEdit.setUserGroupDAO(new MockUserGroupDAO());
        request.setParameter("id","0");
        userGroupsAdminAddEdit.init(config);
        userGroupsAdminAddEdit.doPost(request, response);
        assertEquals("/groupsadminpanel", response.getForwardedUrl());
    }
}
