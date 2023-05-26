import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {

    private ServerSocket serverSocket;
    private int nbJoueur;

    public Serveur(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.nbJoueur = 0;
    }


    public void start() {
        try {
            serverSocket = new ServerSocket(5555);
            System.out.println("Serveur en marche...");
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        Serveur serveur = new Serveur(serverSocket);
        serveur.start();
    }
}