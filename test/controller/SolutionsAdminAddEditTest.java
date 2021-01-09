package controller;

import com.panpawelw.DAO.SolutionDAO;
import com.panpawelw.controller.SolutionsAdminAddEdit;
import com.panpawelw.model.Solution;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import java.sql.Timestamp;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class SolutionsAdminAddEditTest {

    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response  = new MockHttpServletResponse();
    private final SolutionsAdminAddEdit solutionsAdminAddEdit  = new SolutionsAdminAddEdit();
    private final SolutionDAO mockSolutionDAO = mock(SolutionDAO.class);
    private final long now = System.currentTimeMillis();

    @Before
    public void setup() throws Exception {
        solutionsAdminAddEdit.setSolutionDAO(mockSolutionDAO);
        solutionsAdminAddEdit.init(new MockServletConfig());
    }

    @Test
    public void solutionsAdminAddEditGetForwardTest() throws Exception {
        solutionsAdminAddEdit.doGet(request, response);
        assertEquals("/solutionsadminpanel", response.getForwardedUrl());
    }

    @Test
    public void solutionsAdminAddEditGetNotExistingSolutionIdTest() throws Exception {
        request.setParameter("id", "16456");
        expect(mockSolutionDAO.loadSolutionById(16456)).andReturn(null);
        testGetMethod("/solutionsadminpanel", "No such solution exists!");
    }

    @Test
    public void solutionsAdminAddEditGetNewSolutionTest() throws Exception {
        request.setParameter("id", "0");
        testGetMethod("/jsp/solutionsadminaddeditview.jsp", null,
                new Solution(0, null, null, null, 0, 0));
    }

    @Test
    public void solutionsAdminAddEditGetExistingSolutionTest() throws Exception {
        request.setParameter("id", "5");
        Solution expectedSolution = new Solution(5, new Timestamp(now), new Timestamp(now),
                "Test description", 3, 2);
        expect(mockSolutionDAO.loadSolutionById(5)).andReturn(expectedSolution);
        testGetMethod("/jsp/solutionsadminaddeditview.jsp",
                null, expectedSolution);
    }

    @Test
    public void solutionsAdminAddEditPostForwardTest() throws Exception {
        solutionsAdminAddEdit.doPost(request, response);
        assertEquals("/solutionsadminpanel", response.getForwardedUrl());
    }

    @Test
    public void solutionsAdminAddEditPostIncorrectIdParameterTest() throws Exception {
        request.setParameter("id", "xxx");
        request.setParameter("description", "Test description");
        request.setParameter("exercise_id", "1");
        request.setParameter("user_id", "1");
        testPostMethod("Incorrect parameters!");
    }

    @Test
    public void solutionsAdminAddEditPostNullIdParameterTest() throws Exception {
        request.setParameter("description", "Test description");
        request.setParameter("exercise_id", "1");
        request.setParameter("user_id", "1");
        testPostMethod("Incorrect parameters!");
    }

    @Test
    public void solutionsAdminAddEditPostEmptyDescriptionParameterTest() throws Exception {
        request.setParameter("id", "1");
        request.setParameter("description", "");
        request.setParameter("exercise_id", "1");
        request.setParameter("user_id", "1");
        testPostMethod("Incorrect parameters!");
    }

    @Test
    public void solutionsAdminAddEditPostNullDescriptionParameterTest() throws Exception {
        request.setParameter("id", "1");
        request.setParameter("exercise_id", "1");
        request.setParameter("user_id", "1");
        testPostMethod("Incorrect parameters!");
    }

    @Test
    public void solutionsAdminAddEditPostIncorrectUserIdParameterTest() throws Exception {
        request.setParameter("id", "1");
        request.setParameter("description", "Test description");
        request.setParameter("exercise_id", "1");
        request.setParameter("user_id", "xxx");
        testPostMethod("Incorrect parameters!");
    }

    @Test
    public void solutionsAdminAddEditPostNullUserIdParameterTest() throws Exception {
        request.setParameter("id", "1");
        request.setParameter("description", "Test description");
        request.setParameter("exercise_id", "1");
        testPostMethod("Incorrect parameters!");
    }

    @Test
    public void solutionsAdminAddEditPostIncorrectExerciseIdParameterTest() throws Exception {
        request.setParameter("id", "1");
        request.setParameter("description", "Test description");
        request.setParameter("exercise_id", "-5");
        request.setParameter("user_id", "1");
        testPostMethod("Incorrect parameters!");
    }

    @Test
    public void solutionsAdminAddEditPostNullExerciseIdIdParameterTest() throws Exception {
        request.setParameter("id", "1");
        request.setParameter("description", "Test description");
        request.setParameter("user_id", "1");
        testPostMethod("Incorrect parameters!");
    }

    @Test
    public void solutionsAdminAddEditPostNewSolutionTest() throws Exception {
        request.setParameter("id", "0");
        request.setParameter("description", "Test description");
        request.setParameter("exercise_id", "1");
        request.setParameter("user_id", "1");
        expect(mockSolutionDAO.saveSolutionToDB(new Solution(0, anyObject(Timestamp.class),
                null , "Test description", 1, 1))).andReturn(1);
        testPostMethod(null);
    }

    @Test
    public void solutionsAdminAddEditPostExistingSolutionTest() throws Exception {
        request.setParameter("id", "3");
        request.setParameter("description", "Test description");
        request.setParameter("exercise_id", "1");
        request.setParameter("user_id", "1");
        Solution expectedSolution = new Solution(3, new Timestamp(now),
                new Timestamp(now), "Test description", 1, 1);
        expect(mockSolutionDAO.loadSolutionById(3)).andReturn(expectedSolution);
        expect(mockSolutionDAO.saveSolutionToDB(expectedSolution)).andReturn(1);
        testPostMethod(null);
    }

    @Test
    public void solutionsAdminAddEditPostDatabaseErrorTest() throws Exception {
        request.setParameter("id", "3");
        request.setParameter("description", "Test description");
        request.setParameter("exercise_id", "1");
        request.setParameter("user_id", "1");
        Solution expectedSolution = new Solution(3, new Timestamp(now), new Timestamp(now),
                "Test description", 1, 1);
        expect(mockSolutionDAO.loadSolutionById(3)).andReturn(expectedSolution);
        expect(mockSolutionDAO.saveSolutionToDB(expectedSolution)).andReturn(0);
        testPostMethod("Database error!");
    }

    private void testGetMethod(String expectedUrl, String expectedErrorMessage,
                               Solution... expectedSolution) throws Exception {
        replay(mockSolutionDAO);
        solutionsAdminAddEdit.doGet(request,response);
        assertEquals(expectedUrl, response.getForwardedUrl());
        if (expectedErrorMessage != null) {
            assertEquals(expectedErrorMessage, request.getAttribute("errormessage"));
        }
        if (expectedSolution.length > 0) {
            assertEquals(expectedSolution[0], request.getAttribute("solution"));
        }
        verify(mockSolutionDAO);
    }

    private void testPostMethod(String errorMessage) throws Exception {
        replay(mockSolutionDAO);
        solutionsAdminAddEdit.doPost(request, response);
        if (errorMessage != null) {
            assertEquals(errorMessage, request.getAttribute("errormessage"));
        }
        assertEquals("/solutionsadminpanel", response.getForwardedUrl());
        verify(mockSolutionDAO);
    }
}
