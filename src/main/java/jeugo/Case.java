package jeugo;

public class Case {
	private int state;
		
	public Case(int s) {
		state = s;
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
