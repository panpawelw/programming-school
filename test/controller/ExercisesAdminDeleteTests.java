package controller;

import com.panpawelw.controller.ExercisesAdminDelete;
import mockDAOs.MockExerciseDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import static org.junit.Assert.assertEquals;

public class ExercisesAdminDeleteTests {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockServletConfig config;
    private ExercisesAdminDelete exercisesAdminDelete;

    @Before
    public void setup() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        config = new MockServletConfig();
        exercisesAdminDelete = new ExercisesAdminDelete();
    }

    @Test
    public void ExercisesAdminDeleteForwardTest() throws Exception {
        exercisesAdminDelete.setExerciseDAO(new MockExerciseDAO());
        request.setParameter("id","0");
        exercisesAdminDelete.init(config);
        exercisesAdminDelete.doGet(request, response);
        assertEquals("/exercisesadminpanel", response.getForwardedUrl());
    }
}
