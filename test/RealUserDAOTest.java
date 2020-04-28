import com.mockobjects.sql.MockResultSetMetaData;
import com.mockobjects.sql.MockSingleRowResultSet;
import org.junit.Test;
import pl.pjm77.DAO.RealUserDAO;
import pl.pjm77.DAO.UserDAO;
import pl.pjm77.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

public class RealUserDAOTest {

    @Test
    public void testLoadUserById() throws Exception {

        DataSource dataSource = createMock(DataSource.class);
        Connection connection = createMock(Connection.class);
        expect(dataSource.getConnection()).andReturn(connection);
        String sqlQuery = "SELECT * FROM user WHERE id=?;";
        PreparedStatement statement = createMock(PreparedStatement.class);
        expect(connection.prepareStatement(sqlQuery)).andReturn(statement);
        statement.setLong(1, 1);

        MockSingleRowResultSet resultSet = new MockSingleRowResultSet();
        String[] columnsLowercase =
                new String[] {"id", "username", "email", "password", "usergroup_id"};
        String[] columnsUppercase = new String[] {"ID",
                "USERNAME", "EMAIL", "PASSWORD", "USERGROUP_ID"};
        String[] columnClassesNames = new String[] {
                long.class.getName(), String.class.getName(), String.class.getName(),
                String.class.getName(), int.class.getName()};

        MockResultSetMetaData resultSetMetaData = new MockResultSetMetaData();
        resultSetMetaData.setupAddColumnNames(columnsUppercase);
        resultSetMetaData.setupAddColumnClassNames(
                columnClassesNames);
        resultSetMetaData.setupGetColumnCount(2);
        resultSet.setupMetaData(resultSetMetaData);

        resultSet.addExpectedNamedValues(columnsLowercase,
                new Object[] {1L, "Test name", "Test email", "Test password", 1});
        expect(statement.executeQuery()).andReturn(resultSet);

        resultSet.setExpectedCloseCalls(1);
        statement.close();
        connection.close();

        replay(dataSource, connection, statement);
        UserDAO userDAO = new RealUserDAO(dataSource);
        User user = userDAO.loadUserById(1);
        User expectedUser =
                new User("Test name", "Test email", "Test password", 1);
        expectedUser.setId(1L);
        assertEquals(expectedUser.toString(), user.toString());
        verify(dataSource, connection, statement);
        resultSet.verify();
    }
}