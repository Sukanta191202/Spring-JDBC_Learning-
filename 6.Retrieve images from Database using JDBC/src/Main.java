import javax.xml.transform.Result;
import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {

        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String userName = "root";
        String password = "Dua@2002";
        String folder_path = "E:\\1. Programe's\\1.Spring Boot\\1.Spring_jdbc\\1.Learning JDBC\\1.Indian Programmer\\Image\\";
        String sqlQuery = "SELECT image_data FROM image_table WHERE image_id = (?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver load successfully...");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try {
            Connection connection = DriverManager.getConnection(url,userName,password);
            System.out.println("Connection Established successfully...");
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1,4);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                byte[] image_data = resultSet.getBytes("image_data");
                String image_path = folder_path + "extractedImage3.jpg";

                OutputStream outputStream = new FileOutputStream(image_path);
                outputStream.write(image_data);
                outputStream.close();

                System.out.println("Image Extracted Successfully..");
            }else{
                System.out.println("Image not found!!!");
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}