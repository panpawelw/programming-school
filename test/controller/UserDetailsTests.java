package controller;

import com.panpawelw.DAO.LastSolutionDAO;
import com.panpawelw.DAO.UserDAO;
import com.panpawelw.DAO.UserGroupDAO;
import com.panpawelw.controller.UserDetails;
import com.panpawelw.model.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import java.util.List;

import static misc.TestUtils.createMultipleLastSolutions;
import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

public class UserDetailsTests {

    private final UserDetails userDetails = new UserDetails();
    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();
    private final UserDAO mockUserDAO = mock(UserDAO.class);
    private final UserGroupDAO mockUserGroupDAO = mock(UserGroupDAO.class);
    private final LastSolutionDAO mockLastSolutionDAO = mock(LastSolutionDAO.class);


    @Before
    public void setup() throws Exception {

        userDetails.setUserDAO(mockUserDAO);
        userDetails.setUserGroupDAO(mockUserGroupDAO);
        userDetails.setLastSolutionDAO(mockLastSolutionDAO);
        userDetails.init(new MockServletConfig());
    }

    @Test
    public void userDetailsForwardTest() throws Exception {
        userDetails.doGet(request, response);
        assertEquals("/jsp/error.jsp", response.getForwardedUrl());
    }

    @Test
    public void userDetailsIncorrectIntegerParameterTest() throws Exception {
        request.setParameter("id", "16456");
        expect(mockUserDAO.loadUserById(16456)).andReturn(null);
        incorrectParameterTest();
    }

    @Test
    public void userDetailsIncorrectIntegerSubZeroParameterTest() throws Exception {
        request.setParameter("id", "-10");
        incorrectParameterTest();
    }

    @Test
    public void userDetailsCharacterParameterTest() throws Exception {
        request.setParameter("id", "x");
        incorrectParameterTest();
    }

    @Test
    public void userDetailsNullParameterTest() throws Exception {
        request.setParameter("id", "null");
        incorrectParameterTest();
    }

    @Test
    public void userDetailsCorrectParameterTest() throws Exception {
        request.setParameter("id", "8");
        User expectedUser = new User(8, "Test name", "Test email",
                "Test password", 3);
        UserGroup expectedUserGroup = new UserGroup(3, "Test name");
        List<LastSolution> expectedUsersSolutions = createMultipleLastSolutions(10);
        expect(mockUserDAO.loadUserById(8)).andReturn(expectedUser);
        expect(mockUserGroupDAO.loadUserGroupById(3)).andReturn(expectedUserGroup);
        expect(mockLastSolutionDAO.loadMostRecentSolutionsByUserId(8))
                .andReturn(expectedUsersSolutions);
        replay(mockUserDAO, mockUserGroupDAO, mockLastSolutionDAO);
        userDetails.doGet(request, response);
        assertEquals(expectedUser, request.getAttribute("user"));
        assertEquals(expectedUserGroup.getName(), request.getAttribute("groupname"));
        assertEquals(expectedUsersSolutions, request.getAttribute("userslastsolutions"));
        assertEquals("/jsp/userdetailsview.jsp", response.getForwardedUrl());
        verify(mockUserDAO, mockUserGroupDAO, mockLastSolutionDAO);
    }

    private void incorrectParameterTest() throws Exception {
        replay(mockUserDAO);
        userDetails.doGet(request,response);
        assertEquals("No such user exists!", request.getAttribute("errormessage"));
        assertEquals("/jsp/error.jsp", response.getForwardedUrl());
        verify(mockUserDAO);
    }
}
