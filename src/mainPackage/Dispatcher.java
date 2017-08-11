package mainPackage;

// First class with main void

import serverPart.Server;

public class Dispatcher {

    public static void main(String[] args) {
        new Thread(new Server()).start();
    }
}
