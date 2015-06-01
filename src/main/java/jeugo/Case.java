package jeugo;

import java.awt.Point;

public class Case {
	private int state;
	private Point pos;
	private boolean marque;
	private int liberte;
		
	public Case(int s,int x,int y) {
		state = s;
		pos = new Point(x,y);
	}

	//Setter
	public void set_pierre(int v){
		state = v;
	}	
	
	public void set_marque(){
		marque = true;
	}
	
	public void unset_marque(){
		marque = false;
	}
	
	public void set_liberte(int l){
		liberte = l;
	}
	
	//Getter
	public int get_pierre(){
		return state;
	}
	
	public boolean get_marque(){
		return marque;
	}
	
	public int get_liberte(){
		return liberte;
	}
	
	public Point get_position(){
		return pos;
	}
	
}
