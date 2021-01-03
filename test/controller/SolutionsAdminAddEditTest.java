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
    public void solutionsAdminAddEditPostForwardTest() throws Exception {
        solutionsAdminAddEdit.doPost(request, response);
        assertEquals("/solutionsadminpanel", response.getForwardedUrl());
    }

    @Test
    public void solutionsAdminAddEditGetIncorrectIntegerParameterTest() throws Exception {
        request.setParameter("id", "16456");
        expect(mockSolutionDAO.loadSolutionById(16456)).andReturn(null);
        incorrectParameterTest();
    }

    @Test
    public void solutionsAdminAddEditGetIncorrectIntegerSubZeroParameterTest() throws Exception {
        request.setParameter("id", "-10");
        incorrectParameterTest();
    }

    @Test
    public void solutionsAdminAddEditGetCharacterParameterTest() throws Exception {
        request.setParameter("id", "x");
        incorrectParameterTest();
    }

    @Test
    public void solutionsAdminAddEditGetNullParameterTest() throws Exception {
        request.setParameter("id", "null");
        incorrectParameterTest();
    }

    @Test
    public void solutionsAdminAddEditGetCorrectParameterTest() throws Exception {
        request.setParameter("id", "5");
        Solution expectedSolution = new Solution(5, new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                "Test description", 5, 3);
        expect(mockSolutionDAO.loadSolutionById(5)).andReturn(expectedSolution);
        replay(mockSolutionDAO);
        solutionsAdminAddEdit.doGet(request,response);
        Solution returnedSolution = (Solution) request.getAttribute("solution");
        assertEquals(expectedSolution, returnedSolution);
        assertEquals("/jsp/solutionsadminaddeditview.jsp", response.getForwardedUrl());
        verify(mockSolutionDAO);
    }

    private void incorrectParameterTest() throws Exception {
        replay(mockSolutionDAO);
        solutionsAdminAddEdit.doGet(request,response);
        assertEquals("No such solution exists!", request.getAttribute("errormessage"));
        assertEquals("/solutionsadminpanel", response.getForwardedUrl());
        verify(mockSolutionDAO);
    }
}
