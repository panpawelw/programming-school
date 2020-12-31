package controller;

import com.panpawelw.DAO.LastSolutionDAO;
import com.panpawelw.controller.Home;
import com.panpawelw.model.LastSolution;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import java.util.List;

import static misc.TestUtils.createMultipleLastSolutions;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class HomeTests {

    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();
    private final Home home = new Home();
    private final LastSolutionDAO mockLastSolutionDAO = mock(LastSolutionDAO.class);

    @Before
    public void setup() throws Exception {
        home.setLastSolutionDAO(mockLastSolutionDAO);
        home.init(new MockServletConfig());
    }

    @Test
    public void homeForwardTest() throws Exception {
        home.doGet(request, response);
        assertEquals("/jsp/index.jsp", response.getForwardedUrl());
    }

    @Test
    public void homeCorrectInitParameterTest() throws Exception {
        getServletOutput("7", 7);
    }

    @Test
    public void homeIncorrectInitParameterTest() throws Exception {
        getServletOutput("x", 5);
    }

    private void getServletOutput(String initParameter, long listLength) throws Exception {
        request.getServletContext().setInitParameter("last-solutions", initParameter);
        List<LastSolution> expectedList = createMultipleLastSolutions(listLength);
        expect(mockLastSolutionDAO.loadMostRecentSolutions(listLength)).andReturn(expectedList);
        replay(mockLastSolutionDAO);
        home.doGet(request, response);
        assertEquals(request.getAttribute("lastsolutions"), expectedList);
        verify(mockLastSolutionDAO);
    }
}
