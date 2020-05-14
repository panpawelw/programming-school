package misc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

import static org.easymock.EasyMock.replay;

public class TestUtils {

    public static void closeAllAndReplay(DataSource ds, Connection c, Statement s) throws Exception {
        s.close();
        c.close();
        replay(ds, c, s);
    }
}
