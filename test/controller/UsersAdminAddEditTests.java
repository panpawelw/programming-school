package controller;

import com.panpawelw.DAO.UserDAO;
import com.panpawelw.controller.UsersAdminAddEdit;
import com.panpawelw.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class UsersAdminAddEditTests {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private UsersAdminAddEdit usersAdminAddEdit;
    private final UserDAO mockUserDAO = mock(UserDAO.class);

    @Before
    public void setup() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        usersAdminAddEdit = new UsersAdminAddEdit();
        usersAdminAddEdit.setUserDAO(mockUserDAO);
        usersAdminAddEdit.init(new MockServletConfig());
    }

    @Test
    public void usersAdminAddEditGetForwardTest() throws Exception {
        usersAdminAddEdit.doGet(request, response);
        assertEquals("/usersadminpanel", response.getForwardedUrl());
    }

    @Test
    public void usersAdminAddEditPostForwardTest() throws Exception {
        usersAdminAddEdit.doPost(request, response);
        assertEquals("/usersadminpanel", response.getForwardedUrl());
    }

    @Test
    public void usersAdminAddEditGetIncorrectIntegerParameterTest() throws Exception {
        request.setParameter("id", "16456");
        expect(mockUserDAO.loadUserById(16456)).andReturn(null);
        incorrectParameterTest();
    }

    @Test
    public void usersAdminAddEditGetIncorrectIntegerSubZeroParameterTest() throws Exception {
        request.setParameter("id", "-10");
        incorrectParameterTest();
    }

    @Test
    public void usersAdminAddEditGetCharacterParameterTest() throws Exception {
        request.setParameter("id", "x");
        incorrectParameterTest();
    }

    @Test
    public void usersAdminAddEditGetNullParameterTest() throws Exception {
        request.setParameter("id", "null");
        incorrectParameterTest();
    }

    @Test
    public void usersAdminAddEditGetCorrectParameterTest() throws Exception {
        request.setParameter("id", "5");
        User expectedUser = new User(5, "Test name", "Test email",
                "Test password", 1);
        expect(mockUserDAO.loadUserById(5)).andReturn(expectedUser);
        replay(mockUserDAO);
        usersAdminAddEdit.doGet(request,response);
        User returnedUser = (User) request.getAttribute("user");
        assertEquals(expectedUser, returnedUser);
        assertEquals("/jsp/usersadminaddeditview.jsp", response.getForwardedUrl());
        verify(mockUserDAO);
    }

    private void incorrectParameterTest() throws Exception {
        replay(mockUserDAO);
        usersAdminAddEdit.doGet(request,response);
        assertEquals("No such user exists!", request.getAttribute("errormessage"));
        assertEquals("/usersadminpanel", response.getForwardedUrl());
        verify(mockUserDAO);
    }
}
