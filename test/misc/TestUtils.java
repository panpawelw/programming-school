package misc;

import com.mockobjects.sql.MockMultiRowResultSet;
import com.mockobjects.sql.MockSingleRowResultSet;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

public class TestUtils {

    public static void closeAllAndReplay(DataSource ds, Connection c, Statement s) throws Exception {
        s.close();
        c.close();
        replay(ds, c, s);
    }

    public static MockMultiRowResultSet prepareMultiRowResultSetMock
      (Object[][] objectArray, String[] columns, PreparedStatement statement) throws Exception {

        MockMultiRowResultSet resultSet = new MockMultiRowResultSet();
        resultSet.setupColumnNames(columns);
        resultSet.setupRows(objectArray);
        expect(statement.executeQuery()).andReturn(resultSet);
        resultSet.setExpectedCloseCalls(1);
        return resultSet;
    }

    public static MockSingleRowResultSet prepareSingleRowResultSetMock() {
        MockSingleRowResultSet resultSet = new MockSingleRowResultSet();
        resultSet.setExpectedCloseCalls(1);
        return resultSet;
    }
}
