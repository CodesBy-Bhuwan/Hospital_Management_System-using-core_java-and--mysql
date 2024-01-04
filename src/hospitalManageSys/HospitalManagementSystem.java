package hospitalManageSys;

import javax.print.Doc;
import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hospital_data";
    private static final String username ="root";
    private static final String password = "";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url,username,password);
            Patient patient = new Patient(connection,scanner);
            Doctor doctor = new Doctor(connection);
            while (true){
                System.out.println("HOSPITAL MANNAGEMENT SYSTEM");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patient");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter your choices");
                int choice = scanner.nextInt();

                switch (choice){
                    case 1:
                        patient.addPatient();
                    case 2:
                        patient.viewPatient();
                    case 3:
                        doctor.viewDoctor();
                    case 4:
                        bookAppointment(patient, doctor, connection, scanner);
                    case 5:
                        return;
                    default:
                        System.out.println("Invalid");

                }

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner){
        System.out.println("Enter Patient id:");
        int patientid = scanner.nextInt();
        System.out.println("Enter Doctor id");
        int doctorid = scanner.nextInt();
        System.out.println("Enter appointment date(YYYY-MM-DD)");
        String appointmentDate = scanner.next();
        if (patient.getPatientById(patientid)&&(doctor.getDoctorById(doctorid))){
            if (checkDoctorAvailability(doctorid, appointmentDate, connection)){
                String appointmentQuery= "insert into appointments(patinet_id, doctor_id, appointment_date) vlaues (?,?,?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1, patientid);
                    preparedStatement.setInt(2, doctorid);
                    preparedStatement.setString(3,appointmentDate);
                    int rowsaffected = preparedStatement.executeUpdate();
                    if (rowsaffected>0){
                        System.out.println("Appointment Book");
                    }else {
                        System.out.println("Failed to book appointment");
                    }


                }catch (SQLException e){
                    e.printStackTrace();
                }
            }else {
                System.out.println("Doctor isn't available");
            }

        }else {
            System.out.println("Either doctor or patient doesn't exits");

        }

    }
    public static boolean checkDoctorAvailability(int doctorid, String appointmentDate, Connection connection){
        String query = "select count(*) from appointments where doctor_id = ? and appointment_date = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,doctorid);
            preparedStatement.setString(2,appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int count = resultSet.getInt(1);
                if (count == 0){
                    return true;

                }else{
                    return false;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
