import com.mockobjects.sql.MockResultSetMetaData;
import com.mockobjects.sql.MockSingleRowResultSet;
import org.junit.Test;
import pl.pjm77.DAO.ExerciseDAO;
import pl.pjm77.DAO.RealExerciseDAO;
import pl.pjm77.model.Exercise;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

public class RealExerciseDAOTests {

    @Test
    public void testLoadExerciseById() throws Exception {

        DataSource dataSource = createMock(DataSource.class);
        Connection connection = createMock(Connection.class);
        expect(dataSource.getConnection()).andReturn(connection);
        String sqlQuery = "SELECT * FROM exercise WHERE id=?;";
        PreparedStatement statement = createMock(PreparedStatement.class);
        expect(connection.prepareStatement(sqlQuery)).andReturn(statement);
        statement.setInt(1, 1);

        MockSingleRowResultSet resultSet = new MockSingleRowResultSet();
        String[] columnsLowercase =
                new String[] {"id", "title", "description"};
        String[] columnsUppercase = new String[] {"ID",
                "NAME", "DESCRIPTION"};
        String[] columnClassesNames = new String[] {
                int.class.getName(), String.class.getName()};

        MockResultSetMetaData resultSetMetaData = new MockResultSetMetaData();
        resultSetMetaData.setupAddColumnNames(columnsUppercase);
        resultSetMetaData.setupAddColumnClassNames(
                columnClassesNames);
        resultSetMetaData.setupGetColumnCount(2);
        resultSet.setupMetaData(resultSetMetaData);

        resultSet.addExpectedNamedValues(columnsLowercase,
                new Object[] {1, "Test title", "Test description"});
        expect(statement.executeQuery()).andReturn(resultSet);

        resultSet.setExpectedCloseCalls(1);
        statement.close();
        connection.close();

        replay(dataSource, connection, statement);
        ExerciseDAO exerciseDAO = new RealExerciseDAO(dataSource);
        Exercise exercise = exerciseDAO.loadExerciseById(1);
        Exercise expectedExercise =
                new Exercise("Test title", "Test description");
        expectedExercise.setId(1);
        assertEquals(expectedExercise.toString(), exercise.toString());
        verify(dataSource, connection, statement);
        resultSet.verify();
    }

}
