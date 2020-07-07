package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

 public class Server {
    private static ServerSocket server;
    private static final int port=8080;
    public static void main(String[] a) //throws IOException,ClassNotFoundException
    { try{
        server=new ServerSocket(port);
        Socket socket  = server.accept();
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        String message;
        message=(String)input.readObject();
        System.out.println("Hello "+message);
        ObjectOutputStream output =new ObjectOutputStream(socket.getOutputStream());
        output.writeObject("Hello ");
        input.close();
        output.close();
        socket.close();
        server.close();}
        catch(Exception e){e.printStackTrace();}

    }


    }

