import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");


//        String url = "http://localhost:3306/hospital_data";
        String url = "jdbc:mysql://localhost:3306/hospital_data";

        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password)){
        System.out.println("connected to database");
//        This technique below is used to check where the connection is made.
            System.out.println(connection);
    } catch (SQLException e){
        System.err.println("connection failed" + e.getMessage());
    }
    }

}

