package client;

import com.maruf.grpc.User;
import com.maruf.grpc.userGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

public class gRPCClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
        userGrpc.userBlockingStub userStub = userGrpc.newBlockingStub(channel);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Select:");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 1) {
                String s1 = scanner.nextLine();
                String s2 = scanner.nextLine();
                User.RegisterRequest registerRequest = User.RegisterRequest.newBuilder().setUserName(s1).setPassword(s2).build();
                User.APIResponse response = userStub.register(registerRequest);
                System.out.println(response.getResponseMessage());
            } else if (choice == 2) {
                String s3 = scanner.nextLine();
                String s4 = scanner.nextLine();
                User.LoginRequest loginRequest = User.LoginRequest.newBuilder().setUserName(s3).setPassword(s4).build();
                User.APIResponse response = userStub.login(loginRequest);
                System.out.println(response.getResponseMessage());
            } else if (choice == 3) break;
            else {
                System.out.println("some error occurred. Please choose between 1, 2 or 3.");
            }
        }


    }
}

