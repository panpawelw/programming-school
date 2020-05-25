package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.pjm77.model.LastSolution;

import java.sql.Timestamp;

public class LastSolutionTest {

    public static final long EXPECTED_ID = 0;
    public static final String EXPECTED_TITLE = "whatever";
    public static final String EXPECTED_NAME = "whoever";
    public static final Timestamp EXPECTED_MODIFIED = java.sql.Timestamp.valueOf("2020-04-20 23:24:10.0");

    private LastSolution lastSolution;

    @Before
    public void setup() throws Exception {
        lastSolution = new LastSolution("whatever", "whoever",
                java.sql.Timestamp.valueOf("2020-04-20 23:24:10.0"));
    }

    @Test
    public void TestLastSolution() throws Exception {
        Assert.assertEquals(EXPECTED_ID, lastSolution.getId());
        Assert.assertEquals(EXPECTED_TITLE, lastSolution.getTitle());
        Assert.assertEquals(EXPECTED_NAME, lastSolution.getName());
        Assert.assertEquals(EXPECTED_MODIFIED, lastSolution.getModified());
    }
}
