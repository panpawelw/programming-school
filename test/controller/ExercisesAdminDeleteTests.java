package controller;

import com.panpawelw.controller.ExercisesAdminDelete;
import mockDAOs.MockExerciseDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ExercisesAdminDeleteTests {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private ExercisesAdminDelete exercisesAdminDelete;

    @Before
    public void setup() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        exercisesAdminDelete = new ExercisesAdminDelete();
        exercisesAdminDelete.setExerciseDAO(new MockExerciseDAO());
        exercisesAdminDelete.init(new MockServletConfig());
    }

    @Test
    public void ExercisesAdminDeleteForwardTest() throws Exception {
        request.setParameter("id","0");
        exercisesAdminDelete.doGet(request, response);
        assertEquals("/exercisesadminpanel", response.getForwardedUrl());
    }

    @Test
    public void ExercisesAdminDeleteIncorrectParameterTest() throws Exception {
        request.setParameter("id","x");
        exercisesAdminDelete.doGet(request, response);
        assertEquals("Error deleting exercise!", request.getAttribute("errormessage"));
    }

    @Test
    public void ExercisesAdminDeleteCorrectParameterTest() throws Exception {
        request.setParameter("id","4");
        exercisesAdminDelete.doGet(request, response);
        //noinspection ConstantConditions
        assertNull(request.getAttribute("errormessage"));
        assertEquals("/exercisesadminpanel", response.getForwardedUrl());
    }
}
