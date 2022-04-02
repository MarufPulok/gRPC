package services;

import com.maruf.grpc.User;
import com.maruf.grpc.userGrpc;
import io.grpc.stub.StreamObserver;

import java.sql.*;

public class UserService extends userGrpc.userImplBase {
    @Override
    public void register(User.RegisterRequest request, StreamObserver<User.APIResponse> responseObserver) {
        String userName = request.getUserName();
        String password = request.getPassword();

        User.APIResponse.Builder response = User.APIResponse.newBuilder();

        try {
            System.out.println("show");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grpc-db","root", "15052000");
            System.out.println("show2");
            String query = "INSERT INTO users(userName,password) "
                    + "VALUES(\"" + userName+"\",\""+password+"\")";
            System.out.println("show3");
            Statement statement = connection.createStatement();
            System.out.println("show4");
            statement.executeUpdate(query);

            response.setResponseCode(201).setResponseMessage("registration successful!");

        }
        catch (SQLException e) {
//            e.printStackTrace();

            response.setResponseCode(401).setResponseMessage("User already exists.");
        }catch (Exception e) {
//            e.printStackTrace();
            System.out.println("invalid");
        }

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();


    }

    @Override
    public void login(User.LoginRequest request, StreamObserver<User.APIResponse> responseObserver) {
        System.out.println("Inside Login");
        String url = "jdbc:mysql://localhost:3306/grpc-db";
        String user = "root";
        String pass = "15052000";



        String userName = request.getUserName();
        String password = request.getPassword();

        User.APIResponse.Builder response = User.APIResponse.newBuilder();
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from users where userName = \""+userName+"\"");
            boolean b = false;

            while (resultSet.next()) {
                String n = resultSet.getString("userName");
                String p = resultSet.getString("password");
                b = true;

                if (userName.equals(n) && password.equals(p)) {
                    response.setResponseCode(0).setResponseMessage("Login Successful");
                } else {
                    response.setResponseCode(100).setResponseMessage("Invalid Username or password");
                }
            }

            if (!b) {
                response.setResponseCode(100).setResponseMessage("INVALID");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }




//        if (userName.equals(password)) {
//            //return success
//
//            response.setResponseCode(0).setResponseMessage("SUCCESS");
//        }
//        else {
//            //return failure
//            response.setResponseCode(100).setResponseMessage("INVALID");
//        }

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void logout(User.Empty request, StreamObserver<User.APIResponse> responseObserver) {

    }
}
