package controller;

import com.panpawelw.controller.SolutionDetails;
import mockDAOs.MockExerciseDAO;
import mockDAOs.MockSolutionDAO;
import mockDAOs.MockUserDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import static org.junit.Assert.assertEquals;

public class SolutionDetailsTests {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockServletConfig config;
    private SolutionDetails solutionDetails;

    @Before
    public void setup() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        config = new MockServletConfig();
        solutionDetails = new SolutionDetails();
    }

    @Test
    public void SolutionDetailsForwardTest() throws Exception {
        solutionDetails.setUserDAO(new MockUserDAO());
        solutionDetails.setExerciseDAO(new MockExerciseDAO());
        solutionDetails.setSolutionDAO(new MockSolutionDAO());
        solutionDetails.init(config);
        request.setParameter("id", "1");
        solutionDetails.doGet(request, response);
        assertEquals("/jsp/solutiondetailsview.jsp", response.getForwardedUrl());
    }
}