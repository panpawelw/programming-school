package controller;

import com.panpawelw.controller.Home;
import com.panpawelw.model.LastSolution;
import mockDAOs.MockLastSolutionDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class HomeTests {

    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();
    private final Home home = new Home();

    @Before
    public void setup() throws Exception {
        home.setLastSolutionDAO(new MockLastSolutionDAO());
        home.init(new MockServletConfig());
    }

    @Test
    public void homeForwardTest() throws Exception {
        home.doGet(request, response);
        assertEquals("/jsp/index.jsp", response.getForwardedUrl());
    }

    @Test
    public void homeCorrectInitParameterTest() throws Exception {
        List<LastSolution> returnedList = getServletOutput("7");
        assertEquals(returnedList.size(), 7);
    }

    @Test
    public void homeIncorrectInitParameterTest() throws Exception {
        List<LastSolution> returnedList = getServletOutput("x");
        assertEquals(returnedList.size(), 5);
    }

    @Test
    public void homeListsMatchTest() throws Exception {
        List<LastSolution> returnedList = getServletOutput("7");
        List<LastSolution> expectedList = new MockLastSolutionDAO().loadMostRecentSolutions(7);
        assertEquals(expectedList, returnedList);
    }

    private List<LastSolution> getServletOutput(String initParameter) throws Exception {
        request.getServletContext().setInitParameter("last-solutions", initParameter);
        home.doGet(request, response);
        Object rawList = request.getAttribute("lastsolutions");
        return ((List<?>) rawList).stream().map(el -> (LastSolution) el).collect(Collectors.toList());
    }
}
