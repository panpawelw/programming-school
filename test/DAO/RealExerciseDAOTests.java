package DAO;

import com.mockobjects.sql.MockMultiRowResultSet;
import com.mockobjects.sql.MockSingleRowResultSet;
import org.junit.Before;
import org.junit.Test;
import com.panpawelw.DAO.ExerciseDAO;
import com.panpawelw.DAO.RealExerciseDAO;
import com.panpawelw.model.Exercise;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static misc.TestUtils.*;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class RealExerciseDAOTests {

    private DataSource ds;
    private Connection con;
    private PreparedStatement stmt;
    private ExerciseDAO exerciseDAO;
    String[] columns = new String[]{"id", "title", "description"};

    @Before
    public void setup() throws Exception {
        ds = createMock(DataSource.class);
        con = createMock(Connection.class);
        expect(ds.getConnection()).andReturn(con);
        stmt = createMock(PreparedStatement.class);
        exerciseDAO = new RealExerciseDAO(ds);
    }

    @Test
    public void testCreateNewExercise() throws Exception {
        final int EXPECTED_ID = 2;
        int rowCount = 0;
        String sqlQuery = "INSERT INTO exercise (title, description) VALUES (?, ?);";
        Exercise exercise = new Exercise("Test title", "Test description");
        expect(con.prepareStatement(sqlQuery, new String[]{"ID"})).andReturn(stmt);

        stmt.setString(1, exercise.getTitle());
        stmt.setString(2, exercise.getDescription());
        expect(stmt.executeUpdate()).andReturn(rowCount);

        MockSingleRowResultSet rs = prepareSingleRowResultSetMock();
        rs.addExpectedIndexedValues(new Object[]{EXPECTED_ID});
        expect(stmt.getGeneratedKeys()).andReturn(rs);

        closeAllAndReplay(ds, con, stmt);

        exerciseDAO.saveExerciseToDB(exercise);
        assertAndVerify(EXPECTED_ID, exercise.getId(), ds, con, stmt, rs);
    }

    @Test
    public void testUpdateExistingExercise() throws Exception {
        int rowCount = 1;
        String sqlQuery = "UPDATE exercise SET title=?, description=? WHERE id=?;";
        Exercise exercise = new Exercise("Test title", "Test description");
        exercise.setId(1);
        expect(con.prepareStatement(sqlQuery)).andReturn(stmt);

        stmt.setString(1, exercise.getTitle());
        stmt.setString(2, exercise.getDescription());
        stmt.setInt(3, exercise.getId());
        expect(stmt.executeUpdate()).andReturn(rowCount);

        closeAllAndReplay(ds, con, stmt);
        assertEquals(rowCount, exerciseDAO.saveExerciseToDB(exercise));
        verify(ds, con, stmt);
    }

    @Test
    public void testLoadExerciseById() throws Exception {

        String sqlQuery = "SELECT * FROM exercise WHERE id=?;";
        expect(con.prepareStatement(sqlQuery)).andReturn(stmt);
        stmt.setInt(1, 1);

        MockSingleRowResultSet rs = prepareSingleRowResultSetMock();
        rs.addExpectedNamedValues(columns, new Object[] {1, "Test title", "Test description"});
        expect(stmt.executeQuery()).andReturn(rs);

        closeAllAndReplay(ds, con, stmt);

        Exercise result = exerciseDAO.loadExerciseById(1);
        Exercise expected = new Exercise("Test title", "Test description");
        expected.setId(1);
        assertAndVerify(expected, result, ds, con, stmt, rs);

    }

    @Test
    public void testDeleteExercise() throws Exception {
        String sqlQuery = "DELETE FROM exercise WHERE id=?;";
        int rowCount = 1;
        Exercise exercise = new Exercise("Test name", "Test description");
        exercise.setId(3);
        expect(con.prepareStatement(sqlQuery)).andReturn(stmt);
        stmt.setInt(1, 3);
        expect(stmt.executeUpdate()).andReturn(rowCount);

        closeAllAndReplay(ds, con, stmt);
        assertEquals(rowCount, exerciseDAO.deleteExercise(exercise));
        verify(ds, con, stmt);
    }

    @Test
    public void testLoadAllExercises() throws Exception {
        String sql = "SELECT * FROM exercise;";
        expect(con.prepareStatement(sql)).andReturn(stmt);

        List<Exercise> expected = createMultipleExercises();
        MockMultiRowResultSet rs =
          prepareMultiRowResultSetMock(exerciselistTo2dArray(expected), columns, stmt);

        closeAllAndReplay(ds, con, stmt);

        List<Exercise> result = exerciseDAO.loadAllExercises();
        assertAndVerify(expected, result, ds, con, stmt, rs);
    }

    /**
     * Converts a list of test exercises into a two dimensional array
     * @param exercises - list of exercises
     * @return - 2D array of exercises
     */
    private Object[][] exerciselistTo2dArray(List<Exercise> exercises) {
        Object[][] array = new Object[(exercises.size())][3];
        for (int i = 0; i < array.length; i++) {
            Exercise exercise = exercises.get(i);
            array[i] = new Object[] {exercise.getId(), exercise.getTitle(), exercise.getDescription()};
        }
        return array;
    }
}