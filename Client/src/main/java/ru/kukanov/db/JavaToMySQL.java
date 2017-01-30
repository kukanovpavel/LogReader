package ru.kukanov.db;



import javax.naming.Context;
import javax.naming.InitialContext;

import javax.sql.DataSource;
import java.sql.*;


/**
 * Created by Pkukanov on 16.12.2016.
 */
public class JavaToMySQL {

    public static void insertRecordIntoTable(String userName, String event) throws SQLException {

        String insertTableSQL =  "INSERT INTO logging"
                + "(user, event) VALUES"
                + "(?,?)";

        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;


        try {
            dbConnection = getDBConnection();
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);

            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, event);

            preparedStatement.executeUpdate();


        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {


            try { dbConnection.close(); } catch(SQLException se) { /*can't do anything */ }
            try { preparedStatement.close(); } catch(SQLException se) { /*can't do anything */ }
        }

    }


    private static Connection getDBConnection() {



        Connection jndiConnection = null;

        try {
            Context context = new InitialContext();
            DataSource dataSource = (javax.sql.DataSource) context.lookup("jdbc/mydb");
            jndiConnection = dataSource.getConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jndiConnection;
    }


}
