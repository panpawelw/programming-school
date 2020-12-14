package controller;

import com.panpawelw.controller.UsersAdminPanel;
import com.panpawelw.model.User;
import mockDAOs.MockUserDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class UsersAdminPanelTests {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private UsersAdminPanel usersAdminPanel;

    @Before
    public void setup() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        usersAdminPanel = new UsersAdminPanel();
        usersAdminPanel.setUserDAO(new MockUserDAO());
        usersAdminPanel.init(new MockServletConfig());
    }

    @Test
    public void usersAdminPanelForwardTest() throws Exception {
        usersAdminPanel.doGet(request, response);
        assertEquals("/jsp/usersadminview.jsp", response.getForwardedUrl());
    }

    @Test
    public void usersAdminPanelListsMatchTest() throws Exception {
        usersAdminPanel.doGet(request, response);
        Object rawList = request.getAttribute("userslist");
        List<User> returnedList =
                ((List<?>) rawList).stream().map(el -> (User) el).collect(Collectors.toList());
        List<User> expectedList = new MockUserDAO().loadAllUsers();
        assertEquals(returnedList, expectedList);
        assertEquals("/jsp/usersadminview.jsp", response.getForwardedUrl());
    }
}
