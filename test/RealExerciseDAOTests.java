import com.mockobjects.sql.MockMultiRowResultSet;
import com.mockobjects.sql.MockResultSetMetaData;
import com.mockobjects.sql.MockSingleRowResultSet;
import org.junit.Before;
import org.junit.Test;
import pl.pjm77.DAO.ExerciseDAO;
import pl.pjm77.DAO.RealExerciseDAO;
import pl.pjm77.model.Exercise;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

public class RealExerciseDAOTests {

    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement statement;
    String[] columns = new String[]{"id", "title", "description"};

    @Before
    public void setup() throws Exception {
        dataSource = createMock(DataSource.class);
        connection = createMock(Connection.class);
        expect(dataSource.getConnection()).andReturn(connection);
        statement = createMock(PreparedStatement.class);
    }

    @Test
    public void testLoadExerciseById() throws Exception {

        String sqlQuery = "SELECT * FROM exercise WHERE id=?;";
        expect(connection.prepareStatement(sqlQuery)).andReturn(statement);
        statement.setInt(1, 1);

        MockSingleRowResultSet resultSet = new MockSingleRowResultSet();
        String[] columnsUppercase = new String[] {"ID", "TITLE", "DESCRIPTION"};
        String[] columnClassesNames = new String[] {int.class.getName(), String.class.getName()};

        MockResultSetMetaData resultSetMetaData = new MockResultSetMetaData();
        resultSetMetaData.setupAddColumnNames(columnsUppercase);
        resultSetMetaData.setupAddColumnClassNames(columnClassesNames);
        resultSetMetaData.setupGetColumnCount(3);
        resultSet.setupMetaData(resultSetMetaData);

        resultSet.addExpectedNamedValues(columns, new Object[] {1, "Test title", "Test description"});
        expect(statement.executeQuery()).andReturn(resultSet);

        resultSet.setExpectedCloseCalls(1);
        statement.close();
        connection.close();

        replay(dataSource, connection, statement);
        ExerciseDAO exerciseDAO = new RealExerciseDAO(dataSource);
        Exercise exercise = exerciseDAO.loadExerciseById(1);
        Exercise expectedExercise = new Exercise("Test title", "Test description");
        expectedExercise.setId(1);
        assertEquals(expectedExercise.toString(), exercise.toString());
        verify(dataSource, connection, statement);
        resultSet.verify();
    }

    @Test
    public void testLoadAllExercises() throws Exception {
        String sql = "SELECT * FROM exercise;";
        expect(connection.prepareStatement(sql)).andReturn(statement);

        MockMultiRowResultSet resultSet = new MockMultiRowResultSet();
        resultSet.setupColumnNames(columns);
        List<Exercise> expectedExercises = createManyExercises();
        resultSet.setupRows(exerciselistTo2dArray(expectedExercises));
        expect(statement.executeQuery()).andReturn(resultSet);

        resultSet.setExpectedCloseCalls(1);
        statement.close();
        connection.close();

        replay(dataSource, connection, statement);

        ExerciseDAO exerciseDAO = new RealExerciseDAO(dataSource);
        List<Exercise> result = exerciseDAO.loadAllExercises();
        assertEquals(expectedExercises.toString(), result.toString());
        verify(dataSource, connection, statement);
        resultSet.verify();
    }

    private List<Exercise> createManyExercises() {
        List<Exercise> expectedExercises = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            Exercise exercise = new Exercise("Test exercise title " + i,
              "Test exercise description " + i);
            exercise.setId(i);
            expectedExercises.add(exercise);
        }
        return expectedExercises;
    }

    private Object[][] exerciselistTo2dArray(List<Exercise> exercises) {
        Object[][] array = new Object[(exercises.size())][3];
        for (int i = 0; i < array.length; i++) {
            Exercise exercise = exercises.get(i);
            array[i] = new Object[] {exercise.getId(), exercise.getTitle(), exercise.getDescription()};
        }
        return array;
    }
}