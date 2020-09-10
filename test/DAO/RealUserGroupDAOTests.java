package DAO;

import com.mockobjects.sql.MockMultiRowResultSet;
import com.mockobjects.sql.MockSingleRowResultSet;
import org.junit.Before;
import org.junit.Test;

import static misc.TestUtils.*;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

import javax.sql.*;
import java.sql.*;
import java.util.List;

import com.panpawelw.DAO.RealUserGroupDAO;
import com.panpawelw.DAO.UserGroupDAO;
import com.panpawelw.model.UserGroup;

public class RealUserGroupDAOTests {

    private DataSource ds;
    private Connection con;
    private PreparedStatement stmt;
    private UserGroupDAO userGroupDAO;
    String[] columns = new String[]{"id", "name"};

    @Before
    public void setup() throws Exception {
        ds = createMock(DataSource.class);
        con = createMock(Connection.class);
        expect(ds.getConnection()).andReturn(con);
        stmt = createMock(PreparedStatement.class);
        userGroupDAO = new RealUserGroupDAO(ds);
    }

    @Test
    public void testCreateNewUserGroup() throws Exception {
        final int EXPECTED_ID = 3;
        int rowCount = 0;
        String sqlQuery = "INSERT INTO usergroup (name) VALUES (?);";
        UserGroup userGroup = new UserGroup("Test name");
        expect(con.prepareStatement(sqlQuery, new String[]{"ID"})).andReturn(stmt);

        stmt.setString(1, userGroup.getName());
        expect(stmt.executeUpdate()).andReturn(rowCount);

        MockSingleRowResultSet rs = prepareSingleRowResultSetMock();
        rs.addExpectedIndexedValues(new Object[]{EXPECTED_ID});
        expect(stmt.getGeneratedKeys()).andReturn(rs);

        closeAllAndReplay(ds, con, stmt);

        userGroupDAO.saveUserGroupToDB(userGroup);
        assertAndVerify(EXPECTED_ID, userGroup.getId(), ds, con, stmt, rs);
    }

    @Test
    public void testUpdateExistingUserGroup() throws Exception {
        int rowCount = 1;
        String sqlQuery = "UPDATE usergroup SET name=? WHERE id=?;";
        UserGroup userGroup = new UserGroup("Test name");
        userGroup.setId(1);
        expect(con.prepareStatement(sqlQuery)).andReturn(stmt);

        stmt.setString(1, userGroup.getName());
        stmt.setInt(2, userGroup.getId());
        expect(stmt.executeUpdate()).andReturn(rowCount);

        closeAllAndReplay(ds, con, stmt);
        assertEquals(rowCount, userGroupDAO.saveUserGroupToDB(userGroup));
        verify(ds, con, stmt);
    }

    @Test
    public void testLoadUserGroupById() throws Exception {
        String sqlQuery = "SELECT * FROM usergroup WHERE id=?;";
        expect(con.prepareStatement(sqlQuery)).andReturn(stmt);
        stmt.setInt(1, 1);

        MockSingleRowResultSet rs = prepareSingleRowResultSetMock();
        rs.addExpectedNamedValues(columns,
          new Object[]{1, "Test name"});
        expect(stmt.executeQuery()).andReturn(rs);

        closeAllAndReplay(ds, con, stmt);

        UserGroup result = userGroupDAO.loadUserGroupById(1);
        UserGroup expected = new UserGroup("Test name");
        expected.setId(1);

        assertAndVerify(expected, result, ds, con, stmt, rs);
    }

    @Test
    public void testDeleteUserGroup() throws Exception {
        String sqlQuery = "DELETE FROM usergroup WHERE id=?;";
        int rowCount = 1;
        UserGroup userGroup = new UserGroup("Test name");
        userGroup.setId(2);
        expect(con.prepareStatement(sqlQuery)).andReturn(stmt);
        stmt.setInt(1, 2);
        expect(stmt.executeUpdate()).andReturn(rowCount);

        closeAllAndReplay(ds, con, stmt);
        assertEquals(rowCount, userGroupDAO.deleteUserGroup(userGroup));
        verify(ds, con, stmt);
    }

    @Test
    public void testLoadAllUserGroups() throws Exception {
        String sql = "SELECT * FROM usergroup;";
        expect(con.prepareStatement(sql)).andReturn(stmt);

        List<UserGroup> expected = createMultipleUserGroups();
        MockMultiRowResultSet rs =
          prepareMultiRowResultSetMock(userGroupListTo2dArray(expected), columns, stmt);

        closeAllAndReplay(ds, con, stmt);

        List<UserGroup> result = userGroupDAO.loadAllUserGroups();
        assertAndVerify(expected, result, ds, con, stmt, rs);
    }

    private Object[][] userGroupListTo2dArray(List<UserGroup> userGroups) {
        Object[][] array = new Object[(userGroups.size())][2];
        for (int i = 0; i < array.length; i++) {
            UserGroup userGroup = userGroups.get(i);
            array[i] = new Object[] {userGroup.getId(), userGroup.getName()};
        }
        return array;
    }
}