package controller;

import com.panpawelw.DAO.SolutionDAO;
import com.panpawelw.controller.SolutionsAdminDelete;
import com.panpawelw.model.Solution;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import java.sql.Timestamp;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SolutionsAdminDeleteTests {

    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();
    private final SolutionsAdminDelete solutionsAdminDelete = new SolutionsAdminDelete();
    private final SolutionDAO mockSolutionDAO = mock(SolutionDAO.class);

    @Before
    public void setup() throws Exception {
        solutionsAdminDelete.setSolutionDAO(mockSolutionDAO);
        solutionsAdminDelete.init(new MockServletConfig());
    }

    @Test
    public void solutionsAdminDeleteForwardTest() throws Exception {
        request.setParameter("id","0");
        solutionsAdminDelete.doGet(request, response);
        assertEquals("/solutionsadminpanel", response.getForwardedUrl());
    }

    @Test
    public void solutionsAdminDeleteIncorrectIntegerParameterTest() throws Exception {
        request.setParameter("id", "16456");
        expect(mockSolutionDAO.loadSolutionById(16456)).andReturn(null);
        incorrectParameterTest();
    }

    @Test
    public void solutionsAdminDeleteIncorrectIntegerSubZeroParameterTest() throws Exception {
        request.setParameter("id", "-10");
        incorrectParameterTest();
    }

    @Test
    public void solutionsAdminDeleteCharacterParameterTest() throws Exception {
        request.setParameter("id", "x");
        incorrectParameterTest();
    }

    @Test
    public void solutionsAdminDeleteNullParameterTest() throws Exception {
        request.setParameter("id", "null");
        incorrectParameterTest();
    }

    @Test
    public void solutionsAdminDeleteCorrectParameterTest() throws Exception {
        request.setParameter("id","4");
        Solution solutionToDelete = new Solution(4, new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()), "Test description",
                5, 2);
        expect(mockSolutionDAO.loadSolutionById(4)).andReturn(solutionToDelete);
        expect(mockSolutionDAO.deleteSolution(solutionToDelete)).andReturn(1);
        replay(mockSolutionDAO);
        solutionsAdminDelete.doGet(request, response);
        //noinspection ConstantConditions
        assertNull(request.getAttribute("errormessage"));
        assertEquals("/solutionsadminpanel", response.getForwardedUrl());
        verify(mockSolutionDAO);
    }

    private void incorrectParameterTest() throws Exception {
        replay(mockSolutionDAO);
        solutionsAdminDelete.doGet(request,response);
        assertEquals("Error deleting solution!", request.getAttribute("errormessage"));
        assertEquals("/solutionsadminpanel", response.getForwardedUrl());
        verify(mockSolutionDAO);
    }
}
