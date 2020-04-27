package pl.pjm77.misc;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class RealDataSource {

    public static DataSource initDB() {
        DataSource dataSource = null;
        try {
            dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc" +
                    "/programming_school");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return dataSource;
    }
}
