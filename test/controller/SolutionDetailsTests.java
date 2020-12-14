package controller;

import com.panpawelw.controller.SolutionDetails;
import mockDAOs.MockExerciseDAO;
import mockDAOs.MockSolutionDAO;
import mockDAOs.MockUserDAO;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import static org.junit.Assert.assertEquals;

public class SolutionDetailsTests {

    @Test
    public void SolutionDetailsForwardTest() throws Exception {
        MockHttpServletResponse response = new MockHttpServletResponse();
        SolutionDetails solutionDetails = new SolutionDetails();
        solutionDetails.setUserDAO(new MockUserDAO());
        solutionDetails.setExerciseDAO(new MockExerciseDAO());
        solutionDetails.setSolutionDAO(new MockSolutionDAO());
        solutionDetails.init(new MockServletConfig());
        solutionDetails.doGet(new MockHttpServletRequest(), response);
        assertEquals("/jsp/error.jsp", response.getForwardedUrl());
    }
}
