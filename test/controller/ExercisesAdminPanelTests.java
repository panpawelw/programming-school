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
    private MockServletConfig config;
    private ExercisesAdminPanel exercisesAdminPanel;

    @Before
    public void setup() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        config = new MockServletConfig();
        exercisesAdminPanel = new ExercisesAdminPanel();
    }

    @Test
    public void exercisesAdminPanelForwardTest() throws Exception {
        exercisesAdminPanel.setExerciseDAO(new MockExerciseDAO());
        exercisesAdminPanel.init(config);
        exercisesAdminPanel.doGet(request, response);
        assertEquals("/jsp/exercisesadminview.jsp", response.getForwardedUrl());
    }

    @Test
    public void exercisesAdminPanelListsMatchTest() throws Exception {
        exercisesAdminPanel.setExerciseDAO(new MockExerciseDAO());
        exercisesAdminPanel.init(config);
        exercisesAdminPanel.doGet(request, response);
        Object rawList = request.getAttribute("exerciseslist");
        List<Exercise> returnedList =
                ((List<?>) rawList).stream().map(el -> (Exercise) el).collect(Collectors.toList());
        List<Exercise> expectedList = new MockExerciseDAO().loadAllExercises();
        assertEquals(returnedList, expectedList);
        assertEquals("/jsp/exercisesadminview.jsp", response.getForwardedUrl());
    }
}
