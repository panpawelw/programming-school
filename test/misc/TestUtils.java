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

    /**
     * Creates a list of test users, optionally with identical group ID
     *
     * @param howmany - number of users in the list
     * @param optionalGroupId - optional group ID (int)
     * @return - list of users
     */
    public static List<User> createMultipleUsers(long howmany, int... optionalGroupId) {
        List<User> expectedUsers = new ArrayList<>();
        int group_id = 1;
        if (optionalGroupId.length != 0) group_id = optionalGroupId[0];
        for (int i = 1; i <= howmany; i++) {
            if (optionalGroupId.length == 0) group_id = i;
            expectedUsers.add(new User(i, "Test user " + i,
              "Test email " + i, "Test password " + i, group_id));
        }
        return expectedUsers;
    }

    /**
     * Creates a list of test solutions, optionally with identical user ID
     *
     * @param howmany - number of solutions in the list
     * @param optionalUserId {long} - optional user ID (long)
     * @return - list of solutions
     */
    public static List<Solution> createMultipleSolutions(long howmany, long... optionalUserId) {
        List<Solution> expectedSolutions = new ArrayList<>();
        long user_id = 1;
        if (optionalUserId.length != 0) user_id = optionalUserId[0];
        for (int i = 1; i <= howmany; i++) {
            if (optionalUserId.length == 0) user_id = i;
            expectedSolutions.add(new Solution(i, valueOf("2020-04-20 23:24:15." + i),
                    valueOf("2020-04-20 23:25:23.0" + i), "Test description " + i,
                    i, user_id));
        }
        return expectedSolutions;
    }

    /**
     * Creates a list of test user groups
     * @param howmany - number of groups in the list
     * @return - list of user groups
     */
    public static List<UserGroup> createMultipleUserGroups(int howmany) {
        List<UserGroup> expectedUserGroups = new ArrayList<>();
        for (int i = 1; i <= howmany; i++) {
            expectedUserGroups.add(new UserGroup(i,"Test user group " + i));
        }
        return expectedUserGroups;
    }

    /**
     * Creates a list of test exercises
     * @param howmany - number of exercises in the list
     * @return - list of exercises
     */
    public static List<Exercise> createMultipleExercises(int howmany) {
        List<Exercise> expectedExercises = new ArrayList<>();
        for (int i = 1; i <= howmany; i++) {
            expectedExercises.add(new Exercise(i,"Test exercise title " + i,
              "Test exercise description " + i));
        }
        return expectedExercises;
    }

    /**
     * Creates a list of test last solutions
     * @param howmany - number of last solutions in the list
     * @return - list of last solutions
     */
    public static List<LastSolution> createMultipleLastSolutions(long howmany) {
        List<LastSolution> expectedLastSolutions = new ArrayList<>();
        for (long i = 1; i <= howmany; i++) {
            expectedLastSolutions.add(new LastSolution(i, "Test title " + i,
                    "Test name " + i, valueOf("2020-04-20 23:25:23.0" + i)));
        }
        return expectedLastSolutions;
    }
}
