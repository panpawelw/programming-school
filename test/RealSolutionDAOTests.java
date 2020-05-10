import com.mockobjects.sql.MockMultiRowResultSet;
import com.mockobjects.sql.MockResultSetMetaData;
import com.mockobjects.sql.MockSingleRowResultSet;
import org.junit.Before;
import org.junit.Test;
import pl.pjm77.DAO.RealSolutionDAO;
import pl.pjm77.DAO.SolutionDAO;
import pl.pjm77.model.Solution;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Timestamp.*;
import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

public class RealSolutionDAOTests {

    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement statement;
    String[] columns = new String[]{"id", "created", "updated", "description", "exercise_id", "user_id"};

    @Before
    public void setup() throws Exception {
        dataSource = createMock(DataSource.class);
        connection = createMock(Connection.class);
        expect(dataSource.getConnection()).andReturn(connection);
        statement = createMock(PreparedStatement.class);
    }

    @Test
    public void testLoadSolutionById() throws Exception {
        String sqlQuery = "SELECT * FROM solution WHERE id=?;";
        expect(connection.prepareStatement(sqlQuery)).andReturn(statement);
        statement.setLong(1, 1);

        MockSingleRowResultSet resultSet = new MockSingleRowResultSet();
        String[] columnsUppercase =
          new String[]{"ID", "CREATED", "UPDATED", "DESCRIPTION", "EXERCISE_ID", "USER_ID"};
        String[] columnClassesNames = new String[]{long.class.getName(), Timestamp.class.getName(),
          Timestamp.class.getName(), String.class.getName(), int.class.getName(), long.class.getName()};

        MockResultSetMetaData resultSetMetaData = new MockResultSetMetaData();
        resultSetMetaData.setupAddColumnNames(columnsUppercase);
        resultSetMetaData.setupAddColumnClassNames(columnClassesNames);
        resultSetMetaData.setupGetColumnCount(6);
        resultSet.setupMetaData(resultSetMetaData);

        resultSet.addExpectedNamedValues(columns, new Object[]{1L, valueOf("2020-04-20 23:24:10.0"),
          valueOf("2020-04-20 23:25:23.0"), "test description", 1, 1L});
        expect(statement.executeQuery()).andReturn(resultSet);

        resultSet.setExpectedCloseCalls(1);
        statement.close();
        connection.close();

        replay(dataSource, connection, statement);
        SolutionDAO solutionDAO = new RealSolutionDAO(dataSource);
        Solution solution = solutionDAO.loadSolutionById(1);
        Solution expectedSolution = new Solution(valueOf("2020-04-20 23:24:10.0"),
          valueOf("2020-04-20 23:25:23.0"), "test description",1, 1L);
        expectedSolution.setId(1L);
        assertEquals(expectedSolution.toString(), solution.toString());
        verify(dataSource, connection, statement);
        resultSet.verify();
    }

    @Test
    public void testLoadAllSolutions() throws Exception {
        String sql = "SELECT * FROM solution;";
        expect(connection.prepareStatement(sql)).andReturn(statement);

        MockMultiRowResultSet resultSet = new MockMultiRowResultSet();
        resultSet.setupColumnNames(columns);
        List<Solution> expectedSolutions = createManySolutions();
        resultSet.setupRows(solutionlistTo2dArray(expectedSolutions));
        expect(statement.executeQuery()).andReturn(resultSet);

        resultSet.setExpectedCloseCalls(1);
        statement.close();
        connection.close();

        replay(dataSource, connection, statement);

        SolutionDAO solutionDAO = new RealSolutionDAO(dataSource);
        List<Solution> result = solutionDAO.loadAllSolutions();
        assertEquals(expectedSolutions.toString(), result.toString());
        verify(dataSource, connection, statement);
        resultSet.verify();
    }

    @Test
    public void testLoadAllSolutionsByUserId() throws Exception {
        String sql = "SELECT * FROM solution WHERE user_id=?;";
        expect(connection.prepareStatement(sql)).andReturn(statement);
        statement.setLong(1,2);

        MockMultiRowResultSet resultSet = new MockMultiRowResultSet();
        resultSet.setupColumnNames(columns);
        List<Solution> expectedSolutions = createManySolutionsWithSameUserId(2);
        resultSet.setupRows(solutionlistTo2dArray(expectedSolutions));
        expect(statement.executeQuery()).andReturn(resultSet);

        resultSet.setExpectedCloseCalls(1);
        statement.close();
        connection.close();

        replay(dataSource, connection, statement);

        SolutionDAO solutionDAO = new RealSolutionDAO(dataSource);
        List<Solution> result = solutionDAO.loadAllSolutionsByUserId(2);
        assertEquals(expectedSolutions.toString(), result.toString());
        verify(dataSource, connection, statement);
        resultSet.verify();
    }

    private List<Solution> createManySolutions() {
        List<Solution> expectedSolutions = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            Solution solution = new Solution(valueOf("2020-04-20 23:24:15." + i),
              valueOf("2020-04-20 23:25:23.0" + i), "Test description " + i, i, i);
            solution.setId(i);
            expectedSolutions.add(solution);
        }
        return expectedSolutions;
    }

    private List<Solution> createManySolutionsWithSameUserId(long userId) {
        List<Solution> expectedSolutions = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            Solution solution = new Solution(valueOf("2020-04-20 23:24:15." + i),
              valueOf("2020-04-20 23:25:23.0" + i), "Test description " + i, i, userId);
            solution.setId(i);
            expectedSolutions.add(solution);
        }
        return expectedSolutions;
    }

    private Object[][] solutionlistTo2dArray(List<Solution> solutions) {
        Object[][] array = new Object[(solutions.size())][6];
        for (int i = 0; i < array.length; i++) {
            Solution solution = solutions.get(i);
            array[i] = new Object[]{solution.getId(), solution.getCreated(), solution.getUpdated(),
              solution.getDescription(), solution.getExercise_id(), solution.getUser_id()};
        }
        return array;
    }
}