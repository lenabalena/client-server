
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;

public class Client {
    public static void main(String[] a) throws IOException, ClassNotFoundException {
        InetAddress host = InetAddress.getLocalHost();
        Socket socket;
        ObjectOutputStream output;
        ObjectInputStream input;
        Scanner scan=new Scanner(System.in);
        socket = new Socket(host.getHostName(), 8080);



        output = new ObjectOutputStream(socket.getOutputStream());
        String name;
        System.out.println("Enter your name!");
        name=scan.next();
        output.writeObject(name);
        input = new ObjectInputStream(socket.getInputStream());
        String message = (String) input.readObject();
        System.out.println(name + message);

        input.close();
        output.close();
        socket.close();
    }


}

