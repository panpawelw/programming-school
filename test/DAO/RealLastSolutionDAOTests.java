package DAO;

import com.mockobjects.sql.MockMultiRowResultSet;
import org.junit.Before;
import org.junit.Test;
import com.panpawelw.DAO.LastSolutionDAO;
import com.panpawelw.DAO.RealLastSolutionDAO;
import com.panpawelw.model.LastSolution;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static misc.TestUtils.createMultipleLastSolutions;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class RealLastSolutionDAOTests {

    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement statement;
    String[] columns = new String[]{"title", "name", "modified", "id"};

    @Before
    public void setup() throws Exception {
        dataSource = createMock(DataSource.class);
        connection = createMock(Connection.class);
        expect(dataSource.getConnection()).andReturn(connection);
        statement = createMock(PreparedStatement.class);
    }

    @Test
    public void testLoadMostRecentSolutions() throws Exception {
        String sqlQuery = "SELECT exercise.title, user.username, IF(solution.updated > " +
          "solution.created, solution.updated, solution.created), solution.id FROM solution " +
          "LEFT JOIN exercise ON solution.exercise_id=exercise.id LEFT JOIN user ON " +
          "solution.user_id=user.id ORDER BY IF(updated > created, updated, created)" +
          " DESC LIMIT ?;";
        expect(connection.prepareStatement(sqlQuery)).andReturn(statement);
        statement.setLong(1, 3);

        MockMultiRowResultSet resultSet = new MockMultiRowResultSet();
        resultSet.setupColumnNames(columns);
        List<LastSolution> expectedLastSolutions = createMultipleLastSolutions();
        resultSet.setupRows(lastSolutionlistTo2dArray(expectedLastSolutions));
        expect(statement.executeQuery()).andReturn(resultSet);

        resultSet.setExpectedCloseCalls(1);
        statement.close();
        connection.close();

        replay(dataSource, connection, statement);

        LastSolutionDAO lastSolutionDAO = new RealLastSolutionDAO(dataSource);
        List<LastSolution> result = lastSolutionDAO.loadMostRecentSolutions(3);
        assertEquals(expectedLastSolutions.toString(), result.toString());
        verify(dataSource, connection, statement);
        resultSet.verify();
    }

    @Test
    public void testLoadMostRecentSolutionsByUserId() throws Exception {
        String sqlQuery = "SELECT exercise.title, user.username, IF(solution.updated > " +
          "solution.created, solution.updated, solution.created), solution.id FROM solution " +
          "LEFT JOIN exercise ON solution.exercise_id=exercise.id LEFT JOIN user ON " +
          "solution.user_id=user.id  WHERE solution.user_id=? ORDER BY IF(updated > created, " +
          "updated, created) DESC;";
        expect(connection.prepareStatement(sqlQuery)).andReturn(statement);
        statement.setLong(1, 2);

        MockMultiRowResultSet resultSet = new MockMultiRowResultSet();
        resultSet.setupColumnNames(columns);
        List<LastSolution> expectedLastSolutions = createMultipleLastSolutions();
        resultSet.setupRows(lastSolutionlistTo2dArray(expectedLastSolutions));
        expect(statement.executeQuery()).andReturn(resultSet);

        resultSet.setExpectedCloseCalls(1);
        statement.close();
        connection.close();

        replay(dataSource, connection, statement);

        LastSolutionDAO lastSolutionDAO = new RealLastSolutionDAO(dataSource);
        List<LastSolution> result = lastSolutionDAO.loadMostRecentSolutionsByUserId(2);
        assertEquals(expectedLastSolutions.toString(), result.toString());
        verify(dataSource, connection, statement);
        resultSet.verify();
    }

    private Object[][] lastSolutionlistTo2dArray(List<LastSolution> lastSolutions) {
        Object[][] array = new Object[(lastSolutions.size())][6];
        for (int i = 0; i < array.length; i++) {
            LastSolution lastSolution = lastSolutions.get(i);
            array[i] = new Object[]{lastSolution.getTitle(),
              lastSolution.getName(), lastSolution.getModified(), lastSolution.getId()};
        }
        return array;
    }
}
