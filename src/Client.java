import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    private Socket clientSocket;
    private String name;
    private BufferedReader input;
    private PrintWriter output;

    public Client(Socket clientSocket, String name) {

        try {
            this.clientSocket = clientSocket;
            this.output = new PrintWriter(clientSocket.getOutputStream());
            this.input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.name = name;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Thread envoyer = new Thread(new Runnable() {
        String msg;
        Scanner sc = new Scanner(System.in);
        @Override
        public void run() {
            while(true){
                msg = sc.nextLine();
                output.println(name + " : " +msg);
                output.flush();
            }
        }
    });


    Thread recevoir = new Thread(new Runnable() {
        String msg;
        @Override
        public void run() {
            try {
                msg = input.readLine();
                while(msg != null){
                    System.out.println(msg);
                    msg = input.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });



    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Entrez un pseudo :");
            String name = scanner.nextLine();
            Socket socket = new Socket("localhost",5555);
            System.out.println(socket);
            Client client = new Client(socket,name);
            client.recevoir.start();
            client.envoyer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}