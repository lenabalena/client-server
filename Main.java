package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private static ServerSocket server;
    private static int port=8080;

    public static void main(String[] args)throws IOException,ClassNotFoundException{
            server=new ServerSocket(port);
            Socket socket  = server.accept();
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            output.writeObject("Hello");

            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            String message;
            do{message=(String)input.readObject();}
            while(!message.equals("World"));
            input.close();
            output.close();
            socket.close();

        }
        //server.close();
    }

