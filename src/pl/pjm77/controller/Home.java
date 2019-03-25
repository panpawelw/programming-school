package pl.pjm77.controller;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.pjm77.DAO.LastSolutionDAO;
import pl.pjm77.misc.DbUtil;
import pl.pjm77.model.LastSolution;

@WebServlet(value = "/")
public class Home extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Home() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String contextParam = getServletContext().getInitParameter("number-solutions");
		int recentSolutions=5;
		if(contextParam!=null & !contextParam.equals("")) {
			try {
				recentSolutions = Integer.parseInt(contextParam);
			}catch (NumberFormatException n) {
				System.out.println("Parameter must be an integer value, using default value of 5 instead!");
			}
		}
		LastSolution[] lastSolutions = LastSolutionDAO.loadAllSolutions(recentSolutions);
		request.setAttribute("lastsolutions", lastSolutions);
        getServletContext().getRequestDispatcher("/jsp/index.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

//    private void setUpDatabase() {
//        Connection conn = null;
//        Statement stmnt = null;
//        String results = "";
//        String results1 = "";
//        try {
        // Create connection to RDS DB instance
//            conn = DbUtil.getConn();
//            DatabaseMetaData dbmd = conn.getMetaData();
//            String[] types = {"TABLE"};
//            ResultSet rs = dbmd.getTables(null, null, "%", types);
//            while (rs.next()) {
//                results += (rs.getString("TABLE_NAME")) + " ";
//            }
//            stmnt = conn.createStatement();
//            stmnt.addBatch("DROP TABLE Usergroup");
//            stmnt.addBatch("DROP TABLE User");
//            stmnt.addBatch("DROP TABLE Exercise");
//            stmnt.addBatch("DROP TABLE Solution");
//            stmnt.addBatch("CREATE TABLE usergroup (id INT AUTO_INCREMENT, name VARCHAR(255), PRIMARY KEY (id));");
//            stmnt.addBatch("CREATE TABLE user (id BIGINT AUTO_INCREMENT NOT NULL, username VARCHAR(255) NOT NULL, email VARCHAR(255) UNIQUE NOT NULL, password VARCHAR(245) NOT NULL, usergroup_id INT, PRIMARY KEY(id), FOREIGN KEY(usergroup_id) REFERENCES Usergroup(id) ON DELETE CASCADE);");
//            stmnt.addBatch("CREATE TABLE exercise (id INT AUTO_INCREMENT, title VARCHAR (255), description TEXT, PRIMARY KEY(id));");
//            stmnt.addBatch("CREATE TABLE solution (id INT AUTO_INCREMENT, created DATETIME, updated DATETIME, description TEXT, exercise_id INT, user_id BIGINT, PRIMARY KEY (id), FOREIGN KEY(exercise_id) REFERENCES Exercise(id) ON DELETE CASCADE, FOREIGN KEY(user_id) REFERENCES User(id) ON DELETE CASCADE);");
//            stmnt.executeBatch();
//            stmnt.close();
//            ResultSet rs1 = stmnt.executeQuery("SELECT * FROM Solution");
//            ResultSetMetaData rsmd = rs1.getMetaData();
//            results += "No. of columns : " + rsmd.getColumnCount() + " ";
//            results += "Column name of 1st column : " + rsmd.getColumnName(1) + " ";
//            results += "Column type of 1st column : " + rsmd.getColumnTypeName(1);
//            dbmd = conn.getMetaData();
//            rs = dbmd.getTables(null, null, "%", types);
//            while (rs.next()) {
//                results1 += (rs.getString("TABLE_NAME")) + " ";
//            }
//			stmnt = conn.createStatement();
//			resultSet = stmnt.executeQuery("SHOW DATABASES;");
//			if(stmnt.execute("SHOW TABLES;")){
//				resultSet = stmnt.getResultSet();
//			}
//
//			while (resultSet.next()){
//				System.out.println(resultSet.getString("Table"));
//				results += resultSet.getString("Table") + " ";
//			}
//
//			stmnt.executeUpdate("CREATE TABLE Beanstalk (Resource char(50));");
//
//			if(stmnt.execute("SHOW TABLES;")){
//				resultSet = stmnt.getResultSet();
//			}
//
//			while (resultSet.next()){
//				System.out.println(resultSet.getString("Table"));
//				results1 += resultSet.getString("Table") + " ";
//			}
//			setupStatement.addBatch(showDatabases);
//			setupStatement.executeBatch();
//			stmnt.close();
//
//        } catch (SQLException ex) {
//            // Handle any errors
//            System.out.println("SQLException: " + ex.getMessage());
//            System.out.println("SQLState: " + ex.getSQLState());
//            System.out.println("VendorError: " + ex.getErrorCode());
//        } finally {
//            System.out.println("Closing the connection.");
//            if (conn != null) try {
//                conn.close();
//            } catch (SQLException ignore) {
//            }
//        }
//        request.setAttribute("results", results);
//        request.setAttribute("results1", results1);
//    }
}