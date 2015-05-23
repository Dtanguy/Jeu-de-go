package jeugo;

import java.awt.Point;

public class Case {
	private int state;
	private Point pos;
		
	public Case(int s,int x,int y) {
		state = s;
		pos = new Point(x,y);
	}

	//Setter
	public void set_pierre(int v){
		state = v;
	}	
	
	//Getter
	public int get_pierre(){
		return state;
	}
	
}
