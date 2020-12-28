package controller;

import com.panpawelw.DAO.UserDAO;
import com.panpawelw.controller.UsersAdminPanel;
import com.panpawelw.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import java.util.List;

import static misc.TestUtils.createMultipleUsers;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class UsersAdminPanelTests {

    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();
    private final UsersAdminPanel usersAdminPanel = new UsersAdminPanel();
    private final UserDAO mockUserDAO = mock(UserDAO.class);

    @Before
    public void setup() throws Exception {
        usersAdminPanel.setUserDAO(mockUserDAO);
        usersAdminPanel.init(new MockServletConfig());
    }

    @Test
    public void usersAdminPanelForwardTest() throws Exception {
        usersAdminPanel.doGet(request, response);
        assertEquals("/jsp/usersadminview.jsp", response.getForwardedUrl());
    }

    @Test
    public void usersAdminPanelListsMatchTest() throws Exception {
        List<User> expectedList = createMultipleUsers(10);
        expect(mockUserDAO.loadAllUsers()).andReturn(expectedList);
        replay(mockUserDAO);
        usersAdminPanel.doGet(request, response);
        assertEquals(expectedList, request.getAttribute("userslist"));
        assertEquals("/jsp/usersadminview.jsp", response.getForwardedUrl());
        verify(mockUserDAO);
    }
}
