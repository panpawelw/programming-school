package com.panpawelw.misc;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DbUtils {


    private static final String LOCAL_DATABASE = "java:comp/env/jdbc/programming_school_local";
    private static final String AWS_DATABASE = "java:comp/env/jdbc/programming_school_AWS";
    private static DataSource dataSource = null;

    private DbUtils() {}

    /**
     * Prepares a data source.
     *
     * @return Datasource object
     */
    public static DataSource initDB() {
        if (dataSource == null) {
            try {
                dataSource = (DataSource) new InitialContext().lookup(LOCAL_DATABASE);
            } catch (NamingException e) {
                e.printStackTrace();
            }
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
     *             parameters of types of String, int, long or null
     * @return PreparedStatement
     * @throws SQLException - if called on closed connection or
     */
    public static PreparedStatement prepStatement(Connection con, String sql, Object... args)
      throws SQLException {
        int statementIndex = 1, argumentIndex = 0;
        PreparedStatement ps;

        // Set column names for getGeneratedKeys if present
        if (args.length != 0 && args[0].getClass().toString().equals("class [Ljava.lang.String;")) {
            ps = con.prepareStatement(sql, (String[]) args[0]);
            argumentIndex++;
        } else {
            ps = con.prepareStatement(sql);
        }

        // Set values for preparedStatement if present
        while (argumentIndex < args.length) {
            // Edge case when saveSolutionToDB has to set a null Timestamp as "updated" field
            if (args[argumentIndex] == null) {
                ps.setTimestamp(statementIndex, null);
            } else {
                String argClass = args[argumentIndex].getClass().toString();
                switch (argClass) {
                    case "class java.lang.Integer":
                        ps.setInt(statementIndex, (Integer) args[argumentIndex]);
                        break;
                    case "class java.lang.Long":
                        ps.setLong(statementIndex, (Long) args[argumentIndex]);
                        break;
                    case "class java.lang.String":
                        ps.setString(statementIndex, (String) args[argumentIndex]);
                        break;
                    case "class java.sql.Timestamp":
                        ps.setTimestamp(statementIndex, (Timestamp) args[argumentIndex]);
                }
            }
            statementIndex++;
            argumentIndex++;
        }
        return ps;
    }
}