import com.mockobjects.sql.MockResultSetMetaData;
import com.mockobjects.sql.MockSingleRowResultSet;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

import javax.sql.*;
import java.sql.*;

import pl.pjm77.DAO.RealUserGroupDAO;
import pl.pjm77.DAO.UserGroupDAO;
import pl.pjm77.model.UserGroup;

public class RealUserGroupDAOTests {

    @Test
    public void testLoadUserById() throws Exception {

        DataSource dataSource = createMock(DataSource.class);
        Connection connection = createMock(Connection.class);
        expect(dataSource.getConnection()).andReturn(connection);
        String sqlQuery = "SELECT * FROM usergroup WHERE id=?;";
        PreparedStatement statement = createMock(PreparedStatement.class);
        expect(connection.prepareStatement(sqlQuery)).andReturn(statement);
        statement.setInt(1, 1);

        MockSingleRowResultSet resultSet = new MockSingleRowResultSet();
        String[] columnsLowercase =
                new String[] {"id", "name"};
        String[] columnsUppercase = new String[] {"ID",
                "NAME"};
        String[] columnClassesNames = new String[] {
                int.class.getName(), String.class.getName()};

        MockResultSetMetaData resultSetMetaData = new MockResultSetMetaData();
        resultSetMetaData.setupAddColumnNames(columnsUppercase);
        resultSetMetaData.setupAddColumnClassNames(
                columnClassesNames);
        resultSetMetaData.setupGetColumnCount(2);
        resultSet.setupMetaData(resultSetMetaData);

        resultSet.addExpectedNamedValues(columnsLowercase,
                new Object[] {1, "Java"});
        expect(statement.executeQuery()).andReturn(resultSet);

        resultSet.setExpectedCloseCalls(1);
        statement.close();
        connection.close();

        replay(dataSource, connection, statement);
        UserGroupDAO userGroupDAO = new RealUserGroupDAO(dataSource);
        UserGroup userGroup = userGroupDAO.loadUserGroupById(1);
        assertEquals(new UserGroup(1, "Java").toString(), userGroup.toString());
        verify(dataSource, connection, statement);
        resultSet.verify();
    }
}