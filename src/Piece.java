public class Piece {
    private final int taille;
    private int posX;
    private int posY;
    private int orientation; // 1 = horizontal, 0 = vertical
    private boolean coule;
    private int nbOfHit;
    private int[][] allPos;

    public int[][] getAllPos() {
        return allPos;
    }

    public boolean isCoule() {
        return coule;
    }

    public Piece(int taille) {
        this.taille = taille;
        this.coule = false;
        nbOfHit = 0;
        allPos = new int[taille][2];
    }

    public int getTaille() {
        return taille;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }


    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public void setAll(int x, int y, int orientation) {
        this.posX = x;
        this.posY = y;
        this.orientation = orientation;
        for(int i = 0 ; i < taille ; i++) {
            if(orientation == 0) allPos[i] = new int[]{x+i,y};
            else allPos[i] = new int[]{x,y+i};
        }
    }

    public void addHit() {
        nbOfHit++;
        coule = nbOfHit >= taille;
        System.out.println("Le bateau : " + this.toString()  + " a coulé !");
    }



    @Override
    public String toString() {
        return "Piece{" +
                "taille=" + taille +
                ", posX=" + posX +
                ", posY=" + posY +
                ", orientation=" + (orientation == 1 ? "horizontal" : "vertical") +
                '}';
    }


}
