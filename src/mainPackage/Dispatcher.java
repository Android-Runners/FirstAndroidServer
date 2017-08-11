package mainPackage;

// First class with main void

import serverPart.Server;

public class Dispatcher {

    public static void main(String[] args) {
        // Creating a server in a new thread:
        new Thread(new Server()).start();
    }
}
