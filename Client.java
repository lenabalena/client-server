package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] a) throws  IOException,ClassNotFoundException
    { InetAddress host=InetAddress.getLocalHost();
        Socket socket;
        ObjectOutputStream output;
        ObjectInputStream input;
        socket=new Socket(host.getHostName(),8080);

        input=new ObjectInputStream(socket.getInputStream());
        String message=(String)input.readObject();
        System.out.println(message+"World");

        output=new ObjectOutputStream(socket.getOutputStream());
        output.writeObject("World");

        output.close();
        input.close();
        socket.close();
    }


}

