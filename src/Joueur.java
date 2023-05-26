import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Joueur {

//    1 Porte-avions (5 cases)
//    1 Croiseur (4 cases)
//    2 Contre-torpilleurs (3 cases)
//    1 Torpilleur (2 cases)


    private Socket clientSocket;
    private BufferedReader input;
    private PrintWriter output;
    private Piece[] pieces;
    private String name;
    private boolean aPerdu;



    private String[][] plateau; // 0 = case vide, 1 = piece place, 2 = case touché
    private String[][] plateauAdverse;

    public Joueur(Socket clientSocket, String name) {
        try {
            this.clientSocket = clientSocket;
            this.output = new PrintWriter(clientSocket.getOutputStream());
            this.input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.name = name;
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.name = name;
        this.aPerdu = false;
        pieces = new Piece[5];
        plateau = new String[10][10];
        plateauAdverse = new String[10][10];
        for(int i = 0 ; i < plateau.length ; i++) {
            for(int j = 0 ; j < plateau.length ; j++) {
                plateau[i][j] = "˜";
                plateauAdverse[i][j] = "˜";
            }
        }
        pieces[0] = new Piece(5);
        pieces[1] = new Piece(4);
        pieces[2] = new Piece(3);
        pieces[3] = new Piece(3);
        pieces[4] = new Piece(2);
        placerPieces();
    }

    public void showPlateau() {
        System.out.println("Mon plateau :");
        for(int i = 0 ; i < 10 ; i++) {
            for(int j = 0 ; j < 10 ; j++) {
                System.out.print(plateau[i][j] + "  ");
            }
            System.out.print("\n");
        }
        System.out.println("Plateau adverse deviné :");
        for(int i = 0 ; i < 10 ; i++) {
            for(int j = 0 ; j < 10 ; j++) {
                System.out.print(plateauAdverse[i][j] + "  ");
            }
            System.out.print("\n");
        }
    }

    public boolean checkAPerdu() {
        for(Piece p : pieces) {
            if(!p.isCoule()) return false;
        }
        return true;
    }

    // 1 = horizontal, 0 = vertical
    void placerPieceSurPlateau(int x, int y, int orientation, int taille) {
        if(orientation == 0) {
            for(int i = 0; i < taille ; i++) {
                plateau[x+i][y] = "B";
            }
        }
        else if(orientation == 1) {
            for(int i = 0; i < taille ; i++) {
                plateau[x][y+i] = "B";
            }
        }
    }

    public String[][] getPlateauAdverse() {
        return plateauAdverse;
    }

    public String getName() {
        return name;
    }

    public Piece[] getPieces() {
        return pieces;
    }

    public void setPlateauAdverse(String[][] plateauAdverse) {
        this.plateauAdverse = plateauAdverse;
    }

    public String checkIfHit(int x, int y) {
        if(Objects.equals(plateau[x][y], "B")) {
            plateau[x][y] = "T";
            for(Piece p : pieces) {
                for(int[] pos : p.getAllPos() ) {
                    if(pos[0] == x && pos[1] == y) {
                        p.addHit();
                        if (p.isCoule()) {
                            return "COULE " + x + " " + y;
                        }
                        else return "TOUCHE " + x + " " + y;
                    }
                }
            }
        }
        return "RIEN " + x + " " + y;
    }

    void retourAdverse(String msg) {
        String[] retour = msg.split(" ");
        switch (retour[0]) {
            case "TOUCHE":
                plateauAdverse[Integer.parseInt(retour[1])][Integer.parseInt(retour[2])] = "T";
                break;
            case "COULE":
                plateauAdverse[Integer.parseInt(retour[1])][Integer.parseInt(retour[2])] = "C";
                break;
            case "RIEN":
                plateauAdverse[Integer.parseInt(retour[1])][Integer.parseInt(retour[2])] = "N";
                break;
        }
    }

    public String[][] pieceCoule(Piece p, String[][] in) {
        for(int[] pos : p.getAllPos()) {
            plateau[pos[0]][pos[1]] = "C";
            in[pos[0]][pos[1]] = "C";
        }
        return in;
    }

    boolean inBoard(int x, int y) {
        return x <= 9 && x >= 0 && y >= 0 && y <= 9;
    }

    void placerPieces() {
        System.out.println("C'est au tour de " + name + " de placer ses pieces");
        int x = -1 ,y = -1 , orientation = -1;
        boolean place;
        Scanner sc = new Scanner(System.in);
        for(Piece p : pieces) {
            while ( !inBoard(x,y) ) {
                System.out.println("Ou souhaitez vous placer votre piece de taille " + p.getTaille() + " ?");
                System.out.println("Position X :");
                x = sc.nextInt();
                System.out.println("Position Y :");
                y = sc.nextInt();
                System.out.println("Orientation : 0 pour vertical et 1 pour horizontal");
                orientation = sc.nextInt();
                if(Objects.equals(plateau[x][y], "˜") && ((orientation == 0 && y+ p.getTaille() < 10) || (orientation == 1 && x+ p.getTaille() < 10))){
                    placerPieceSurPlateau(x,y,orientation,p.getTaille());
                }
                else {
                    System.out.println("Cette case est deja prise par une autre piece");
                    x = -1; y = -1 ; orientation = -1;
                }
            }
            p.setAll(x,y,orientation);
            x = -1; y = -1 ; orientation = -1;
            System.out.println("Vous avez placé votre pièce " + p.toString() ) ;

        }
        showPlateau();
    }

    int[] getInput(String input) {
        String[] s = input.split(" ");
        return new int[]{Integer.parseInt(s[0]),Integer.parseInt(s[1])};
    }

    Thread envoyer = new Thread(new Runnable() {
        String msg;
        Scanner sc = new Scanner(System.in);
        @Override
        public void run() {
//            output.println("Vous jouez contre " + name);
//            output.flush();
            while(true){
                msg = sc.nextLine();
                output.println(msg);
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
                    if(msg.startsWith("TOUCHE") || msg.startsWith("COULE") || msg.startsWith("RIEN")) {
                        retourAdverse(msg);

                    }
                    else {
                        int[] curr = getInput(msg);
                        output.println(checkIfHit(curr[0],curr[1]));
                        output.flush();
                    }
                    showPlateau();
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
            Joueur joueur = new Joueur(socket,name);
            joueur.recevoir.start();
            joueur.envoyer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
