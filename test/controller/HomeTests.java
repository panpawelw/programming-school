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

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockServletConfig config;
    private Home home;

    @Before
    public void setup() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        config = new MockServletConfig();
        home = new Home();
        home.setLastSolutionDAO(new MockLastSolutionDAO());
    }

    @Test
    public void homeForwardTest() throws Exception {
        home.init(config);
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
        assertEquals(returnedList.size(), 7);
    }

    private List<LastSolution> getServletOutput(String initParameter) throws Exception {
        request.getServletContext().setInitParameter("last-solutions", initParameter);
        home.init(config);
        home.doGet(request, response);
        Object rawList = request.getAttribute("lastsolutions");
        return ((List<?>) rawList).stream().map(el -> (LastSolution) el).collect(Collectors.toList());
    }
}
