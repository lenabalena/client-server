package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static ServerSocket server;
    private static final int port = 8080;

    public static void main(String[] a) //throws IOException,ClassNotFoundException
    {
        Socket socket;
        try {
            server = new ServerSocket(port);
            try {
                while (true) {
                    socket = server.accept();
                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                    output.writeObject("Hello ");
                    ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                    String message;
                    message = (String) input.readObject();
                    System.out.println("Hello " + message);
                    input.close();
                    output.close();
                    socket.close();
                }
            } catch (Exception e) {
                server.close();
                e.printStackTrace();
            }

        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }


}




