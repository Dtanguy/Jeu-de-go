package jeugo;
	
public class Joueur {
	
	private int couleur;
	private boolean type;
	public int point;

	//Couleur des pions	
	private int vide = 0;
	private int blanc = 1;
	private int noir = 2;
	
	public Joueur(int col,boolean typ){	
		couleur = col;
		type = typ;
		point = 0;
	}

	//Getteur
	public int get_color(){
		return couleur;
	}
	
	public boolean get_type(){
		return type;
	}

}
