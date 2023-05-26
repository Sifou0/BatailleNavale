import java.util.Scanner;

public class Jeu implements Runnable{

    private Joueur[] joueurs;

//    public Jeu(String name1, String name2) {
//        this.joueurs = new Joueur[]{new Joueur(name1), new Joueur(name2)};
//    }

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


    @Override
    public void run() {
        boolean gameRunning = true;
        int x,y;
        Scanner sc = new Scanner(System.in);
        while (isGameRunning()) {
            for(int i = 0 ; i < 2 ; i++) {
                System.out.println("C'est au tour de " + getJoueurs()[i].getName() );
                System.out.println("Position X Y à attaquer:");

                //getJoueurs()[i].setPlateauAdverse(getJoueurs()[(i == 0 ? 0 : 1)].checkIfHit(,,getJoueurs()[i].getPlateauAdverse()));
               // getJoueurs()[(i == 0 ? 1 : 0)].showPlateau();
            }
        }
    }


}
