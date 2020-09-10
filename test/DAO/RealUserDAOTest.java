package DAO;

import com.mockobjects.sql.MockMultiRowResultSet;
import com.mockobjects.sql.MockSingleRowResultSet;
import org.junit.Before;
import org.junit.Test;
import com.panpawelw.DAO.RealUserDAO;
import com.panpawelw.DAO.UserDAO;
import com.panpawelw.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

        stmt.setString(1, user.getName());
        stmt.setString(2, user.getEmail());
        stmt.setString(3, user.getPassword());
        stmt.setInt(4, user.getGroup_id());
        expect(stmt.executeUpdate()).andReturn(rowCount);

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
        assertEquals(rowCount, userDAO.saveUserToDB(user));
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
    public void testDeleteUser() throws Exception {
        String sqlQuery = "DELETE FROM user WHERE id=?";
        int rowCount = 1;
        User user = new User("Test name", "Test email", "Test password", 3);
        user.setId(4);
        expect(con.prepareStatement(sqlQuery)).andReturn(stmt);
        stmt.setLong(1, 4);
        expect(stmt.executeUpdate()).andReturn(rowCount);

        closeAllAndReplay(ds, con, stmt);
        assertEquals(rowCount, userDAO.deleteUser(user));
        verify(ds, con, stmt);
    }

    @Test
    public void testLoadAllUsers() throws Exception {
        String sqlQuery = "SELECT * FROM user;";
        expect(con.prepareStatement(sqlQuery)).andReturn(stmt);

        List<User> expected = createMultipleUsers();
        MockMultiRowResultSet rs =
          prepareMultiRowResultSetMock(userlistTo2dArray(expected), columns, stmt);

        closeAllAndReplay(ds, con, stmt);

        List<User> result = userDAO.loadAllUsers();
        assertAndVerify(expected, result, ds, con, stmt, rs);
    }

    @Test
    public void testLoadAllUsersByGroupId() throws Exception {
        String sqlQuery = "SELECT * FROM user WHERE usergroup_id=?;";
        stmt.setInt(1, 3);
        expect(con.prepareStatement(sqlQuery)).andReturn(stmt);


        List<User> expected = createMultipleUsers(3);
        MockMultiRowResultSet rs =
          prepareMultiRowResultSetMock(userlistTo2dArray(expected), columns, stmt);

        closeAllAndReplay(ds, con, stmt);

        List<User> result = userDAO.loadAllUsersByGroupId(3);
        assertAndVerify(expected, result, ds, con, stmt, rs);
    }

    /**
     * Converts a list of test users into a two dimensional array
     *
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