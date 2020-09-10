package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.panpawelw.model.Solution;

import java.sql.Timestamp;

public class SolutionTest {

    public static final long EXPECTED_ID = 0L;
    public static final Timestamp EXPECTED_CREATED =
            java.sql.Timestamp.valueOf("2020-04-20 23:24:10.0");
    public static final Timestamp EXPECTED_UPDATED =
            java.sql.Timestamp.valueOf("2020-04-20 23:25:23.0");
    public static final String EXPECTED_DESCRIPTION = "whatever description";
    public static final int EXPECTED_EXERCISE_ID = 1;
    public static final long EXPECTED_USER_ID = 1L;

    private Solution solution;

    @Before
    public void setup() throws Exception {
        solution = new Solution(java.sql.Timestamp.valueOf("2020-04-20 23:24:10.0"),
                java.sql.Timestamp.valueOf("2020-04-20 23:25:23.0"),
                "whatever description", 1, 1L);
    }

    @Test
    public void TestSolution() throws Exception {
        Assert.assertEquals(EXPECTED_ID, solution.getId());
        Assert.assertEquals(EXPECTED_CREATED, solution.getCreated());
        Assert.assertEquals(EXPECTED_UPDATED, solution.getUpdated());
        Assert.assertEquals(EXPECTED_DESCRIPTION, solution.getDescription());
        Assert.assertEquals(EXPECTED_EXERCISE_ID, solution.getExercise_id());
        Assert.assertEquals(EXPECTED_USER_ID, solution.getUser_id());
    }
}