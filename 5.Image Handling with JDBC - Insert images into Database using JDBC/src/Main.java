import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws ClassNotFoundException {

        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String userName = "root";
        String password = "Dua@2002";
        String image_path = "C:\\Users\\Sukanta\\Desktop\\images (8).jpeg";
        String sqlQuery = "INSERT INTO image_table(image_data) VALUES (?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver load successfully...");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try {
            Connection connection = DriverManager.getConnection(url,userName,password);
            System.out.println("Connection Established successfully...");
            FileInputStream fileInputStream = new FileInputStream(image_path);
            byte[] imageData = new byte[fileInputStream.available()];
            fileInputStream.read(imageData);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setBytes(1,imageData);
            int rowAffected = preparedStatement.executeUpdate();
            System.out.println(rowAffected > 0 ? "Image inserted successfully..." :"Image not inserted!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}