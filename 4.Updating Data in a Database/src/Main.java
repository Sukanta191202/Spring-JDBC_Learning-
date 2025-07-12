import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException{

        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "Dua@2002";
        String query = "UPDATE employees SET job_title = 'Backend Developer', salarY = '20000' WHERE id = 3;";

        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver Load Successfully....");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection con = DriverManager.getConnection(url,username,password);
            System.out.println();
            System.out.println(" <------ Updating Data in a Database ------> ");
            Statement stmt = con.createStatement();
            int rowsAffected = stmt.executeUpdate(query);

            if(rowsAffected>0){
                System.out.println("Updated successfully " +rowsAffected + " row(s) affected.");
            }else {
                System.out.println("Update Failed!!");
            }

            con.close();
            stmt.close();
            System.out.println();
            System.out.println("Connection closed successfully.......");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}