package jeugo;

import java.awt.Point;

public class Case {
	
	//Etat de la case
	private int state;
	//Position de lacase
	private Point pos;
	//Presence ou non d'une marque dessus (sert pour le parcour de territoire)
	private boolean marque;
	//Nombre de libertée de la case
	private int liberte;
		
	//Constructeur avec en parametre l'etat (noir, blanc, vide) et la position
	public Case(int s,int x,int y) {
		state = s;
		pos = new Point(x,y);
	}

	//Setter pour changer state
	public void set_pierre(int v){
		state = v;
	}	
	
	//Setter pour ajouter une marque
	public void set_marque(){
		marque = true;
	}
	
	//Setter pour enlever une marque
	public void unset_marque(){
		marque = false;
	}
	
	//Setter pour changer les libertée
	public void set_liberte(int l){
		liberte = l;
	}
	
	//Getter pour state
	public int get_pierre(){
		return state;
	}
	
	//Getter pour la marque
	public boolean get_marque(){
		return marque;
	}
	
	//Getter pour les libertée
	public int get_liberte(){
		return liberte;
	}
	
	//Getter pour la position
	public Point get_position(){
		return pos;
	}
	
}
