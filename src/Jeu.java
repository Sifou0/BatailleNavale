import java.util.Scanner;

public class Jeu {
    private Joueur[] joueurs;

    public Jeu() {
        this.joueurs = new Joueur[]{new Joueur("Joueur 1"), new Joueur("Joueur 2")};
    }

    public Joueur[] getJoueurs() {
        return joueurs;
    }

    private boolean isGameRunning() {
        int countPlayers = 0;
        for(Joueur j : joueurs) {
            if(!j.checkAPerdu()) countPlayers++;
        }
        return countPlayers > 1;
    }

    public static void main(String[] args) {
        Jeu j = new Jeu();
        boolean gameRunning = true;
        int x,y;
        Scanner sc = new Scanner(System.in);
        while (j.isGameRunning()) {
            for(int i = 0 ; i < 2 ; i++) {
                System.out.println("C'est au tour de " + j.getJoueurs()[(i == 0 ? 1 : 0)].getName() );
                System.out.println("Position X à attaquer:");
                x = sc.nextInt();
                System.out.println("Position Y à attaquer:");
                y = sc.nextInt();
                j.getJoueurs()[(i == 0 ? 1 : 0)].checkIfHit(x,y);
            }
        }
    }
}
