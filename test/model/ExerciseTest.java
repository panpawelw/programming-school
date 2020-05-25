package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.pjm77.model.Exercise;

public class ExerciseTest {

    public static final int EXPECTED_ID = 0;
    public static final String EXPECTED_TITLE = "whatever title";
    public static final String EXPECTED_DESCRIPTION = "whatever description";

    private Exercise exercise;

    @Before
    public void setup() throws Exception {
        exercise = new Exercise("whatever title", "whatever description");
    }

    @Test
    public void testExercise() throws Exception {
        Assert.assertEquals(EXPECTED_ID, exercise.getId());
        Assert.assertEquals(EXPECTED_TITLE, exercise.getTitle());
        Assert.assertEquals(EXPECTED_DESCRIPTION, exercise.getDescription());
    }
}
