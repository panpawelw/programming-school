package controller;

import com.panpawelw.controller.UsersAdminAddEdit;
import mockDAOs.MockUserDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import static org.junit.Assert.assertEquals;

public class UsersAdminAddEditTests {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private UsersAdminAddEdit usersAdminAddEdit;

    @Before
    public void setup() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        usersAdminAddEdit = new UsersAdminAddEdit();
        usersAdminAddEdit.setUserDAO(new MockUserDAO());
        usersAdminAddEdit.init(new MockServletConfig());
    }

    @Test
    public void usersAdminAddEditGetForwardTest() throws Exception {
        request.setParameter("id","0");
        usersAdminAddEdit.doGet(request, response);
        assertEquals("/jsp/usersadminaddeditview.jsp", response.getForwardedUrl());
    }

    @Test
    public void usersAdminAddEditPostForwardTest() throws Exception {
        request.setParameter("id","0");
        request.setParameter("group_id","1");
        usersAdminAddEdit.doPost(request, response);
        assertEquals("/usersadminpanel", response.getForwardedUrl());
    }
}
