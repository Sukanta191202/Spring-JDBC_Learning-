import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {

        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "Dua@2002";
        String query = "Select * from employees;";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver load successfully!!");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try{
            Connection connection = DriverManager.getConnection(url,username,password);
            System.out.println();
            System.out.println("<------ First JDBC Program: Connecting Java to MySQL ------>");
            System.out.println("Connection Established Successfully");
            System.out.println();
            System.out.println(" <------Show table data from database ------> ");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);  //executeQuery ---> database er table data show er jonno use kora hoi.
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String job_title = rs.getString("job_title");
                double salary = rs.getDouble("salary");
                System.out.println();
                System.out.println("=============================");
                System.out.println("Id : " +id);
                System.out.println("Name : "+name);
                System.out.println("Job Title : "+job_title);
                System.out.println("Salary : "+salary);
            }
            rs.close();
            stmt.close();
            connection.close();
            System.out.println();
            System.out.println("Connection Closed Successfully!!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}