import com.mockobjects.sql.MockMultiRowResultSet;
import com.mockobjects.sql.MockSingleRowResultSet;
import org.junit.Before;
import org.junit.Test;
import pl.pjm77.DAO.RealSolutionDAO;
import pl.pjm77.DAO.SolutionDAO;
import pl.pjm77.model.Solution;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Timestamp.*;
import static misc.TestUtils.*;
import static org.easymock.EasyMock.*;

public class RealSolutionDAOTests {

    private DataSource ds;
    private Connection con;
    private PreparedStatement stmt;
    private SolutionDAO solutionDAO;
    String[] columns = new String[]{"id", "created", "updated", "description", "exercise_id", "user_id"};

    @Before
    public void setup() throws Exception {
        ds = createMock(DataSource.class);
        con = createMock(Connection.class);
        expect(ds.getConnection()).andReturn(con);
        stmt = createMock(PreparedStatement.class);
        solutionDAO = new RealSolutionDAO(ds);
    }

    @Test
    public void testLoadSolutionById() throws Exception {
        String sqlQuery = "SELECT * FROM solution WHERE id=?;";
        expect(con.prepareStatement(sqlQuery)).andReturn(stmt);
        stmt.setLong(1, 1);

        MockSingleRowResultSet rs = prepareSingleRowResultSetMock();
        rs.addExpectedNamedValues(columns, new Object[]{1L, valueOf("2020-04-20 23:24:10.0"),
          valueOf("2020-04-20 23:25:23.0"), "test description", 1, 1L});
        expect(stmt.executeQuery()).andReturn(rs);

        closeAllAndReplay(ds, con, stmt);

        Solution result = solutionDAO.loadSolutionById(1);
        Solution expected = new Solution(valueOf("2020-04-20 23:24:10.0"),
          valueOf("2020-04-20 23:25:23.0"), "test description",1, 1L);
        expected.setId(1L);
        assertAndVerify(expected, result, ds, con, stmt, rs);
    }

    @Test
    public void testLoadAllSolutions() throws Exception {
        String sql = "SELECT * FROM solution;";
        expect(con.prepareStatement(sql)).andReturn(stmt);

        List<Solution> expected = createMultipleSolutions();
        MockMultiRowResultSet rs =
          prepareMultiRowResultSetMock(solutionlistTo2dArray(expected), columns, stmt);

        closeAllAndReplay(ds, con, stmt);

        List<Solution> result = solutionDAO.loadAllSolutions();
        assertAndVerify(expected, result, ds, con, stmt, rs);
    }

    @Test
    public void testLoadAllSolutionsByUserId() throws Exception {
        String sql = "SELECT * FROM solution WHERE user_id=?;";
        expect(con.prepareStatement(sql)).andReturn(stmt);
        stmt.setLong(1,2);

        List<Solution> expected = createMultipleSolutions(2);
        MockMultiRowResultSet rs =
          prepareMultiRowResultSetMock(solutionlistTo2dArray(expected), columns, stmt);

        closeAllAndReplay(ds, con, stmt);

        List<Solution> result = solutionDAO.loadAllSolutionsByUserId(2);
        assertAndVerify(expected, result, ds, con, stmt, rs);
    }

    /**
     * Creates a list of test solutions, optionally with identical user ID
     * @param args - optional user ID (long)
     * @return - list of solutions
     */
    private List<Solution> createMultipleSolutions(long...args) {
        List<Solution> expectedSolutions = new ArrayList<>();
        long user_id = 1;
        if (args.length != 0) user_id = args[0];
        for (int i = 1; i < 6; i++) {
            if(args.length == 0) user_id = i;
            Solution solution = new Solution(valueOf("2020-04-20 23:24:15." + i),
              valueOf("2020-04-20 23:25:23.0" + i), "Test description " + i, i, user_id);
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