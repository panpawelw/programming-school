import com.mockobjects.sql.MockMultiRowResultSet;
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

import static misc.TestUtils.*;
import static org.easymock.EasyMock.*;

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
     * Creates a list of test exercises
     * @return - list of exercises
     */
    private List<Exercise> createMultipleExercises() {
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