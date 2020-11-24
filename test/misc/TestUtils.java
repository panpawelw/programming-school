package misc;

import com.mockobjects.MockObject;
import com.mockobjects.sql.MockMultiRowResultSet;
import com.mockobjects.sql.MockSingleRowResultSet;
import com.panpawelw.model.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Timestamp.valueOf;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class TestUtils {

    public static void closeAllAndReplay(DataSource ds, Connection c, Statement s) throws Exception {
        s.close();
        c.close();
        replay(ds, c, s);
    }

    public static MockMultiRowResultSet prepareMultiRowResultSetMock
      (Object[][] objectArray, String[] columns, PreparedStatement statement) throws Exception {

        MockMultiRowResultSet resultSet = new MockMultiRowResultSet();
        resultSet.setupColumnNames(columns);
        resultSet.setupRows(objectArray);
        expect(statement.executeQuery()).andReturn(resultSet);
        resultSet.setExpectedCloseCalls(1);
        return resultSet;
    }

    public static MockSingleRowResultSet prepareSingleRowResultSetMock() {
        MockSingleRowResultSet resultSet = new MockSingleRowResultSet();
        resultSet.setExpectedCloseCalls(1);
        return resultSet;
    }

    public static void assertAndVerify(Object expectedObject, Object resultObject, DataSource ds,
                                       Connection c, Statement s, MockObject rs) throws Exception {

        assertEquals(expectedObject.toString(), resultObject.toString());
        verify(ds, c, s);
        rs.verify();
    }
    public static List<LastSolution> createMultipleLastSolutions(long number) {
        List<LastSolution> expectedLastSolutions = new ArrayList<>();
        for (long i = 1; i <= number; i++) {
            LastSolution lastSolution = new LastSolution(i, "Test title " + i,
              "Test name " + i, valueOf("2020-04-20 23:25:23.0" + i));
            expectedLastSolutions.add(lastSolution);
        }
        return expectedLastSolutions;
    }

    /**
     * Creates a list of test users, optionally with identical group ID
     *
     * @param args - optional group ID (int)
     * @return - list of users
     */
    public static List<User> createMultipleUsers(int... args) {
        List<User> expectedUsers = new ArrayList<>();
        int group_id = 1;
        if (args.length != 0) group_id = args[0];
        for (int i = 1; i < 6; i++) {
            if (args.length == 0) group_id = i;
            User user = new User("Test user " + i,
              "Test email " + i, "Test password " + i, group_id);
            user.setId(i);
            expectedUsers.add(user);
        }
        return expectedUsers;
    }

    public static List<UserGroup> createMultipleUserGroups() {
        List<UserGroup> expectedUserGroups = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            UserGroup userGroup = new UserGroup("Test user group " + i);
            userGroup.setId(i);
            expectedUserGroups.add(userGroup);
        }
        return expectedUserGroups;
    }

    /**
     * Creates a list of test exercises
     * @return - list of exercises
     */
    public static List<Exercise> createMultipleExercises() {
        List<Exercise> expectedExercises = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            Exercise exercise = new Exercise("Test exercise title " + i,
              "Test exercise description " + i);
            exercise.setId(i);
            expectedExercises.add(exercise);
        }
        return expectedExercises;
    }

    /**
     * Creates a list of test solutions, optionally with identical user ID
     *
     * @param args - optional user ID (long)
     * @return - list of solutions
     */
    public static List<Solution> createMultipleSolutions(long... args) {
        List<Solution> expectedSolutions = new ArrayList<>();
        long user_id = 1;
        if (args.length != 0) user_id = args[0];
        for (int i = 1; i < 6; i++) {
            if (args.length == 0) user_id = i;
            Solution solution = new Solution(valueOf("2020-04-20 23:24:15." + i),
              valueOf("2020-04-20 23:25:23.0" + i), "Test description " + i, i, user_id);
            solution.setId(i);
            expectedSolutions.add(solution);
        }
        return expectedSolutions;
    }
}
