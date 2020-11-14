package controller;

import com.panpawelw.controller.SolutionsAdminAddEdit;
import mockDAOs.MockExerciseDAO;
import mockDAOs.MockSolutionDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import static org.junit.Assert.assertEquals;

public class SolutionsAdminAddEditTest {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockServletConfig config;
    private SolutionsAdminAddEdit solutionsAdminAddEdit;

    @Before
    public void setup() throws Exception {

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        config = new MockServletConfig();
        solutionsAdminAddEdit = new SolutionsAdminAddEdit();
    }

    @Test
    public void solutionsAdminAddEditGetForwardTest() throws Exception {
        solutionsAdminAddEdit.setSolutionDAO(new MockSolutionDAO());
        request.setParameter("id","0");
        solutionsAdminAddEdit.init(config);
        solutionsAdminAddEdit.doGet(request, response);
        assertEquals("/jsp/solutionsadminaddeditview.jsp", response.getForwardedUrl());
    }

    @Test
    public void solutionsAdminAddEditPostForwardTest() throws Exception {
        solutionsAdminAddEdit.setSolutionDAO(new MockSolutionDAO());
        request.setParameter("id","0");
        request.setParameter("exercise_id","0");
        request.setParameter("user_id","0");
        solutionsAdminAddEdit.init(config);
        solutionsAdminAddEdit.doPost(request, response);
        assertEquals("/solutionsadminpanel", response.getForwardedUrl());
    }
}
