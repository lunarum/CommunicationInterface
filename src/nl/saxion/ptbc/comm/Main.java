package nl.saxion.ptbc.comm;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.System.exit;

public class Main {

    public static void main(String[] args) {
        try {
            var serverSocket = new ServerSocket(ChatServerHandler.SERVER_PORT, 0, InetAddress.getByName(ChatServerHandler.SERVER_ADDRESS));
            System.out.println("Server 2.0 is listening on " + serverSocket.toString());
            System.out.println("Heartbeat is " + (ChatServerHandler.HEARTBEAT_ON ? "on" : "off"));
            while (true) {
                // Wait for an incoming client-connection request (blocking).
                Socket socket = serverSocket.accept();
                // Start a handler thread for each client
                try {
                    var chatHandler = new ChatServerHandler(socket);
                    var clientThread = new Thread(chatHandler);
                    clientThread.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            exit(ExitErrors.ERROR_CREATE_SOCKET.getValue());
        }
    }
}
