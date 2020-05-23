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
import java.util.Date;
import java.util.List;

import static java.sql.Timestamp.*;
import static misc.TestUtils.*;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class RealSolutionDAOTests {

    private DataSource ds;
    private Connection con;
    private PreparedStatement stmt;
    private PreparedStatement niceStmt;
    private SolutionDAO solutionDAO;
    String[] columns = new String[]{"id", "created", "updated", "description", "exercise_id", "user_id"};

    @Before
    public void setup() throws Exception {
        ds = createMock(DataSource.class);
        con = createMock(Connection.class);
        expect(ds.getConnection()).andReturn(con);
        stmt = createMock(PreparedStatement.class);
        niceStmt = createNiceMock(PreparedStatement.class);
        solutionDAO = new RealSolutionDAO(ds);
    }

    @Test
    public void testCreateNewSolution() throws Exception {
        final long EXPECTED_ID = 3L;
        int rowCount = 0;
        String sqlQuery = "INSERT INTO solution" +
          "(created, updated, description, exercise_id, user_id) VALUES (?, ?, ?, ?, ?);";
        Solution solution = new Solution(new java.sql.Timestamp(new Date().getTime()),
          null, "test description", 1, 1L);
        expect(con.prepareStatement(sqlQuery, new String[]{"ID"})).andReturn(niceStmt);

        niceStmt.setTimestamp(1, solution.getCreated());
        niceStmt.setTimestamp(2, solution.getUpdated());
        niceStmt.setString(3, solution.getDescription());
        niceStmt.setInt(4, solution.getExercise_id());
        niceStmt.setLong(5, solution.getUser_id());
        expect(niceStmt.executeUpdate()).andReturn(rowCount);

        MockSingleRowResultSet rs = prepareSingleRowResultSetMock();
        rs.addExpectedIndexedValues(new Object[]{EXPECTED_ID});
        expect(niceStmt.getGeneratedKeys()).andReturn(rs);

        closeAllAndReplay(ds, con, niceStmt);

        solutionDAO.saveSolutionToDB(solution);
        assertEquals(EXPECTED_ID, solution.getId());
        verify(ds, con);
        verifyUnexpectedCalls(niceStmt);
        rs.verify();
    }

    @Test
    public void testUpdateExistingSolution() throws Exception {
        int rowCount = 1;
        String sqlQuery = "UPDATE solution SET " +
          "updated=Now(), description=?, exercise_id=?, user_id=? WHERE id = ?;";
        Solution solution = new Solution(null, new java.sql.Timestamp(new Date().getTime()),
          "Test description", 1, 3);
        solution.setId(1);
        expect(con.prepareStatement(sqlQuery)).andReturn(niceStmt);

        niceStmt.setString(1, solution.getDescription());
        niceStmt.setInt(2, solution.getExercise_id());
        niceStmt.setLong(3, solution.getUser_id());
        niceStmt.setLong(3, solution.getId());
        expect(niceStmt.executeUpdate()).andReturn(rowCount);

        closeAllAndReplay(ds, con, niceStmt);

        assertEquals(rowCount, solutionDAO.saveSolutionToDB(solution));
        verify(ds, con);
        verifyUnexpectedCalls(niceStmt);
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
          valueOf("2020-04-20 23:25:23.0"), "test description", 1, 1L);
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
        stmt.setLong(1, 2);

        List<Solution> expected = createMultipleSolutions(2);
        MockMultiRowResultSet rs =
          prepareMultiRowResultSetMock(solutionlistTo2dArray(expected), columns, stmt);

        closeAllAndReplay(ds, con, stmt);

        List<Solution> result = solutionDAO.loadAllSolutionsByUserId(2);
        assertAndVerify(expected, result, ds, con, stmt, rs);
    }

    /**
     * Creates a list of test solutions, optionally with identical user ID
     *
     * @param args - optional user ID (long)
     * @return - list of solutions
     */
    private List<Solution> createMultipleSolutions(long... args) {
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