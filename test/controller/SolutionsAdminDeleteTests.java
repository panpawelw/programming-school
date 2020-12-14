package controller;

import com.panpawelw.controller.SolutionsAdminDelete;
import mockDAOs.MockSolutionDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SolutionsAdminDeleteTests {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private SolutionsAdminDelete solutionsAdminDelete;

    @Before
    public void setup() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        solutionsAdminDelete = new SolutionsAdminDelete();
        solutionsAdminDelete.setSolutionDAO(new MockSolutionDAO());
        solutionsAdminDelete.init(new MockServletConfig());
    }

    @Test
    public void SolutionsAdminDeleteForwardTest() throws Exception {
        request.setParameter("id", "0");
        solutionsAdminDelete.doGet(request, response);
        assertEquals("/solutionsadminpanel", response.getForwardedUrl());
    }

    @Test
    public void SolutionsAdminDeleteIncorrectParameterTest() throws Exception {
        request.setParameter("id","x");
        solutionsAdminDelete.doGet(request, response);
        assertEquals("Error deleting solution!", request.getAttribute("errormessage"));
    }

    @Test
    public void SolutionsAdminDeleteCorrectParameterTest() throws Exception {
        request.setParameter("id","2");
        solutionsAdminDelete.doGet(request, response);
        //noinspection ConstantConditions
        assertNull(request.getAttribute("errormessage"));
        assertEquals("/solutionsadminpanel", response.getForwardedUrl());
    }
}
