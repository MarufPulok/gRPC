package services;

import com.maruf.grpc.User;
import com.maruf.grpc.userGrpc;
import io.grpc.stub.StreamObserver;

import java.sql.*;

public class UserService extends userGrpc.userImplBase {

    @Override
    public void login(User.LoginRequest request, StreamObserver<User.APIResponse> responseObserver) {
        System.out.println("Inside Login");
        String url = "jdbc:mysql://localhost:3306/usersdb";
        String user = "root";
        String pass = "15052000";



        String userName = request.getUserName();
        String password = request.getPassword();

        User.APIResponse.Builder response = User.APIResponse.newBuilder();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from users where userName = \""+userName+"\"");
            boolean b = false;

            while (resultSet.next()) {
                String n = resultSet.getString("userName");
                String p = resultSet.getString("password");
                b = true;

                if (userName.equals(n) && password.equals(p)) {
                    response.setResponseCode(0).setResponseMessage("SUCCESS");
                } else {
                    response.setResponseCode(100).setResponseMessage("INVALID");
                }
            }

            if (!b) {
                response.setResponseCode(100).setResponseMessage("INVALID");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
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