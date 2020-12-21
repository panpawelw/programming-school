package controller;

import com.panpawelw.DAO.SolutionDAO;
import com.panpawelw.DAO.ExerciseDAO;
import com.panpawelw.DAO.UserDAO;
import com.panpawelw.controller.SolutionDetails;
import com.panpawelw.model.Exercise;
import com.panpawelw.model.Solution;
import com.panpawelw.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import java.sql.Timestamp;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

public class SolutionDetailsTests {

    private final SolutionDetails solutionDetails = new SolutionDetails();
    private final SolutionDAO mockSolutionDAO = mock(SolutionDAO.class);
    private final ExerciseDAO mockExerciseDAO = mock(ExerciseDAO.class);
    private final UserDAO mockUserDAO = mock(UserDAO.class);
    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();

    @Before
    public void setup() throws Exception {
        solutionDetails.setSolutionDAO(mockSolutionDAO);
        solutionDetails.setExerciseDAO(mockExerciseDAO);
        solutionDetails.setUserDAO(mockUserDAO);
        solutionDetails.init(new MockServletConfig());
    }

    @Test
    public void solutionDetailsForwardTest() throws Exception {
        solutionDetails.doGet(request, response);
        assertEquals("/jsp/error.jsp", response.getForwardedUrl());
    }

    @Test
    public void solutionDetailsIncorrectIntegerParameterTest() throws Exception {
        request.setParameter("id", "16456");
        expect(mockSolutionDAO.loadSolutionById(16456)).andReturn(null);
        incorrectParameterTest();
    }

    @Test
    public void solutionDetailsIncorrectIntegerSubZeroParameterTest() throws Exception {
        request.setParameter("id", "-10");
        incorrectParameterTest();
    }

    @Test
    public void solutionDetailsCharacterParameterTest() throws Exception {
        request.setParameter("id", "x");
        incorrectParameterTest();
    }

    @Test
    public void solutionDetailsNullParameterTest() throws Exception {
        request.setParameter("id", "null");
        incorrectParameterTest();
    }

    @Test
    public void correctParameterTest() throws Exception {
        request.setParameter("id", "12");
        Solution expectedSolution = new Solution(12, new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()), "Test description", 5,
                8);
        expect(mockSolutionDAO.loadSolutionById(12)).andReturn(expectedSolution);
        expect(mockExerciseDAO.loadExerciseById(5)).andReturn(new Exercise(5, "Test title",
                "Test description"));
        expect(mockUserDAO.loadUserById(8)).andReturn(new User(8, "Test name",
                "Test email","Test password", 3));
        replay(mockSolutionDAO, mockExerciseDAO, mockUserDAO);
        solutionDetails.doGet(request, response);
        Solution returnedSolution = (Solution) request.getAttribute("solution");
        assertEquals(expectedSolution, returnedSolution);
        assertEquals("/jsp/solutiondetailsview.jsp", response.getForwardedUrl());
        verify(mockSolutionDAO, mockExerciseDAO, mockUserDAO);
    }

    private void incorrectParameterTest() throws Exception {
        replay(mockSolutionDAO);
        solutionDetails.doGet(request,response);
        assertEquals("No such solution exists!", request.getAttribute("errormessage"));
        assertEquals("/jsp/error.jsp", response.getForwardedUrl());
        verify(mockSolutionDAO);
    }
}
