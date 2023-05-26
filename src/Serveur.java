import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] test) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(5555);
            System.out.println("Serveur en marche...");
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println(socket);
                new Thread(new ClientHandler(socket)).start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}