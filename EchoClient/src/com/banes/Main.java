package com.banes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try(Socket socket = new Socket("localhost", 5000)) {
            //this will cause the socket to timeout before it gets a response because the response takes 10000ms to return.
//            socket.setSoTimeout(10000);
            BufferedReader echoes = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter stringToEcho = new PrintWriter(socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);
            String echoString;
            String response;

            do {
                System.out.println("Enter string to be echoed: ");
                echoString = scanner.nextLine();

                stringToEcho.println(echoString);
                if (!echoString.equals("exit")) {
                    response = echoes.readLine();
                    System.out.println(response);
                }
            } while(!echoString.equals("exit"));
        } catch (SocketTimeoutException e) {
            System.out.println("The socket timed out.");
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
