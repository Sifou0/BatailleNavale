import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Serveur {

    public static void main(String[] test) {

        final ServerSocket serverSocket ;
        final Socket socket ;
        final BufferedReader in;
        final PrintWriter out;
        final Scanner sc=new Scanner(System.in);

        try {
            serverSocket = new ServerSocket(5000);
            socket = serverSocket.accept();
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader (new InputStreamReader (socket.getInputStream()));
            Thread send = new Thread(new Runnable() {
                String msg;
                @Override
                public void run() {
                    while(true){
                        msg = sc.nextLine();
                        out.println(msg);
                        out.flush();
                    }
                }
            });
            send.start();

            Thread receive = new Thread(new Runnable() {
                String msg ;
                @Override
                public void run() {
                    try {
                        msg = in.readLine();
                        //tant que le client est connecté
                        while(msg!=null){
                            System.out.println("Client : "+msg);
                            msg = in.readLine();
                        }
                        //sortir de la boucle si le client a déconecté
                        System.out.println("Client déconecté");
                        //fermer le flux et la session socket
                        out.close();
                        socket.close();
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            receive.start();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}