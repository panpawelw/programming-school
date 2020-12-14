package controller;

import com.panpawelw.controller.ExercisesAdminAddEdit;
import mockDAOs.MockExerciseDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import static org.junit.Assert.assertEquals;

public class ExercisesAdminAddEditTests {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private ExercisesAdminAddEdit exercisesAdminAddEdit;

    @Before
    public void setup() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        exercisesAdminAddEdit = new ExercisesAdminAddEdit();
        exercisesAdminAddEdit.setExerciseDAO(new MockExerciseDAO());
        exercisesAdminAddEdit.init(new MockServletConfig());
    }

    @Test
    public void exercisesAdminAddEditGetForwardTest() throws Exception {
        exercisesAdminAddEdit.doGet(request, response);
        assertEquals("/jsp/exercisesadminaddeditview.jsp", response.getForwardedUrl());
    }

    @Test
    public void exercisesAdminAddEditPostForwardTest() throws Exception {
        exercisesAdminAddEdit.doPost(request, response);
        assertEquals("/exercisesadminpanel", response.getForwardedUrl());
    }
}
