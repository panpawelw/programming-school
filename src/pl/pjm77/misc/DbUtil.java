package pl.pjm77.misc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DbUtil {

    private DataSource ds;

    public static Connection getConn() throws SQLException {
        // get local JDBC database connection
        return new DbUtil().getLocalConnection();

        // or get AWS database connection
//        return new DbUtil.getRDSConnection();
    }


    private Connection getLocalConnection() throws SQLException {
        if (ds == null) {
            try {
                Context ctx = new InitialContext();
                ds = (DataSource) ctx.lookup
                        ("java:comp/env/jdbc/programming_school");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        return ds.getConnection();
    }

    private Connection getRDSConnection() throws SQLException {

        // Read RDS connection information from the environment
        String dbName = System.getProperty("RDS_DB_NAME");
        String userName = System.getProperty("RDS_USERNAME");
        String password = System.getProperty("RDS_PASSWORD");
        String hostname = System.getProperty("RDS_HOSTNAME");
        String port = System.getProperty("RDS_PORT");
        String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/"
                + dbName + "?user=" + userName + "&password=" + password;

        // Load the JDBC driver
        try {
            System.out.println("Loading driver...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException
                    ("Cannot find the driver in the classpath!", e);
        }

        // Get connection
        return DriverManager.getConnection(jdbcUrl);
    }

    /**
     * Prepares a statement.
     *
     * @param con - Database connection
     * @param sql - SQL query
     * @param id - optional id parameter
     * @return - PreparedStatement object
     * @throws SQLException - in case of database problems
     */
    public static PreparedStatement createStatement(Connection con, String sql, int... id)
            throws SQLException {
        PreparedStatement ps = con.prepareStatement(sql);
        if (id.length > 0) {
            ps.setInt(1, id[0]);
        }
        return ps;
    }
}