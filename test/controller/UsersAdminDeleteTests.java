package controller;

import com.panpawelw.controller.UsersAdminDelete;
import mockDAOs.MockUserDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UsersAdminDeleteTests {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private UsersAdminDelete usersAdminDelete;

    @Before
    public void setup() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        usersAdminDelete = new UsersAdminDelete();
        usersAdminDelete.setUserDAO(new MockUserDAO());
        usersAdminDelete.init(new MockServletConfig());
    }

    @Test
    public void UsersAdminDeleteForwardTest() throws Exception {
        request.setParameter("id", "1");
        usersAdminDelete.doGet(request, response);
        assertEquals("/usersadminpanel", response.getForwardedUrl());
    }

    @Test
    public void UsersAdminDeleteIncorrectParameterTest() throws Exception {
        request.setParameter("id","x");
        usersAdminDelete.doGet(request, response);
        assertEquals("Error deleting user!", request.getAttribute("errormessage"));
    }

    @Test
    public void UsersAdminDeleteCorrectParameterTest() throws Exception {
        request.setParameter("id","3");
        usersAdminDelete.doGet(request, response);
        //noinspection ConstantConditions
        assertNull(request.getAttribute("errormessage"));
        assertEquals("/usersadminpanel", response.getForwardedUrl());
    }
}
