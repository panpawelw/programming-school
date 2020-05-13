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

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

public class RealUserDAOTest {

    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement statement;
    private UserDAO userDAO;
    String[] columns = new String[]{"id", "username", "email", "password", "usergroup_id"};

    @Before
    public void setup() throws Exception {
        dataSource = createMock(DataSource.class);
        connection = createMock(Connection.class);
        expect(dataSource.getConnection()).andReturn(connection);
        statement = createMock(PreparedStatement.class);
        userDAO = new RealUserDAO(dataSource);
    }

    @Test
    public void testCreateNewUser() throws Exception {
        final long EXPECTED_ID = 3L;
        int rowCount = 0;
        String sqlQuery = "INSERT INTO user(username, email, password, usergroup_id) " +
          "VALUES (?, ?, ?, ?);";
        User user = new User("Test name", "Test email", "Test password", 3);
        expect(connection.prepareStatement(sqlQuery, new String[]{"ID"})).andReturn(statement);

        statement.setString(2, user.getName());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getPassword());
        statement.setInt(5, user.getGroup_id());
        expect(statement.executeUpdate()).andReturn(rowCount);

        MockSingleRowResultSet resultSet = new MockSingleRowResultSet();
        resultSet.addExpectedIndexedValues(new Object[]{EXPECTED_ID});
        expect(statement.getGeneratedKeys()).andReturn(resultSet);

        resultSet.setExpectedCloseCalls(1);
        statement.close();
        connection.close();

        replay(dataSource, connection, statement);
        userDAO.saveUserToDB(user);
        assertEquals(user.getId(), EXPECTED_ID);
        resultSet.verify();
    }

    @Test
    public void testUpdateExistingUser() throws Exception {

    }

    @Test
    public void testLoadUserById() throws Exception {
        String sqlQuery = "SELECT * FROM user WHERE id=?;";
        expect(connection.prepareStatement(sqlQuery)).andReturn(statement);
        statement.setLong(1, 1);

        MockSingleRowResultSet resultSet = new MockSingleRowResultSet();
        resultSet.addExpectedNamedValues(columns,
          new Object[]{1L, "Test name", "Test email", "Test password", 1});
        expect(statement.executeQuery()).andReturn(resultSet);

        resultSet.setExpectedCloseCalls(1);
        statement.close();
        connection.close();

        replay(dataSource, connection, statement);

        User user = userDAO.loadUserById(1);
        User expectedUser =
          new User("Test name", "Test email", "Test password", 1);
        expectedUser.setId(1L);
        assertEquals(expectedUser.toString(), user.toString());
        verify(dataSource, connection, statement);
        resultSet.verify();
    }

    @Test
    public void testLoadAllUsers() throws Exception {
        String sql = "SELECT * FROM user;";
        expect(connection.prepareStatement(sql)).andReturn(statement);

        MockMultiRowResultSet resultSet = new MockMultiRowResultSet();
        resultSet.setupColumnNames(columns);
        List<User> expectedUsers = createManyUsers();
        resultSet.setupRows(userlistTo2dArray(expectedUsers));
        expect(statement.executeQuery()).andReturn(resultSet);

        resultSet.setExpectedCloseCalls(1);
        statement.close();
        connection.close();

        replay(dataSource, connection, statement);

        List<User> result = userDAO.loadAllUsers();
        assertEquals(expectedUsers.toString(), result.toString());
        verify(dataSource, connection, statement);
        resultSet.verify();
    }

    @Test
    public void testLoadAllUsersByGroupId() throws Exception {
        String sql = "SELECT * FROM user WHERE usergroup_id=?;";
        expect(connection.prepareStatement(sql)).andReturn(statement);
        statement.setInt(1, 3);

        MockMultiRowResultSet resultSet = new MockMultiRowResultSet();
        resultSet.setupColumnNames(columns);
        List<User> expectedUsers = createManyUsersWithSameGroupId(3);
        resultSet.setupRows(userlistTo2dArray(expectedUsers));
        expect(statement.executeQuery()).andReturn(resultSet);

        resultSet.setExpectedCloseCalls(1);
        statement.close();
        connection.close();

        replay(dataSource, connection, statement);

        List<User> result = userDAO.loadAllUsersByGroupId(3);
        assertEquals(expectedUsers.toString(), result.toString());
        verify(dataSource, connection, statement);
        resultSet.verify();
    }

    private List<User> createManyUsers() {
        List<User> expectedUsers = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            User user = new User("Test user " + i,
              "Test email " + i, "Test password " + i, i);
            user.setId(i);
            expectedUsers.add(user);
        }
        return expectedUsers;
    }

    private List<User> createManyUsersWithSameGroupId(int groupId) {
        List<User> expectedUsers = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            User user = new User("Test user " + i,
              "Test email " + i, "Test password " + i, groupId);
            user.setId(i);
            expectedUsers.add(user);
        }
        return expectedUsers;
    }

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