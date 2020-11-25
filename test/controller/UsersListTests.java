package controller;

import com.panpawelw.controller.UsersList;
import mockDAOs.MockUserDAO;
import mockDAOs.MockUserGroupDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import static org.junit.Assert.assertEquals;

public class UsersListTests {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockServletConfig config;
    private UsersList userslist;

    @Before
    public void setup() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        config = new MockServletConfig();
        userslist = new UsersList();
    }

    @Test
    public void UsersListForwardTest() throws Exception {
        userslist.setUserDAO(new MockUserDAO());
        userslist.setUserGroupDAO(new MockUserGroupDAO());
        request.setParameter("id", "1");
        userslist.init(config);
        userslist.doGet(request, response);
        assertEquals("/jsp/userslistview.jsp", response.getForwardedUrl());
    }

    @Test
    public void UsersListCorrectUserGroupIdTest() throws Exception {
        userslist.setUserDAO(new MockUserDAO());
        userslist.setUserGroupDAO(new MockUserGroupDAO());
        request.setParameter("id", "1");
        userslist.init(config);
        userslist.doGet(request, response);
        System.out.println(request.getAttribute("groupuserslist"));
        System.out.println(request.getAttribute("groupname"));
    }
}
