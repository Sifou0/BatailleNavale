import java.util.Arrays;
import java.util.Scanner;

public class Joueur {

//    1 Porte-avions (5 cases)
//    1 Croiseur (4 cases)
//    2 Contre-torpilleurs (3 cases)
//    1 Torpilleur (2 cases)

    public String getName() {
        return name;
    }



    private Piece[] pieces;
    private String name;
    private boolean aPerdu;
    public Piece[] getPieces() {
        return pieces;
    }

    private int[][] plateau; // 0 = case vide, 1 = piece place, 2 = case touché

    public Joueur(String name) {
        this.name = name;
        this.aPerdu = false;
        pieces = new Piece[5];
        plateau = new int[10][10];
        for(int i = 0 ; i < plateau.length ; i++) {
            for(int j = 0 ; j < plateau.length ; j++) {
                plateau[i][j] = 0;
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
        for(int i = 0 ; i < 10 ; i++) {
            for(int j = 0 ; j < 10 ; j++) {
                System.out.print(plateau[i][j] + "  ");
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
                plateau[x+i][y] = 1;
            }
        }
        else if(orientation == 1) {
            for(int i = 0; i < taille ; i++) {
                plateau[x][y+i] = 1;
            }
        }
    }

    void checkIfHit(int x, int y) {
        if(plateau[x][y] == 1) {
            plateau[x][y]++;
            for(Piece p : pieces) {
                for(int[] pos : p.getAllPos() ) {
                    if(pos[0] == x && pos[1] == y) {
                        p.addHit();
                        return;
                    }
                }
            }
        }
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
                if(plateau[x][y] == 0 && ((orientation == 0 && y+ p.getTaille() < 10) || (orientation == 1 && x+ p.getTaille() < 10))){
                    placerPieceSurPlateau(x,y,orientation,p.getTaille());
                }
                else {
                    System.out.println("Cette case est deja prise par une autre piece");
                    x = -1; y = -1 ; orientation = -1;
                }
            }
            p.setOrientation(orientation); p.setPosX(x); p.setPosY(y);
            x = -1; y = -1 ; orientation = -1;
            System.out.println("Vous avez placé votre pièce " + p.toString() ) ;
        }
        showPlateau();
    }
}
