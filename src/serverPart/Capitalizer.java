package serverPart;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

// Class that makes all stuff with clients (except adding, of course)

public class Capitalizer extends Thread {

    private Socket socket;
    private int clientNumber;

    private ObjectOutputStream output;
    private ObjectInputStream input;

    Capitalizer(Socket socket, int clientNumber) {
        this.socket = socket;
        this.clientNumber = clientNumber;

        setPriority(NORM_PRIORITY);


    }

    @Override
    public void run() {
        try {
            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();

            sendData("Hello, client # " + clientNumber + "\\n");

            while(true) {
                String message = (String) input.readObject();
                System.out.println("Client # " + clientNumber + " sent you: " + message);
                sendData("Your message: " + message);
            }
        } catch (IOException e) {
            System.err.println("Something wrong with input or output stream in client # " + clientNumber);
            System.exit(-1);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            close();
        }
    }

    private void close() {
        try {
            output.close();
            input.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendData(Object o) {
        try {
            output.flush();
            output.writeObject(o);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
