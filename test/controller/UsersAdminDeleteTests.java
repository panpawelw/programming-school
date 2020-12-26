package controller;

import com.panpawelw.DAO.UserDAO;
import com.panpawelw.controller.UsersAdminDelete;
import com.panpawelw.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UsersAdminDeleteTests {

    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();
    private final UsersAdminDelete usersAdminDelete = new UsersAdminDelete();
    private final UserDAO mockUserDAO = mock(UserDAO.class);

    @Before
    public void setup() throws Exception {
        usersAdminDelete.setUserDAO(mockUserDAO);
        usersAdminDelete.init(new MockServletConfig());
    }

    @Test
    public void usersAdminDeleteForwardTest() throws Exception {
        request.setParameter("id","0");
        usersAdminDelete.doGet(request, response);
        assertEquals("/usersadminpanel", response.getForwardedUrl());
    }

    @Test
    public void usersAdminDeleteIncorrectLongParameterTest() throws Exception {
        request.setParameter("id", "16456");
        expect(mockUserDAO.loadUserById(16456)).andReturn(null);
        incorrectParameterTest();
    }

    @Test
    public void usersAdminDeleteIncorrectIntegerSubZeroParameterTest() throws Exception {
        request.setParameter("id", "-10");
        incorrectParameterTest();
    }

    @Test
    public void usersAdminDeleteCharacterParameterTest() throws Exception {
        request.setParameter("id", "x");
        incorrectParameterTest();
    }

    @Test
    public void usersAdminDeleteNullParameterTest() throws Exception {
        request.setParameter("id", "null");
        incorrectParameterTest();
    }

    @Test
    public void usersAdminDeleteCorrectParameterTest() throws Exception {
        request.setParameter("id","4");
        User userToDelete = new User(4, "Test name",
                "Test email", "Test password", 1);
        expect(mockUserDAO.loadUserById(4)).andReturn(userToDelete);
        expect(mockUserDAO.deleteUser(userToDelete)).andReturn(1);
        replay(mockUserDAO);
        usersAdminDelete.doGet(request, response);
        //noinspection ConstantConditions
        assertNull(request.getAttribute("errormessage"));
        assertEquals("/usersadminpanel", response.getForwardedUrl());
        verify(mockUserDAO);
    }

    private void incorrectParameterTest() throws Exception {
        replay(mockUserDAO);
        usersAdminDelete.doGet(request,response);
        assertEquals("Error deleting user!", request.getAttribute("errormessage"));
        assertEquals("/usersadminpanel", response.getForwardedUrl());
        verify(mockUserDAO);
    }
}
