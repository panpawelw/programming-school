package pl.pjm77.misc;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DbUtils {


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
     * Attempts to prepare a statement using connection, SQL query, optional String array with
     * column names and up to 5 further optional parameters.
     *
     * @param con  - Connection object
     * @param sql  - SQL query
     * @param args - if column names are included must be as first parameter and up to 5 optional
     *            parameters of types of String, int, long or null
     * @return PreparedStatement
     * @throws SQLException - if called on closed connection or
     */
    public static PreparedStatement prepStatement(Connection con, String sql, Object... args)
            throws SQLException {
        PreparedStatement ps = con.prepareStatement(sql);
        int paramIndex = 0;
        while (paramIndex < args.length) {
            // If a string array with column names is the first parameter
            if (args[0].getClass().toString().equals("class [Ljava.lang.String;")) {
                ps = con.prepareStatement(sql, (String[]) args[0]);
                paramIndex++;
            }
            // Edge case when saveSolutionToDB has to set a null Timestamp as "updated" field
            if (args[paramIndex] == null) {
                ps.setTimestamp(paramIndex + 1, null);
            } else {
                String argClass = args[paramIndex].getClass().toString();
                switch (argClass) {
                    case "class java.lang.Integer":
                        ps.setInt(paramIndex + 1, (Integer) args[paramIndex]); break;
                    case "class java.lang.Long":
                        ps.setLong(paramIndex + 1, (Long) args[paramIndex]); break;
                    case "class java.lang.String":
                        ps.setString(paramIndex + 1, (String) args[paramIndex]); break;
                    case "class java.sql.Timestamp":
                        ps.setTimestamp(paramIndex + 1, (Timestamp) args[paramIndex]);
                }
            }
            paramIndex++;
        }
        return ps;
    }
}