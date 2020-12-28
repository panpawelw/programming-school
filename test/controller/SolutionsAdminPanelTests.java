package controller;

import com.panpawelw.DAO.SolutionDAO;
import com.panpawelw.controller.SolutionsAdminPanel;
import com.panpawelw.model.Solution;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import java.util.List;

import static misc.TestUtils.createMultipleSolutions;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class SolutionsAdminPanelTests {

    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();
    private final SolutionsAdminPanel solutionsAdminPanel = new SolutionsAdminPanel();
    private final SolutionDAO mockSolutionDAO = mock(SolutionDAO.class);

    @Before
    public void setup() throws Exception {
        solutionsAdminPanel.setSolutionDAO(mockSolutionDAO);
        solutionsAdminPanel.init(new MockServletConfig());
    }

    @Test
    public void solutionsAdminPanelForwardTest() throws Exception {
        solutionsAdminPanel.doGet(request, response);
        assertEquals("/jsp/solutionsadminview.jsp", response.getForwardedUrl());
    }

    @Test
    public void solutionsAdminPanelListsMatchTest() throws Exception {
        List<Solution> expectedList = createMultipleSolutions(10);
        expect(mockSolutionDAO.loadAllSolutions()).andReturn(expectedList);
        replay(mockSolutionDAO);
        solutionsAdminPanel.doGet(request, response);
        assertEquals(expectedList, request.getAttribute("solutionslist"));
        assertEquals("/jsp/solutionsadminview.jsp", response.getForwardedUrl());
        verify(mockSolutionDAO);
    }
}
