package controller;

import com.panpawelw.controller.ExercisesAdminPanel;
import com.panpawelw.model.Exercise;
import mockDAOs.MockExerciseDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class ExercisesAdminPanelTests {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private ExercisesAdminPanel exercisesAdminPanel;

    @Before
    public void setup() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        exercisesAdminPanel = new ExercisesAdminPanel();
        exercisesAdminPanel.setExerciseDAO(new MockExerciseDAO());
        exercisesAdminPanel.init(new MockServletConfig());
    }

    @Test
    public void exercisesAdminPanelForwardTest() throws Exception {
        exercisesAdminPanel.doGet(request, response);
        assertEquals("/jsp/exercisesadminview.jsp", response.getForwardedUrl());
    }

    @Test
    public void exercisesAdminPanelListsMatchTest() throws Exception {
        exercisesAdminPanel.doGet(request, response);
        Object rawList = request.getAttribute("exerciseslist");
        List<Exercise> returnedList =
                ((List<?>) rawList).stream().map(el -> (Exercise) el).collect(Collectors.toList());
        List<Exercise> expectedList = new MockExerciseDAO().loadAllExercises();
        assertEquals(returnedList, expectedList);
        assertEquals("/jsp/exercisesadminview.jsp", response.getForwardedUrl());
    }
}
