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
    public void exercisesAdminAddEditGetNotExistingExerciseIdTest() throws Exception {
        request.setParameter("id", "16456");
        expect(mockExerciseDAO.loadExerciseById(16456)).andReturn(null);
        testGetMethod("/exercisesadminpanel", "No such exercise exists!");
    }

    @Test
    public void exercisesAdminAddEditGetNewExerciseTest() throws Exception {
        request.setParameter("id", "0");
        testGetMethod("/jsp/exercisesadminaddeditview.jsp", null,
                new Exercise(0, null, null));
    }

    @Test
    public void exercisesAdminAddEditGetExistingExerciseTest() throws Exception {
        request.setParameter("id", "5");
        Exercise expectedExercise = new Exercise(5, "Test title",
                "Test description");
        expect(mockExerciseDAO.loadExerciseById(5)).andReturn(expectedExercise);
        testGetMethod("/jsp/exercisesadminaddeditview.jsp",
                null, expectedExercise);
    }

    @Test
    public void exercisesAdminAddEditPostForwardTest() throws Exception {
        exercisesAdminAddEdit.doPost(request, response);
        assertEquals("/exercisesadminpanel", response.getForwardedUrl());
    }

    @Test
    public void exercisesAdminAddEditPostIncorrectIdParameterTest() throws Exception {
        request.setParameter("id", "xxx");
        request.setParameter("title", "Test title");
        request.setParameter("description", "Test description");
        testPostMethod("Incorrect parameters!");
    }

    @Test
    public void exercisesAdminAddEditPostNullIdParameterTest() throws Exception {
        request.setParameter("title", "Test title");
        request.setParameter("description", "Test description");
        testPostMethod("Incorrect parameters!");
    }

    @Test
    public void exercisesAdminAddEditPostEmptyTitleParameterTest() throws Exception {
        request.setParameter("id", "1");
        request.setParameter("title", "");
        request.setParameter("description", "Test description");
        testPostMethod("Incorrect parameters!");
    }

    @Test
    public void exercisesAdminAddEditPostNullTitleParameterTest() throws Exception {
        request.setParameter("id", "1");
        request.setParameter("description", "Test description");
        testPostMethod("Incorrect parameters!");
    }

    @Test
    public void exercisesAdminAddEditPostEmptyDescriptionParameterTest() throws Exception {
        request.setParameter("id", "1");
        request.setParameter("title", "Test title");
        request.setParameter("description", "");
        testPostMethod("Incorrect parameters!");
    }

    @Test
    public void exercisesAdminAddEditPostNullDescriptionParameterTest() throws Exception {
        request.setParameter("id", "1");
        request.setParameter("title", "Test title");
        testPostMethod("Incorrect parameters!");
    }

    @Test
    public void exercisesAdminAddEditPostNewExerciseTest() throws Exception {
        request.setParameter("id", "0");
        request.setParameter("title", "Test title");
        request.setParameter("description", "Test description");
        expect(mockExerciseDAO.saveExerciseToDB(new Exercise(0, "Test title",
                "Test description"))).andReturn(1);
        testPostMethod(null);
    }

    @Test
    public void exercisesAdminAddEditPostExistingExerciseTest() throws Exception {
        request.setParameter("id", "1");
        request.setParameter("title", "Test title");
        request.setParameter("description", "Test description");
        expect(mockExerciseDAO.loadExerciseById(1)).andReturn(new Exercise(1, "Test title",
                "Test description"));
        expect(mockExerciseDAO.saveExerciseToDB(new Exercise(1, "Test title",
                "Test description"))).andReturn(1);
        testPostMethod(null);
    }

    @Test
    public void exercisesAdminAddEditPostDatabaseErrorTest() throws Exception {
        request.setParameter("id", "1");
        request.setParameter("title", "Test title");
        request.setParameter("description", "Test description");
        expect(mockExerciseDAO.loadExerciseById(1)).andReturn(new Exercise(1, "Test title",
                "Test description"));
        expect(mockExerciseDAO.saveExerciseToDB(new Exercise(1, "Test title",
                "Test description"))).andReturn(0);
        testPostMethod("Database error!");
    }

    private void testGetMethod(String expectedUrl, String expectedErrorMessage,
                               Exercise... expectedExercise) throws Exception {
        replay(mockExerciseDAO);
        exercisesAdminAddEdit.doGet(request,response);
        assertEquals(expectedUrl, response.getForwardedUrl());
        if (expectedErrorMessage != null) {
            assertEquals(expectedErrorMessage, request.getAttribute("errormessage"));
        }
        if (expectedExercise.length > 0) {
            assertEquals(expectedExercise[0], request.getAttribute("exercise"));
        }
        verify(mockExerciseDAO);
    }

    private void testPostMethod(String errorMessage) throws Exception {
        replay(mockExerciseDAO);
        exercisesAdminAddEdit.doPost(request, response);
        if (errorMessage != null) {
            assertEquals(errorMessage, request.getAttribute("errormessage"));
        }
        assertEquals("/exercisesadminpanel", response.getForwardedUrl());
        verify(mockExerciseDAO);
    }
}
