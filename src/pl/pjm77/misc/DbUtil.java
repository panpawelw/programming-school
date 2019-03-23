package pl.pjm77.misc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DbUtil {
    private static DataSource ds;

    public static Connection getConn() throws SQLException {
//        return getInstance().getConnection();
        return getRDSConnection();
    }

    private static DataSource getInstance() {
        if (ds == null) {
            try {
                Context ctx = new InitialContext();
                ds = (DataSource) ctx.lookup("java:comp/env/jdbc/programming_school");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        return ds;
    }

    private static Connection getRDSConnection() throws SQLException {

        // Read RDS connection information from the environment
        String dbName = System.getProperty("RDS_DB_NAME");
        String userName = System.getProperty("RDS_USERNAME");
        String password = System.getProperty("RDS_PASSWORD");
        String hostname = System.getProperty("RDS_HOSTNAME");
        String port = System.getProperty("RDS_PORT");
        String jdbcUrl = "jdbc:mysql://" + hostname + ":" +
                port + "/" + dbName + "?user=" + userName + "&password=" + password;

        // Load the JDBC driver
        try {
            System.out.println("Loading driver...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot find the driver in the classpath!", e);
        }

        // Get connection
        Connection conn = null;
        conn = DriverManager.getConnection(jdbcUrl);
        return conn;
    }
}