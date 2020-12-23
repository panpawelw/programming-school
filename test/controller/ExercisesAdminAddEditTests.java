package controller;

import com.panpawelw.DAO.ExerciseDAO;
import com.panpawelw.controller.ExercisesAdminAddEdit;
import com.panpawelw.model.Exercise;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class ExercisesAdminAddEditTests {

    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response  = new MockHttpServletResponse();
    private final ExercisesAdminAddEdit exercisesAdminAddEdit = new ExercisesAdminAddEdit();
    private final ExerciseDAO mockExerciseDAO = mock(ExerciseDAO.class);

    @Before
    public void setup() throws Exception {
        exercisesAdminAddEdit.setExerciseDAO(mockExerciseDAO);
        exercisesAdminAddEdit.init(new MockServletConfig());
    }

    @Test
    public void exercisesAdminAddEditGetForwardTest() throws Exception {
        exercisesAdminAddEdit.doGet(request, response);
        assertEquals("/exercisesadminpanel", response.getForwardedUrl());
    }

    @Test
    public void exercisesAdminAddEditPostForwardTest() throws Exception {
        exercisesAdminAddEdit.doPost(request, response);
        assertEquals("/exercisesadminpanel", response.getForwardedUrl());
    }

    @Test
    public void exercisesAdminAddEditGetIncorrectIntegerParameterTest() throws Exception {
        request.setParameter("id", "16456");
        expect(mockExerciseDAO.loadExerciseById(16456)).andReturn(null);
        incorrectParameterTest();
    }

    @Test
    public void exercisesAdminAddEditGetIncorrectIntegerSubZeroParameterTest() throws Exception {
        request.setParameter("id", "-10");
        incorrectParameterTest();
    }

    @Test
    public void exercisesAdminAddEditGetCharacterParameterTest() throws Exception {
        request.setParameter("id", "x");
        incorrectParameterTest();
    }

    @Test
    public void exercisesAdminAddEditGetNullParameterTest() throws Exception {
        request.setParameter("id", "null");
        incorrectParameterTest();
    }

    @Test
    public void exercisesAdminAddEditGetCorrectParameterTest() throws Exception {
        request.setParameter("id", "5");
        Exercise expectedExercise = new Exercise(5, "Test title",
                "Test description");
        expect(mockExerciseDAO.loadExerciseById(5)).andReturn(expectedExercise);
        replay(mockExerciseDAO);
        exercisesAdminAddEdit.doGet(request,response);
        assertEquals(expectedExercise, request.getAttribute("exercise"));
        assertEquals("/jsp/exercisesadminaddeditview.jsp", response.getForwardedUrl());
        verify(mockExerciseDAO);
    }

    private void incorrectParameterTest() throws Exception {
        replay(mockExerciseDAO);
        exercisesAdminAddEdit.doGet(request,response);
        assertEquals("No such exercise exists!", request.getAttribute("errormessage"));
        assertEquals("/exercisesadminpanel", response.getForwardedUrl());
        verify(mockExerciseDAO);
    }
}
