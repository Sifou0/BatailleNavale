import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class Serveur extends Thread {

    private Socket clientSocket;
    private String name;
    private HashMap<String,Socket> allClients;
    private InputStream input;
    private OutputStream output;

    public Serveur(Socket clientSocket, String name, HashMap<String,Socket> allClients) {
        this.clientSocket = clientSocket;
        this.name = name;
        this.allClients = allClients;
        try {
            input = clientSocket.getInputStream();
            output = clientSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            while (true) {
                String out = input.readLine();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}