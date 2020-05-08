import com.mockobjects.sql.MockMultiRowResultSet;
import com.mockobjects.sql.MockResultSetMetaData;
import com.mockobjects.sql.MockSingleRowResultSet;
import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

import javax.sql.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import pl.pjm77.DAO.RealUserGroupDAO;
import pl.pjm77.DAO.UserGroupDAO;
import pl.pjm77.model.UserGroup;

public class RealUserGroupDAOTests {

    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement statement;

    @Before
    public void setup() throws Exception {
        dataSource = createMock(DataSource.class);
        connection = createMock(Connection.class);
        expect(dataSource.getConnection()).andReturn(connection);
        statement = createMock(PreparedStatement.class);
    }

    @Test
    public void testLoadUserGroupById() throws Exception {

        String sqlQuery = "SELECT * FROM usergroup WHERE id=?;";
        expect(connection.prepareStatement(sqlQuery)).andReturn(statement);
        statement.setInt(1, 1);

        MockSingleRowResultSet resultSet = new MockSingleRowResultSet();
        String[] columnsLowercase = new String[]{"id", "name"};
        String[] columnsUppercase = new String[]{"ID", "NAME"};
        String[] columnClassesNames = new String[]{int.class.getName(), String.class.getName()};
        MockResultSetMetaData resultSetMetaData = new MockResultSetMetaData();
        resultSetMetaData.setupAddColumnNames(columnsUppercase);
        resultSetMetaData.setupAddColumnClassNames(columnClassesNames);
        resultSetMetaData.setupGetColumnCount(2);
        resultSet.setupMetaData(resultSetMetaData);
        resultSet.addExpectedNamedValues(columnsLowercase, new Object[]{1, "Test name"});
        expect(statement.executeQuery()).andReturn(resultSet);

        resultSet.setExpectedCloseCalls(1);
        statement.close();
        connection.close();

        replay(dataSource, connection, statement);

        UserGroupDAO userGroupDAO = new RealUserGroupDAO(dataSource);
        UserGroup result = userGroupDAO.loadUserGroupById(1);
        UserGroup expectedUserGroup = new UserGroup("Test name");
        expectedUserGroup.setId(1);

        assertEquals(expectedUserGroup.toString(), result.toString());
        verify(dataSource, connection, statement);
        resultSet.verify();
    }

    @Test
    public void testLoadAllUserGroups() throws Exception {
        String sql = "SELECT * FROM usergroup;";
        expect(connection.prepareStatement(sql)).andReturn(statement);

        MockMultiRowResultSet resultSet = new MockMultiRowResultSet();
        String[] columns = new String[]{"id", "name"};
        resultSet.setupColumnNames(columns);
        List<UserGroup> expectedUserGroups = createAllUserGroups();
        resultSet.setupRows(userGrouplistTo2dArray(expectedUserGroups));
        expect(statement.executeQuery()).andReturn(resultSet);

        resultSet.setExpectedCloseCalls(1);
        statement.close();
        connection.close();

        replay(dataSource, connection, statement);

        UserGroupDAO userGroupDAO = new RealUserGroupDAO(dataSource);
        List<UserGroup> result = userGroupDAO.loadAllUserGroups();
        assertEquals(expectedUserGroups.toString(), result.toString());
        verify(dataSource, connection, statement);
        resultSet.verify();
    }

    private List<UserGroup> createAllUserGroups() {
        List<UserGroup> expectedUserGroups = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            UserGroup userGroup = new UserGroup("Test user group " + i);
            userGroup.setId(i);
            expectedUserGroups.add(userGroup);
        }
        return expectedUserGroups;
    }

    private Object[][] userGrouplistTo2dArray(List<UserGroup> userGroups) {
        Object[][] array = new Object[(userGroups.size())][2];
        for (int i = 0; i < array.length; i++) {
            UserGroup userGroup = userGroups.get(i);
            array[i] = new Object[] {userGroup.getId(), userGroup.getName()};
        }
        return array;
    }
}