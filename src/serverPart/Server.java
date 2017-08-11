package serverPart;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server implements Runnable {

    private static Integer clientCount = 0;
    private static ServerSocket serverSocket;

    // List of clients:
    private static LinkedList<Socket> clients = new LinkedList<>();

    @Override
    public void run() {
        println("Server was started");

        // Initialization some objects:
        initialization();

        Socket socket;

        try {
            while((socket = serverSocket.accept()) != null) {
                try {
                    // Adding a new client:
                    clients.add(socket);
                    new Capitalizer(clients.peekLast(), ++clientCount).start();
                    println("Client # " + clientCount + " was connected");
                } finally {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) { }
    }

    private static void initialization() {
        try {
            serverSocket = new ServerSocket(6000);
        } catch (IOException e) {
            System.err.println("Probably something wrong with port 6000. Maybe it is already used in another application.");
            System.err.println("This application will be switched off.");
            System.exit(-1);
        }
    }

    private static void println(Object o) {
        System.out.println(o);
    }
}
