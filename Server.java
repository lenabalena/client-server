
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static ServerSocket server;
    private static final int port = 8080;

    public static void main(String[] a) {
        Socket socket;
        try {
            server = new ServerSocket(port);
            try {
                while (true) {
                    socket = server.accept();
                    Thread t = new ClientHandler(socket);
                    t.start();
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

class ClientHandler extends Thread {

    final Socket s;

    public ClientHandler(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
            try {
                ObjectInputStream input = new ObjectInputStream(s.getInputStream());
                String name;
                name = (String) input.readObject();
                System.out.println( name + " joined chat! Hello:)");
                ObjectOutputStream output = new ObjectOutputStream(s.getOutputStream());
                output.writeObject(" joined chat! Hello:)");
                input.close();
                output.close();
                s.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


    }
}




