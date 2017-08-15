package serverPart;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server implements Runnable {

    private static Integer clientCount = 0;
    private static ServerSocket serverSocket;

    // List of clients:
    private LinkedList<Socket> clients = new LinkedList<>();

    @Override
    public void run() {
        println("Server was started");

        // Initialization some objects:
        initialization();

        try {
            while(true) {
                try {
                    // Adding a new client:
                    clients.add(serverSocket.accept());
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

    private void initialization() {
        try {
            serverSocket = new ServerSocket(7000, 0, InetAddress.getByName("localhost"));
        } catch (IOException e) {
            System.err.println("Probably something wrong with port 6000. Maybe it is already used in another application.");
            System.err.println("This application will be switched off.");
            System.exit(-1);
        }
    }

    private void println(Object o) {
        System.out.println(o);
    }
}
