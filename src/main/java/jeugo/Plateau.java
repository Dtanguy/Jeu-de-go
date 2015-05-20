package jeugo;

import java.awt.Point;

public class Plateau {
	
	//Attribut
	public Point size;
	// Plateau contenant des pi�ces
	public Piece[][] plateau;
	
	//Constructeur
	public Plateau(int x, int y) {
		size = new Point(x,y);
	}
	
	//Initialisation
	public void init(){
		for (int i=0; i < size.x; i++) {
			for (int j=0; j < size.y; j++) {
				// On cr�e une pi�ce � chaque fois
				plateau[i][j]=new Piece();
			}
		}	
	}
}
