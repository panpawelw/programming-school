package controller;

import com.panpawelw.DAO.ExerciseDAO;
import com.panpawelw.controller.ExercisesAdminPanel;
import com.panpawelw.model.Exercise;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import java.util.List;

import static misc.TestUtils.createMultipleExercises;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class ExercisesAdminPanelTests {

    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();
    private final ExercisesAdminPanel exercisesAdminPanel = new ExercisesAdminPanel();
    private final ExerciseDAO mockExerciseDAO = mock(ExerciseDAO.class);

    @Before
    public void setup() throws Exception {
        exercisesAdminPanel.setExerciseDAO(mockExerciseDAO);
        exercisesAdminPanel.init(new MockServletConfig());
    }

    @Test
    public void exercisesAdminPanelForwardTest() throws Exception {
        exercisesAdminPanel.doGet(request, response);
        assertEquals("/jsp/exercisesadminview.jsp", response.getForwardedUrl());
    }

    @Test
    public void exercisesAdminPanelListsMatchTest() throws Exception {
        List<Exercise> expectedList = createMultipleExercises(10);
        expect(mockExerciseDAO.loadAllExercises()).andReturn(expectedList);
        replay(mockExerciseDAO);
        exercisesAdminPanel.doGet(request, response);
        assertEquals(expectedList, request.getAttribute("exerciseslist"));
        assertEquals("/jsp/exercisesadminview.jsp", response.getForwardedUrl());
        verify(mockExerciseDAO);
    }
}
