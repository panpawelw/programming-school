package controller;

import com.panpawelw.controller.SolutionsAdminDelete;
import mockDAOs.MockSolutionDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import static org.junit.Assert.assertEquals;

public class SolutionsAdminDeleteTests {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockServletConfig config;
    private SolutionsAdminDelete solutionsAdminDelete;

    @Before
    public void setup() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        config = new MockServletConfig();
        solutionsAdminDelete = new SolutionsAdminDelete();
    }

    @Test
    public void SolutionsAdminDeleteForwardTest() throws Exception {
        solutionsAdminDelete.setSolutionDAO(new MockSolutionDAO());
        request.setParameter("id", "0");
        solutionsAdminDelete.init(config);
        solutionsAdminDelete.doGet(request, response);
        assertEquals("/solutionsadminpanel", response.getForwardedUrl());
    }

    @Test
    public void SolutionsAdminDeleteIncorrectParameterTest() throws Exception {
        solutionsAdminDelete.setSolutionDAO(new MockSolutionDAO());
        request.setParameter("id","x");
        solutionsAdminDelete.init(config);
        solutionsAdminDelete.doGet(request, response);
        assertEquals("Error deleting solution!", request.getAttribute("errormessage"));
    }
}
