
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;

public class Client implements Runnable{
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public Client()
    { try {
        InetAddress host = InetAddress.getLocalHost();
         socket = new Socket(host.getHostName(), 8080);
       output = new ObjectOutputStream(socket.getOutputStream());
       input = new ObjectInputStream(socket.getInputStream());
    } catch(Exception e){e.printStackTrace();}
    }
    public void run()
    { try {
        while (true) {
            String message = (String)input.readObject();
            System.out.println(message);
        }
    } catch(Exception e){e.printStackTrace();}
    }

    public void nameValidation( Scanner scanner) {
        try {
            System.out.println("Enter your name!");
            String name = scanner.next();
            output.writeObject(name);
            String messageFromServer = (String) input.readObject();
            while (messageFromServer.equals("exist")) {
                System.out.println("This name already exist, enter another!");
                name = scanner.next();
                output.writeObject(name);
                messageFromServer = (String) input.readObject();
            }
            System.out.println(messageFromServer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void messageWriting(Scanner scanner) {
        try {
            System.out.println("Enter your messages.(write exit for break)");
            new Thread(this).start();
            String message;
            do {
                message = scanner.nextLine();
                output.writeObject(message);
            } while (!message.equals("exit"));
            System.out.println("You left chat!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] a) throws IOException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);
        Client client = new Client();

        client.nameValidation( scan);
        client.messageWriting(scan);

    }


}

