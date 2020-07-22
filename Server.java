
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    private static ServerSocket server;
    static Hashtable<String, Socket> names = new Hashtable<>();
    private Hashtable outputStreams = new Hashtable();

    public Server () throws IOException
    {
        listen(8080);
    }
    private void listen(int port) throws IOException
    {
        server = new ServerSocket(port);
        System.out.println("Listening on :" +port);
        while (true) {
            Socket socket = server.accept();
            System.out.println("Connecting from : "+socket);
            ObjectOutputStream output =new ObjectOutputStream(socket.getOutputStream());
            outputStreams.put(socket,output);
            Thread t = new ClientHandler(this,socket,output);
            t.start();

        }

    }


    public static synchronized boolean ifNameExist(String name,Socket socket) {
        if (names.containsKey(name)) return true;
        else {
            names.put(name, socket);
            return false;
        }
    }

    Enumeration getOutputStreams()
    {
        return outputStreams.elements();
    }

    void sendToAll(String message,String name)
    {
        synchronized (outputStreams)
        {
            for(Enumeration e=getOutputStreams();e.hasMoreElements(); )
            {
                ObjectOutputStream out=(ObjectOutputStream)e.nextElement();
                try{
                    out.writeObject(name + ":" + message);
                } catch(IOException ie){System.out.println(ie);}
            }
        }
    }

    void removeConnection(Socket socket)
    {
        synchronized (outputStreams) {
            System.out.println("Removing connection to " + socket);

            outputStreams.remove(socket);
            try{
                socket.close();
            }catch(IOException ie){ie.printStackTrace();}
        }
    }




    public static void main(String[] a) throws  Exception{
        new Server();
    }
}

class ClientHandler extends Thread {
    private Socket socket;
    private Server server;
    private ObjectOutputStream output;
    private ObjectInputStream input;


    public ClientHandler(Server ss,Socket s,ObjectOutputStream out) {
        this.server = ss;
        this.socket=s;
        try {
            output = out;
            input=new ObjectInputStream(s.getInputStream());
        }catch(Exception e){e.printStackTrace();}
    }

    public String clientNameValidation() {
        String name = null;
        try {
            name = (String) input.readObject();
            while (Server.ifNameExist(name,socket)) {
                output.writeObject("exist");
                name = (String) input.readObject();
            }
            System.out.println(name + " joined chat! Hello:)");
            output.writeObject(name + "joined chat! Hello:)");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    public void clientMessagePrinting(String name) {
        boolean done = false;
        while (!done) {
            try {
                String message = (String) input.readObject();
                if (!message.equals("exit")) {
                    System.out.println(name + ":" + message);
                    server.sendToAll(message,name);
                }
                else {
                    System.out.println(name + " left chat.");
                }
                done = message.equals("exit");

            } catch (Exception e) {
                done = true;
                e.printStackTrace();
            }
        }
        if (name != null) {
            Server.names.remove(name);
        }
    }


    @Override
    public void run() {
        try {
            String name = clientNameValidation();
            clientMessagePrinting(name);
            input.close();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            server.removeConnection(socket);
        }


    }
}




