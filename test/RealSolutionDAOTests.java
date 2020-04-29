import com.mockobjects.sql.MockResultSetMetaData;
import com.mockobjects.sql.MockSingleRowResultSet;
import org.junit.Test;
import pl.pjm77.DAO.RealSolutionDAO;
import pl.pjm77.DAO.SolutionDAO;
import pl.pjm77.model.Solution;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

public class RealSolutionDAOTests {

    @Test
    public void testLoadSolutionById() throws Exception {

        DataSource dataSource = createMock(DataSource.class);
        Connection connection = createMock(Connection.class);
        expect(dataSource.getConnection()).andReturn(connection);
        String sqlQuery = "SELECT * FROM solution WHERE id=?;";
        PreparedStatement statement = createMock(PreparedStatement.class);
        expect(connection.prepareStatement(sqlQuery)).andReturn(statement);
        statement.setLong(1, 1);

        MockSingleRowResultSet resultSet = new MockSingleRowResultSet();
        String[] columnsLowercase =
                new String[]{"id", "created", "updated", "description",
                        "exercise_id", "user_id"};
        String[] columnsUppercase = new String[]{"ID", "CREATED", "UPDATED",
                "DESCRIPTION", "EXERCISE_ID", "USER_ID"};
        String[] columnClassesNames = new String[]{long.class.getName(), Timestamp.class.getName(),
                Timestamp.class.getName(), String.class.getName(), int.class.getName(),
                long.class.getName()};

        MockResultSetMetaData resultSetMetaData = new MockResultSetMetaData();
        resultSetMetaData.setupAddColumnNames(columnsUppercase);
        resultSetMetaData.setupAddColumnClassNames(
                columnClassesNames);
        resultSetMetaData.setupGetColumnCount(6);
        resultSet.setupMetaData(resultSetMetaData);

        resultSet.addExpectedNamedValues(columnsLowercase,
                new Object[]{1L, java.sql.Timestamp.valueOf("2020-04-20 23:24:10.0"),
                        java.sql.Timestamp.valueOf("2020-04-20 23:25:23.0"),
                        "test description", 1, 1L});
        expect(statement.executeQuery()).andReturn(resultSet);

        resultSet.setExpectedCloseCalls(1);
        statement.close();
        connection.close();

        replay(dataSource, connection, statement);
        SolutionDAO solutionDAO = new RealSolutionDAO(dataSource);
        Solution solution = solutionDAO.loadSolutionById(1);
        Solution expectedSolution = new Solution(
                java.sql.Timestamp.valueOf("2020-04-20 23:24:10.0"),
                java.sql.Timestamp.valueOf("2020-04-20 23:25:23.0"),
                "test description", 1, 1L);
        expectedSolution.setId(1L);
        assertEquals(expectedSolution.toString(), solution.toString());
        verify(dataSource, connection, statement);
        resultSet.verify();
    }
}