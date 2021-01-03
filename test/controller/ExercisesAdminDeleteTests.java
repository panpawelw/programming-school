package controller;

import com.panpawelw.DAO.ExerciseDAO;
import com.panpawelw.controller.ExercisesAdminDelete;
import com.panpawelw.model.Exercise;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ExercisesAdminDeleteTests {

    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();
    private final ExercisesAdminDelete exercisesAdminDelete = new ExercisesAdminDelete();
    private final ExerciseDAO mockExerciseDAO = mock(ExerciseDAO.class);

    @Before
    public void setup() throws Exception {
        exercisesAdminDelete.setExerciseDAO(mockExerciseDAO);
        exercisesAdminDelete.init(new MockServletConfig());
    }

    @Test
    public void exercisesAdminDeleteForwardTest() throws Exception {
        request.setParameter("id","0");
        exercisesAdminDelete.doGet(request, response);
        assertEquals("/exercisesadminpanel", response.getForwardedUrl());
    }

    @Test
    public void exercisesAdminDeleteIncorrectIntegerParameterTest() throws Exception {
        request.setParameter("id", "16456");
        expect(mockExerciseDAO.loadExerciseById(16456)).andReturn(null);
        incorrectParameterTest();
    }

    @Test
    public void exercisesAdminDeleteIncorrectIntegerSubZeroParameterTest() throws Exception {
        request.setParameter("id", "-10");
        incorrectParameterTest();
    }

    @Test
    public void exercisesAdminDeleteCharacterParameterTest() throws Exception {
        request.setParameter("id", "x");
        incorrectParameterTest();
    }

    @Test
    public void exercisesAdminDeleteNullParameterTest() throws Exception {
        request.setParameter("id", "null");
        incorrectParameterTest();
    }

    @Test
    public void exercisesAdminDeleteCorrectParameterTest() throws Exception {
        request.setParameter("id","4");
        Exercise exerciseToDelete = new Exercise(4,
                "Test title", "Test description");
        expect(mockExerciseDAO.loadExerciseById(4)).andReturn(exerciseToDelete);
        expect(mockExerciseDAO.deleteExercise(exerciseToDelete)).andReturn(1);
        replay(mockExerciseDAO);
        exercisesAdminDelete.doGet(request, response);
        //noinspection ConstantConditions
        assertNull(request.getAttribute("errormessage"));
        assertEquals("/exercisesadminpanel", response.getForwardedUrl());
        verify(mockExerciseDAO);
    }

    private void incorrectParameterTest() throws Exception {
        replay(mockExerciseDAO);
        exercisesAdminDelete.doGet(request,response);
        assertEquals("Error deleting exercise!", request.getAttribute("errormessage"));
        assertEquals("/exercisesadminpanel", response.getForwardedUrl());
        verify(mockExerciseDAO);
    }
}
