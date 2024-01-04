package hospitalManageSys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class Patient {
    private Connection connection;

    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner){

        this.connection = connection;
        this.scanner = scanner;

    }
    public void addPatient(){
        System.out.print("Enter Patient Name");
        String patient_name = scanner.next();
        System.out.print("Enter Patient Gender");
        String patient_gender = scanner.next();
        System.out.print("Enter Patient DoB");
        String patientDoB = scanner.next();
        System.out.print("Enter Patient Symptoms");
        String patientSymp = scanner.next();

        try {
            String query = "Insert into patient(patient_name, patient_gender,patientDoB, patientSymp) VALUES(?,?,?,?)";
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            preparedStatement.setString(1, patient_name);
            preparedStatement.setString(2, patient_gender);
            preparedStatement.setString(3, patientDoB);
            preparedStatement.setString(4, patientSymp);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows>0){
                System.out.println("Patient Info Added Successfully");
            }else {
                System.out.println("Patient Addition Failed");
            }


        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    public void viewPatient(){
        String query = "select * from patient";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients= ");
            System.out.println("---------------------------------------------------------------");
            System.out.println(" Patient id  Name           Gender         DoB         Symptoms");
            while (resultSet.next()){
                int id = resultSet.getInt("Id");
                String patient_name =resultSet.getString("Name");
                String patient_gender =resultSet.getString("Gender");
                String patientDoB =resultSet.getString("DoB");
                String Symptoms = resultSet.getString("Symptoms");
                System.out.printf(" %-12s %-20s %-10s %-12s %-10s\n", id,patient_name,patient_gender,patientDoB,Symptoms);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public boolean getPatientById(int id) {
        String query = "select * from patient where id = ?";
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
