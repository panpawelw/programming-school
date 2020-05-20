import com.mockobjects.sql.MockMultiRowResultSet;
import com.mockobjects.sql.MockSingleRowResultSet;
import org.junit.Before;
import org.junit.Test;
import pl.pjm77.DAO.RealUserDAO;
import pl.pjm77.DAO.UserDAO;
import pl.pjm77.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import static misc.TestUtils.*;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class RealUserDAOTest {

    private DataSource ds;
    private Connection con;
    private PreparedStatement stmt;
    private UserDAO userDAO;
    String[] columns = new String[]{"id", "username", "email", "password", "usergroup_id"};

    @Before
    public void setup() throws Exception {
        ds = createMock(DataSource.class);
        con = createMock(Connection.class);
        expect(ds.getConnection()).andReturn(con);
        stmt = createMock(PreparedStatement.class);
        userDAO = new RealUserDAO(ds);
    }

    @Test
    public void testCreateNewUser() throws Exception {
        final long EXPECTED_ID = 3L;
        int rowCount = 1;
        String sqlQuery = "INSERT INTO user(username, email, password, usergroup_id) " +
          "VALUES (?, ?, ?, ?);";
        User user = new User("Test name", "Test email", "Test password", 3);
        expect(con.prepareStatement(sqlQuery, new String[]{"ID"})).andReturn(stmt);

        stmt.setString(2, user.getName());
        stmt.setString(3, user.getEmail());
        stmt.setString(4, user.getPassword());
        stmt.setInt(5, user.getGroup_id());
        expect(stmt.executeUpdate()).andReturn(rowCount);
        System.out.println((rowCount));

        MockSingleRowResultSet rs = prepareSingleRowResultSetMock();
        rs.addExpectedIndexedValues(new Object[]{EXPECTED_ID});
        expect(stmt.getGeneratedKeys()).andReturn(rs);

        closeAllAndReplay(ds, con, stmt);

        userDAO.saveUserToDB(user);
        assertAndVerify(EXPECTED_ID, user.getId(), ds, con, stmt, rs);
    }

    @Test
    public void testUpdateExistingUser() throws Exception {
        int rowCount = 1;
        String sqlQuery = "UPDATE user SET " +
          "username=?, email=?, password=?, usergroup_id=? WHERE id = ?;";
        User user = new User("Test name", "Test email", "Test password", 3);
        user.setId(1);
        expect(con.prepareStatement(sqlQuery)).andReturn(stmt);

        stmt.setString(1, user.getName());
        stmt.setString(2, user.getEmail());
        stmt.setString(3, user.getPassword());
        stmt.setInt(4, user.getGroup_id());
        stmt.setLong(5, user.getId());
        expect(stmt.executeUpdate()).andReturn(rowCount);

        closeAllAndReplay(ds, con, stmt);
        userDAO.saveUserToDB(user);
        assertEquals(rowCount, 1);
        verify(ds, con, stmt);
    }

    @Test
    public void testLoadUserById() throws Exception {
        String sqlQuery = "SELECT * FROM user WHERE id=?;";
        expect(con.prepareStatement(sqlQuery)).andReturn(stmt);
        stmt.setLong(1, 1);

        MockSingleRowResultSet rs = prepareSingleRowResultSetMock();
        rs.addExpectedNamedValues(columns,
          new Object[]{1L, "Test name", "Test email", "Test password", 1});
        expect(stmt.executeQuery()).andReturn(rs);

        closeAllAndReplay(ds, con, stmt);

        User result = userDAO.loadUserById(1);
        User expected =
          new User("Test name", "Test email", "Test password", 1);
        expected.setId(1L);
        assertAndVerify(expected, result, ds, con, stmt, rs);
    }

    @Test
    public void testLoadAllUsers() throws Exception {
        String sql = "SELECT * FROM user;";
        expect(con.prepareStatement(sql)).andReturn(stmt);

        List<User> expected = createMultipleUsers();
        MockMultiRowResultSet rs =
          prepareMultiRowResultSetMock(userlistTo2dArray(expected), columns, stmt);

        closeAllAndReplay(ds, con, stmt);

        List<User> result = userDAO.loadAllUsers();
        assertAndVerify(expected, result, ds, con, stmt, rs);
    }

    @Test
    public void testLoadAllUsersByGroupId() throws Exception {
        String sql = "SELECT * FROM user WHERE usergroup_id=?;";
        expect(con.prepareStatement(sql)).andReturn(stmt);
        stmt.setInt(1, 3);

        List<User> expected = createMultipleUsers(3);
        MockMultiRowResultSet rs =
          prepareMultiRowResultSetMock(userlistTo2dArray(expected), columns, stmt);

        closeAllAndReplay(ds, con, stmt);

        List<User> result = userDAO.loadAllUsersByGroupId(3);
        assertAndVerify(expected, result, ds, con, stmt, rs);
    }

    /**
     * Creates a list of test users, optionally with identical group ID
     * @param args - optional group ID (int)
     * @return - list of users
     */
    private List<User> createMultipleUsers(int...args) {
        List<User> expectedUsers = new ArrayList<>();
        int group_id = 1;
        if (args.length != 0) group_id = args[0];
        for (int i = 1; i < 6; i++) {
            if(args.length == 0) group_id = i;
            User user = new User("Test user " + i,
              "Test email " + i, "Test password " + i, group_id);
            user.setId(i);
            expectedUsers.add(user);
        }
        return expectedUsers;
    }

    /**
     * Converts a list of test users into a two dimensional array
     * @param users - list of users
     * @return - 2D array of users
     */
    private Object[][] userlistTo2dArray(List<User> users) {
        Object[][] array = new Object[(users.size())][5];
        for (int i = 0; i < array.length; i++) {
            User user = users.get(i);
            array[i] = new Object[]{user.getId(), user.getName(),
              user.getEmail(), user.getPassword(), user.getGroup_id()};
        }
        return array;
    }
}