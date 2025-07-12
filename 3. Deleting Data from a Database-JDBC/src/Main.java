import java.sql.Statement;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws ClassNotFoundException {

        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "Dua@2002";
        String query = "DELETE FROM employees where id = 6;";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver load successfully.....");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try{
            Connection con = DriverManager.getConnection(url,username,password);
            System.out.println();
            System.out.println(" <------ Deleting Data from a Database ------>");
            Statement stmt = con.createStatement();
            int rowsAffected = stmt.executeUpdate(query);

            if(rowsAffected>0){
                System.out.println("Deletion Successfully "+rowsAffected+" row(s) affected");
            }else {
                System.out.println("Deletion Failed !!");
            }

            con.close();
            stmt.close();
            System.out.println();
            System.out.println("Connection Closed Successfully....");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}