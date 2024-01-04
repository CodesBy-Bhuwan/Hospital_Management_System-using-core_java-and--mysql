package hospitalManageSys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    private Connection connection;


    public Doctor(Connection connection){

        this.connection = connection;


    }

    public void viewDoctor(){
        String query = "select * from doctor";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Docotrs= ");
            System.out.println("---------------------------------------------------------------");
            System.out.println(" doctor id  Name              DoJ         Specaialist");
            while (resultSet.next()){
                int id = resultSet.getInt("Id");
                String doctor_name =resultSet.getString("Name");
                String DoJ =resultSet.getString("DoJ");
                String Specialist = resultSet.getString("Specialist");
                System.out.printf(" %-12s %-20s %-10s %-12s\n",id,doctor_name,DoJ,Specialist);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public boolean getDoctorById(int id) {
        String query = "select * from doctor where id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;

            } else {
                return false;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
