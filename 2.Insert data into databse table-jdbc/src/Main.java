import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
//import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws ClassNotFoundException{
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "Dua@2002";
        String query = "INSERT INTO employees(id,name,job_title,salary) VALUES (6,'riya','Beginner Developer',5000.0);";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver load Successfully...");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try {
            Connection con = DriverManager.getConnection(url,username,password);
            System.out.println("Connection Established Successfully....");
            System.out.println();
            System.out.println(" <------ Insert data into table database ------>");
            Statement stmt = con.createStatement();
            int rowAffected = stmt.executeUpdate(query);

            if (rowAffected>0){
                System.out.println("Insertion Successfully "+rowAffected +"row(s) affected.");
            }else {
                System.out.println("Insertion failed !!");
            }

            con.close();
            stmt.close();
            System.out.println();
            System.out.println("Connection Closed Successfully...");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}