package client;

import com.maruf.grpc.User;
import com.maruf.grpc.userGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

public class gRPCClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",9090).usePlaintext().build();
        userGrpc.userBlockingStub userStub = userGrpc.newBlockingStub(channel);
        Scanner scanner =new Scanner(System.in);
        while(true) {
            String s1 = scanner.nextLine();
            String s2 = scanner.nextLine();

            User.LoginRequest loginRequest = User.LoginRequest.newBuilder().setUserName(s1).setPassword(s2).build();
            User.APIResponse response = userStub.login(loginRequest);
            System.out.println(response.getResponseMessage());
        }
    }
}
