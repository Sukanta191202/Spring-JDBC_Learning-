import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class ImageCRUD {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "Dua@2002";
    private static final String FOLDER_PATH = "E:\\1. Programe's\\1.Spring Boot\\1.Spring_jdbc\\1.Learning JDBC\\1.Indian Programmer\\Image\\"; // Ensure this path exists

    public static void main(String[] args) throws ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        Class.forName("com.mysql.cj.jdbc.Driver");

        while (true) {
            System.out.println("\n===== Image CRUD Operations =====");
            System.out.println("1. Insert Image");
            System.out.println("2. Retrieve Image");
            System.out.println("3. Update Image");
            System.out.println("4. Delete Image");
            System.out.println("5. View All Records");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> insertImage(sc);
                case 2 -> retrieveImage(sc);
                case 3 -> updateImage(sc);
                case 4 -> deleteImage(sc);
                case 5 -> viewAllImages();
                case 6 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private static void insertImage(Scanner sc) {
        System.out.print("Enter image path to insert: ");
        String imagePath = sc.nextLine();

        String sql = "INSERT INTO image_table(image_data) VALUES (?)";

        try (
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                FileInputStream fis = new FileInputStream(imagePath);
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            byte[] data = fis.readAllBytes();
            ps.setBytes(1, data);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "✅ Image inserted successfully." : "❌ Insertion failed.");

        } catch (Exception e) {
            System.out.println("⚠️ Error inserting image: " + e.getMessage());
        }
    }

    private static void retrieveImage(Scanner sc) {
        System.out.print("Enter image ID to retrieve: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter output file name (e.g. image.jpg): ");
        String fileName = sc.nextLine();

        String sql = "SELECT image_data FROM image_table WHERE image_id = ?";

        try (
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                byte[] imageData = rs.getBytes("image_data");
                try (FileOutputStream fos = new FileOutputStream(FOLDER_PATH + fileName)) {
                    fos.write(imageData);
                    System.out.println("✅ Image retrieved and saved to: " + FOLDER_PATH + fileName);
                }
            } else {
                System.out.println("❌ Image with ID " + id + " not found.");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Error retrieving image: " + e.getMessage());
        }
    }

    private static void updateImage(Scanner sc) {
        System.out.print("Enter image ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter new image path: ");
        String newImagePath = sc.nextLine();

        String sql = "UPDATE image_table SET image_data = ? WHERE image_id = ?";

        try (
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                FileInputStream fis = new FileInputStream(newImagePath);
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            byte[] newData = fis.readAllBytes();
            ps.setBytes(1, newData);
            ps.setInt(2, id);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "✅ Image updated successfully." : "❌ Update failed or ID not found.");

        } catch (Exception e) {
            System.out.println("⚠️ Error updating image: " + e.getMessage());
        }
    }

    private static void deleteImage(Scanner sc) {
        System.out.print("Enter image ID to delete: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM image_table WHERE image_id = ?";

        try (
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "✅ Image deleted successfully." : "❌ Delete failed or ID not found.");

        } catch (Exception e) {
            System.out.println("⚠️ Error deleting image: " + e.getMessage());
        }
    }

    private static void viewAllImages() {
        String sql = "SELECT image_id, upload_date FROM image_table";

        try (
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            System.out.println("\n--- All Image Records ---");
            boolean found = false;
            while (rs.next()) {
                int id = rs.getInt("image_id");
                Timestamp date = rs.getTimestamp("upload_date");
                System.out.println("Image ID: " + id + " | Uploaded At: " + date);
                found = true;
            }
            if (!found) {
                System.out.println("No image records found.");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Error viewing records: " + e.getMessage());
        }
    }
}
