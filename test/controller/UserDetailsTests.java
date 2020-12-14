package controller;

import com.panpawelw.controller.UserDetails;
import mockDAOs.MockLastSolutionDAO;
import mockDAOs.MockUserDAO;
import mockDAOs.MockUserGroupDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import static org.junit.Assert.assertEquals;

public class UserDetailsTests {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private UserDetails userDetails;

    @Before
    public void setup() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        userDetails = new UserDetails();
        userDetails.setUserDAO(new MockUserDAO());
        userDetails.setUserGroupDAO(new MockUserGroupDAO());
        userDetails.setLastSolutionDAO(new MockLastSolutionDAO());
        userDetails.init(new MockServletConfig());
    }

    @Test
    public void UserDetailsForwardTest() throws Exception {
        userDetails.doGet(request, response);
        assertEquals("/jsp/error.jsp", response.getForwardedUrl());
    }
}
