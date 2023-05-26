import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> allClients = new ArrayList<>();
    private Socket clientSocket;
    private BufferedReader input;
    private PrintWriter output;

    public ClientHandler(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            this.output = new PrintWriter(clientSocket.getOutputStream());
            this.input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            allClients.add(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        String msg;
        while (true) {
            try {
                msg = input.readLine();
                broadcastATousLesUsers(msg);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void broadcastATousLesUsers(String msg) throws IOException {
        int i = allClients.indexOf(this);
        if(i%2 == 0) {
            allClients.get(i).output.println(msg);
            allClients.get(i).output.flush();
            allClients.get(i+1).output.println(msg);
            allClients.get(i+1).output.flush();
        }
        else {
            allClients.get(i).output.println(msg);
            allClients.get(i).output.flush();
            allClients.get(i-1).output.println(msg);
            allClients.get(i-1).output.flush();
        }
//        for (ClientHandler client : allClients) {
//            if (!client.clientSocket.equals(this.clientSocket)){
//                client.output.println(msg);
//                client.output.flush();
//            }
//        }
    }


}