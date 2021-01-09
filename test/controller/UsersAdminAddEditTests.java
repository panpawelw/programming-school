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

    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();
    private final UsersAdminAddEdit usersAdminAddEdit = new UsersAdminAddEdit();
    private final UserDAO mockUserDAO = mock(UserDAO.class);

    @Before
    public void setup() throws Exception {
        usersAdminAddEdit.setUserDAO(mockUserDAO);
        usersAdminAddEdit.init(new MockServletConfig());
    }

    @Test
    public void usersAdminAddEditGetForwardTest() throws Exception {
        usersAdminAddEdit.doGet(request, response);
        assertEquals("/usersadminpanel", response.getForwardedUrl());
    }

    @Test
    public void usersAdminAddEditGetNotExistingUserIdTest() throws Exception {
        request.setParameter("id", "16456");
        expect(mockUserDAO.loadUserById(16456)).andReturn(null);
        testGetMethod("/usersadminpanel", "No such user exists!");
    }

    @Test
    public void usersAdminAddEditGetNewUserTest() throws Exception {
        request.setParameter("id", "0");
        testGetMethod("/jsp/usersadminaddeditview.jsp", null,
                new User(0, null, null, null, 0));
    }

    @Test
    public void usersAdminAddEditGetExistingUserTest() throws Exception {
        request.setParameter("id", "5");
        User expectedUser = new User(5, "Test name", "Test email",
                "Test password", 5);
        expect(mockUserDAO.loadUserById(5)).andReturn(expectedUser);
        testGetMethod("/jsp/usersadminaddeditview.jsp",
                null, expectedUser);
    }

    @Test
    public void usersAdminAddEditPostForwardTest() throws Exception {
        usersAdminAddEdit.doPost(request, response);
        assertEquals("/usersadminpanel", response.getForwardedUrl());
    }

    @Test
    public void usersAdminAddEditPostIncorrectIdParameterTest() throws Exception {
        request.setParameter("id", "xxx");
        request.setParameter("name", "Test name");
        request.setParameter("email", "Test email");
        request.setParameter("password", "Test password");
        request.setParameter("group_id", "1");
        testPostMethod("Incorrect parameters!");
    }

    @Test
    public void usersAdminAddEditPostNullIdParameterTest() throws Exception {
        request.setParameter("name", "Test name");
        request.setParameter("email", "Test email");
        request.setParameter("password", "Test password");
        request.setParameter("group_id", "1");
        testPostMethod("Incorrect parameters!");
    }

    @Test
    public void usersAdminAddEditPostEmptyNameParameterTest() throws Exception {
        request.setParameter("id", "1");
        request.setParameter("name", "");
        request.setParameter("email", "Test email");
        request.setParameter("password", "Test password");
        request.setParameter("group_id", "1");
        testPostMethod("Incorrect parameters!");
    }

    @Test
    public void usersAdminAddEditPostNullNameParameterTest() throws Exception {
        request.setParameter("id", "1");
        request.setParameter("email", "Test email");
        request.setParameter("password", "Test password");
        request.setParameter("group_id", "1");
        testPostMethod("Incorrect parameters!");
    }

    @Test
    public void usersAdminAddEditPostEmptyEmailParameterTest() throws Exception {
        request.setParameter("id", "1");
        request.setParameter("name", "Test name");
        request.setParameter("email", "");
        request.setParameter("password", "Test password");
        request.setParameter("group_id", "1");
        testPostMethod("Incorrect parameters!");
    }

    @Test
    public void usersAdminAddEditPostNullEmailParameterTest() throws Exception {
        request.setParameter("id", "1");
        request.setParameter("name", "Test name");
        request.setParameter("password", "Test password");
        request.setParameter("group_id", "1");
        testPostMethod("Incorrect parameters!");
    }

    @Test
    public void usersAdminAddEditPostEmptyPasswordParameterTest() throws Exception {
        request.setParameter("id", "1");
        request.setParameter("name", "Test name");
        request.setParameter("email", "Test email");
        request.setParameter("password", "");
        request.setParameter("group_id", "1");
        testPostMethod("Incorrect parameters!");
    }

    @Test
    public void usersAdminAddEditPostNullPasswordParameterTest() throws Exception {
        request.setParameter("id", "1");
        request.setParameter("name", "Test name");
        request.setParameter("email", "Test email");
        request.setParameter("group_id", "1");
        testPostMethod("Incorrect parameters!");
    }

    @Test
    public void usersAdminAddEditPostIncorrectGroupIdParameterTest() throws Exception {
        request.setParameter("id", "1");
        request.setParameter("name", "Test name");
        request.setParameter("email", "Test email");
        request.setParameter("password", "Test password");
        request.setParameter("group_id", "abc");
        testPostMethod("Incorrect parameters!");
    }

    @Test
    public void usersAdminAddEditPostNullGroupIdParameterTest() throws Exception {
        request.setParameter("id", "1");
        request.setParameter("name", "Test name");
        request.setParameter("email", "Test email");
        request.setParameter("password", "Test password");
        testPostMethod("Incorrect parameters!");
    }

    @Test
    public void usersAdminAddEditPostNewUserTest() throws Exception {
        request.setParameter("id", "0");
        request.setParameter("name", "Test name");
        request.setParameter("email", "Test email");
        request.setParameter("password", "Test password");
        request.setParameter("group_id", "5");
        expect(mockUserDAO.saveUserToDB(new User(0, "Test name", "Test email",
                anyString(), 5))).andReturn(1);
        testPostMethod(null);
    }

    @Test
    public void usersAdminAddEditPostExistingUserTest() throws Exception {
        request.setParameter("id", "1");
        request.setParameter("name", "Test name");
        request.setParameter("email", "Test email");
        request.setParameter("password", "Test password");
        request.setParameter("group_id", "5");
        expect(mockUserDAO.loadUserById(1)).andReturn(new User(1, "Test name",
                "Test email", "Test password", 1));
        expect(mockUserDAO.saveUserToDB(new User(1, "Test name", "Test email",
                anyString(), 1))).andReturn(1);
        testPostMethod(null);
    }

    @Test
    public void usersAdminAddEditPostDatabaseErrorTest() throws Exception {
        request.setParameter("id", "1");
        request.setParameter("name", "Test name");
        request.setParameter("email", "Test email");
        request.setParameter("password", "Test password");
        request.setParameter("group_id", "5");
        expect(mockUserDAO.loadUserById(1)).andReturn(new User(1, "Test name",
                "Test email", "Test password", 1));
        expect(mockUserDAO.saveUserToDB(new User(1, "Test name", "Test email",
                anyString(), 1))).andReturn(0);
        testPostMethod("Database error!");
    }

    private void testGetMethod(String expectedUrl, String expectedErrorMessage,
                               User... expectedUser) throws Exception {
        replay(mockUserDAO);
        usersAdminAddEdit.doGet(request,response);
        assertEquals(expectedUrl, response.getForwardedUrl());
        if (expectedErrorMessage != null) {
            assertEquals(expectedErrorMessage, request.getAttribute("errormessage"));
        }
        if (expectedUser.length > 0) {
            assertEquals(expectedUser[0], request.getAttribute("user"));
        }
        verify(mockUserDAO);
    }

    private void testPostMethod(String errorMessage) throws Exception {
        replay(mockUserDAO);
        usersAdminAddEdit.doPost(request, response);
        if (errorMessage != null) {
            assertEquals(errorMessage, request.getAttribute("errormessage"));
        }
        assertEquals("/usersadminpanel", response.getForwardedUrl());
        verify(mockUserDAO);
    }
}
