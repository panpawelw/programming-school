package controller;

import com.panpawelw.controller.Home;
import mockDAOs.MockLastSolutionDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

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
        config.addInitParameter("number-solutions", "7");
        home.init(config);
        home.doGet(request, response);
        assertEquals("7", home.getServletConfig().getInitParameter("number-solutions"));
    }
}
