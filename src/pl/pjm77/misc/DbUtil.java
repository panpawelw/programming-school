package pl.pjm77.misc;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbUtil {


    /**
     * Prepares a data source.
     *
     * @return Datasource object
     */
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

    /**
     * Prepares a statement.
     *
     * @param con - Database connection
     * @param sqlQuery - SQL query
     * @param id  - optional id parameter
     * @return - PreparedStatement object
     * @throws SQLException - in case of database problems
     */
    public static PreparedStatement createStatement(Connection con, String sqlQuery, int... id)
            throws SQLException {
        PreparedStatement ps = con.prepareStatement(sqlQuery);
        if (id.length > 0) {
            ps.setInt(1, id[0]);
        }
        return ps;
    }
}